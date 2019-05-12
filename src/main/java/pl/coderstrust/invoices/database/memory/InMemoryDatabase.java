package pl.coderstrust.invoices.database.memory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import pl.coderstrust.invoices.database.Database;
import pl.coderstrust.invoices.database.DatabaseOperationException;
import pl.coderstrust.invoices.model.Invoice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryDatabase implements Database {

  private static Logger logger = LoggerFactory.getLogger(InMemoryDatabase.class);

  private final Map<Long, Invoice> invoices = new HashMap<>();
  private Long lastId = 0L;

  @Override
  public Invoice saveInvoice(Invoice invoice) {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null");
    }
    if (invoice.getId() != null && !(invoice.getId() instanceof Long)) {
      logger.warn("Use long or int type ID");
      throw new IllegalArgumentException("Id must be number Long type");
    }
    Invoice cloneInvoice = new Invoice(invoice);
    if (invoice.getId() == null || !invoices.containsKey(invoice.getId())) {
      lastId += 1;
      Long id = lastId;
      cloneInvoice.setId(id);
      invoices.put(id, cloneInvoice);
      logger.info("Adding new invoice");
      return new Invoice(cloneInvoice);
    }
    logger.info("Updating new invoice");
    invoices.put((Long) invoice.getId(), invoice);
    return new Invoice(invoice);
  }

  @Override
  public Collection<Invoice> getAllInvoices() throws DatabaseOperationException {
    Map<Object, Invoice> copyOfInvoices = new HashMap<>(invoices);
    return copyOfInvoices.values();
  }

  @Override
  public Invoice getInvoice(Object id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    if (!(id instanceof Long)) {
      logger.warn("Use long or int type ID");
      throw new IllegalArgumentException("Id must be number Long type");
    }
    if (!invoices.containsKey(id)) {
      throw new DatabaseOperationException(String.format("Invoice for id=[%d] is not exist", id));
    }
    return new Invoice(invoices.get(id));
  }

  @Override
  public Collection<Invoice> getInvoicesBetweenDates(LocalDate fromDate, LocalDate toDate)
      throws DatabaseOperationException {
    Collection<Invoice> invoicesByDate = new ArrayList<>();
    if (fromDate == null && toDate == null) {
      logger.warn("No dates was specified");
      throw new IllegalArgumentException("Date can't be null");
    }
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
    if (!(id instanceof Long)) {
      logger.warn("Use long or int type ID");
      throw new IllegalArgumentException("Id must be number Long type");
    }
    if (invoices.isEmpty()) {
      logger.warn("Invoice list is empty");
      throw new DatabaseOperationException("List of invoices is empty");
    }
    Invoice removedInvoice = invoices.get(id);
    invoices.remove(id);
    return removedInvoice;
  }
}
