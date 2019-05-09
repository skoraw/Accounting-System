package pl.coderstrust.invoices.database.hibernate;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import pl.coderstrust.invoices.model.hibernate.InvoiceHibernate;

public interface InvoiceRepository extends CrudRepository<InvoiceHibernate, Long> {

  List<InvoiceHibernate> findByIssueDateBetween(LocalDate fromDate, LocalDate toDate);
}
