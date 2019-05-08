package pl.coderstrust.invoices.service;

import java.time.LocalDate;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderstrust.invoices.database.Database;
import pl.coderstrust.invoices.database.DatabaseOperationException;
import pl.coderstrust.invoices.database.InvoiceBookException;
import pl.coderstrust.invoices.model.Invoice;

@Service
public class InvoiceBook {

  @Autowired
  private Database database;

  public InvoiceBook(Database database) {
    this.database = database;
  }

  public Collection<Invoice> getAllInvoices() throws InvoiceBookException {
    try {
      return database.getAllInvoices();
    } catch (DatabaseOperationException exception) {
      throw new InvoiceBookException("Invoice Base doesn't exist", exception);
    }
  }

  public Invoice saveInvoice(Invoice invoice) throws InvoiceBookException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null");
    }
    try {
      return database.saveInvoice(invoice);
    } catch (DatabaseOperationException exception) {
      throw new InvoiceBookException("Couldn't save Invoice", exception);
    }
  }

  public Invoice removeInvoice(Long id) throws InvoiceBookException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    try {
      return database.removeInvoice(id);
    } catch (DatabaseOperationException exception) {
      throw new InvoiceBookException("No invoice with given id to remove", exception);
    }
  }

  public Invoice getInvoice(Long id) throws InvoiceBookException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    try {
      return database.getInvoice(id);
    } catch (DatabaseOperationException exception) {
      throw new InvoiceBookException("No invoice with id", exception);
    }
  }

  public Collection<Invoice> getInvoicesBetweenDates(LocalDate fromDate, LocalDate toDate)
      throws InvoiceBookException {
    try {
      return database.getInvoicesBetweenDates(fromDate, toDate);
    } catch (DatabaseOperationException exception) {
      throw new InvoiceBookException("Couldn't get invoices", exception);
    }
  }
}
