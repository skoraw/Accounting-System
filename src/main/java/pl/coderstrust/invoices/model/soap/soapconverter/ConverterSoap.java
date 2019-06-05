package pl.coderstrust.invoices.model.soap.soapconverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import pl.coderstrust.invoices.model.Company;
import pl.coderstrust.invoices.model.Company.CompanyBuilder;
import pl.coderstrust.invoices.model.Invoice;
import pl.coderstrust.invoices.model.Invoice.InvoiceBuilder;
import pl.coderstrust.invoices.model.InvoiceEntry;
import pl.coderstrust.invoices.model.soap.CompanySoap;
import pl.coderstrust.invoices.model.soap.EntrySoap;
import pl.coderstrust.invoices.model.soap.InvoiceSoap;
import pl.coderstrust.invoices.model.soap.Vat;

@Component
public class ConverterSoap {

  public InvoiceSoap getInvoiceSoap(Invoice invoice) {
    InvoiceSoap invoiceSoap = new InvoiceSoap();
    invoiceSoap.setId((Long) invoice.getId());
    invoiceSoap.setNumber(invoice.getNumber());
    invoiceSoap.setIssueDate(convertLocalDate(invoice.getIssueDate()));
    invoiceSoap.setIssuePlace(invoice.getIssuePlace());
    invoiceSoap.setSellDate(convertLocalDate(invoice.getSellDate()));
    invoiceSoap.setSeller(getCompanySoap(invoice.getSeller()));
    invoiceSoap.setBuyer(getCompanySoap(invoice.getBuyer()));
    for (int i = 0; i < invoice.getEntries().size(); i++) {
      invoiceSoap.getEntries().add(getEntrySoap(invoice.getEntries().get(i)));
    }
    return invoiceSoap;
  }

  public Invoice getInvoice(InvoiceSoap invoiceSoap) {
    return new InvoiceBuilder()
        .id(invoiceSoap.getId())
        .number(invoiceSoap.getNumber())
        .issueDate(convertStringToLocalDate(invoiceSoap.getIssueDate()))
        .issuePlace(invoiceSoap.getIssuePlace())
        .sellDate(convertStringToLocalDate(invoiceSoap.getSellDate()))
        .seller(getCompany(invoiceSoap.getSeller()))
        .buyer(getCompany(invoiceSoap.getBuyer()))
        .entries(getInvoiceEntriesList(invoiceSoap.getEntries()))
        .build();
  }

  private CompanySoap getCompanySoap(Company company) {
    CompanySoap companySoap = new CompanySoap();
    companySoap.setId((Long) company.getId());
    companySoap.setName(company.getName());
    companySoap.setTaxIdentificationNumber(company.getTaxIdentificationNumber());
    companySoap.setStreet(company.getStreet());
    companySoap.setPostalCode(company.getPostalCode());
    companySoap.setTown(company.getTown());
    return companySoap;
  }

  private EntrySoap getEntrySoap(InvoiceEntry invoicEntry) {
    EntrySoap entrySoap = new EntrySoap();
    entrySoap.setId((Long) invoicEntry.getId());
    entrySoap.setProductName(invoicEntry.getProductName());
    entrySoap.setAmount(invoicEntry.getAmount());
    entrySoap.setPrice(invoicEntry.getPrice());
    entrySoap.setVat(getVatSoap(invoicEntry.getVat()));
    return entrySoap;
  }

  private Company getCompany(CompanySoap companySoap) {
    return new CompanyBuilder()
        .id(companySoap.getId())
        .name(companySoap.getName())
        .taxIdentificationNumber(companySoap.getTaxIdentificationNumber())
        .street(companySoap.getStreet())
        .postalCode(companySoap.getPostalCode())
        .town(companySoap.getTown())
        .build();
  }

  private List<InvoiceEntry> getInvoiceEntriesList(List<EntrySoap> entrySoap) {
    List<InvoiceEntry> invoiceEntryList = new ArrayList<>();
    for (EntrySoap soap : entrySoap) {
      InvoiceEntry invoiceEntry = new InvoiceEntry();
      invoiceEntry.setId(soap.getId());
      invoiceEntry.setProductName(soap.getProductName());
      invoiceEntry.setAmount(soap.getAmount());
      invoiceEntry.setPrice(soap.getPrice());
      invoiceEntry.setVat(getVatInvoice(soap.getVat()));
      invoiceEntry.setVat(getVatInvoice(soap.getVat()));
      invoiceEntryList.add(invoiceEntry);
    }
    return invoiceEntryList;
  }

  private pl.coderstrust.invoices.model.Vat getVatInvoice(Vat vat) {
    return pl.coderstrust.invoices.model.Vat.valueOf(vat.value());
  }

  private Vat getVatSoap(pl.coderstrust.invoices.model.Vat vat) {
    BigDecimal vatValue = vat.getValue();
    if (vatValue.compareTo(BigDecimal.valueOf(0.23)) == 0) {
      return Vat.valueOf("VAT_23");
    } else if (vatValue.compareTo(BigDecimal.valueOf(0.08)) == 0) {
      return Vat.valueOf("VAT_8");
    } else if (vatValue.compareTo(BigDecimal.valueOf(0.05)) == 0) {
      return Vat.valueOf("VAT_5");
    } else if (vatValue.compareTo(BigDecimal.valueOf(0)) == 0) {
      return Vat.valueOf("VAT_0");
    }
    return null;
  }

  public LocalDate convertStringToLocalDate(String date) {
    return LocalDate.parse(date);
  }

  private String convertLocalDate(LocalDate localDate) {
    return localDate.getYear()
        + "-"
        + localDate.getMonthValue()
        + "-"
        + localDate.getDayOfMonth();
  }
}
