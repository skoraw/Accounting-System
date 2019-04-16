package pl.coderstrust.invoices.database.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.coderstrust.invoices.model.Invoice;
import pl.coderstrust.invoices.model.Invoice.InvoiceBuilder;

class InFileDatabaseTest {

  private static InFileDatabase inFileDatabase;
  private Path pathIdInvoices = Paths.get("./src/test/resources/");
  private Path pathInvoices = Paths.get("./src/test/resources/");

  @BeforeEach
  void setupBeforeEach() throws IOException {
    pathInvoices = Files.createTempFile(pathInvoices, "test_invoices", ".json");
    pathIdInvoices = Files.createTempFile(pathIdInvoices, "test_id", ".json");
    Configuration configuration = new Configuration(pathInvoices.toString(),
        pathIdInvoices.toString());
    inFileDatabase = new InFileDatabase(configuration);
    String ex = "0";
    byte[] data = ex.getBytes();
    Files.write(pathIdInvoices, data);
  }

  @AfterEach
  void closeAfterEach() throws IOException {
    Files.deleteIfExists(pathInvoices);
    Files.deleteIfExists(pathIdInvoices);
  }

  @Test
  void shouldSaveInvoiceWhenNullIdIsPassed() throws IOException {
    //given
    Invoice given = new InvoiceBuilder().id(null).number("1").issueDate(LocalDate.of(2019, 4, 15))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 15)).seller(null).buyer(null).entries(null)
        .build();
    Invoice expected = new InvoiceBuilder().id(1).number("1")
        .issueDate(LocalDate.of(2019, 4, 15))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 15)).seller(null).buyer(null).entries(null)
        .build();

    //when
    Invoice actual = inFileDatabase.saveInvoice(given);

    //then
    assertEquals(expected, actual);
  }

  @Test
  void shouldSaveInvoiceWithGivenId() throws IOException {
    //given
    Invoice given = new InvoiceBuilder().id(1).number("1").issueDate(LocalDate.of(2019, 4, 15))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 15)).seller(null).buyer(null).entries(null)
        .build();
    Invoice expected = new InvoiceBuilder().id(1).number("1")
        .issueDate(LocalDate.of(2019, 4, 15))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 15)).seller(null).buyer(null).entries(null)
        .build();

    //when
    Invoice actual = inFileDatabase.saveInvoice(given);

    //then
    assertEquals(expected, actual);
  }

  @Test
  void shouldUpdateInvoice() throws IOException {
    //given
    String givenInvoice = "{\"id\":1,\"number\":\"11\",\"issueDate\":\"2019-04-25\","
        + "\"issuePlace\":null,\"sellDate\":\"2019-04-25\",\"seller\":null,"
        + "\"buyer\":null,\"entries\":null}";
    Files.writeString(pathInvoices, givenInvoice);

    Invoice expected = new InvoiceBuilder().id(1).number("1").issueDate(LocalDate.of(2019, 4, 1))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 25)).seller(null).buyer(null).entries(null)
        .build();

    //when
    Invoice actual = inFileDatabase.saveInvoice(expected);

    //then
    assertEquals(expected, actual);
  }

  @Test
  void shouldThrowExceptionWhenTrySaveNullInvoice() {
    assertThrows(IllegalArgumentException.class, () -> inFileDatabase.saveInvoice(null));
  }

  @Test
  void shouldReturnAllInvoices() throws IOException {
    //given
    List<String> givenInvoice = new ArrayList<>();
    givenInvoice.add(
        "{\"id\":1,\"number\":\"1\",\"issueDate\":\"2019-04-01\",\"issuePlace\":null,"
            + "\"sellDate\":\"2019-04-25\",\"seller\":null,\"buyer\":null,\"entries\":null}");
    givenInvoice.add(
        "{\"id\":2,\"number\":\"2\",\"issueDate\":\"2019-04-01\",\"issuePlace\":null,"
            + "\"sellDate\":\"2019-04-25\",\"seller\":null,\"buyer\":null,\"entries\":null}");
    givenInvoice.add(
        "{\"id\":3,\"number\":\"3\",\"issueDate\":\"2019-04-01\",\"issuePlace\":null,"
            + "\"sellDate\":\"2019-04-25\",\"seller\":null,\"buyer\":null,\"entries\":null}");
    Files.write(pathInvoices, givenInvoice);

    List<Invoice> expectedInvoices = new ArrayList<>();
    expectedInvoices.add(new InvoiceBuilder().id(1).number("1").issueDate(LocalDate.of(2019, 4, 1))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 25)).seller(null).buyer(null).entries(null)
        .build());
    expectedInvoices.add(new InvoiceBuilder().id(2).number("2").issueDate(LocalDate.of(2019, 4, 1))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 25)).seller(null).buyer(null).entries(null)
        .build());
    expectedInvoices.add(new InvoiceBuilder().id(3).number("3").issueDate(LocalDate.of(2019, 4, 1))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 25)).seller(null).buyer(null).entries(null)
        .build());

    //when
    List<Invoice> actualInvoices = (List<Invoice>) inFileDatabase.getAllInvoices();

    //then
    assertEquals(expectedInvoices, actualInvoices);
  }

  @Test
  void shouldGetInvoiceByGivenId() throws IOException {
    //given
    List<String> givenInvoice = new ArrayList<>();
    givenInvoice.add(
        "{\"id\":1,\"number\":\"1\",\"issueDate\":\"2019-04-01\",\"issuePlace\":null,"
            + "\"sellDate\":\"2019-04-01\",\"seller\":null,\"buyer\":null,\"entries\":null}");
    givenInvoice.add(
        "{\"id\":2,\"number\":\"2\",\"issueDate\":\"2019-04-02\",\"issuePlace\":null,"
            + "\"sellDate\":\"2019-04-02\",\"seller\":null,\"buyer\":null,\"entries\":null}");
    givenInvoice.add(
        "{\"id\":3,\"number\":\"3\",\"issueDate\":\"2019-04-03\",\"issuePlace\":null,"
            + "\"sellDate\":\"2019-04-03\",\"seller\":null,\"buyer\":null,\"entries\":null}");
    givenInvoice.add(
        "{\"id\":4,\"number\":\"4\",\"issueDate\":\"2019-04-04\",\"issuePlace\":null,"
            + "\"sellDate\":\"2019-04-04\",\"seller\":null,\"buyer\":null,\"entries\":null}");
    Files.write(pathInvoices, givenInvoice);
    Invoice expected = new InvoiceBuilder().id(2).number("2").issueDate(LocalDate.of(2019, 4, 2))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 2)).seller(null).buyer(null).entries(null)
        .build();

    //when
    Invoice actual = inFileDatabase.getInvoice(expected.getId());

    //then
    assertEquals(expected, actual);
  }

  @Test
  void shouldThrowExceptionWhenInvoiceIsNullWhileGettingInvoice() {
    assertThrows(IllegalArgumentException.class, () -> inFileDatabase.getInvoice(null));
  }

  @Test
  void shouldThrowExceptionWhenGivenIdNotExistsWhileGettingInvoice() {
    Invoice invoice = new InvoiceBuilder().id(2).number("2").issueDate(LocalDate.of(2019, 4, 2))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 2)).seller(null).buyer(null).entries(null)
        .build();
    assertThrows(IllegalArgumentException.class, () -> inFileDatabase.getInvoice(invoice));
  }

  @Test
  void shouldGetInvoicesBetweenDates() throws IOException {
    //given
    List<String> givenInvoice = new ArrayList<>();
    givenInvoice.add(
        "{\"id\":1,\"number\":\"1\",\"issueDate\":\"2019-04-01\",\"issuePlace\":null,"
            + "\"sellDate\":\"2019-04-01\",\"seller\":null,\"buyer\":null,\"entries\":null}");
    givenInvoice.add(
        "{\"id\":2,\"number\":\"2\",\"issueDate\":\"2019-04-02\",\"issuePlace\":null,"
            + "\"sellDate\":\"2019-04-02\",\"seller\":null,\"buyer\":null,\"entries\":null}");
    givenInvoice.add(
        "{\"id\":3,\"number\":\"3\",\"issueDate\":\"2019-04-03\",\"issuePlace\":null,"
            + "\"sellDate\":\"2019-04-03\",\"seller\":null,\"buyer\":null,\"entries\":null}");
    givenInvoice.add(
        "{\"id\":4,\"number\":\"4\",\"issueDate\":\"2019-04-04\",\"issuePlace\":null,"
            + "\"sellDate\":\"2019-04-04\",\"seller\":null,\"buyer\":null,\"entries\":null}");
    Files.write(pathInvoices, givenInvoice);

    List<Invoice> expectedInvoices = new ArrayList<>();
    expectedInvoices.add(new InvoiceBuilder().id(2).number("2").issueDate(LocalDate.of(2019, 4, 2))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 2)).seller(null).buyer(null).entries(null)
        .build());
    expectedInvoices.add(new InvoiceBuilder().id(3).number("3").issueDate(LocalDate.of(2019, 4, 3))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 3)).seller(null).buyer(null).entries(null)
        .build());
    LocalDate dateFrom = LocalDate.of(2019, 4, 2);
    LocalDate dateTo = LocalDate.of(2019, 4, 3);

    //when
    List<Invoice> actualInvoices = (List<Invoice>) inFileDatabase
        .getInvoicesInBetweenDates(dateFrom, dateTo);

    //then
    assertEquals(expectedInvoices, actualInvoices);
  }

  @Test
  void shouldThrowExceptionWhenWrongDatesPassed() {
    LocalDate dateTo = LocalDate.of(2019, 4, 2);
    LocalDate dateFrom = LocalDate.of(2019, 4, 3);
    assertThrows(IllegalArgumentException.class,
        () -> inFileDatabase.getInvoicesInBetweenDates(dateFrom, dateTo));
  }

  @Test
  void shouldRemoveInvoice() throws IOException {
    //given
    String givenInvoice = "{\"id\":1,\"number\":\"1\",\"issueDate\":\"2019-04-25\","
        + "\"issuePlace\":null,\"sellDate\":\"2019-04-25\",\"seller\":null,"
        + "\"buyer\":null,\"entries\":null}";
    Files.writeString(pathInvoices, givenInvoice);
    Invoice expected = new InvoiceBuilder().id(1).number("1").issueDate(LocalDate.of(2019, 4, 25))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 25)).seller(null).buyer(null).entries(null)
        .build();

    //when
    Invoice actual = inFileDatabase.removeInvoice(expected.getId());

    //then
    assertEquals(expected, actual);
  }

  @Test
  void shouldThrowExceptionWhenGivenInvoiceIsNullWhileRemovingInvoice() {
    assertThrows(IllegalArgumentException.class, () -> inFileDatabase.removeInvoice(null));
  }

  @Test
  void shouldThrowExceptionWhenGivenInvoiceIdIsNotExistsWhileRemovingInvoice() {
    Invoice invoice = new InvoiceBuilder().id(1).number("1").issueDate(LocalDate.of(2019, 4, 25))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 25)).seller(null).buyer(null).entries(null)
        .build();
    assertThrows(IllegalArgumentException.class, () -> inFileDatabase.removeInvoice(invoice));
  }
}
