package pl.coderstrust.invoices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.coderstrust.invoices.model.soap.GetInvoiceRequest;
import pl.coderstrust.invoices.model.soap.GetInvoiceResponse;
import pl.coderstrust.invoices.service.InvoiceBook;

@Endpoint
public class SoapEndpoint {

  private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

  private InvoiceBook invoiceBook;

  @Autowired
  public SoapEndpoint(InvoiceBook invoiceBook) {
    this.invoiceBook = invoiceBook;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoiceRequest")
  @ResponsePayload
  public GetInvoiceResponse getCountry(@RequestPayload GetInvoiceRequest request) {
    GetInvoiceResponse response = new GetInvoiceResponse();

    return response;
  }
}