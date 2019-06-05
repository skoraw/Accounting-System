package pl.coderstrust.invoices.database.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.coderstrust.invoices.database.InvoiceBookException;
import pl.coderstrust.invoices.model.Company;
import pl.coderstrust.invoices.model.Invoice;
import pl.coderstrust.invoices.service.InvoiceBook;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class HibernateDatabaseTest {

  @Autowired
  private InvoiceBook invoiceBook;

  private Company buyer = Company.builder()
      .id(1L)
      .town(null)
      .postalCode(null)
      .street(null)
      .taxIdentificationNumber(null)
      .name(null)
      .build();
  private Company seller = Company.builder()
      .id(2L)
      .town(null)
      .postalCode(null)
      .street(null)
      .taxIdentificationNumber(null)
      .name(null)
      .build();
  private Invoice invoice = Invoice.builder()
      .id(1L)
      .number("2019/1")
      .buyer(buyer)
      .issueDate(LocalDate.of(2019, 5, 11))
      .issuePlace("SomePlace")
      .sellDate(LocalDate.of(2019, 5, 11))
      .seller(seller)
      .entries(Collections.emptyList())
      .build();
  private Invoice invoice2nd = Invoice.builder()
      .id(2L)
      .number("2019/2")
      .buyer(buyer)
      .issueDate(LocalDate.of(2019, 5, 12))
      .issuePlace("SomePlace")
      .sellDate(LocalDate.of(2019, 5, 12))
      .seller(seller)
      .entries(Collections.emptyList())
      .build();
  private Invoice invoice3rd = Invoice.builder()
      .id(3L)
      .number("2019/3")
      .buyer(buyer)
      .issueDate(LocalDate.of(2019, 5, 13))
      .issuePlace("SomePlace")
      .sellDate(LocalDate.of(2019, 5, 13))
      .seller(seller)
      .entries(Collections.emptyList())
      .build();

  @Test
  void shouldSaveInvoiceInDatabase() throws InvoiceBookException {
    //given
    Invoice expected = Invoice.builder()
        .id(1L)
        .number("2019/1")
        .buyer(buyer)
        .issueDate(LocalDate.of(2019, 5, 11))
        .issuePlace("SomePlace")
        .sellDate(LocalDate.of(2019, 5, 11))
        .seller(seller)
        .entries(Collections.emptyList())
        .build();

    //when
    Invoice actual = invoiceBook.saveInvoice(invoice);

    //then
    assertEquals(expected, actual);
  }

  @Test
  void shouldSaveInvoiceWhenIdIsNull() throws InvoiceBookException {
    //given
    Invoice expected = Invoice.builder()
        .id(1L)
        .number("2019/1")
        .buyer(buyer)
        .issueDate(LocalDate.of(2019, 5, 11))
        .issuePlace("SomePlace")
        .sellDate(LocalDate.of(2019, 5, 11))
        .seller(seller)
        .entries(Collections.emptyList())
        .build();

    //when
    Invoice actual = invoiceBook.saveInvoice(invoice);

    //then
    assertEquals(expected, actual);
  }

  @Test
  void shouldThrowExceptionWhenNullIsPassedToSaveMethod() {
    assertThrows(IllegalArgumentException.class, () -> invoiceBook.saveInvoice(null));
  }

  @Test
  void shouldGetAllInvoicesFromDatabase() throws InvoiceBookException {
    //given
    invoiceBook.saveInvoice(invoice);
    invoiceBook.saveInvoice(invoice2nd);
    invoiceBook.saveInvoice(invoice3rd);
    List<Invoice> expected = new ArrayList<>();
    expected.add(invoice);
    expected.add(invoice2nd);
    expected.add(invoice3rd);

    //when
    ArrayList<Invoice> actual = (ArrayList<Invoice>) invoiceBook.getAllInvoices();
    System.out.println(actual.toString());
    //then
    assertEquals(expected, actual);
  }

  @Test
  void shouldGetSingleInvoiceBygivenId() throws InvoiceBookException {
    //given
    Invoice expected = invoiceBook.saveInvoice(invoice);

    //when
    Invoice actual = invoiceBook.getInvoice(1L);

    //then
    assertEquals(expected, actual);
  }

  @Test
  void shouldThrowExceptionWhenTryGetInvoiceWithUnsupportedId() {
    String id = "id";
    assertThrows(IllegalArgumentException.class, () -> invoiceBook.getInvoice(Long.valueOf(id)));
  }

  @Test
  void shouldThrowExceptionWhentryGetNotExistInvoice() {
    assertThrows(NoSuchElementException.class, () -> invoiceBook.getInvoice(1L));
  }

  @Test
  void shouldThrowExceptionWhenNullIsPassedToGetInvoiceMethod() {
    assertThrows(IllegalArgumentException.class, () -> invoiceBook.getInvoice(null));
  }

  @Test
  void shouldThrowExceptionWhenNotExistingIdIsPassedToGetInvoiceMethod() {
    assertThrows(NoSuchElementException.class, () -> invoiceBook.getInvoice(1L));
  }

  @Test
  void shouldRemoveInvoiceWithGivenId() throws InvoiceBookException {
    //given
    Invoice expected = invoiceBook.saveInvoice(invoice);

    //when
    Invoice actual = invoiceBook.removeInvoice(1L);

    //then
    assertEquals(expected, actual);
  }

  @Test
  void shouldThrowExceptionWhenNotExistingIdIsPassedToRemoveMethod() {
    assertThrows(NoSuchElementException.class, () -> invoiceBook.removeInvoice(1L));
  }

  @Test
  void shouldThrowExceptionWhenTryRemoveInvoiceWithUnsupportedIdType() {
    String id = "id";
    assertThrows(IllegalArgumentException.class, () -> invoiceBook.removeInvoice(Long.valueOf(id)));
  }

  @Test
  void shouldThrowExceptionWhenNullIsPassedToRemoveMethod() {
    assertThrows(IllegalArgumentException.class, () -> invoiceBook.removeInvoice(null));
  }

  @Test
  void shouldGetInvoicesBetweenDates() throws InvoiceBookException {
    //given
    invoiceBook.saveInvoice(invoice);
    invoiceBook.saveInvoice(invoice2nd);
    invoiceBook.saveInvoice(invoice3rd);
    List<Invoice> expected = new ArrayList<>();
    expected.add(invoice2nd);

    //when
    List<Invoice> actual = (List<Invoice>) invoiceBook
        .getInvoicesBetweenDates(LocalDate.of(2019, 5, 12), LocalDate.of(2019, 5, 12));

    //then
    assertEquals(expected, actual);
  }

  @Test
  void shouldThrowExceptionWhenDatesAreChanged() throws InvoiceBookException {
    //given
    List<Invoice> expeted = Collections.EMPTY_LIST;

    //when
    List<Invoice> actual = (List<Invoice>) invoiceBook
        .getInvoicesBetweenDates(LocalDate.of(2019, 5, 11), LocalDate.of(2019, 5, 10));

    //then
    assertEquals(expeted, actual);
  }

  @Test
  void shouldUpdateInvoice() throws InvoiceBookException {
    //given
    invoiceBook.saveInvoice(invoice);
    Invoice expected = Invoice.builder()
        .id(1L)
        .number("2019/1")
        .buyer(buyer)
        .issueDate(LocalDate.of(2019, 5, 15))
        .issuePlace("SomePlace")
        .sellDate(LocalDate.of(2019, 5, 15))
        .seller(seller)
        .entries(Collections.emptyList())
        .build();

    //when
    invoiceBook.saveInvoice(expected);
    Invoice actual = invoiceBook.getInvoice(1L);

    //then
    assertEquals(expected, actual);
  }
}
