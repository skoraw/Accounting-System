package pl.coderstrust.invoices.database.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import pl.coderstrust.invoices.model.Invoice;

class Converter {

  private ObjectMapper objectMapper;

  Converter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    this.objectMapper.registerModule(new JSR310Module());
    this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
  }

  String objectToString(Invoice invoice) {
    try {
      return objectMapper.writeValueAsString(invoice);
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    return null;
  }

  Invoice stringToInvoice(String line) {
    try {
      return objectMapper.readValue(line, Invoice.class);
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    return null;
  }

  Collection<Invoice> stringListToInvoicesList(List<String> list) {
    List<Invoice> invoicesList = new ArrayList<>();
    for (String s : list) {
      invoicesList.add(stringToInvoice(s));
    }
    return invoicesList;
  }

  Collection<String> invoicesListToStringList(List<Invoice> invoices) {
    List<String> stringList = new ArrayList<>();
    for (Invoice invoice : invoices) {
      stringList.add(objectToString(invoice));
    }
    return stringList;
  }
}
