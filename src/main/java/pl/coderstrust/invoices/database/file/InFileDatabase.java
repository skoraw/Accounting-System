package pl.coderstrust.invoices.database.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collection;
import pl.coderstrust.invoices.database.Database;
import pl.coderstrust.invoices.database.DatabaseOperationException;
import pl.coderstrust.invoices.model.Invoice;

public class InFileDatabase implements Database {

  private static final String invoicesPath = "F:\\projects\\project-10-tomasz-wiktor\\src\\main\\resources\\invoices.txt";
  private static final String invoicesIdPath = "F:\\projects\\project-10-tomasz-wiktor\\src\\main\\resources\\id.txt";

  private Converter converter;
  private FileHelper fileHelper;
  private IdFileHelper idFileHelper;

  public InFileDatabase() {
    this.converter = new Converter(new ObjectMapper());
    this.fileHelper = new FileHelper(invoicesPath);
    this.idFileHelper = new IdFileHelper(invoicesIdPath);
  }

  @Override
  public Invoice saveInvoice(Invoice invoice) throws DatabaseOperationException {

    return null;
  }

  @Override
  public Collection<Invoice> getAllInvoices() throws DatabaseOperationException {
    return null;
  }

  @Override
  public Invoice getInvoice(Object id) throws DatabaseOperationException {
    return null;
  }

  @Override
  public Collection<Invoice> getInvoicesInBetweenDates(LocalDate fromDate, LocalDate toDate)
      throws DatabaseOperationException {
    return null;
  }

  @Override
  public Invoice removeInvoice(Object id) throws DatabaseOperationException {
    return null;
  }
}
