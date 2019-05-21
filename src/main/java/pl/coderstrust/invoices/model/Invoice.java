package pl.coderstrust.invoices.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.List;

@ApiModel(value = "Invoice", description = "Describes an invoice")
public class Invoice {

  @ApiModelProperty(value = "Invoice ID", dataType = "java.lang.Long", example = "3")
  private Object id;

  @ApiModelProperty(value = "Tax identification number", example = "1/5/2019")
  private String number;

  @ApiModelProperty(value = "Issue date", example = "2019-12-05")
  private LocalDate issueDate;

  @ApiModelProperty(value = "Issue place", example = "Kraków")
  private String issuePlace;

  @ApiModelProperty(value = "Sell date", example = "2019-12-05")
  private LocalDate sellDate;

  @ApiModelProperty(value = "Seller", example = "John Industries")
  private Company seller;

  @ApiModelProperty(value = "Buyer", example = "Januszex Z O. O.")
  private Company buyer;

  @ApiModelProperty(value = "List of entries")
  private List<InvoiceEntry> entries;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public Invoice(@JsonProperty("id") Object id,
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

  public Invoice() {
  }

  public Invoice(Invoice invoice) {
    this(invoice.getId(), invoice.getNumber(), invoice.getIssueDate(),
        invoice.getIssuePlace(), invoice.getSellDate(), invoice.getSeller(),
        invoice.getBuyer(), invoice.getEntries());
  }

  public static class InvoiceBuilder {

    private Object id;
    private String number;
    private LocalDate issueDate;
    private String issuePlace;
    private LocalDate sellDate;
    private Company seller;
    private Company buyer;
    private List<InvoiceEntry> entries;

    public InvoiceBuilder id(Object id) {
      this.id = id;
      return this;
    }

    public InvoiceBuilder number(String number) {
      this.number = number;
      return this;
    }

    public InvoiceBuilder issueDate(LocalDate issueDate) {
      this.issueDate = issueDate;
      return this;
    }

    public InvoiceBuilder issuePlace(String issuePlace) {
      this.issuePlace = issuePlace;
      return this;
    }

    public InvoiceBuilder sellDate(LocalDate sellDate) {
      this.sellDate = sellDate;
      return this;
    }

    public InvoiceBuilder seller(Company seller) {
      this.seller = seller;
      return this;
    }

    public InvoiceBuilder buyer(Company buyer) {
      this.buyer = buyer;
      return this;
    }

    public InvoiceBuilder entries(List<InvoiceEntry> entries) {
      this.entries = entries;
      return this;
    }

    public Invoice build() {
      return new Invoice(id, number, issueDate, issuePlace, sellDate, seller, buyer, entries);
    }
  }

  public static InvoiceBuilder builder() {
    return new InvoiceBuilder();
  }

  public Object getId() {
    return id;
  }

  public void setId(Object id) {
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
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Invoice invoice = (Invoice) obj;

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
