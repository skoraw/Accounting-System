package pl.coderstrust.invoices.database.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import pl.coderstrust.invoices.database.DatabaseOperationException;
import pl.coderstrust.invoices.model.Invoice;

class InFileDatabaseTest {

  private static FileHelper fileHelper;

  @Mock
  private static IdFileHelper idFileHelper;

  private static Converter converter;
  private static InFileDatabase inFileDatabase;
  private Path pathIdInvoices = Paths.get("./src/test/resources/");
  private Path pathInvoices = Paths.get("./src/test/resources/");


  @BeforeAll
  static void setupBeforeAll() {
    converter = new Converter(new ObjectMapper());
  }

  @BeforeEach
  void setupBeforeEach() {
    try {
      pathInvoices = Files.createTempFile(pathInvoices, "test_invoices", ".json");
      pathIdInvoices = Files.createTempFile(pathIdInvoices, "test_id", ".json");
    } catch (IOException exception) {
      exception.printStackTrace();
    }

    fileHelper = new FileHelper(pathInvoices.toString());
    idFileHelper = new IdFileHelper(pathIdInvoices.toString());
    inFileDatabase = new InFileDatabase(fileHelper, idFileHelper);
    String ex = "0";
    byte[] data = ex.getBytes();
    try {
      Files.write(pathIdInvoices, data);
    } catch (IOException exception) {
      exception.printStackTrace();
    }

  }

  @AfterEach
  void closeAfterEach() {
    try {
      Files.deleteIfExists(pathInvoices);
      Files.deleteIfExists(pathIdInvoices);
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  @Test
  void shouldSaveInvoiceWhenNullIdIsPassed() throws DatabaseOperationException {
    //given
    Invoice given = new Invoice(null, "1", LocalDate.of(2019, 4, 15), null,
        LocalDate.of(2019, 4, 15), null, null, null);
    Invoice expected = new Invoice(1, "1", LocalDate.of(2019, 4, 15), null,
        LocalDate.of(2019, 4, 15), null, null, null);

    //when
    Invoice actual = inFileDatabase.saveInvoice(given);

    //then
    assertEquals(expected, actual);
  }

  @Test
  void shouldSaveInvoiceWithGivenId() throws DatabaseOperationException {
    //given
    Invoice given = new Invoice(1, "1", LocalDate.of(2019, 4, 15), null,
        LocalDate.of(2019, 4, 15), null, null, null);
    Invoice expected = new Invoice(1, "1", LocalDate.of(2019, 4, 15), null,
        LocalDate.of(2019, 4, 15), null, null, null);

    //when
    Invoice actual = inFileDatabase.saveInvoice(given);

    //then
    assertEquals(expected, actual);
  }

  @Test
  void shouldUpdateInvoice() throws DatabaseOperationException, IOException {
    //given
    String givenInvoice = "{\"id\":1,\"number\":\"11\",\"issueDate\":\"2019-04-25\",\"issuePlace\":null,\"sellDate\":\"2019-04-25\",\"seller\":null,\"buyer\":null,\"entries\":null}";
    Files.writeString(pathInvoices, givenInvoice);

    Invoice expected = new Invoice(1, "1", LocalDate.of(2019, 4, 1), null,
        LocalDate.of(2019, 4, 25), null, null, null);

    //when
    Invoice actual = inFileDatabase.saveInvoice(expected);

    //then
    assertEquals(expected, actual);

  }

  @Test
  void shouldThrowExceptionWhenTrySaveNullInvoice() {
    Invoice invoice = null;
    assertThrows(IllegalArgumentException.class, () -> inFileDatabase.saveInvoice(invoice));
  }

  @Test
  void shouldReturnAllInvoices() throws DatabaseOperationException, IOException {
    //given
    List<String> givenInvoice = new ArrayList<String>();
    givenInvoice.add(
        "{\"id\":1,\"number\":\"1\",\"issueDate\":\"2019-04-01\",\"issuePlace\":null,\"sellDate\":\"2019-04-25\",\"seller\":null,\"buyer\":null,\"entries\":null}");
    givenInvoice.add(
        "{\"id\":2,\"number\":\"2\",\"issueDate\":\"2019-04-01\",\"issuePlace\":null,\"sellDate\":\"2019-04-25\",\"seller\":null,\"buyer\":null,\"entries\":null}");
    givenInvoice.add(
        "{\"id\":3,\"number\":\"3\",\"issueDate\":\"2019-04-01\",\"issuePlace\":null,\"sellDate\":\"2019-04-25\",\"seller\":null,\"buyer\":null,\"entries\":null}");
    Files.write(pathInvoices, givenInvoice);

    List<Invoice> expectedInvoices = new ArrayList<>();
    expectedInvoices.add(new Invoice(1, "1", LocalDate.of(2019, 4, 1), null,
        LocalDate.of(2019, 4, 25), null, null, null));
    expectedInvoices.add(new Invoice(2, "2", LocalDate.of(2019, 4, 1), null,
        LocalDate.of(2019, 4, 25), null, null, null));
    expectedInvoices.add(new Invoice(3, "3", LocalDate.of(2019, 4, 1), null,
        LocalDate.of(2019, 4, 25), null, null, null));

    //when
    List<Invoice> actualInvoices = (List<Invoice>) inFileDatabase.getAllInvoices();

    //then
    assertEquals(expectedInvoices, actualInvoices);
  }

  @Test
  void shouldGetInvoiceByGivenId() throws IOException, DatabaseOperationException {
    //given
    List<String> givenInvoice = new ArrayList<String>();
    givenInvoice.add(
        "{\"id\":1,\"number\":\"1\",\"issueDate\":\"2019-04-01\",\"issuePlace\":null,\"sellDate\":\"2019-04-01\",\"seller\":null,\"buyer\":null,\"entries\":null}");
    givenInvoice.add(
        "{\"id\":2,\"number\":\"2\",\"issueDate\":\"2019-04-02\",\"issuePlace\":null,\"sellDate\":\"2019-04-02\",\"seller\":null,\"buyer\":null,\"entries\":null}");
    givenInvoice.add(
        "{\"id\":3,\"number\":\"3\",\"issueDate\":\"2019-04-03\",\"issuePlace\":null,\"sellDate\":\"2019-04-03\",\"seller\":null,\"buyer\":null,\"entries\":null}");
    givenInvoice.add(
        "{\"id\":4,\"number\":\"4\",\"issueDate\":\"2019-04-04\",\"issuePlace\":null,\"sellDate\":\"2019-04-04\",\"seller\":null,\"buyer\":null,\"entries\":null}");
    Files.write(pathInvoices, givenInvoice);
    Invoice expected = new Invoice(2, "2", LocalDate.of(2019, 4, 2), null, LocalDate.of(2019, 4, 2),
        null, null, null);

    //when
    Invoice actual = inFileDatabase.getInvoice(expected.getId());

    //then
    assertEquals(expected, actual);

  }

  @Test
  void shouldThrowExceptionWhenInvoiceIsNullWhileGettingInvoice() {
    Invoice invoice = null;
    assertThrows(IllegalArgumentException.class, () -> inFileDatabase.getInvoice(invoice));
  }

  @Test
  void shouldThrowExceptionWhenGivenIdNotExistsWhileGettingInvoice() {
    Invoice invoice = new Invoice(2, "2", LocalDate.of(2019, 4, 2), null, LocalDate.of(2019, 4, 2),
        null, null, null);
    assertThrows(IllegalArgumentException.class, () -> inFileDatabase.getInvoice(invoice));
  }

  @Test
  void shouldGetInvoicesBetweenDates() throws DatabaseOperationException, IOException {
    //given
    List<String> givenInvoice = new ArrayList<String>();
    givenInvoice.add(
        "{\"id\":1,\"number\":\"1\",\"issueDate\":\"2019-04-01\",\"issuePlace\":null,\"sellDate\":\"2019-04-01\",\"seller\":null,\"buyer\":null,\"entries\":null}");
    givenInvoice.add(
        "{\"id\":2,\"number\":\"2\",\"issueDate\":\"2019-04-02\",\"issuePlace\":null,\"sellDate\":\"2019-04-02\",\"seller\":null,\"buyer\":null,\"entries\":null}");
    givenInvoice.add(
        "{\"id\":3,\"number\":\"3\",\"issueDate\":\"2019-04-03\",\"issuePlace\":null,\"sellDate\":\"2019-04-03\",\"seller\":null,\"buyer\":null,\"entries\":null}");
    givenInvoice.add(
        "{\"id\":4,\"number\":\"4\",\"issueDate\":\"2019-04-04\",\"issuePlace\":null,\"sellDate\":\"2019-04-04\",\"seller\":null,\"buyer\":null,\"entries\":null}");
    Files.write(pathInvoices, givenInvoice);

    List<Invoice> expectedInvoices = new ArrayList<>();
    expectedInvoices.add(new Invoice(2, "2", LocalDate.of(2019, 4, 2), null,
        LocalDate.of(2019, 4, 2), null, null, null));
    expectedInvoices.add(new Invoice(3, "3", LocalDate.of(2019, 4, 3), null,
        LocalDate.of(2019, 4, 3), null, null, null));
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
  void shouldRemoveInvoice() throws IOException, DatabaseOperationException {
    //given
    String givenInvoice = "{\"id\":1,\"number\":\"1\",\"issueDate\":\"2019-04-25\",\"issuePlace\":null,\"sellDate\":\"2019-04-25\",\"seller\":null,\"buyer\":null,\"entries\":null}";
    Files.writeString(pathInvoices, givenInvoice);
    Invoice expected = new Invoice(1, "1", LocalDate.of(2019, 4, 25), null,
        LocalDate.of(2019, 4, 25), null, null, null);

    //when
    Invoice actual = inFileDatabase.removeInvoice(expected.getId());

    //then
    assertEquals(expected, actual);
  }

  @Test
  void shouldThrowExceptionWhenGivenInvoiceIsNullWhileRemovingInvoice() {
    Invoice invoice = null;
    assertThrows(IllegalArgumentException.class, () -> inFileDatabase.removeInvoice(invoice));
  }

  @Test
  void shouldThrowExceptionWhenGivenInvoiceIdIsNotExistsWhileRemovingInvoice() {
    Invoice invoice = new Invoice(1, "1", LocalDate.of(2019, 4, 25), null,
        LocalDate.of(2019, 4, 25), null, null, null);
    assertThrows(IllegalArgumentException.class, () -> inFileDatabase.removeInvoice(invoice));
  }
}