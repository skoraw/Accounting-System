package pl.coderstrust.invoices.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.coderstrust.invoices.model.Invoice;
import pl.coderstrust.invoices.service.InvoiceBook;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class InvoiceControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private InvoiceBook invoiceBook;

  @Autowired
  private ObjectMapper objectMapper;

  private Invoice invoice = Invoice.builder()
      .id(1L)
      .number("23/11/2019")
      .buyer(null)
      .issueDate(LocalDate.of(2018, 11, 11))
      .issuePlace("SomePlace")
      .sellDate(LocalDate.of(2013, 11, 27))
      .seller(null)
      .entries(null)
      .build();

  private Invoice invoice2nd = Invoice.builder()
      .id(2L)
      .number("01/01/2011")
      .buyer(null)
      .issueDate(LocalDate.now())
      .issuePlace("SomePlace")
      .sellDate(LocalDate.of(2017, 11, 27))
      .seller(null)
      .entries(null)
      .build();


  private String toJson(Invoice invoice) throws JsonProcessingException {
    return objectMapper.writeValueAsString(invoice);
  }

  @Test
  public void shouldReturnEmptyArray() throws Exception {
    mvc.perform(get("/invoice"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  public void shouldReturnArrayOfInvoicesWithTwoInvoices() throws Exception {
    when(invoiceBook.getAllInvoices()).thenReturn(Arrays.asList(invoice, invoice2nd));

    mvc.perform(get("/invoice"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].number", is("23/11/2019")))
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(jsonPath("$[1].number", is("01/01/2011")))
        .andExpect(jsonPath("$[1].id", is(2)));
  }

  @Test
  public void shouldAddInvoiceToDatabase() throws Exception {
    when(invoiceBook.saveInvoice(invoice2nd)).thenReturn(invoice2nd);

    mvc.perform(put("/invoice").content(toJson(invoice2nd))
        .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldDeleteInvoice() throws Exception {
    when(invoiceBook.removeInvoice(2L)).thenReturn(invoice2nd);

    mvc.perform(delete("/invoice/{id}", 2))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(2)));

    verify(invoiceBook, times(1)).removeInvoice(2L);
  }

  @Test
  public void shouldReturnInvoiceWithPassedId() throws Exception {
    when(invoiceBook.getInvoice(2L)).thenReturn(invoice2nd);

    mvc.perform(get("/invoice/{id}", 2)
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(2)));

    verify(invoiceBook, times(1)).getInvoice(2L);
  }

  @Test
  public void shouldReturnArrayWithInvoiceBetweenDates() throws Exception {
    when(invoiceBook.getInvoicesBetweenDates(LocalDate.of(2011, 5, 21), LocalDate.of(2019, 9, 21)))
        .thenReturn(Arrays.asList(invoice, invoice2nd));

    mvc.perform(get("/invoice/byDates")
        .param("fromDate", "2015-05-21")
        .param("toDate", "2019-05-21"))
        .andDo(print())
        .andExpect(status().isOk());
  }
}
