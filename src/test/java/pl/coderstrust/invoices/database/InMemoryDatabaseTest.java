package pl.coderstrust.invoices.database;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Test;
import pl.coderstrust.invoices.model.Invoice;

public class InMemoryDatabaseTest {

  @Test
  public void shouldSaveInvoiceInList() throws DatabaseOperationException {
    Database database = new InMemoryDatabase();
    Invoice invoiceToAdd = new Invoice(2L, "2/10/2018", null, null, null, null, null, null);

    Invoice addedInvoice = database.saveInvoice(invoiceToAdd);

    assertEquals(invoiceToAdd.getNumber(), addedInvoice.getNumber());
    assertNotNull(addedInvoice.getId());
  }

  @Test
  public void shouldReturnCollectionOfInvoices() throws DatabaseOperationException {
    Database database = new InMemoryDatabase();
    Invoice invoice = new Invoice(1L, "2/10/2018", null, null, null, null, null, null);
    database.saveInvoice(invoice);
    Collection<Invoice> expectedInvoiceList = new ArrayList<>();
    expectedInvoiceList.add(invoice);

    Collection<Invoice> resultInvoiceList = database.getAllInvoices();

    assertThat(resultInvoiceList, contains(expectedInvoiceList));
    //hamcrest matcher
  }

  @Test
  public void shouldGetInvoiceFromMemoryDatabase() throws DatabaseOperationException {
    Database database = new InMemoryDatabase();
    Invoice invoice = new Invoice(1L, "2/10/2018", null, null, null, null, null, null);
    Invoice invoice1 = database.saveInvoice(invoice);
    Object id = invoice1.getId();

    Invoice resultInvoice = database.getInvoice(id);

    assertEquals(invoice, resultInvoice);
  }

  @Test
  public void shouldReturnInvoicesBetweenDates() throws DatabaseOperationException {
    Database database = new InMemoryDatabase();
    Invoice invoice = new Invoice(1L, "2/10/2018", null, null, LocalDate.of(2018, 11, 27), null,
        null, null);
    Invoice invoice2 = new Invoice(2L, "2/10/2018", null, null, LocalDate.of(2016, 9, 19), null,
        null, null);
    Invoice invoice3 = new Invoice(3L, "2/10/2018", null, null, LocalDate.of(1999, 11, 7), null,
        null, null);
    database.saveInvoice(invoice);
    database.saveInvoice(invoice2);
    database.saveInvoice(invoice3);

    Collection<Invoice> resultInvoiceList = database
        .getInvoicesBetweenDates(LocalDate.of(2013, 1, 1), LocalDate.of(2021, 1, 7));

    assertEquals(2, resultInvoiceList.size());
  }

  @Test
  public void shouldRemoveInvoiceFromMemoryDatabase() throws DatabaseOperationException {
    Database database = new InMemoryDatabase();
    Invoice invoice = new Invoice(2L, "2/10/2018", null, null, LocalDate.of(2018, 11, 27), null,
        null, null);
    Invoice addedInvoice = database.saveInvoice(invoice);

    Invoice removedInvoice = database.removeInvoice(addedInvoice.getId());

    assertEquals(addedInvoice.getId(), removedInvoice.getId());
  }
}
