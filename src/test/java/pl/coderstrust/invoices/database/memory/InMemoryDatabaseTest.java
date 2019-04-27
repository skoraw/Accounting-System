package pl.coderstrust.invoices.database.memory;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Test;
import pl.coderstrust.invoices.database.Database;
import pl.coderstrust.invoices.database.DatabaseOperationException;
import pl.coderstrust.invoices.model.Invoice;

public class InMemoryDatabaseTest {

  @Test
  public void shouldSaveInvoiceInList() throws DatabaseOperationException {
    Database database = new InMemoryDatabase();
    Invoice invoiceToAdd = Invoice.builder()
        .id(1L)
        .number("23/11/2019")
        .buyer(null)
        .issueDate(LocalDate.now())
        .issuePlace("SomePlace")
        .sellDate(LocalDate.of(2018, 11, 27))
        .seller(null)
        .entries(null)
        .build();

    Invoice addedInvoice = database.saveInvoice(invoiceToAdd);

    assertEquals(invoiceToAdd.getId(), addedInvoice.getId());
    assertNotNull(addedInvoice.getId());
  }

  @Test
  public void shouldCheckIfInvoiceWasCopied() throws DatabaseOperationException {
    Database database = new InMemoryDatabase();
    Invoice invoice = Invoice.builder()
        .id(3L)
        .number("23/11/2019")
        .buyer(null)
        .issueDate(LocalDate.now())
        .issuePlace("SomePlace")
        .sellDate(LocalDate.of(2018, 11, 27))
        .seller(null)
        .entries(null)
        .build();
    Invoice inv = database.saveInvoice(invoice);
    Object id = inv.getId();
    inv.setId(7L);

    Invoice result = database.getInvoice(id);
    assertEquals(id, result.getId());
  }

  @Test
  public void shouldReturnCollectionOfInvoices() throws DatabaseOperationException {
    Database database = new InMemoryDatabase();
    Invoice invoice = Invoice.builder()
        .id(1L)
        .number("23/11/2019")
        .buyer(null)
        .issueDate(LocalDate.now())
        .issuePlace("SomePlace")
        .sellDate(LocalDate.of(2018, 11, 27))
        .seller(null)
        .entries(null)
        .build();
    database.saveInvoice(invoice);
    Collection<Invoice> expectedInvoiceList = new ArrayList<>();
    expectedInvoiceList.add(invoice);

    Collection<Invoice> resultInvoiceList = database.getAllInvoices();

    assertThat(resultInvoiceList, contains(expectedInvoiceList.toArray()));
  }

  @Test
  public void shouldGetInvoiceFromMemoryDatabase() throws DatabaseOperationException {
    Database database = new InMemoryDatabase();
    Invoice invoice = Invoice.builder()
        .id(1L)
        .number("23/11/2019")
        .buyer(null)
        .issueDate(LocalDate.now())
        .issuePlace("SomePlace")
        .sellDate(LocalDate.of(2018, 11, 27))
        .seller(null)
        .entries(null)
        .build();
    Invoice invoice1 = database.saveInvoice(invoice);
    Object id = invoice1.getId();

    Invoice resultInvoice = database.getInvoice(id);

    assertEquals(invoice, resultInvoice);
  }

  @Test
  public void shouldReturnInvoicesBetweenDates() throws DatabaseOperationException {
    Database database = new InMemoryDatabase();
    Invoice invoice = Invoice.builder()
        .id(1L)
        .number("23/11/2019")
        .buyer(null)
        .issueDate(LocalDate.now())
        .issuePlace("SomePlace")
        .sellDate(LocalDate.of(2018, 11, 27))
        .seller(null)
        .entries(null)
        .build();
    Invoice invoice2 = Invoice.builder()
        .id(2L)
        .number("23/11/2019")
        .buyer(null)
        .issueDate(LocalDate.now())
        .issuePlace("SomePlace")
        .sellDate(LocalDate.of(1999, 11, 7))
        .seller(null)
        .entries(null)
        .build();
    Invoice invoice3 = Invoice.builder()
        .id(3L)
        .number("23/11/2019")
        .buyer(null)
        .issueDate(LocalDate.now())
        .issuePlace("SomePlace")
        .sellDate(LocalDate.of(2016, 9, 19))
        .seller(null)
        .entries(null)
        .build();
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
    Invoice invoice = Invoice.builder()
        .id(2L)
        .number("23/11/2019")
        .buyer(null)
        .issueDate(LocalDate.now())
        .issuePlace("SomePlace")
        .sellDate(LocalDate.now())
        .seller(null)
        .entries(null)
        .build();
    Invoice addedInvoice = database.saveInvoice(invoice);

    Invoice removedInvoice = database.removeInvoice(addedInvoice.getId());

    assertEquals(addedInvoice.getId(), removedInvoice.getId());
  }
}
