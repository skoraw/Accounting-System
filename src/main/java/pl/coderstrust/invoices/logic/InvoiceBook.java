package pl.coderstrust.invoices.logic;

import java.time.LocalDate;
import java.util.Collection;
import pl.coderstrust.invoices.database.Database;
import pl.coderstrust.invoices.database.DatabaseOperationException;
import pl.coderstrust.invoices.database.InvoiceBookException;
import pl.coderstrust.invoices.model.Invoice;

public class InvoiceBook {

  private Database database;

  public InvoiceBook(Database database) {
    this.database = database;
  }

  public Collection<Invoice> getAllInvoices() throws DatabaseOperationException {
    return database.getAllInvoices();
  }

  public Invoice saveInvoice(Invoice invoice) throws InvoiceBookException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null");
    }
    try {
      return database.saveInvoice(invoice);
    } catch (DatabaseOperationException e) {
      throw new InvoiceBookException("Couldn't save Invoice", e);
    }
  }

  public Invoice removeInvoice(Object id) throws InvoiceBookException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    try {
      return database.removeInvoice(id);
    } catch (DatabaseOperationException e) {
      throw new InvoiceBookException("No invoice with given id to remove", e);
    }
  }

  public Invoice getInvoice(Object id) throws InvoiceBookException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    try {
      return database.getInvoice(id);
    } catch (DatabaseOperationException e) {
      throw new InvoiceBookException("No invoice with id", e);
    }
  }

  public Collection<Invoice> getInvoicesBetweenDates(LocalDate fromDate, LocalDate toDate)
      throws InvoiceBookException {
    try {
      return database.getInvoicesInBetweenDates(fromDate, toDate);
    } catch (DatabaseOperationException e) {
      throw new InvoiceBookException("Couldn't get invoices", e);
    }
  }
}
