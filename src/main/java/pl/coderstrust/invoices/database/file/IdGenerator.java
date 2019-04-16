package pl.coderstrust.invoices.database.file;

import java.io.FileNotFoundException;
import java.io.IOException;

class IdGenerator {

  private FileHelper fileHelper;

  IdGenerator(FileHelper fileHelper) throws IOException {
    this.fileHelper = fileHelper;
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

//  private void createInvoicesIdFile() throws IOException {
//    if (Files.exists(Paths.get(filePath))) {
//      return;
//    }
//    try (BufferedWriter bufferedWriter = new BufferedWriter(
//        new FileWriter(filePath))) {
//      bufferedWriter.write("0");
//    }
//  }
}
