package pl.coderstrust.invoices.database.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import pl.coderstrust.invoices.database.Database;
import pl.coderstrust.invoices.database.DatabaseOperationException;
import pl.coderstrust.invoices.model.Invoice;

public class InFileDatabase implements Database {

  private static final String invoicesPath = "F:\\projects\\project-10-tomasz-wiktor\\src\\main\\resources\\invoices.txt";
  private static final String invoicesIdPath = "F:\\projects\\project-10-tomasz-wiktor\\src\\main\\resources\\id.txt";

  private Converter converter;
  private FileHelper fileHelper;
  private IdFileHelper idFileHelper;

  public InFileDatabase() {
    this.converter = new Converter(new ObjectMapper());
    this.fileHelper = new FileHelper(invoicesPath);
    this.idFileHelper = new IdFileHelper(invoicesIdPath);
  }

  @Override
  public Invoice saveInvoice(Invoice invoice) throws DatabaseOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null");
    }
    if (fileHelper.isInvoiceID(invoice.getId())) {
      //zrobic update zamiast exception
    } else {
      //dodanie nowej faktury
      Integer maxId = Integer.parseInt(idFileHelper.getMaxId());
      Invoice copiedInvoice = invoice.deepCopy(invoice);
      if (copiedInvoice.getId() == null) {
        maxId++;
        copiedInvoice.setId(maxId);
      }
      String id = String.valueOf(copiedInvoice.getId());
      fileHelper.addInvoice(converter.objectToString(copiedInvoice));
      idFileHelper.setNewId(id);
      return copiedInvoice;
    }
    return null;
  }

  @Override
  public Collection<Invoice> getAllInvoices() throws DatabaseOperationException {
    return null;
  }

  @Override
  public Invoice getInvoice(Object id) throws DatabaseOperationException {
    return null;
  }

  @Override
  public Collection<Invoice> getInvoicesInBetweenDates(LocalDate fromDate, LocalDate toDate)
      throws DatabaseOperationException {
    return null;
  }

  @Override
  public Invoice removeInvoice(Object id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Invoice cannot be null");
    }
    if (fileHelper.isInvoiceID(id)) {
      Invoice invoice = new Invoice();
      List<String> stringList = (ArrayList<String>) fileHelper.readAllLines();
      List<Invoice> invoicesList = (ArrayList<Invoice>) converter
          .stringListToInvoicesList(stringList);
      for (int i = 0; i < invoicesList.size(); i++) {
        if (invoicesList.get(i).getId().equals(id)) {
          invoice = invoicesList.get(i);
          invoicesList.remove(i);
        }
        //jezeli usuwam ostatnia fakture to zmniejsz id
      }
      stringList.clear();
      stringList = (List<String>) converter.invoicesListToStringList(invoicesList);
      fileHelper.rewriteFile(stringList);
      return invoice;
    } else {
      throw new IllegalArgumentException("Invoice with given id not exists");
    }
  }
}
