package pl.coderstrust.invoices.database.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import pl.coderstrust.invoices.database.Database;
import pl.coderstrust.invoices.model.Invoice;

public class InFileDatabase implements Database {

  private Converter converter;
  private FileHelper fileHelper;
  private IdGenerator idGenerator;

  InFileDatabase(Configuration configuration) throws IOException {
    this.converter = new Converter(new ObjectMapper());
    this.fileHelper = new FileHelper(configuration.getInvoicesFilePath());
    FileHelper idFileHelper = new FileHelper(configuration.getInvoicesIdFilePath());
    this.idGenerator = new IdGenerator(idFileHelper);
  }

  @Override
  public Invoice saveInvoice(Invoice invoice) throws IOException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null");
    }
    Invoice copiedInvoice = invoice.deepCopy(invoice);
    if (!(copiedInvoice.getId() instanceof Integer)) {
      copiedInvoice.setId(null);
    }
    if (isInvoiceId(invoice.getId())) {
      return updateInvoice(copiedInvoice);
    } else {
      return addInvoice(copiedInvoice);
    }
  }

  private Invoice addInvoice(Invoice invoice) throws IOException {
    Integer id = idGenerator.getNextId();
    invoice.setId(id);
    fileHelper.addLine(converter.objectToString(invoice));
    idGenerator.setNewId(id);
    return invoice;
  }

  private Invoice updateInvoice(Invoice invoice) throws IOException {
    ArrayList<String> list = (ArrayList<String>) fileHelper.readAllLines();
    ArrayList<Invoice> invoicesList = (ArrayList<Invoice>) converter
        .stringListToInvoicesList(list);
    list.clear();
    Integer id = (Integer) invoice.getId();
    for (Invoice value : invoicesList) {
      if (value.getId().equals(id)) {
        list.add(converter.objectToString(invoice));
      } else {
        list.add(converter.objectToString(value));
      }
    }
    fileHelper.rewriteFile(list);
    return invoice;
  }

  @Override
  public Collection<Invoice> getAllInvoices()
      throws FileNotFoundException {
    return converter.stringListToInvoicesList((List<String>) fileHelper.readAllLines());
  }

  @Override
  public Invoice getInvoice(Object id) throws FileNotFoundException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    if (isInvoiceId(id)) {
      List<Invoice> invoiceList = (ArrayList<Invoice>) converter.stringListToInvoicesList(
          (List<String>) fileHelper.readAllLines());
      for (Invoice invoice : invoiceList) {
        if (invoice.getId().equals(id)) {
          return invoice;
        }
      }
    } else {
      throw new IllegalArgumentException("Invoice with given id not exists");
    }
    return null;
  }

  @Override
  public Collection<Invoice> getInvoicesInBetweenDates(LocalDate fromDate, LocalDate toDate)
      throws FileNotFoundException {
    if (fromDate.isBefore(toDate)) {
      List<Invoice> invoicesList = (List<Invoice>) converter
          .stringListToInvoicesList((List<String>) fileHelper.readAllLines());
      List<Invoice> betweenDatesInvoicesList = new ArrayList<>();
      fromDate = fromDate.minusDays(1);
      toDate = toDate.plusDays(1);
      for (Invoice invoice : invoicesList) {
        if (invoice.getSellDate().isAfter(fromDate) && invoice.getSellDate()
            .isBefore(toDate)) {
          betweenDatesInvoicesList.add(invoice);
        }
      }
      return betweenDatesInvoicesList;
    } else {
      throw new IllegalArgumentException("fromDate must be earlier date than toDate");
    }
  }

  @Override
  public Invoice removeInvoice(Object id) throws IOException {
    if (id == null) {
      throw new IllegalArgumentException("Invoice cannot be null");
    }
    if (isInvoiceId(id)) {
      Invoice invoice = new Invoice();
      List<String> stringList = (ArrayList<String>) fileHelper.readAllLines();
      List<Invoice> invoicesList = (ArrayList<Invoice>) converter
          .stringListToInvoicesList(stringList);
      for (int i = 0; i < invoicesList.size(); i++) {
        if (invoicesList.get(i).getId().equals(id)) {
          invoice = invoicesList.get(i);
          invoicesList.remove(i);
        }
      }
      stringList.clear();
      stringList = (List<String>) converter.invoicesListToStringList(invoicesList);
      fileHelper.rewriteFile(stringList);
      return invoice;
    } else {
      throw new IllegalArgumentException("Invoice with given id not exists");
    }
  }

  private boolean isInvoiceId(Object id) throws FileNotFoundException {
    Invoice invoice;
    List<String> strings = (List<String>) fileHelper.readAllLines();
    for (String string : strings) {
      invoice = converter.stringToInvoice(string);
      if (invoice.getId().equals(id)) {
        return true;
      }
    }
    return false;
  }
}
