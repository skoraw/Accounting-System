package pl.coderstrust.invoices.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;
import pl.coderstrust.invoices.model.soap.Invoice;

@Repository
public class SoapInvoiceRepository {

  private List<Invoice> invoices = new ArrayList<>();

  public Invoice saveInvoice(Invoice invoice) {
    invoices.add(invoice);
    return invoice;
  }

  Collection<Invoice> getAllInvoices() {
    return invoices;
  }

  Invoice getInvoice(Object id) {
    Long idLong = (Long) id;
    return invoices.get(Math.toIntExact(idLong));
  }

  Collection<Invoice> getInvoicesBetweenDates(LocalDate fromDate,
      LocalDate toDate) {
    throw new IllegalArgumentException("Not implemented yet");
  }

  Invoice removeInvoice(Object id) {
    Long idLong = (Long) id;
    Invoice invoice = invoices.get(Math.toIntExact(idLong));
    invoices.remove(id);
    return invoice;
  }

}
