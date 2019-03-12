package pl.coderstrust.invoices.model;

import java.time.LocalDate;
import java.util.List;

public class Invoice {

  private Object id;
  private String number;
  private LocalDate issueDate;
  private String issuePlace;
  private LocalDate sellDate;
  private Company seller;
  private Company buyer;
  private List<InvoiceEntry> entries;

  public Invoice(Object id, String number, LocalDate issueDate, String issuePlace, LocalDate sellDate,
      Company seller, Company buyer, List<InvoiceEntry> entries) {
    this.id = id;
    this.number = number;
    this.issueDate = issueDate;
    this.issuePlace = issuePlace;
    this.sellDate = sellDate;
    this.seller = seller;
    this.buyer = buyer;
    this.entries = entries;
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Invoice invoice = (Invoice) o;

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
