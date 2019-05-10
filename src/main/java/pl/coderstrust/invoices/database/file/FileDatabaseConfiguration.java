package pl.coderstrust.invoices.database.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileDatabaseConfiguration {

  @Value("${inFileDatabase.path}")
  private String invoicesFilePath;
  @Value("${inFileIdDatabase.path}")
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
