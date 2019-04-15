package pl.coderstrust.invoices.database.file;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IdFileHelperTest {

  private IdFileHelper idFileHelper;
  private Path pathIdInvoices = Paths
      .get("./src/test/resources/");

  @BeforeEach
  void setupBeforeEach() throws IOException {
    pathIdInvoices = Files.createTempFile(pathIdInvoices, "test_id", ".json");
    idFileHelper = new IdFileHelper(pathIdInvoices.toString());
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
    String actual = idFileHelper.getMaxId();

    //then
    assertEquals(expected, actual);

  }

  @Test
  void shouldSetNewId() throws IOException {
    //given
    String expected = "2";
    idFileHelper.setNewId(expected);

    //when
    String actual = Files.readString(pathIdInvoices);

    //then
    assertEquals(expected, actual);

  }
}
