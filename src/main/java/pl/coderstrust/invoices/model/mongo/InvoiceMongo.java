package pl.coderstrust.invoices.model.mongo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.annotation.Id;
import pl.coderstrust.invoices.model.Company;
import pl.coderstrust.invoices.model.Invoice;
import pl.coderstrust.invoices.model.InvoiceEntry;

public class InvoiceMongo {

  @Id
  private String id;
  private String number;
  private LocalDate issueDate;
  private String issuePlace;
  private LocalDate sellDate;
  private Company seller;
  private Company buyer;
  private List<InvoiceEntry> entries;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public InvoiceMongo(@JsonProperty("id") String id,
      @JsonProperty("number") String number,
      @JsonProperty("issueDate") LocalDate issueDate,
      @JsonProperty("issuePlace") String issuePlace,
      @JsonProperty("sellDate") LocalDate sellDate,
      @JsonProperty("seller") Company seller,
      @JsonProperty("buyer") Company buyer,
      @JsonProperty("entries") List<InvoiceEntry> entries) {
    this.id = id;
    this.number = number;
    this.issueDate = issueDate;
    this.issuePlace = issuePlace;
    this.sellDate = sellDate;
    this.seller = seller;
    this.buyer = buyer;
    this.entries = entries;
  }

  public InvoiceMongo() {
  }

  public InvoiceMongo(Invoice invoice) {
    this((String) invoice.getId(), invoice.getNumber(), invoice.getIssueDate(),
        invoice.getIssuePlace(), invoice.getSellDate(), invoice.getSeller(),
        invoice.getBuyer(), invoice.getEntries());
  }

  public static class MongoInvoiceBuilder {

    private String id;
    private String number;
    private LocalDate issueDate;
    private String issuePlace;
    private LocalDate sellDate;
    private Company seller;
    private Company buyer;
    private List<InvoiceEntry> entries;

    public InvoiceMongo.MongoInvoiceBuilder id(String id) {
      this.id = id;
      return this;
    }

    public InvoiceMongo.MongoInvoiceBuilder number(String number) {
      this.number = number;
      return this;
    }

    public InvoiceMongo.MongoInvoiceBuilder issueDate(LocalDate issueDate) {
      this.issueDate = issueDate;
      return this;
    }

    public InvoiceMongo.MongoInvoiceBuilder issuePlace(String issuePlace) {
      this.issuePlace = issuePlace;
      return this;
    }

    public InvoiceMongo.MongoInvoiceBuilder sellDate(LocalDate sellDate) {
      this.sellDate = sellDate;
      return this;
    }

    public InvoiceMongo.MongoInvoiceBuilder seller(Company seller) {
      this.seller = seller;
      return this;
    }

    public InvoiceMongo.MongoInvoiceBuilder buyer(Company buyer) {
      this.buyer = buyer;
      return this;
    }

    public InvoiceMongo.MongoInvoiceBuilder entries(List<InvoiceEntry> entries) {
      this.entries = entries;
      return this;
    }

    public InvoiceMongo build() {
      return new InvoiceMongo(id, number, issueDate, issuePlace, sellDate, seller, buyer, entries);
    }
  }

  public static MongoInvoiceBuilder builder() {
    return new MongoInvoiceBuilder();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
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

  public Company getSeller() {
    return seller;
  }

  public void setSeller(Company seller) {
    this.seller = seller;
  }

  public Company getBuyer() {
    return buyer;
  }

  public void setBuyer(Company buyer) {
    this.buyer = buyer;
  }

  public List<InvoiceEntry> getEntries() {
    return entries;
  }

  public void setEntries(List<InvoiceEntry> entries) {
    this.entries = entries;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    InvoiceMongo that = (InvoiceMongo) o;

    if (id != null ? !id.equals(that.id) : that.id != null) {
      return false;
    }
    if (number != null ? !number.equals(that.number) : that.number != null) {
      return false;
    }
    if (issueDate != null ? !issueDate.equals(that.issueDate) : that.issueDate != null) {
      return false;
    }
    if (issuePlace != null ? !issuePlace.equals(that.issuePlace) : that.issuePlace != null) {
      return false;
    }
    if (sellDate != null ? !sellDate.equals(that.sellDate) : that.sellDate != null) {
      return false;
    }
    if (seller != null ? !seller.equals(that.seller) : that.seller != null) {
      return false;
    }
    if (buyer != null ? !buyer.equals(that.buyer) : that.buyer != null) {
      return false;
    }
    return entries != null ? entries.equals(that.entries) : that.entries == null;
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
    return "InvoiceMongo{" +
        "id='" + id + '\'' +
        ", number='" + number + '\'' +
        ", issueDate=" + issueDate +
        ", issuePlace='" + issuePlace + '\'' +
        ", sellDate=" + sellDate +
        ", seller=" + seller +
        ", buyer=" + buyer +
        ", entries=" + entries +
        '}';
  }
}
