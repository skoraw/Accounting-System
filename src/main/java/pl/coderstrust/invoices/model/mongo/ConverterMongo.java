package pl.coderstrust.invoices.model.mongo;

import org.springframework.stereotype.Component;
import pl.coderstrust.invoices.model.Invoice;
import pl.coderstrust.invoices.model.Invoice.InvoiceBuilder;

@Component
public class ConverterMongo {

  public Invoice getInvoice(InvoiceMongo invoiceMongo) {
    return new InvoiceBuilder()
        .id(invoiceMongo.getId())
        .number(invoiceMongo.getNumber())
        .issueDate(invoiceMongo.getIssueDate())
        .issuePlace(invoiceMongo.getIssuePlace())
        .sellDate(invoiceMongo.getSellDate())
        .seller(invoiceMongo.getSeller())
        .buyer(invoiceMongo.getBuyer())
        .entries(invoiceMongo.getEntries())
        .build();
  }
}
