package pl.coderstrust.invoices.database;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import pl.coderstrust.invoices.model.Invoice;

public interface Database {

  Invoice saveInvoice(Invoice invoice) throws DatabaseOperationException, IOException;

  Collection<Invoice> getAllInvoices() throws DatabaseOperationException, IOException;

  Invoice getInvoice(Object id) throws DatabaseOperationException, IOException;

  Collection<Invoice> getInvoicesInBetweenDates(LocalDate fromDate, LocalDate toDate)
      throws DatabaseOperationException, IOException;

  Invoice removeInvoice(Object id) throws DatabaseOperationException, IOException;

}
