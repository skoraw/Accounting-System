package pl.coderstrust.invoices.database.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import pl.coderstrust.invoices.model.Invoice;

public class FileHelper {

  private String filePath;
  private Converter converter;

  public FileHelper(String filePath) {
    this.filePath = filePath;
    this.converter = new Converter(new ObjectMapper());
    createFileDatabase();
  }

  public Collection<Invoice> readBetweenDates(LocalDate fromDate, LocalDate toDate) {
    if (fromDate.isBefore(toDate)) {
      try (Scanner scanner = new Scanner(new File(filePath))) {
        List<Invoice> list = new ArrayList<>();
        String line = "";
        Invoice invoice;
        fromDate = fromDate.minusDays(1);
        toDate = toDate.plusDays(1);
        while (scanner.hasNext()) {
          line = scanner.nextLine();
          invoice = converter.stringToInvoice(line);
          if (invoice.getSellDate().isAfter(fromDate) && invoice.getSellDate().isBefore(toDate)) {
            list.add(invoice);
          }
        }
        return list;
      } catch (IOException exception) {
        exception.printStackTrace();
      }
    } else {
      throw new IllegalArgumentException("fromDate must be earlier date than toDate");
    }
    return null;
  }

  //OK
////////////////////////////////////////////
  public boolean isInvoiceID(Object id) {
    try (Scanner scanner = new Scanner(new File(filePath))) {
      String line = "";
      Invoice invoice;
      while (scanner.hasNext()) {
        line = scanner.nextLine();
        invoice = converter.stringToInvoice(line);
        if (invoice != null && invoice.getId().equals(id)) {
          return true;
        }
      }
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    return false;
  }

  /////////////////////////////////////////
  private void createInvoicesFile() {
    if (Files.exists(Paths.get(filePath))) {
      return;
    }
    try (BufferedWriter bufferedWriter = new BufferedWriter(
        new FileWriter(filePath))) {
      bufferedWriter.write("");
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  /////////////////////////////////////////////////
  public void addInvoice(String line) {
    try (BufferedWriter bufferedWriter = new BufferedWriter(
        new FileWriter(filePath, true))) {
      bufferedWriter.append(line);
      bufferedWriter.newLine();
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  /////////////////////////////////////////////////////////
  public void rewriteFile(List<String> list) {
    try (BufferedWriter bufferedWriter = new BufferedWriter(
        new FileWriter(filePath))) {
      for (int i = 0; i < list.size(); i++) {
        bufferedWriter.write(list.get(i));
        bufferedWriter.newLine();
      }
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  //////////////////////////////////////////////////
  public Collection<String> readAllLines() {
    try (Scanner scanner = new Scanner(new File(filePath))) {
      Collection<String> list = new ArrayList<>();
      String line = "";
      while (scanner.hasNext()) {
        line = scanner.nextLine();
        list.add(line);
      }
      return list;
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    return null;
  }
}
