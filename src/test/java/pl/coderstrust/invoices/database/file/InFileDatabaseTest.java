package pl.coderstrust.invoices.database.file;

import java.time.LocalDate;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.coderstrust.invoices.database.DatabaseOperationException;
import pl.coderstrust.invoices.model.Invoice;

@ExtendWith(TemporaryFolderExtension.class)
class InFileDatabaseTest {

  //robic nowy plik przy kazdym tescie i potem go usuwac
  @Test
  public void shouldAddInvoice() throws DatabaseOperationException {

    InFileDatabase inFileDatabase = new InFileDatabase(fileHelper, idFileHelper);
    Invoice invoice = new Invoice(null, null, LocalDate.of(2019, 4, 11), null,
        LocalDate.of(2019, 4, 11), null, null,
        Collections.EMPTY_LIST);

  }

}