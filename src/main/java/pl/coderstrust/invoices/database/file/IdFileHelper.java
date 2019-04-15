package pl.coderstrust.invoices.database.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

class IdFileHelper {

  private String filePath;

  IdFileHelper(String filePath) throws IOException {
    this.filePath = filePath;
    createInvoicesIdFile();
  }

  String getMaxId() throws FileNotFoundException {
    try (Scanner scanner = new Scanner(new File(filePath))) {
      return scanner.nextLine();
    }
  }

  void setNewId(Object id) throws IOException {
    try (BufferedWriter bufferedWriter = new BufferedWriter(
        new FileWriter(filePath))) {
      bufferedWriter.write(String.valueOf(id));
    }
  }

  private void createInvoicesIdFile() throws IOException {
    if (Files.exists(Paths.get(filePath))) {
      return;
    }
    try (BufferedWriter bufferedWriter = new BufferedWriter(
        new FileWriter(filePath))) {
      bufferedWriter.write("0");
    }
  }
}
