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
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    if (fileHelper.isInvoiceID(id)) {
      List<Invoice> invoiceList;
      invoiceList = (ArrayList<Invoice>) fileHelper.readAll();
      for (int i = 0; i < invoiceList.size(); i++) {
        if (invoiceList.get(i).getId().equals(id)) {
          return invoiceList.get(i);
        }
      }
    } else {
      throw new IllegalArgumentException("Invoice with given id not exists");
    }
    return null;
  }

  @Override
  public Collection<Invoice> getInvoicesInBetweenDates(LocalDate fromDate, LocalDate toDate)
      throws DatabaseOperationException {
    if (fromDate.isBefore(toDate)) {
      List<Invoice> invoicesList = (List<Invoice>) converter
          .stringListToInvoicesList((List<String>) fileHelper.readAllLines());
      List<Invoice> betweenDatesInvoicesList = new ArrayList<>();
      fromDate = fromDate.minusDays(1);
      toDate = toDate.plusDays(1);
      for (int i = 0; i < invoicesList.size(); i++) {
        if (invoicesList.get(i).getSellDate().isAfter(fromDate) && invoicesList.get(i).getSellDate()
            .isBefore(toDate)) {
          betweenDatesInvoicesList.add(invoicesList.get(i));
        }
      }
      return invoicesList;
    } else {
      throw new IllegalArgumentException("fromDate must be earlier date than toDate");
    }
  }

  @Override
  public Invoice removeInvoice(Object id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Invoice cannot be null");
    }
    if (isInvoiceID(id)) {
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

  public boolean isInvoiceID(Object id) {
    Invoice invoice;
    List<String> list = (ArrayList<String>) fileHelper.readAllLines();
    for (int i = 0; i < list.size(); i++) {
      invoice = converter.stringToInvoice(list.get(i));
      if (invoice != null && invoice.getId().equals(id)) {
        return true;
      }
    }
    return false;
  }
}
