package pl.coderstrust.invoices.database;

public class InvoiceBookException extends Exception {

  public InvoiceBookException() {
  }

  public InvoiceBookException(String message) {
    super(message);
  }

  public InvoiceBookException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvoiceBookException(Throwable cause) {
    super(cause);
  }
}
