package pl.coderstrust.invoices.database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import pl.coderstrust.invoices.model.Invoice;

public interface Database {

  Invoice saveInvoice(Invoice invoice) throws DatabaseOperationException, IOException;

  Collection<Invoice> getAllInvoices() throws DatabaseOperationException, FileNotFoundException;

  Invoice getInvoice(Object id) throws DatabaseOperationException, FileNotFoundException;

  Collection<Invoice> getInvoicesInBetweenDates(LocalDate fromDate, LocalDate toDate)
      throws DatabaseOperationException, FileNotFoundException;

  Invoice removeInvoice(Object id) throws DatabaseOperationException, IOException;

}
