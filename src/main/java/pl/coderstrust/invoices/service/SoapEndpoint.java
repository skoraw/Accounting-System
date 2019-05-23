package pl.coderstrust.invoices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.coderstrust.invoices.database.soap.InvoiceRepository;
import pl.coderstrust.invoices.model.soap.GetInvoiceRequest;
import pl.coderstrust.invoices.model.soap.GetInvoiceResponse;

@Endpoint
public class SoapEndpoint {

  private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

  private InvoiceRepository invoiceRepository;

  @Autowired
  public SoapEndpoint(InvoiceRepository invoiceRepository) {
    this.invoiceRepository = invoiceRepository;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoiceRequest")
  @ResponsePayload
  public GetInvoiceResponse getCountry(@RequestPayload GetInvoiceRequest request) {
    GetInvoiceResponse response = new GetInvoiceResponse();
    //response.setCountry(invoiceRepository.findCountry(request.getName()));

    return response;
  }
}