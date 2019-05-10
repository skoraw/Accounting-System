package pl.coderstrust.invoices.database.hibernate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import pl.coderstrust.invoices.database.Database;
import pl.coderstrust.invoices.database.DatabaseOperationException;
import pl.coderstrust.invoices.model.Invoice;
import pl.coderstrust.invoices.model.Invoice.InvoiceBuilder;
import pl.coderstrust.invoices.model.hibernate.InvoiceHibernate;

@Repository
@Primary
@ConditionalOnProperty(name = "database.type", havingValue = "hibernate", matchIfMissing = true)
public class HibernateDatabase implements Database {

  private InvoiceRepository invoiceRepository;

  public HibernateDatabase(
      InvoiceRepository invoiceRepository) {
    this.invoiceRepository = invoiceRepository;
  }

  private Invoice getInvoiceObject(InvoiceHibernate invoiceHibernate) {
    return new InvoiceBuilder()
        .id(invoiceHibernate.getId())
        .number(invoiceHibernate.getNumber())
        .issueDate(invoiceHibernate.getIssueDate())
        .issuePlace(invoiceHibernate.getIssuePlace())
        .sellDate(invoiceHibernate.getSellDate())
        .seller(invoiceHibernate.getSeller())
        .buyer(invoiceHibernate.getBuyer())
        .entries(invoiceHibernate.getEntries())
        .build();
  }

  @Override
  public Invoice saveInvoice(Invoice invoice) throws DatabaseOperationException {
    InvoiceHibernate invoiceHibernate = new InvoiceHibernate(invoice);
    invoiceRepository.save(invoiceHibernate);
    return getInvoiceObject(invoiceHibernate);
  }

  @Override
  public Collection<Invoice> getAllInvoices() throws DatabaseOperationException {
    Iterable<InvoiceHibernate> invoicesHibernate = invoiceRepository.findAll();
    List<InvoiceHibernate> collect = StreamSupport.stream(invoicesHibernate.spliterator(), false)
        .collect(Collectors.toList());
    List<Invoice> invoices = new ArrayList<>();
    for (InvoiceHibernate invoiceHibernate : collect) {
      invoices.add(getInvoiceObject(invoiceHibernate));
    }
    return invoices;
  }

  @Override
  public Invoice getInvoice(Object id) throws DatabaseOperationException {
    InvoiceHibernate invoiceHibernate = invoiceRepository.findById((Long) id).get();
    return getInvoiceObject(invoiceHibernate);
  }

  @Override
  public Collection<Invoice> getInvoicesBetweenDates(LocalDate fromDate, LocalDate toDate)
      throws DatabaseOperationException {
    Collection<InvoiceHibernate> invoiceHibernateList = invoiceRepository
        .findByIssueDateBetween(fromDate, toDate);
    List<Invoice> invoices = new ArrayList<>();
    for (InvoiceHibernate invoiceHibernate : invoiceHibernateList) {
      invoices.add(getInvoiceObject(invoiceHibernate));
    }
    return invoices;
  }

  @Override
  public Invoice removeInvoice(Object id) throws DatabaseOperationException {
    Invoice tempInvoice = getInvoice(id);
    Invoice invoice = new InvoiceBuilder()
        .id(tempInvoice.getId())
        .number(tempInvoice.getNumber())
        .issueDate(tempInvoice.getIssueDate())
        .issuePlace(tempInvoice.getIssuePlace())
        .sellDate(tempInvoice.getSellDate())
        .seller(tempInvoice.getSeller())
        .buyer(tempInvoice.getBuyer())
        .entries(tempInvoice.getEntries())
        .build();
    invoiceRepository.deleteById((Long) id);
    return invoice;
  }
}
