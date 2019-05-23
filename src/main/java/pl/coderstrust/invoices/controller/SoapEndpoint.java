package pl.coderstrust.invoices.controller;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.coderstrust.invoices.database.InvoiceBookException;
import pl.coderstrust.invoices.model.soap.AddInvoiceRequest;
import pl.coderstrust.invoices.model.soap.AddInvoiceResponse;
import pl.coderstrust.invoices.model.soap.Entry;
import pl.coderstrust.invoices.model.soap.GetInvoiceRequest;
import pl.coderstrust.invoices.model.soap.GetInvoiceResponse;
import pl.coderstrust.invoices.model.soap.Invoice;

@Endpoint
public class SoapEndpoint {

  private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

//  private InvoiceBook invoiceBook;
//
//  @Autowired
//  public SoapEndpoint(InvoiceBook invoiceBook) {
//    this.invoiceBook = invoiceBook;
//  }

  private SoapInvoiceRepository soapInvoiceRepository;

  @Autowired
  public SoapEndpoint(SoapInvoiceRepository soapInvoiceRepository) {
    this.soapInvoiceRepository = soapInvoiceRepository;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoiceRequest")
  @ResponsePayload
  public GetInvoiceResponse getInvoice(@RequestPayload GetInvoiceRequest request)
      throws InvoiceBookException {
    GetInvoiceResponse response = new GetInvoiceResponse();
    response.setInvoice(soapInvoiceRepository.getInvoice(request.getId()));
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addInvoiceRequest")
  @ResponsePayload
  public AddInvoiceResponse saveInvoice(@RequestPayload AddInvoiceRequest request)
      throws InvoiceBookException {
    AddInvoiceResponse response = new AddInvoiceResponse();
    Invoice invoice = new Invoice();
    invoice.setId(request.getId());
    invoice.setNumber(request.getNumber());
    invoice.setIssueDate(request.getIssueDate());
    invoice.setIssuePlace(request.getIssuePlace());
    invoice.setSellDate(request.getSellDate());
    invoice.setSeller(request.getSeller());
    invoice.setBuyer(request.getBuyer());
    List<Entry> entries = Collections.EMPTY_LIST;
    //dodac entries
    soapInvoiceRepository.saveInvoice(invoice);
    response.setInvoice(invoice);
    return response;
  }
}