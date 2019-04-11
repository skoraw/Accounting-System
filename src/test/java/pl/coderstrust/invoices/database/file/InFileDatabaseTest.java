package pl.coderstrust.invoices.database.file;

import java.time.LocalDate;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import pl.coderstrust.invoices.model.Invoice;

class InFileDatabaseTest {

  @Mock
  private FileHelper fileHelper;

  @Test
  public void shouldAddInvoice() {
    Invoice invoice = new Invoice(null, null, LocalDate.of(2019, 4, 11), null,
        LocalDate.of(2019, 4, 11), null, null,
        Collections.EMPTY_LIST);


  }

}