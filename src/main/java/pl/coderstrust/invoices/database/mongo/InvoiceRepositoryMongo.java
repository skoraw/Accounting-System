package pl.coderstrust.invoices.database.mongo;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.coderstrust.invoices.model.mongo.InvoiceMongo;

public interface InvoiceRepositoryMongo extends MongoRepository<InvoiceMongo, String> {

  List<InvoiceMongo> findByIssueDateBetween(LocalDate fromDate, LocalDate toDate);
}
