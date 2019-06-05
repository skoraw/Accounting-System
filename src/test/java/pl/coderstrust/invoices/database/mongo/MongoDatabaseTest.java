package pl.coderstrust.invoices.database.mongo;

import static java.util.Optional.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import pl.coderstrust.invoices.database.DatabaseOperationException;
import pl.coderstrust.invoices.model.Invoice;
import pl.coderstrust.invoices.model.mongo.ConverterMongo;
import pl.coderstrust.invoices.model.mongo.InvoiceMongo;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class MongoDatabaseTest {

  @Mock
  private InvoiceRepositoryMongo invoiceRepositoryMongo;
  @Mock
  private ConverterMongo converterMongo;

  @InjectMocks
  private MongoDatabase mongoDatabase;

  @Test
  public void shouldAddInvoiceToDatabase() throws DatabaseOperationException {
    InvoiceMongo invoiceMongo = InvoiceMongo.builder()
        .id("1as")
        .number("2019/1")
        .buyer(null)
        .issueDate(LocalDate.of(2019, 5, 11))
        .issuePlace("SomePlace")
        .sellDate(LocalDate.of(2019, 5, 11))
        .seller(null)
        .entries(null)
        .build();

    Invoice invoice = Invoice.builder()
        .id("1as")
        .number("2019/1")
        .buyer(null)
        .issueDate(LocalDate.of(2019, 5, 11))
        .issuePlace("SomePlace")
        .sellDate(LocalDate.of(2019, 5, 11))
        .seller(null)
        .entries(null)
        .build();

    Invoice invoice2nd = Invoice.builder()
        .id("1as")
        .number("2019/1")
        .buyer(null)
        .issueDate(LocalDate.of(2019, 5, 11))
        .issuePlace("Forest")
        .sellDate(LocalDate.of(2019, 5, 11))
        .seller(null)
        .entries(null)
        .build();

    when(invoiceRepositoryMongo.save(invoiceMongo)).thenReturn(invoiceMongo);
    when(converterMongo.getInvoice(invoiceMongo)).thenReturn(invoice2nd);

    Invoice addedInvoice = mongoDatabase.saveInvoice(invoice);

    assertEquals(addedInvoice, invoice2nd);
  }

  @Test
  public void getEmptyListOfInvoice() throws DatabaseOperationException {
    when(invoiceRepositoryMongo.findAll()).thenReturn(Arrays.asList());

    Collection<Invoice> result = mongoDatabase.getAllInvoices();

    assertEquals(0, result.size());
  }

  @Test
  public void getInvoicesBetweenDates() throws DatabaseOperationException {
    Invoice invoice = Invoice.builder()
        .id("1as")
        .number("2019/1")
        .buyer(null)
        .issueDate(LocalDate.of(2015, 5, 11))
        .issuePlace("SomePlace")
        .sellDate(LocalDate.of(2019, 5, 11))
        .seller(null)
        .entries(null)
        .build();

    InvoiceMongo invoiceMongo = new InvoiceMongo();

    when(invoiceRepositoryMongo
        .findByIssueDateBetween(LocalDate.of(2010, 6, 1), LocalDate.of(2017, 1, 1)))
        .thenReturn(Arrays.asList(invoiceMongo));
    when(converterMongo.getInvoice(invoiceMongo)).thenReturn(invoice);

    Collection<Invoice> expected = Arrays.asList(invoice);
    Collection<Invoice> result = mongoDatabase
        .getInvoicesBetweenDates(LocalDate.of(2010, 6, 1), LocalDate.of(2017, 1, 1));

    assertEquals(expected, result);
  }

  @Test
  public void shouldGetInvoiceFromDatabaseById() throws DatabaseOperationException {
    InvoiceMongo invoiceMongo = InvoiceMongo.builder()
        .id("1as")
        .number("2019/1")
        .buyer(null)
        .issueDate(LocalDate.of(2019, 5, 11))
        .issuePlace("SomePlace")
        .sellDate(LocalDate.of(2019, 5, 11))
        .seller(null)
        .entries(null)
        .build();

    Invoice invoice = Invoice.builder()
        .id("1as")
        .number("2019/1")
        .buyer(null)
        .issueDate(LocalDate.of(2015, 5, 11))
        .issuePlace("SomePlace")
        .sellDate(LocalDate.of(2019, 5, 11))
        .seller(null)
        .entries(null)
        .build();

    mongoDatabase.saveInvoice(invoice);
    invoiceRepositoryMongo.save(invoiceMongo);

    when(invoiceRepositoryMongo.findById((String) invoice.getId()))
        .thenReturn(ofNullable(invoiceMongo));
    when(converterMongo.getInvoice(invoiceMongo)).thenReturn(invoice);

    Invoice result = mongoDatabase.getInvoice("1as");

    assertEquals(invoice, result);
  }

  @Test
  public void shouldRemoveInvoiceFromDB() throws DatabaseOperationException {
    InvoiceMongo invoice = InvoiceMongo.builder()
        .id("1as")
        .number("2019/1")
        .buyer(null)
        .issueDate(LocalDate.of(2019, 5, 11))
        .issuePlace("SomePlace")
        .sellDate(LocalDate.of(2019, 5, 11))
        .seller(null)
        .entries(null)
        .build();

    invoiceRepositoryMongo.save(invoice);
    invoiceRepositoryMongo.deleteById("1as");

    boolean expected = invoiceRepositoryMongo.existsById("1as");

    assertFalse(expected);
  }

  @Test
  void shouldThrowExceptionWhenNotExistingIdIsPassedToGetInvoiceMethod() {
    assertThrows(NoSuchElementException.class, () -> mongoDatabase.getInvoice("SomeID"));
  }

  @Test
  void shouldThrowExceptionWhenTryGetInvoiceWithUnsupportedId() {
    Long id = 3L;
    assertThrows(IllegalArgumentException.class, () -> mongoDatabase.getInvoice(id));
  }

  @Test
  void shouldThrowExceptionWhenGetNotExistingInvoice() {
    assertThrows(NoSuchElementException.class, () -> mongoDatabase.getInvoice("SomeID"));
  }

  @Test
  void shouldThrowExceptionWhenNullIsPassedToGetInvoiceMethod() {
    assertThrows(IllegalArgumentException.class, () -> mongoDatabase.getInvoice(null));
  }
}
