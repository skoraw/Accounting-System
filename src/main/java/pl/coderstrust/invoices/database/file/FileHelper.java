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



  public Invoice add(Invoice invoice) {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null");
    } else if (isInvoiceID(invoice.getId())) {
      throw new IllegalArgumentException("Invoice with given id exists");
    }
    Integer maxId = Integer.parseInt(getMaxId());
    if (invoice.getId() != null) {
      if ((Integer) invoice.getId() <= maxId) {
        throw new IllegalArgumentException("Id can not be lower or equal than actual max Id");
      }
    }
    String id;
    Invoice copiedInvoice = invoice.deepCopy(invoice);
    maxId++;
    if (copiedInvoice.getId() == null) {
      id = String.valueOf(maxId);
      copiedInvoice.setId(maxId);
    } else {
      id = String.valueOf(copiedInvoice.getId());
    }
    addInvoice(copiedInvoice);
    addId(id);
    return copiedInvoice;
  }

  private void addInvoice(Invoice invoice) {
    try (BufferedWriter bufferedWriter = new BufferedWriter(
        new FileWriter(filePath, true))) {
      String line = converter.objectToString(invoice);
      bufferedWriter.append(line);
      bufferedWriter.newLine();
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }



  public Collection<Invoice> readAll() {
    try (Scanner scanner = new Scanner(new File(filePath))) {
      Collection<Invoice> list = new ArrayList<>();
      String line = "";
      while (scanner.hasNext()) {
        line = scanner.nextLine();
        list.add(converter.stringToInvoice(line));
      }
      return list;
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    return null;
  }

  public Invoice readInvoice(Object id) {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    if (isInvoiceID(id)) {
      List<Invoice> invoiceList;
      invoiceList = (ArrayList<Invoice>) readAll();
      for (int i = 0; i < invoiceList.size(); i++) {
        if (invoiceList.get(i).getId().equals(id)) {
          return invoiceList.get(i);
        }
      }
    } else {
      throw new IllegalArgumentException("Invoice with given id not exists");
    }
    return null;
  }

  public Invoice remove(Object id) {
    if (id == null) {
      throw new IllegalArgumentException("Invoice cannot be null");
    }
    if (isInvoiceID(id)) {
      List<Invoice> invoices = (ArrayList<Invoice>) readAll();
      try (BufferedWriter bufferedWriter = new BufferedWriter(
          new FileWriter(filePath))) {
        Invoice invoice = new Invoice();
        for (int i = 0; i < invoices.size(); i++) {
          if (!invoices.get(i).getId().equals(id)) {
            bufferedWriter.write(converter.objectToString(invoices.get(i)));
            bufferedWriter.newLine();
          } else {
            invoice = invoices.get(i).deepCopy(invoices.get(i));
          }
        }
        return invoice;
      } catch (IOException exception) {
        exception.printStackTrace();
      }
    } else {
      throw new IllegalArgumentException("Invoice with given id not exists");
    }
    return null;
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

  private boolean isInvoiceID(Object id) {
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
}
