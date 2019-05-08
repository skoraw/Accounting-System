package pl.coderstrust.invoices.database.file;

public class FileDatabaseConfiguration {

  private String invoicesFilePath;
  private String invoicesIdFilePath;

  public FileDatabaseConfiguration(String invoicesFilePath, String invoicesIdFilePath) {
    this.invoicesFilePath = invoicesFilePath;
    this.invoicesIdFilePath = invoicesIdFilePath;
  }

  String getInvoicesFilePath() {
    return invoicesFilePath;
  }

  String getInvoicesIdFilePath() {
    return invoicesIdFilePath;
  }
}
