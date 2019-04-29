package pl.coderstrust.invoices.database.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import pl.coderstrust.invoices.database.Database;
import pl.coderstrust.invoices.database.DatabaseOperationException;
import pl.coderstrust.invoices.model.Invoice;

public class InFileDatabase implements Database {

  private InvoiceConverter invoiceConverter;
  private FileHelper fileHelper;
  private IdGenerator idGenerator;

  InFileDatabase(FileHelper fileHelper, IdGenerator idGenerator,
      InvoiceConverter invoiceConverter) {
    this.fileHelper = fileHelper;
    this.idGenerator = idGenerator;
    this.invoiceConverter = invoiceConverter;
  }

  @Override
  public Invoice saveInvoice(Invoice invoice) throws DatabaseOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null");
    }
    if (!(invoice.getId() instanceof Integer)) {
      throw new IllegalArgumentException("Incorrect type of Invoice Id");
    }
    Invoice copiedInvoice = new Invoice(invoice);
    try {
      if (isInvoiceExist(copiedInvoice.getId())) {
        return updateInvoice(copiedInvoice);
      } else {
        return addInvoice(copiedInvoice);
      }
    } catch (IOException exception) {
      throw new DatabaseOperationException();
    }
  }

  private Invoice addInvoice(Invoice invoice) throws IOException {
    Integer id = idGenerator.getNextId();
    invoice.setId(id);
    fileHelper.addLine(invoiceConverter.objectToString(invoice));
    idGenerator.setNewId(id);
    return invoice;
  }

  private Invoice updateInvoice(Invoice invoice) throws IOException {
    ArrayList<String> list = (ArrayList<String>) fileHelper.readAllLines();
    ArrayList<Invoice> invoicesList = (ArrayList<Invoice>) invoiceConverter
        .stringListToInvoicesList(list);
    list.clear();
    Integer id = (Integer) invoice.getId();
    for (Invoice value : invoicesList) {
      if (value.getId().equals(id)) {
        list.add(invoiceConverter.objectToString(invoice));
      } else {
        list.add(invoiceConverter.objectToString(value));
      }
    }
    fileHelper.rewriteFile(list);
    return invoice;
  }

  @Override
  public Collection<Invoice> getAllInvoices()
      throws DatabaseOperationException {
    try {
      return invoiceConverter.stringListToInvoicesList((List<String>) fileHelper.readAllLines());
    } catch (FileNotFoundException exception) {
      throw new DatabaseOperationException();
    }
  }

  @Override
  public Invoice getInvoice(Object id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    if (!(id instanceof Integer)) {
      throw new IllegalArgumentException("Incorrect type of Invoice Id");
    }
    try {
      if (!isInvoiceExist(id)) {
        throw new IllegalArgumentException("Invoice with given id not exists");
      } else {
        List<Invoice> invoiceList = (ArrayList<Invoice>) invoiceConverter.stringListToInvoicesList(
            (List<String>) fileHelper.readAllLines());
        for (Invoice invoice : invoiceList) {
          if (invoice.getId().equals(id)) {
            return invoice;
          }
        }
      }
      return null;
    } catch (IOException exception) {
      throw new DatabaseOperationException();
    }
  }

  @Override
  public Collection<Invoice> getInvoicesBetweenDates(LocalDate fromDate, LocalDate toDate)
      throws DatabaseOperationException {
    if (!fromDate.isBefore(toDate)) {
      throw new IllegalArgumentException("fromDate must be earlier date than toDate");
    } else {
      try {
        List<Invoice> invoicesList = (List<Invoice>) invoiceConverter
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
      } catch (IOException exception) {
        throw new DatabaseOperationException();
      }
    }
  }

  @Override
  public Invoice removeInvoice(Object id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Invoice cannot be null");
    }
    if (!(id instanceof Integer)) {
      throw new IllegalArgumentException("Incorrect type of Invoice Id");
    }
    try {
      if (!isInvoiceExist(id)) {
        throw new IllegalArgumentException("Invoice with given id not exists");
      } else {
        Invoice invoice = null;
        List<String> stringList = (ArrayList<String>) fileHelper.readAllLines();
        List<Invoice> invoicesList = (ArrayList<Invoice>) invoiceConverter
            .stringListToInvoicesList(stringList);
        for (int i = 0; i < invoicesList.size(); i++) {
          if (invoicesList.get(i).getId().equals(id)) {
            invoice = invoicesList.get(i);
            invoicesList.remove(i);
          }
        }
        stringList.clear();
        stringList = (List<String>) invoiceConverter.invoicesListToStringList(invoicesList);
        fileHelper.rewriteFile(stringList);
        return invoice;
      }
    } catch (IOException exception) {
      throw new DatabaseOperationException();
    }
  }

  private boolean isInvoiceExist(Object id) throws IOException {
    Invoice invoice;
    List<String> strings = (List<String>) fileHelper.readAllLines();
    for (String string : strings) {
      invoice = invoiceConverter.stringToInvoice(string);
      if (invoice.getId().equals(id)) {
        return true;
      }
    }
    return false;
  }
}
