package pl.coderstrust.invoices.database.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

class InFileDatabaseTest {

  private static FileHelper fileHelper;
  private static IdFileHelper idFileHelper;
  private static Converter converter;
  private static InFileDatabase inFileDatabase;
  private Path pathIdInvoices = Paths.get("./src/test/resources/");
  private Path pathInvoices = Paths.get("./src/test/resources/");

  @BeforeAll
  static void setupBeforeALL() {
    inFileDatabase = new InFileDatabase(fileHelper, idFileHelper);
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
  }

  @AfterEach
  void closeAfterEach() {
    try {
      Files.deleteIfExists(pathInvoices);
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }
}