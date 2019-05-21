package pl.coderstrust.invoices.database.hibernate;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import pl.coderstrust.invoices.database.Database;
import pl.coderstrust.invoices.database.DatabaseOperationException;
import pl.coderstrust.invoices.model.Invoice;
import pl.coderstrust.invoices.model.hibernate.ConverterHibernate;
import pl.coderstrust.invoices.model.hibernate.InvoiceHibernate;

@Repository
@Primary
@ConditionalOnProperty(name = "database.type", havingValue = "hibernate", matchIfMissing = true)
public class HibernateDatabase implements Database {

  private InvoiceRepository invoiceRepository;
  private ConverterHibernate converterHibernate;

  @Autowired
  public HibernateDatabase(
      InvoiceRepository invoiceRepository, ConverterHibernate converterHibernate) {
    this.invoiceRepository = invoiceRepository;
    this.converterHibernate = converterHibernate;
  }

  @Override
  public Invoice saveInvoice(Invoice invoice) throws DatabaseOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null");
    }
    InvoiceHibernate invoiceHibernate = new InvoiceHibernate(invoice);
    InvoiceHibernate savedInvoice = invoiceRepository.save(invoiceHibernate);
    return converterHibernate.getInvoiceObject(savedInvoice);
  }

  @Override
  public Collection<Invoice> getAllInvoices() throws DatabaseOperationException {
    Iterable<InvoiceHibernate> invoicesHibernate = invoiceRepository.findAll();
    return StreamSupport.stream(invoicesHibernate.spliterator(), false)
        .map(converterHibernate::getInvoiceObject)
        .collect(Collectors.toList());
  }

  @Override
  public Invoice getInvoice(Object id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Id must be number Long type");
    }
    InvoiceHibernate invoiceHibernate = invoiceRepository.findById((Long) id).get();
    return converterHibernate.getInvoiceObject(invoiceHibernate);
  }

  @Override
  public Collection<Invoice> getInvoicesBetweenDates(LocalDate fromDate, LocalDate toDate)
      throws DatabaseOperationException {
    Collection<InvoiceHibernate> invoiceHibernateList = invoiceRepository
        .findByIssueDateBetween(fromDate, toDate);
    return invoiceHibernateList.stream().map(converterHibernate::getInvoiceObject)
        .collect(Collectors.toList());
  }

  @Override
  public Invoice removeInvoice(Object id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Id must be number Long type");
    }
    Invoice tempInvoice = getInvoice(id);
    if (tempInvoice == null) {
      throw new DatabaseOperationException("Invoice cannot be null");
    }
    invoiceRepository.deleteById((Long) id);
    return tempInvoice;
  }
}
