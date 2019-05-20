package pl.coderstrust.invoices.model.hibernate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import pl.coderstrust.invoices.model.Invoice;

@Entity
@Table(name = "invoices")
public class InvoiceHibernate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String number;
  private LocalDate issueDate;
  private String issuePlace;
  private LocalDate sellDate;

  @ManyToOne(cascade = CascadeType.ALL)
  private CompanyHibernate seller;
  @ManyToOne(cascade = CascadeType.ALL)
  private CompanyHibernate buyer;
  @ManyToMany(cascade = CascadeType.ALL)
  private List<InvoiceEntryHibernate> entries;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public InvoiceHibernate(@JsonProperty("id") Long id,
      @JsonProperty("number") String number,
      @JsonProperty("issueDate") LocalDate issueDate,
      @JsonProperty("issuePlace") String issuePlace,
      @JsonProperty("sellDate") LocalDate sellDate,
      @JsonProperty("seller") CompanyHibernate seller,
      @JsonProperty("buyer") CompanyHibernate buyer,
      @JsonProperty("entries") List<InvoiceEntryHibernate> entries) {
    this.id = id;
    this.number = number;
    this.issueDate = issueDate;
    this.issuePlace = issuePlace;
    this.sellDate = sellDate;
    this.seller = seller;
    this.buyer = buyer;
    this.entries = entries;
  }

  public InvoiceHibernate() {
  }

  public InvoiceHibernate(Invoice invoice) {
    this.id = (Long) invoice.getId();
    this.number = invoice.getNumber();
    this.issueDate = invoice.getIssueDate();
    this.issuePlace = invoice.getIssuePlace();
    this.sellDate = invoice.getSellDate();
    this.seller = new CompanyHibernate(invoice.getSeller());
    this.buyer = new CompanyHibernate(invoice.getBuyer());
    this.entries = invoice.getEntries()
        .stream()
        .map(InvoiceEntryHibernate::new)
        .collect(Collectors.toList());
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public LocalDate getIssueDate() {
    return issueDate;
  }

  public void setIssueDate(LocalDate issueDate) {
    this.issueDate = issueDate;
  }

  public String getIssuePlace() {
    return issuePlace;
  }

  public void setIssuePlace(String issuePlace) {
    this.issuePlace = issuePlace;
  }

  public LocalDate getSellDate() {
    return sellDate;
  }

  public void setSellDate(LocalDate sellDate) {
    this.sellDate = sellDate;
  }

  public CompanyHibernate getSeller() {
    return seller;
  }

  public void setSeller(CompanyHibernate seller) {
    this.seller = seller;
  }

  public CompanyHibernate getBuyer() {
    return buyer;
  }

  public void setBuyer(CompanyHibernate buyer) {
    this.buyer = buyer;
  }

  public List<InvoiceEntryHibernate> getEntries() {
    return entries;
  }

  public void setEntries(List<InvoiceEntryHibernate> entries) {
    this.entries = entries;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    InvoiceHibernate invoice = (InvoiceHibernate) obj;

    if (id != null ? !id.equals(invoice.id) : invoice.id != null) {
      return false;
    }
    if (number != null ? !number.equals(invoice.number) : invoice.number != null) {
      return false;
    }
    if (issueDate != null ? !issueDate.equals(invoice.issueDate) : invoice.issueDate != null) {
      return false;
    }
    if (issuePlace != null ? !issuePlace.equals(invoice.issuePlace) : invoice.issuePlace != null) {
      return false;
    }
    if (sellDate != null ? !sellDate.equals(invoice.sellDate) : invoice.sellDate != null) {
      return false;
    }
    if (seller != null ? !seller.equals(invoice.seller) : invoice.seller != null) {
      return false;
    }
    if (buyer != null ? !buyer.equals(invoice.buyer) : invoice.buyer != null) {
      return false;
    }
    return entries != null ? entries.equals(invoice.entries) : invoice.entries == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (number != null ? number.hashCode() : 0);
    result = 31 * result + (issueDate != null ? issueDate.hashCode() : 0);
    result = 31 * result + (issuePlace != null ? issuePlace.hashCode() : 0);
    result = 31 * result + (sellDate != null ? sellDate.hashCode() : 0);
    result = 31 * result + (seller != null ? seller.hashCode() : 0);
    result = 31 * result + (buyer != null ? buyer.hashCode() : 0);
    result = 31 * result + (entries != null ? entries.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Invoice{"
        + "id=" + id
        + "number='" + number + '\''
        + ", issueDate=" + issueDate
        + ", issuePlace='" + issuePlace + '\''
        + ", sellDate=" + sellDate
        + ", seller=" + seller
        + ", buyer=" + buyer
        + ", entries=" + entries
        + '}';
  }
}
