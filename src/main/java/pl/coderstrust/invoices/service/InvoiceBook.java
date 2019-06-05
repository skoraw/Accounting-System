package pl.coderstrust.invoices.service;

import java.time.LocalDate;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderstrust.invoices.database.Database;
import pl.coderstrust.invoices.database.DatabaseOperationException;
import pl.coderstrust.invoices.database.InvoiceBookException;
import pl.coderstrust.invoices.model.Invoice;

@Service
public class InvoiceBook {

  private static Logger logger = LoggerFactory.getLogger(InvoiceBook.class);

  @Autowired
  private Database database;

  public InvoiceBook(Database database) {
    this.database = database;
  }

  public Collection<Invoice> getAllInvoices() throws InvoiceBookException {
    try {
      logger.info("Getting all invoices in database");
      return database.getAllInvoices();
    } catch (DatabaseOperationException exception) {
      logger.warn("Invoice base doesn't exist");
      throw new InvoiceBookException("Invoice Base doesn't exist", exception);
    }
  }

  public Invoice saveInvoice(Invoice invoice) throws InvoiceBookException {
    if (invoice == null) {
      logger.error("No invoice was passed");
      throw new IllegalArgumentException("Invoice cannot be null");
    }
    try {
      return database.saveInvoice(invoice);
    } catch (DatabaseOperationException exception) {
      logger.warn("Couldn't save or update invoice");
      throw new InvoiceBookException("Couldn't save Invoice", exception);
    }
  }

  public Invoice removeInvoice(Object id) throws InvoiceBookException {
    if (id == null) {
      logger.error("ID cannot be null, use Long or Int number");
      throw new IllegalArgumentException("Id cannot be null");
    }
    try {
      logger.info("Invoice with id = {} removed", id);
      return database.removeInvoice(id);
    } catch (DatabaseOperationException exception) {
      logger.warn("No invoice to remove with id = {}", id);
      throw new InvoiceBookException("No invoice with given id to remove", exception);
    }
  }

  public Invoice getInvoice(Object id) throws InvoiceBookException {
    if (id == null) {
      logger.error("ID cannot be null, use Long or Int number");
      throw new IllegalArgumentException("Id cannot be null");
    }
    try {
      logger.info("Getting invoice");
      return database.getInvoice(id);
    } catch (DatabaseOperationException exception) {
      logger.warn("Invoice for id = {} does not exist", id);
      throw new InvoiceBookException("No invoice with id", exception);
    }
  }

  public Collection<Invoice> getInvoicesBetweenDates(LocalDate fromDate, LocalDate toDate)
      throws InvoiceBookException {
    try {
      logger.info("Returning list of invoice between dates {} and {}", fromDate, toDate);
      return database.getInvoicesBetweenDates(fromDate, toDate);
    } catch (DatabaseOperationException exception) {
      logger.error("Can't get invoice list between dates");
      throw new InvoiceBookException("Couldn't get invoices", exception);
    }
  }
}
