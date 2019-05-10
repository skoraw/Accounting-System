package pl.coderstrust.invoices.database.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
class FileHelper {

  private String filePath;

  FileHelper(String filePath) throws IOException {
    this.filePath = filePath;
    initFileDatabase();
  }

  void addLine(String line) throws IOException {
    try (BufferedWriter bufferedWriter = new BufferedWriter(
        new FileWriter(filePath, true))) {
      bufferedWriter.append(line);
      bufferedWriter.newLine();
    }
  }

  void rewriteFile(List<String> list) throws IOException {
    try (BufferedWriter bufferedWriter = new BufferedWriter(
        new FileWriter(filePath))) {
      for (String s : list) {
        bufferedWriter.write(s);
        bufferedWriter.newLine();
      }
    }
  }

  Collection<String> readAllLines() throws FileNotFoundException {
    try (Scanner scanner = new Scanner(new File(filePath))) {
      Collection<String> list = new ArrayList<>();
      String line;
      while (scanner.hasNext()) {
        line = scanner.nextLine();
        list.add(line);
      }
      return list;
    }
  }

  String readLine() throws FileNotFoundException {
    try (Scanner scanner = new Scanner(new File(filePath))) {
      if (scanner.hasNextLine()) {
        return scanner.nextLine();
      } else {
        return null;
      }
    }
  }

  void writeLine(Object id) throws IOException {
    try (BufferedWriter bufferedWriter = new BufferedWriter(
        new FileWriter(filePath))) {
      bufferedWriter.write(String.valueOf(id));
    }
  }

  private void initFileDatabase() throws IOException {
    if (Files.exists(Paths.get(filePath))) {
      return;
    }
    try (BufferedWriter bufferedWriter = new BufferedWriter(
        new FileWriter(filePath))) {
      bufferedWriter.write("");
    }
  }
}
