package pl.coderstrust.invoices.database.file;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.coderstrust.invoices.model.Invoice;
import pl.coderstrust.invoices.model.Invoice.InvoiceBuilder;

class ConverterTest {

  private static Converter converter;

  @BeforeAll
  static void setup() {
    converter = new Converter(new ObjectMapper());
  }

  @Test
  void shouldConvertInvoiceObjectToString() {
    //given
    Invoice invoice = new InvoiceBuilder().id(1).number("1").issueDate(LocalDate.of(2019, 4, 11))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 11)).seller(null).buyer(null).entries(null)
        .build();
    String expected = "{\"id\":1,\"number\":\"1\",\"issueDate\":\"2019-04-11\",\"issuePlace\":null"
        + ",\"sellDate\":\"2019-04-11\",\"seller\":null,\"buyer\":null,\"entries\":null}";

    //when
    String actual = converter.objectToString(invoice);

    //then
    assertEquals(expected, actual);
  }

  @Test
  void shouldConvertStringToInvoiceObject() {
    //given
    String line = "{\"id\":1,\"number\":\"1\",\"issueDate\":\"2019-04-11\",\"issuePlace\":null,"
        + "\"sellDate\":\"2019-04-11\",\"seller\":null,\"buyer\":null,\"entries\":null}";
    Invoice expected = new InvoiceBuilder().id(1).number("1").issueDate(LocalDate.of(2019, 4, 11))
        .issuePlace(null).sellDate(LocalDate.of(2019, 4, 11)).seller(null).buyer(null).entries(null)
        .build();

    //when
    Invoice actual = converter.stringToInvoice(line);

    //then
    assertEquals(expected, actual);
  }

  @Test
  void shouldConvertStringListToInvoicesList() {
    //given
    List<String> list = new ArrayList<>(Arrays.asList(
        "{\"id\":1,\"number\":\"1\",\"issueDate\":\"2019-04-11\",\"issuePlace\":null,"
            + "\"sellDate\":\"2019-04-11\",\"seller\":null,\"buyer\":null,\"entries\":null}",
        "{\"id\":2,\"number\":\"2\",\"issueDate\":\"2019-04-11\",\"issuePlace\":null,"
            + "\"sellDate\":\"2019-04-11\",\"seller\":null,\"buyer\":null,\"entries\":null}",
        "{\"id\":3,\"number\":\"3\",\"issueDate\":\"2019-04-11\",\"issuePlace\":null,"
            + "\"sellDate\":\"2019-04-11\",\"seller\":null,\"buyer\":null,\"entries\":null}"));

    List<Invoice> expected = new ArrayList<>(
        Arrays.asList(
            new InvoiceBuilder().id(1).number("1").issueDate(LocalDate.of(2019, 4, 11))
                .issuePlace(null).sellDate(LocalDate.of(2019, 4, 11)).seller(null).buyer(null)
                .entries(null)
                .build(),
            new InvoiceBuilder().id(2).number("2").issueDate(LocalDate.of(2019, 4, 11))
                .issuePlace(null).sellDate(LocalDate.of(2019, 4, 11)).seller(null).buyer(null)
                .entries(null)
                .build(),
            new InvoiceBuilder().id(3).number("3").issueDate(LocalDate.of(2019, 4, 11))
                .issuePlace(null).sellDate(LocalDate.of(2019, 4, 11)).seller(null).buyer(null)
                .entries(null)
                .build()));

    //when
    List<Invoice> actual = (List<Invoice>) converter.stringListToInvoicesList(list);

    //then
    assertEquals(expected, actual);
  }

  @Test
  void shouldConvertInvoicesListToStringList() {
    //given
    List<Invoice> list = new ArrayList<>(
        Arrays.asList(
            new InvoiceBuilder().id(1).number("1").issueDate(LocalDate.of(2019, 4, 11))
                .issuePlace(null).sellDate(LocalDate.of(2019, 4, 11)).seller(null).buyer(null)
                .entries(null)
                .build(),
            new InvoiceBuilder().id(2).number("2").issueDate(LocalDate.of(2019, 4, 11))
                .issuePlace(null).sellDate(LocalDate.of(2019, 4, 11)).seller(null).buyer(null)
                .entries(null)
                .build(),
            new InvoiceBuilder().id(3).number("3").issueDate(LocalDate.of(2019, 4, 11))
                .issuePlace(null).sellDate(LocalDate.of(2019, 4, 11)).seller(null).buyer(null)
                .entries(null)
                .build()));

    List<String> expected = new ArrayList<>(Arrays.asList(
        "{\"id\":1,\"number\":\"1\",\"issueDate\":\"2019-04-11\",\"issuePlace\":null,"
            + "\"sellDate\":\"2019-04-11\",\"seller\":null,\"buyer\":null,\"entries\":null}",
        "{\"id\":2,\"number\":\"2\",\"issueDate\":\"2019-04-11\",\"issuePlace\":null,"
            + "\"sellDate\":\"2019-04-11\",\"seller\":null,\"buyer\":null,\"entries\":null}",
        "{\"id\":3,\"number\":\"3\",\"issueDate\":\"2019-04-11\",\"issuePlace\":null,"
            + "\"sellDate\":\"2019-04-11\",\"seller\":null,\"buyer\":null,\"entries\":null}"));

    //when
    List<String> actual = (List<String>) converter.invoicesListToStringList(list);

    //then
    assertEquals(expected, actual);
  }
}
