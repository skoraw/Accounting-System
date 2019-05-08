package pl.coderstrust.invoices.database.file;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IdGeneratorTest {

  private IdGenerator idGenerator;
  private Path pathIdInvoices = Paths
      .get("./src/test/resources/");

  @BeforeEach
  void setupBeforeEach() throws IOException {
    pathIdInvoices = Files.createTempFile(pathIdInvoices, "test_id", ".json");
    idGenerator = new IdGenerator(new FileHelper(pathIdInvoices.toString()));
  }

  @AfterEach
  void closeAfterEach() throws IOException {
    Files.deleteIfExists(pathIdInvoices);
  }

  @Test
  void shouldReturnMaxId() throws IOException {
    //given
    String expected = "2";
    byte[] data = expected.getBytes();
    Files.write(pathIdInvoices, data);

    //when
    String actual = String.valueOf(idGenerator.getId());

    //then
    assertEquals(expected, actual);
  }

  @Test
  void shouldReturnNextId() throws IOException {
    //given
    String value = "2";
    byte[] data = value.getBytes();
    Files.write(pathIdInvoices, data);
    String expected = "3";

    //when
    String actual = String.valueOf(idGenerator.getNextId());

    //then
    assertEquals(expected, actual);
  }

  @Test
  void shouldSetNewId() throws IOException {
    //given
    String expected = "2";
    idGenerator.setNewId(expected);

    //when
    String actual = Files.readString(pathIdInvoices);

    //then
    assertEquals(expected, actual);
  }
}
