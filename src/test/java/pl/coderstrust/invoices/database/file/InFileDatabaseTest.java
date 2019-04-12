package pl.coderstrust.invoices.database.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.coderstrust.invoices.database.DatabaseOperationException;
import pl.coderstrust.invoices.model.Invoice;

class InFileDatabaseTest {

  //mockowac
  private static InFileDatabase inFileDatabase;
  private static String invoicesTestPath;
  private static String idTestPath;

  private static FileHelper fileHelper;
  private static IdFileHelper idFileHelper;
  Path pathInvoices = Paths.get(invoicesTestPath);
  Path pathId = Paths.get(idTestPath);


  @BeforeAll
  static void setupBeforeAll() {
    invoicesTestPath = "F:\\projects\\project-10-tomasz-wiktor\\src\\test\\resources\\test_invoices.txt";
    idTestPath = "F:\\projects\\project-10-tomasz-wiktor\\src\\test\\resources\\test_id.txt";
    fileHelper = new FileHelper(invoicesTestPath);
    idFileHelper = new IdFileHelper(idTestPath);
    inFileDatabase = new InFileDatabase(fileHelper, idFileHelper);
  }

  @BeforeEach
  void setupBeforeEach() throws IOException {
    Files.createFile(pathInvoices);
    Files.createFile(pathId);
    String str = "0";
    byte[] bs = str.getBytes();
    Files.write(pathInvoices, bs);
  }

  @AfterEach
  void closeAfterEach() throws IOException {
    Files.deleteIfExists(pathInvoices);
    Files.deleteIfExists(pathId);
  }

  //robic nowy plik przy kazdym tescie i potem go usuwac


  @Test
  void shouldAddInvoice() throws DatabaseOperationException {

    InFileDatabase inFileDatabase = new InFileDatabase(fileHelper, idFileHelper);
    Invoice invoice = new Invoice(null, null, LocalDate.of(2019, 4, 11), null,
        LocalDate.of(2019, 4, 11), null, null,
        Collections.EMPTY_LIST);
    inFileDatabase.saveInvoice(invoice);

  }

}