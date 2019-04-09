package pl.coderstrust.invoices.database.file;

public class Configuration {

  private String invoicesFilePath;
  private String invoicesIdFilePath;

  public Configuration(String invoicesFilePath, String invoicesIdFilePath) {
    this.invoicesFilePath = invoicesFilePath;
    this.invoicesIdFilePath = invoicesIdFilePath;
  }

  public String getInvoicesFilePath() {
    return invoicesFilePath;
  }

  public String getInvoicesIdFilePath() {
    return invoicesIdFilePath;
  }
}
