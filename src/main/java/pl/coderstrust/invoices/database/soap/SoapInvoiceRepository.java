package pl.coderstrust.invoices.database.soap;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import pl.coderstrust.invoices.model.soap.Invoice;

@Component
public class SoapInvoiceRepository {

  private static final Map<String, Invoice> invoices = new HashMap<>();

//	@PostConstruct
//	public void initData() {
//		Invoice spain = new Invoice();
//		spain.setName("Spain");
//		spain.setCapital("Madrid");
//		spain.setCurrency(Currency.EUR);
//		spain.setPopulation(46704314);
//
//		countries.put(spain.getName(), spain);
//
//		Invoice poland = new Invoice();
//		poland.setName("Poland");
//		poland.setCapital("Warsaw");
//		poland.setCurrency(Currency.PLN);
//		poland.setPopulation(38186860);
//
//		countries.put(poland.getName(), poland);
//
//		Invoice uk = new Invoice();
//		uk.setName("United Kingdom");
//		uk.setCapital("London");
//		uk.setCurrency(Currency.GBP);
//		uk.setPopulation(63705000);
//
//		countries.put(uk.getName(), uk);
//	}

  public Invoice findCountry(String name) {
    Assert.notNull(name, "The country's name must not be null");
    return invoices.get(name);
  }
}