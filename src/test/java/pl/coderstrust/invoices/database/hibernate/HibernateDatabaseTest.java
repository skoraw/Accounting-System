package pl.coderstrust.invoices.database.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.coderstrust.invoices.database.InvoiceBookException;
import pl.coderstrust.invoices.model.Invoice;
import pl.coderstrust.invoices.service.InvoiceBook;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class HibernateDatabaseTest {

  @Autowired
  private InvoiceBook invoiceBook;

  private Invoice invoice = Invoice.builder()
      .id(1L)
      .number("2019/1")
      .buyer(null)
      .issueDate(LocalDate.of(2019, 5, 11))
      .issuePlace("SomePlace")
      .sellDate(LocalDate.of(2019, 5, 11))
      .seller(null)
      .entries(Collections.emptyList())
      .build();
  private Invoice invoice2nd = Invoice.builder()
      .id(2L)
      .number("2019/2")
      .buyer(null)
      .issueDate(LocalDate.of(2019, 5, 12))
      .issuePlace("SomePlace")
      .sellDate(LocalDate.of(2019, 5, 12))
      .seller(null)
      .entries(Collections.emptyList())
      .build();
  private Invoice invoice3rd = Invoice.builder()
      .id(3L)
      .number("2019/3")
      .buyer(null)
      .issueDate(LocalDate.of(2019, 5, 13))
      .issuePlace("SomePlace")
      .sellDate(LocalDate.of(2019, 5, 13))
      .seller(null)
      .entries(Collections.emptyList())
      .build();
  @Test
  void shouldSaveInvoiceInDatabase() throws InvoiceBookException {
    //given
    Invoice expected = Invoice.builder()
        .id(1L)
        .number("2019/1")
        .buyer(null)
        .issueDate(LocalDate.of(2019, 5, 11))
        .issuePlace("SomePlace")
        .sellDate(LocalDate.of(2019, 5, 11))
        .seller(null)
        .entries(Collections.emptyList())
        .build();

    //when
    Invoice actual = invoiceBook.saveInvoice(invoice);

    //then
    assertEquals(expected, actual);
  }

  @Test
  void shouldGetAllInvoicesFromDatabase() throws InvoiceBookException {
    //given
    invoiceBook.saveInvoice(invoice);
//    invoiceBook.saveInvoice(invoice2nd);
    List<Invoice> expected = new ArrayList<>();
    invoice.setId(1L);
    expected.add(invoice);
//    invoice2nd.setId(2L);
//    expected.add(invoice2nd);

    //when
    List<Invoice> actual = (List<Invoice>) invoiceBook.getAllInvoices();

    //then
    assertEquals(expected, actual);

  }

  //  @Test
//  void shouldGetSingleInvoiceBygivenId() throws InvoiceBookException {
//    //given
////    Invoice given = Invoice.builder()
////        .id(null)
////        .number("23/11/2019")
////        .buyer(null)
////        .issueDate(LocalDate.of(2018, 11, 11))
////        .issuePlace("SomePlace")
////        .sellDate(LocalDate.of(2013, 11, 27))
////        .seller(null)
////        .entries(Arrays.asList())
////        .build();
//    invoiceBook.saveInvoice(invoice);
//    Invoice actual = invoiceBook.saveInvoice(invoice2nd);
//    //when
//    Invoice expected = invoiceBook.getInvoice(2L);
//
//    //then
//    assertEquals(expected, actual);
//
//  }
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
  void shouldUpdateInvoice() throws InvoiceBookException {
    //given
    invoiceBook.saveInvoice(invoice);
    Invoice expected = Invoice.builder()
        .id(1L)
        .number("2019/1")
        .buyer(null)
        .issueDate(LocalDate.of(2019, 5, 15))
        .issuePlace("SomePlace")
        .sellDate(LocalDate.of(2019, 5, 15))
        .seller(null)
        .entries(Collections.emptyList())
        .build();

    //when
    invoiceBook.saveInvoice(expected);
    Invoice actual = invoiceBook.getInvoice(1L);

    //then
    assertEquals(expected, actual);
  }

  //przetestowac exceptiony rzucane
}