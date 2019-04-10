package pl.coderstrust.invoices.database.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class IdFileHelper {

  private String filePath;

  public IdFileHelper(String filePath) {
    this.filePath = filePath;
  }

  public void createInvoicesIdFile() {
    if (Files.exists(Paths.get(filePath))) {
      return;
    }
    try (BufferedWriter bufferedWriter = new BufferedWriter(
        new FileWriter(filePath))) {
      bufferedWriter.write("0");
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  public String getMaxId() {
    try (Scanner scanner = new Scanner(new File(filePath))) {
      return scanner.nextLine();
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    return null;
  }

  public void setNewId(Object id) {
    try (BufferedWriter bufferedWriter = new BufferedWriter(
        new FileWriter(filePath))) {
      bufferedWriter.write(String.valueOf(id));
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }
}
