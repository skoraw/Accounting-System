package pl.coderstrust.invoices.database.mongo;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
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

  private InvocieRepositoryMongo invocieRepositoryMongo;
  private ConverterMongo converterMongo;

  public MongoDatabase(
      InvocieRepositoryMongo invocieRepositoryMongo,
      ConverterMongo converterMongo) {
    this.invocieRepositoryMongo = invocieRepositoryMongo;
    this.converterMongo = converterMongo;
  }

  @Override
  public Invoice saveInvoice(Invoice invoice) throws DatabaseOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null");
    }
    InvoiceMongo invoiceMongo = new InvoiceMongo(invoice);
    InvoiceMongo savedInvoice = invocieRepositoryMongo.save(invoiceMongo);
    return converterMongo.getInvoice(savedInvoice);
  }

  @Override
  public Collection<Invoice> getAllInvoices() throws DatabaseOperationException {
    Iterable<InvoiceMongo> invoicesMongo = invocieRepositoryMongo.findAll();
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
    InvoiceMongo invoiceMongo = invocieRepositoryMongo.findById((String) id).get();
    return converterMongo.getInvoice(invoiceMongo);
  }

  @Override
  public Collection<Invoice> getInvoicesBetweenDates(LocalDate fromDate, LocalDate toDate)
      throws DatabaseOperationException {
    Collection<InvoiceMongo> invoiceMongoList = invocieRepositoryMongo
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
      throw new DatabaseOperationException("Invoice cannot be null");
    }
    invocieRepositoryMongo.deleteById((String) id);
    return tempInvoice;
  }
}
