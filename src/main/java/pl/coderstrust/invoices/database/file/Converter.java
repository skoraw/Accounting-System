package pl.coderstrust.invoices.database.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import pl.coderstrust.invoices.model.Invoice;

public class Converter {

  private ObjectMapper objectMapper;

  public Converter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    this.objectMapper.registerModule(new JSR310Module());
    this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
  }

  public String objectToString(Invoice invoice) {
    try {
      return objectMapper.writeValueAsString(invoice);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public Invoice stringToInvoice(String line) {
    try {
      return objectMapper.readValue(line, Invoice.class);
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    return null;
  }

  public Collection<Invoice> stringListToInvoicesList(List<String> list) {
    List<Invoice> invoicesList = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
      invoicesList.add(stringToInvoice(list.get(i)));
    }
    return invoicesList;
  }

  public Collection<String> invoicesListToStringList(List<Invoice> invoices) {
    List<String> stringList = new ArrayList<>();
    for (int i = 0; i < invoices.size(); i++) {
      stringList.add(objectToString(invoices.get(i)));
    }
    return stringList;
  }
}
