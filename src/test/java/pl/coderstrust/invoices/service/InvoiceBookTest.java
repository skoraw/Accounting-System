package pl.coderstrust.invoices.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.coderstrust.invoices.database.Database;
import pl.coderstrust.invoices.database.DatabaseOperationException;
import pl.coderstrust.invoices.database.InMemoryDatabase;
import pl.coderstrust.invoices.database.InvoiceBookException;
import pl.coderstrust.invoices.model.Invoice;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceBookTest {

  @Mock
  private Database database;

  @Test
  public void shouldReturnEmptyInvoicesListWhenInitialize()
      throws DatabaseOperationException, InvoiceBookException {
    InvoiceBook invoiceBook = new InvoiceBook(database);
    when(database.getAllInvoices()).thenReturn(Arrays.asList());

    Collection<Invoice> invoices = invoiceBook.getAllInvoices();

    assertEquals(0, invoices.size());
  }

  @Test
  public void shouldAddInvoiceToDatabase() throws DatabaseOperationException, InvoiceBookException {
    Database database = mock(InMemoryDatabase.class);
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
    Invoice savedInvoice = Invoice.builder()
        .id(2L)
        .number("23/11/2019")
        .buyer(null)
        .issueDate(LocalDate.now())
        .issuePlace("SomePlace")
        .sellDate(LocalDate.of(2018, 11, 27))
        .seller(null)
        .entries(null)
        .build();
    InvoiceBook invoiceBook = new InvoiceBook(database);

    when(database.saveInvoice(invoice)).thenReturn(savedInvoice);

    Invoice result = invoiceBook.saveInvoice(invoice);

    assertEquals(result, savedInvoice);
  }

  @Test
  public void shouldRemoveInvoiceFromList()
      throws InvoiceBookException, DatabaseOperationException {
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
    InvoiceBook invoiceBook = new InvoiceBook(database);

    when(database.removeInvoice(invoice.getId())).thenReturn(null);

    Invoice result = invoiceBook.removeInvoice((Long) invoice.getId());

    verify(database).removeInvoice(invoice.getId());
    assertNull(result);
  }

  @Test
  public void shouldReturnInvoiceById() throws DatabaseOperationException, InvoiceBookException {
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
    InvoiceBook invoiceBook = new InvoiceBook(database);

    when(database.getInvoice(invoice.getId())).thenReturn(invoice);

    Invoice result = invoiceBook.getInvoice((Long) invoice.getId());

    assertEquals(invoice, result);
  }

  @Test
  public void shouldFindInvoicesBetweenDates()
      throws DatabaseOperationException, InvoiceBookException {
    InvoiceBook invoiceBook = new InvoiceBook(database);
    Collection<Invoice> invoicesBetweenDates = new ArrayList<>();

    when(database.getInvoicesBetweenDates(any(), any())).thenReturn(invoicesBetweenDates);

    Collection<Invoice> result = invoiceBook
        .getInvoicesBetweenDates(LocalDate.of(2010, 11, 1), LocalDate.now());

    assertEquals(result, invoicesBetweenDates);
  }

  @Test
  public void shouldThrowExceptionForInvalidArgumentWhenRemoveInvoiceMethodIsCalled() {
    InvoiceBook invoiceBook = new InvoiceBook(database);
    assertThrows(IllegalArgumentException.class, () -> invoiceBook.removeInvoice(null));
  }

  @Test
  public void shouldThrowExceptionForInvalidArgumentWhenAddInvoiceMethodIsCalled() {
    InvoiceBook invoiceBook = new InvoiceBook(database);
    assertThrows(IllegalArgumentException.class, () -> invoiceBook.saveInvoice(null));
  }

  @Test
  public void shouldThrowExceptionForInvalidArgumentWhenGetInvoiceMethodIsCalled() {
    InvoiceBook invoiceBook = new InvoiceBook(database);
    assertThrows(IllegalArgumentException.class, () -> invoiceBook.getInvoice(null));
  }
}
