package pl.coderstrust.invoices.database.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderstrust.invoices.database.DatabaseOperationException;
import pl.coderstrust.invoices.model.Invoice;
import pl.coderstrust.invoices.model.Invoice.InvoiceBuilder;

@ExtendWith(MockitoExtension.class)
class InFileDatabaseTest {

  private static InFileDatabase inFileDatabase;

  @Mock
  private FileHelper fileHelper;

  @Mock
  private IdGenerator idGenerator;

  @BeforeEach
  void setupBeforeEach() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    mapper.registerModule(new JSR310Module());
    inFileDatabase = new InFileDatabase(
        fileHelper,
        idGenerator,
        new InvoiceConverter(mapper));
  }

  @Test
  void shouldSaveInvoiceWithGivenId() throws DatabaseOperationException, FileNotFoundException {
    //given
    Invoice given = new InvoiceBuilder().id(1).number("1").issueDate(LocalDate.of(2019, 4, 15))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 15)).seller(null).buyer(null).entries(null)
        .build();
    Invoice expected = new InvoiceBuilder().id(1).number("1")
        .issueDate(LocalDate.of(2019, 4, 15))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 15)).seller(null).buyer(null).entries(null)
        .build();

    when(fileHelper.readAllLines()).thenReturn(Collections.emptyList());
    when(idGenerator.getNextId()).thenReturn(1);

    //when
    Invoice actual = inFileDatabase.saveInvoice(given);

    //then
    assertEquals(expected, actual);
  }

  @Test
  void shouldUpdateInvoice() throws DatabaseOperationException, FileNotFoundException {
    //given
    List<String> stringInvoiceList = new ArrayList<>();
    String givenStringInvoice = "{\"id\":1,\"number\":\"11\",\"issueDate\":\"2019-04-25\","
        + "\"issuePlace\":null,\"sellDate\":\"2019-04-25\",\"seller\":null,"
        + "\"buyer\":null,\"entries\":null}";
    stringInvoiceList.add(givenStringInvoice);

    Invoice expected = new InvoiceBuilder().id(1).number("1").issueDate(LocalDate.of(2019, 4, 1))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 25)).seller(null).buyer(null).entries(null)
        .build();

    when(fileHelper.readAllLines()).thenReturn(stringInvoiceList);

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
  void shouldReturnAllInvoices() throws DatabaseOperationException, FileNotFoundException {
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

    when(fileHelper.readAllLines()).thenReturn(givenInvoice);

    //when
    List<Invoice> actualInvoices = (List<Invoice>) inFileDatabase.getAllInvoices();

    //then
    assertEquals(expectedInvoices, actualInvoices);
  }

  @Test
  void shouldGetInvoiceByGivenId() throws DatabaseOperationException, FileNotFoundException {
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
    Invoice expected = new InvoiceBuilder().id(2).number("2").issueDate(LocalDate.of(2019, 4, 2))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 2)).seller(null).buyer(null).entries(null)
        .build();

    when(fileHelper.readAllLines()).thenReturn(givenInvoice);

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
  void shouldThrowExceptionWhenInvoiceIdIsIncorrectType() {
    Invoice given = new InvoiceBuilder().id("incorrect id").number("1")
        .issueDate(LocalDate.of(2019, 4, 15))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 15)).seller(null).buyer(null).entries(null)
        .build();
    assertThrows(IllegalArgumentException.class, () -> inFileDatabase.getInvoice(given));
  }

  @Test
  void shouldThrowExceptionWhenGivenIdNotExistsWhileGettingInvoice() {
    Invoice invoice = new InvoiceBuilder().id(2).number("2").issueDate(LocalDate.of(2019, 4, 2))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 2)).seller(null).buyer(null).entries(null)
        .build();
    assertThrows(IllegalArgumentException.class, () -> inFileDatabase.getInvoice(invoice));
  }

  @Test
  void shouldGetInvoicesBetweenDates() throws DatabaseOperationException, FileNotFoundException {
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

    List<Invoice> expectedInvoices = new ArrayList<>();
    expectedInvoices.add(new InvoiceBuilder().id(2).number("2").issueDate(LocalDate.of(2019, 4, 2))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 2)).seller(null).buyer(null).entries(null)
        .build());
    expectedInvoices.add(new InvoiceBuilder().id(3).number("3").issueDate(LocalDate.of(2019, 4, 3))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 3)).seller(null).buyer(null).entries(null)
        .build());
    LocalDate dateFrom = LocalDate.of(2019, 4, 2);
    LocalDate dateTo = LocalDate.of(2019, 4, 3);

    when(fileHelper.readAllLines()).thenReturn(givenInvoice);

    //when
    List<Invoice> actualInvoices = (List<Invoice>) inFileDatabase
        .getInvoicesBetweenDates(dateFrom, dateTo);

    //then
    assertEquals(expectedInvoices, actualInvoices);
  }

  @Test
  void shouldThrowExceptionWhenWrongDatesPassed() {
    LocalDate dateTo = LocalDate.of(2019, 4, 2);
    LocalDate dateFrom = LocalDate.of(2019, 4, 3);
    assertThrows(IllegalArgumentException.class,
        () -> inFileDatabase.getInvoicesBetweenDates(dateFrom, dateTo));
  }

  @Test
  void shouldRemoveInvoice() throws DatabaseOperationException, FileNotFoundException {
    //given
    List<String> stringInvoicesList = new ArrayList<>();
    String givenInvoice = "{\"id\":1,\"number\":\"1\",\"issueDate\":\"2019-04-25\","
        + "\"issuePlace\":null,\"sellDate\":\"2019-04-25\",\"seller\":null,"
        + "\"buyer\":null,\"entries\":null}";
    stringInvoicesList.add(givenInvoice);
    Invoice expected = new InvoiceBuilder().id(1).number("1").issueDate(LocalDate.of(2019, 4, 25))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 25)).seller(null).buyer(null).entries(null)
        .build();

    when(fileHelper.readAllLines()).thenReturn(stringInvoicesList);

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
  void shouldThrowExceptionWhenGivenInvoiceIdIsIncorrectType() {
    Invoice given = new InvoiceBuilder().id("incorrect id").number("1")
        .issueDate(LocalDate.of(2019, 4, 15))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 15)).seller(null).buyer(null).entries(null)
        .build();
    assertThrows(IllegalArgumentException.class, () -> inFileDatabase.removeInvoice(given));
  }

  @Test
  void shouldThrowExceptionWhenGivenInvoiceIdIsNotExistsWhileRemovingInvoice() {
    Invoice invoice = new InvoiceBuilder().id(1).number("1").issueDate(LocalDate.of(2019, 4, 25))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 25)).seller(null).buyer(null).entries(null)
        .build();
    assertThrows(IllegalArgumentException.class, () -> inFileDatabase.removeInvoice(invoice));
  }
}
