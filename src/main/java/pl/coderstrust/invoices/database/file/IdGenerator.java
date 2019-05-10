package pl.coderstrust.invoices.database.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
class IdGenerator {

  private FileHelper fileHelper;

  IdGenerator(FileHelper fileHelper) throws IOException {
    this.fileHelper = fileHelper;
    if (fileHelper.readLine() == null) {
      setNewId(0);
    }
  }

  int getId() throws FileNotFoundException {
    return Integer.parseInt(fileHelper.readLine());
  }

  int getNextId() throws FileNotFoundException {
    return Integer.parseInt(fileHelper.readLine()) + 1;
  }

  void setNewId(Object id) throws IOException {
    fileHelper.writeLine(id);
  }
}
