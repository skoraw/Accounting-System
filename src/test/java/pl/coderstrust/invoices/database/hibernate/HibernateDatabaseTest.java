package pl.coderstrust.invoices.database.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderstrust.invoices.database.InvoiceBookException;
import pl.coderstrust.invoices.model.Invoice;
import pl.coderstrust.invoices.service.InvoiceBook;

@RunWith(SpringRunner.class)
@SpringBootTest
class HibernateDatabaseTest {

  @Autowired
  private InvoiceBook invoiceBook;

  private Invoice invoice = Invoice.builder()
      .id(1L)
      .number("23/11/2019")
      .buyer(null)
      .issueDate(LocalDate.of(2018, 11, 11))
      .issuePlace("SomePlace")
      .sellDate(LocalDate.of(2013, 11, 27))
      .seller(null)
      .entries(Arrays.asList())
      .build();
  private Invoice invoice2nd = Invoice.builder()
      .id(2L)
      .number("01/01/2011")
      .buyer(null)
      .issueDate(LocalDate.now())
      .issuePlace("SomePlace")
      .sellDate(LocalDate.of(2017, 11, 27))
      .seller(null)
      .entries(Arrays.asList())
      .build();

  @Test
  void shouldSaveInvoiceInDatabase() throws InvoiceBookException {
    //given
    Invoice expected = Invoice.builder()
        .id(1L)
        .number("23/11/2019")
        .buyer(null)
        .issueDate(LocalDate.of(2018, 11, 11))
        .issuePlace("SomePlace")
        .sellDate(LocalDate.of(2013, 11, 27))
        .seller(null)
        .entries(Arrays.asList())
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
    invoiceBook.saveInvoice(invoice2nd);
    List<Invoice> expected = new ArrayList<>();
    invoice.setId(1L);
    expected.add(invoice);
    invoice2nd.setId(2L);
    expected.add(invoice2nd);

    //when
    List<Invoice> actual = (List<Invoice>) invoiceBook.getAllInvoices();

    //then
    assertEquals(expected, actual);

  }

  @Test
  void shouldGetSingleInvoiceBygivenId() throws InvoiceBookException {
    //given
    invoiceBook.saveInvoice(invoice);
    //when
    Invoice expected = invoiceBook.getInvoice(1L);

    //then
    assertEquals(expected, invoice);

  }

}