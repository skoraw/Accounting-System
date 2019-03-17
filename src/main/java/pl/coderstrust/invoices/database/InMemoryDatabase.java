package pl.coderstrust.invoices.database;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import pl.coderstrust.invoices.model.Invoice;

public class InMemoryDatabase implements Database {

  private final List<Invoice> invoices = new ArrayList<>();

  @Override
  public Invoice saveInvoice(Invoice invoice) throws DatabaseOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null");
    }
    invoices.add(invoice);
    return invoice;
  }

  @Override
  public Collection<Invoice> getAllInvoices() throws DatabaseOperationException {
    return invoices;
  }

  @Override
  public Invoice getInvoice(Object id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    for (Invoice invoice : invoices) {
      if (id.equals(invoice.getId())) {
        return invoice;
      }
    }
    throw new DatabaseOperationException("No invoice with id");
  }

  @Override
  public Collection<Invoice> getInvoicesInBetweenDates(LocalDate fromDate, LocalDate toDate)
      throws DatabaseOperationException {
    Collection<Invoice> invoicesByDate = new ArrayList<>();
    for (Invoice invoice : invoices) {
      if (fromDate.isAfter(invoice.getSellDate()) && toDate.isBefore(invoice.getSellDate())) {
        invoicesByDate.add(invoice);
      }
    }
    return invoicesByDate;
  }

  @Override
  public Invoice removeInvoice(Object id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    if (invoices.isEmpty()) {
      throw new DatabaseOperationException("List of invoices is empty");
    }
    invoices.remove(id);
    return getInvoice(id);
  }
}
