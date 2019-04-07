package pl.coderstrust.invoices.database;

import pl.coderstrust.invoices.model.Invoice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryDatabase implements Database {

  private final Map<Long, Invoice> invoices = new HashMap<>();

  @Override
  public Invoice saveInvoice(Invoice invoice) throws DatabaseOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null");
    }
    Invoice cloneInvoice = new Invoice(invoice);
    if (!(cloneInvoice.getId() instanceof Long)) {
      throw new IllegalArgumentException("Id must be number Long type");
    }
    invoices.put((Long) cloneInvoice.getId(), cloneInvoice);
    return cloneInvoice;
  }

  @Override
  public Collection<Invoice> getAllInvoices() throws DatabaseOperationException {
    Map<Long, Invoice> copyListOfInvoices = new HashMap<>(invoices);
    return copyListOfInvoices.values();
  }

  @Override
  public Invoice getInvoice(Object id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    if (!invoices.containsKey(id)) {
      throw new DatabaseOperationException("No invoice with id");
    }
    return new Invoice(invoices.get(id));
  }

  @Override
  public Collection<Invoice> getInvoicesBetweenDates(LocalDate fromDate, LocalDate toDate)
      throws DatabaseOperationException {
    Collection<Invoice> invoicesByDate = new ArrayList<>();
    for (Invoice invoice : invoices.values()) {
      if (invoice.getSellDate().isAfter(fromDate) && invoice.getSellDate().isBefore(toDate)) {
        invoicesByDate.add(new Invoice(invoice));
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
    return new Invoice(invoices.get(id));
  }
}
