package pl.coderstrust.invoices.database.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import pl.coderstrust.invoices.model.Invoice;

class InvoiceConverter {

  private ObjectMapper objectMapper;

  InvoiceConverter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    this.objectMapper.registerModule(new JSR310Module());
    this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
  }

  String objectToString(Invoice invoice) throws IOException {
    return objectMapper.writeValueAsString(invoice);
  }

  Invoice stringToInvoice(String line) throws IOException {
    return objectMapper.readValue(line, Invoice.class);
  }

  Collection<Invoice> stringListToInvoicesList(List<String> list) throws IOException {
    return list.stream().map(n -> {
      try {
        return stringToInvoice(n);
      } catch (IOException exception) {
        return null;
      }
    }).collect(Collectors.toList());
  }

  Collection<String> invoicesListToStringList(List<Invoice> invoices) throws IOException {
    return invoices.stream().map(n -> {
      try {
        return objectToString(n);
      } catch (IOException exception) {
        return null;
      }
    }).collect(Collectors.toList());
  }
}
