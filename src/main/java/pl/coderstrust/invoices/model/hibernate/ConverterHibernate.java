package pl.coderstrust.invoices.model.hibernate;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import pl.coderstrust.invoices.model.Company;
import pl.coderstrust.invoices.model.Company.CompanyBuilder;
import pl.coderstrust.invoices.model.Invoice;
import pl.coderstrust.invoices.model.Invoice.InvoiceBuilder;
import pl.coderstrust.invoices.model.InvoiceEntry;

@Component
public class ConverterHibernate {

  public Invoice getInvoiceObject(InvoiceHibernate invoiceHibernate) {
    return new InvoiceBuilder()
        .id(invoiceHibernate.getId())
        .number(invoiceHibernate.getNumber())
        .issueDate(invoiceHibernate.getIssueDate())
        .issuePlace(invoiceHibernate.getIssuePlace())
        .sellDate(invoiceHibernate.getSellDate())
        .seller(getCompanyObject(invoiceHibernate.getSeller()))
        .buyer(getCompanyObject(invoiceHibernate.getBuyer()))
        .entries(getInvoiceEntryList(invoiceHibernate.getEntries()))
        .build();
  }

  private Company getCompanyObject(CompanyHibernate companyHibernate) {
    return new CompanyBuilder()
        .id(companyHibernate.getId())
        .name(companyHibernate.getName())
        .taxIdentificationNumber(companyHibernate.getTaxIdentificationNumber())
        .street(companyHibernate.getStreet())
        .postalCode(companyHibernate.getPostalCode())
        .town(companyHibernate.getTown())
        .build();
  }

  private List<InvoiceEntry> getInvoiceEntryList(List<InvoiceEntryHibernate> listToConvert) {
    return listToConvert.stream()
        .map(invoiceEntryHibernate -> new InvoiceEntry(
            invoiceEntryHibernate.getId(),
            invoiceEntryHibernate.getProductName(),
            invoiceEntryHibernate.getAmount(),
            invoiceEntryHibernate.getPrice(),
            invoiceEntryHibernate.getVat()))
        .collect(Collectors.toList());
  }
}
