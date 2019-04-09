package pl.coderstrust.invoices.database.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.coderstrust.invoices.model.Invoice;
import pl.coderstrust.invoices.model.InvoiceEntry;
import pl.coderstrust.invoices.model.Vat;

class InFileDatabaseTest {

  private static FileHelper fileHelper;
  private static String invoicesTestPath;
  private static String idTestPath;
  private static int id;

  @BeforeEach
  static void setup() {
    invoicesTestPath = "F:\\projects\\project-10-tomasz-wiktor\\src\\test\\resources\\test_invoices.txt";
    idTestPath = "F:\\projects\\project-10-tomasz-wiktor\\src\\test\\resources\\test_id.txt";
    Configuration configuration = new Configuration(invoicesTestPath, idTestPath);
    fileHelper = new FileHelper(configuration);
  }

  @AfterEach
  static void clean() {
    File invoicesFile = new File(invoicesTestPath);
    invoicesFile.delete();
    File idFile = new File(idTestPath);
    idFile.delete();
    id = 0;
  }

  @Test
  void shouldAddInvoice() {
    LocalDate localDate = LocalDate.of(2019, 03, 19);
    Invoice actual = new Invoice(1, "1", localDate, null, localDate, null, null, Arrays
        .asList(new InvoiceEntry(1, null, "1", new BigDecimal("100.0"), Vat.VAT_23)));
    assertEquals(fileHelper.add(actual), actual);

  }

  @Test
  void shouldReturnAllInvoices() {
    Collection<Invoice> invoices = new ArrayList<>();
    invoices.add(new Invoice(1, "1", LocalDate.now(), null, LocalDate.now(), null, null, null));
    invoices.add(new Invoice(2, "2", LocalDate.now(), null, LocalDate.now(), null, null, null));
    invoices.add(new Invoice(3, "3", LocalDate.now(), null, LocalDate.now(), null, null, null));
    when(fileHelper.readAll()).thenReturn(invoices);
    assertEquals(invoices, fileHelper.readAll());
  }

  @Test
  void shouldReturnEmptyListOfInvoices() {
    Collection<Invoice> invoices = new ArrayList<>();
    when(fileHelper.readAll()).thenReturn(invoices);
    assertEquals(invoices, fileHelper.readAll());
  }

  @Test
  void shouldReturnInvoiceWithGivenId() {
    Invoice invoice = new Invoice(1, "1", LocalDate.now(), null, LocalDate.now(), null, null, null);
    int givenId = 1;
    when(fileHelper.readInvoice(givenId)).thenReturn(invoice);
    assertEquals(invoice, fileHelper.readInvoice(givenId));
  }

  @Test
  void shouldReturnInvoicesBetweenDates() {
    Collection<Invoice> invoices = new ArrayList<>(Arrays
        .asList(
            new Invoice(1, "1", LocalDate.of(2019, 3, 15), null, LocalDate.of(2019, 3, 15), null,
                null, null),
            new Invoice(1, "2", LocalDate.of(2019, 3, 16), null, LocalDate.of(2019, 3, 16), null,
                null, null),
            new Invoice(1, "3", LocalDate.of(2019, 3, 17), null, LocalDate.of(2019, 3, 17), null,
                null, null),
            new Invoice(1, "4", LocalDate.of(2019, 3, 18), null, LocalDate.of(2019, 3, 18), null,
                null, null),
            new Invoice(1, "5", LocalDate.of(2019, 3, 19), null, LocalDate.of(2019, 3, 19), null,
                null, null)));

    Collection<Invoice> invoices2 = new ArrayList<>(Arrays
        .asList(
            new Invoice(1, "1", LocalDate.of(2019, 3, 15), null, LocalDate.of(2019, 3, 15), null,
                null, null),
            new Invoice(1, "2", LocalDate.of(2019, 3, 16), null, LocalDate.of(2019, 3, 16), null,
                null, null),
            new Invoice(1, "3", LocalDate.of(2019, 3, 17), null, LocalDate.of(2019, 3, 17), null,
                null, null),
            new Invoice(1, "4", LocalDate.of(2019, 3, 18), null, LocalDate.of(2019, 3, 18), null,
                null, null),
            new Invoice(1, "5", LocalDate.of(2019, 3, 19), null, LocalDate.of(2019, 3, 19), null,
                null, null)));

    when(fileHelper.readBetweenDates(any(), any())).thenReturn(invoices);

    assertEquals(invoices2,
        fileHelper.readBetweenDates(LocalDate.of(2019, 3, 15), LocalDate.of(2019, 3, 18)));
  }
}