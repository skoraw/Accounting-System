package pl.coderstrust.invoices.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.coderstrust.invoices.database.InvoiceBookException;
import pl.coderstrust.invoices.model.Invoice;
import pl.coderstrust.invoices.service.InvoiceBook;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

  private final InvoiceBook invoiceBook;

  public InvoiceController(InvoiceBook invoiceBook) {
    this.invoiceBook = invoiceBook;
  }

  @GetMapping
  public Collection<Invoice> getAllInvoices() throws InvoiceBookException {
    return invoiceBook.getAllInvoices();
  }

  @PutMapping
  public Invoice addInvoice(@RequestBody Invoice invoice) throws InvoiceBookException {
    return invoiceBook.saveInvoice(invoice);
  }

  @DeleteMapping("/{id}")
  public Invoice removeInvoice(@PathVariable("id") Long id) throws InvoiceBookException {
    return invoiceBook.removeInvoice(id);
  }

  @GetMapping("/{id}")
  public Invoice getInvoice(@PathVariable("id") Long id) throws InvoiceBookException {
    return invoiceBook.getInvoice(id);
  }

  @GetMapping("/byDates")
  public Collection<Invoice> getInvoiceBetweenDates(@RequestParam("fromDate") String fromDate,
      @RequestParam("toDate") String toDate) throws InvoiceBookException {
    LocalDate fromDateConverter = LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(fromDate));
    LocalDate toDateConverter = LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(toDate));
    return invoiceBook.getInvoicesBetweenDates(fromDateConverter, toDateConverter);
  }
}
