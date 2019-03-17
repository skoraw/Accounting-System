package pl.coderstrust.invoices.database;

import java.time.LocalDate;
import java.util.Collection;
import pl.coderstrust.invoices.model.Invoice;

public interface Database {

  Invoice saveInvoice(Invoice invoice) throws DatabaseOperationException;

  Collection<Invoice> getAllInvoices() throws DatabaseOperationException;

  Invoice getInvoice(Object id) throws DatabaseOperationException;

  Collection<Invoice> getInvoicesInBetweenDates(LocalDate fromDate, LocalDate toDate) throws DatabaseOperationException;

  Invoice removeInvoice(Object id) throws DatabaseOperationException;

}
