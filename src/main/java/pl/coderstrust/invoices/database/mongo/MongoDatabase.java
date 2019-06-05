package pl.coderstrust.invoices.database.mongo;

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
import pl.coderstrust.invoices.model.mongo.ConverterMongo;
import pl.coderstrust.invoices.model.mongo.InvoiceMongo;

@Repository
@Primary
@ConditionalOnProperty(name = "database.type", havingValue = "mongo", matchIfMissing = true)
public class MongoDatabase implements Database {

  private InvoiceRepositoryMongo invoiceRepositoryMongo;
  private ConverterMongo converterMongo;

  @Autowired
  public MongoDatabase(
      InvoiceRepositoryMongo invoiceRepositoryMongo,
      ConverterMongo converterMongo) {
    this.invoiceRepositoryMongo = invoiceRepositoryMongo;
    this.converterMongo = converterMongo;
  }

  @Override
  public Invoice saveInvoice(Invoice invoice) throws DatabaseOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null");
    }
    InvoiceMongo invoiceMongo = new InvoiceMongo(invoice);
    InvoiceMongo savedInvoice = invoiceRepositoryMongo.save(invoiceMongo);
    return converterMongo.getInvoice(savedInvoice);
  }

  @Override
  public Collection<Invoice> getAllInvoices() throws DatabaseOperationException {
    Iterable<InvoiceMongo> invoicesMongo = invoiceRepositoryMongo.findAll();
    return StreamSupport.stream(invoicesMongo.spliterator(), false)
        .map(converterMongo::getInvoice)
        .collect(Collectors.toList());
  }

  @Override
  public Invoice getInvoice(Object id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    if (!(id instanceof String)) {
      throw new IllegalArgumentException("Id must be String type");
    }
    InvoiceMongo invoiceMongo = invoiceRepositoryMongo.findById((String) id).get();
    return converterMongo.getInvoice(invoiceMongo);
  }

  @Override
  public Collection<Invoice> getInvoicesBetweenDates(LocalDate fromDate, LocalDate toDate)
      throws DatabaseOperationException {
    Collection<InvoiceMongo> invoiceMongoList = invoiceRepositoryMongo
        .findByIssueDateBetween(fromDate, toDate);
    return invoiceMongoList.stream().map(converterMongo::getInvoice)
        .collect(Collectors.toList());
  }

  @Override
  public Invoice removeInvoice(Object id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    if (!(id instanceof String)) {
      throw new IllegalArgumentException("Id must be String type");
    }
    Invoice tempInvoice = getInvoice(id);
    if (tempInvoice == null) {
      throw new DatabaseOperationException(String.format("Invoice for id=[%s] is not exists.", id));
    }
    invoiceRepositoryMongo.deleteById((String) id);
    return tempInvoice;
  }
}
