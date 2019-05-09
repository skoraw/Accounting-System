package pl.coderstrust.invoices.database.hibernate;

import org.springframework.data.repository.CrudRepository;
import pl.coderstrust.invoices.model.hibernate.InvoiceHibernate;

public interface InvoiceRepository extends CrudRepository<InvoiceHibernate, Long> {

}
