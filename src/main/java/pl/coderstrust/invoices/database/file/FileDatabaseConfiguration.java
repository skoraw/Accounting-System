package pl.coderstrust.invoices.database.file;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "database.type", havingValue = "in-file")
public class FileDatabaseConfiguration {

  @Value("${inFileDatabase.path}")
  private String invoicesFilePath;
  @Value("${inFileIdDatabase.path}")
  private String invoicesIdFilePath;

  @Qualifier("invoices")
  @Bean
  public FileHelper getInvoicesFileHelper() throws IOException {
    return new FileHelper(invoicesFilePath);
  }

  @Qualifier("id")
  @Bean
  public FileHelper getIdFileHelper() throws IOException {
    return new FileHelper(invoicesIdFilePath);
  }
}
