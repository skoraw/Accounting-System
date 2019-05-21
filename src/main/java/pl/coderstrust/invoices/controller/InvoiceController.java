package pl.coderstrust.invoices.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.coderstrust.invoices.database.InvoiceBookException;
import pl.coderstrust.invoices.model.Invoice;
import pl.coderstrust.invoices.service.InvoiceBook;

@RestController
@Api(tags = "Invoices", description = "Available operations")
public class InvoiceController {

  private final InvoiceBook invoiceBook;

  public InvoiceController(InvoiceBook invoiceBook) {
    this.invoiceBook = invoiceBook;
  }

  @GetMapping("/invoices")
  @ApiOperation(value = "Get invoices from given range of time, if dates are not provided all invoices will be returned", notes = "Date format: YYYY-MM-DD")
  public Collection<Invoice> getInvoiceBetweenDates(
      @RequestParam(value = "fromDate", required = false) @ApiParam(value = "Begin date - exclusive", example = "YYYY-MM-DD") String fromDate,
      @RequestParam(value = "toDate", required = false) @ApiParam(value = "End date - exclusive", example = "YYYY-MM-DD") String toDate)
      throws InvoiceBookException {
    if (fromDate == null && toDate == null) {
      return invoiceBook.getAllInvoices();
    }

    LocalDate fromDateConverter =
        fromDate != null ? LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(fromDate))
            : LocalDate.MIN;
    LocalDate toDateConverter =
        toDate != null ? LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(toDate))
            : LocalDate.MAX;

    return invoiceBook.getInvoicesBetweenDates(fromDateConverter, toDateConverter);
  }

  @PutMapping("/invoice")
  @ApiOperation(value = "Insert or update an invoice")
  public Invoice addInvoice(
      @ApiParam(value = "Invoice document", name = "Invoice") @RequestBody Invoice invoice)
      throws InvoiceBookException {
    return invoiceBook.saveInvoice(invoice);
  }

  @DeleteMapping("/invoice/{id}")
  @ApiOperation(value = "Remove the invoice")
  public Invoice removeInvoice(
      @PathVariable("id") @ApiParam(value = "Invoice ID", example = "2") Long id)
      throws InvoiceBookException {
    return invoiceBook.removeInvoice(id);
  }

  @GetMapping("/invoice/{id}")
  @ApiOperation(value = "Get the invoice")
  public Invoice getInvoice(
      @PathVariable("id") @ApiParam(value = "Invoice ID", example = "2") Long id)
      throws InvoiceBookException {
    return invoiceBook.getInvoice(id);
  }

}
