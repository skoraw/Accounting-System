package pl.coderstrust.invoices.database.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import pl.coderstrust.invoices.model.Invoice;

@Component
class InvoiceConverter {

  private ObjectMapper objectMapper;

  InvoiceConverter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  String objectToString(Invoice invoice) throws IOException {
    return objectMapper.writeValueAsString(invoice);
  }

  Invoice stringToInvoice(String line) throws IOException {
    return objectMapper.readValue(line, Invoice.class);
  }

  Collection<Invoice> stringListToInvoicesList(List<String> list) {
    return list
        .stream()
        .map(n -> {
          try {
            return stringToInvoice(n);
          } catch (IOException exception) {
            return null;
          }
        })
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  Collection<String> invoicesListToStringList(List<Invoice> invoices) {
    return invoices
        .stream()
        .map(n -> {
          try {
            return objectToString(n);
          } catch (IOException exception) {
            return null;
          }
        })
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }
}
