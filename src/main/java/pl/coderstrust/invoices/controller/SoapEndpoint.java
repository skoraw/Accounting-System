package pl.coderstrust.invoices.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.coderstrust.invoices.database.InvoiceBookException;
import pl.coderstrust.invoices.model.Company;
import pl.coderstrust.invoices.model.Company.CompanyBuilder;
import pl.coderstrust.invoices.model.Invoice;
import pl.coderstrust.invoices.model.InvoiceEntry;
import pl.coderstrust.invoices.model.hibernate.ConverterHibernate;
import pl.coderstrust.invoices.model.soap.AddInvoiceRequest;
import pl.coderstrust.invoices.model.soap.AddInvoiceResponse;
import pl.coderstrust.invoices.model.soap.CompanySoap;
import pl.coderstrust.invoices.model.soap.EntrySoap;
import pl.coderstrust.invoices.model.soap.GetInvoiceRequest;
import pl.coderstrust.invoices.model.soap.GetInvoiceResponse;
import pl.coderstrust.invoices.model.soap.InvoiceSoap;
import pl.coderstrust.invoices.model.soap.RemoveInvoiceRequest;
import pl.coderstrust.invoices.model.soap.RemoveInvoiceResponse;
import pl.coderstrust.invoices.service.InvoiceBook;

@Endpoint
public class SoapEndpoint {

  private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

  private InvoiceBook invoiceBook;
  private ConverterHibernate converterHibernate;

  @Autowired
  public SoapEndpoint(InvoiceBook invoiceBook, ConverterHibernate converterHibernate) {
    this.invoiceBook = invoiceBook;
    this.converterHibernate = converterHibernate;
  }

//  private SoapInvoiceRepository soapInvoiceRepository;
//
//  @Autowired
//  public SoapEndpoint(SoapInvoiceRepository soapInvoiceRepository) {
//    this.soapInvoiceRepository = soapInvoiceRepository;
//  }

  private String convertLocalDate(LocalDate localDate) {
    return localDate.getYear()
        + "-"
        + localDate.getMonthValue()
        + "-"
        + localDate.getDayOfMonth();
  }

  private InvoiceSoap getInvoiceSoap(Invoice invoice) {
    InvoiceSoap invoiceSoap = new InvoiceSoap();
    invoiceSoap.setId((Long) invoice.getId());
    invoiceSoap.setNumber(invoice.getNumber());
    invoiceSoap.setIssueDate(convertLocalDate(invoice.getIssueDate()));
    invoiceSoap.setIssuePlace(invoice.getIssuePlace());
    invoiceSoap.setSellDate(convertLocalDate(invoice.getSellDate()));
    invoiceSoap.setSeller(getCompanySoap(invoice.getSeller()));


  }

  private CompanySoap getCompanySoap(Company company) {

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

  private List<InvoiceEntry> getInvoiceEntryList(List<EntrySoap> listToConvert) {
    return listToConvert.stream()
        .map(entrySoap -> new InvoiceEntry(
            entrySoap.getId(),
            entrySoap.getProductName(),
            entrySoap.getAmount(),
            entrySoap.getPrice(),
            entrySoap.getVat()))
        .collect(Collectors.toList());
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoiceRequest")
  @ResponsePayload
  public GetInvoiceResponse getInvoice(@RequestPayload GetInvoiceRequest request)
      throws InvoiceBookException {
    GetInvoiceResponse response = new GetInvoiceResponse();
    //zamien na zwyklego invoica
    Invoice invoice = invoiceBook.getInvoice(request.getId());
    response.setInvoice(getInvoiceSoap(invoice));
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addInvoiceRequest")
  @ResponsePayload
  public AddInvoiceResponse saveInvoice(@RequestPayload AddInvoiceRequest request)
      throws InvoiceBookException {
    AddInvoiceResponse response = new AddInvoiceResponse();
    InvoiceSoap invoiceSoap = new InvoiceSoap();
    invoiceSoap.setId(request.getId());
    invoiceSoap.setNumber(request.getNumber());
    invoiceSoap.setIssueDate(request.getIssueDate());
    invoiceSoap.setIssuePlace(request.getIssuePlace());
    invoiceSoap.setSellDate(request.getSellDate());
    invoiceSoap.setSeller(request.getSeller());
    invoiceSoap.setBuyer(request.getBuyer());
    for (int i = 0; i < request.getEntries().size(); i++) {
      invoiceSoap.getEntries().add(request.getEntries().get(i));
    }
    //zamien na zwyklego invoica
    invoiceBook.saveInvoice(invoiceSoap);
    response.setInvoice(invoiceSoap);
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "removeInvoiceRequest")
  @ResponsePayload
  public RemoveInvoiceResponse removeInvoice(@RequestPayload RemoveInvoiceRequest request)
      throws InvoiceBookException {
    RemoveInvoiceResponse response = new RemoveInvoiceResponse();
    Invoice invoice = invoiceBook.getInvoice(request.getId());
    //zamien na zwyklego invoica
    invoiceBook.removeInvoice(request.getId());
    response.setInvoice(invoice);
    return response;
  }
}