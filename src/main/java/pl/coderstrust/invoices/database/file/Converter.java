package pl.coderstrust.invoices.database.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import java.io.IOException;
import pl.coderstrust.invoices.model.Invoice;

public class Converter {

  private ObjectMapper objectMapper;

  public Converter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    this.objectMapper.registerModule(new JSR310Module());
    this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
  }

  public String objectToString(Invoice invoice) throws JsonProcessingException {
    return objectMapper.writeValueAsString(invoice);
  }

  public Invoice stringToInvoice(String line) {
    try {
      return objectMapper.readValue(line, Invoice.class);
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    return null;
  }
}
