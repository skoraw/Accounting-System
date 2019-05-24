package pl.coderstrust.invoices.controller;

import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.noFault;
import static org.springframework.ws.test.server.ResponseMatchers.payload;
import static org.springframework.ws.test.server.ResponseMatchers.validPayload;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.ws.test.server.MockWebServiceClient;
import pl.coderstrust.invoices.configuration.WebServiceConfig;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = WebServiceConfig.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SoapEndpointTest {

  @Autowired
  private ResourceLoader resourceLoader;

  @Autowired
  private WebApplicationContext applicationContext;

  private MockWebServiceClient mockClient;
  private Resource xsdSchema = new ClassPathResource("invoices.xsd");

  @BeforeEach
  void createClient() {
    mockClient = MockWebServiceClient.createClient(applicationContext);
  }

  @Test
  void shouldAddInvoice() throws IOException {
    Resource requestPayload = applicationContext
        .getResource("file:F:\\projects\\src\\test\\resources\\addInvoiceRequest.xml");
    Resource responsePayload = applicationContext
        .getResource("file:F:\\projects\\src\\test\\resources\\addInvoiceResponse.xml");

    mockClient
        .sendRequest(withPayload(requestPayload))
        .andExpect(noFault())
        .andExpect(payload(responsePayload))
        .andExpect(validPayload(xsdSchema));
  }


}