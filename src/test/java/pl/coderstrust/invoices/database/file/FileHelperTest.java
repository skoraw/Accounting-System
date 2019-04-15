package pl.coderstrust.invoices.database.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileHelperTest {

  private FileHelper fileHelper;

  private Path pathInvoices = Paths.get("./src/test/resources/");

  @BeforeEach
  void setupBeforeEach() throws IOException {
    pathInvoices = Files.createTempFile(pathInvoices, "test_invoices", ".json");
    fileHelper = new FileHelper(pathInvoices.toString());
  }

  @AfterEach
  void closeAfterEach() throws IOException {
      Files.deleteIfExists(pathInvoices);
  }

  @Test
  void shouldReadAllLinesFromFile() throws IOException {
    //given
    List<String> expectedList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      expectedList.add(i + "line");
    }
    Files.write(pathInvoices, expectedList);

    //when
    List<String> actualList = (ArrayList<String>) fileHelper.readAllLines();

    //then
    assertEquals(expectedList, actualList);
  }

  @Test
  void shouldAddLineOfTextToFile() throws IOException {
    //given
    String stringToWriteToFile = "Line of text";
    String expected = "Line of text" + System.lineSeparator();

    //when
    fileHelper.addLine(stringToWriteToFile);
    String actual = "";
    actual = Files.readString(pathInvoices);

    //then
    assertEquals(expected, actual);
  }

  @Test
  void shouldRewriteFile() throws IOException {
    //given
    List<String> expectedList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      expectedList.add(i + "line");
    }

    //when
    fileHelper.rewriteFile(expectedList);
    List<String> actualList = new ArrayList<>();
    actualList = Files.readAllLines(pathInvoices);

    //then
    assertEquals(expectedList, actualList);
  }

  @Test
  void shouldTestFileHelperConstructor() {
    assertTrue(Files.exists(pathInvoices));
  }

  @Test
  void shouldThrowException() {
    assertThrows(NullPointerException.class, () -> new FileHelper(null));
  }
}
