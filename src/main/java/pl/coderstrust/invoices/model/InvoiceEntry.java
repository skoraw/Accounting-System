package pl.coderstrust.invoices.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;

@ApiModel(value = "Invoice entry", description = "Describes an invoice entry")
public class InvoiceEntry {

  @ApiModelProperty(value = "Entry ID", example = "1")
  private Long id;

  @ApiModelProperty(value = "Product name", example = "MTB bike")
  private String productName;

  @ApiModelProperty(value = "Amount", example = "2")
  private String amount;

  @ApiModelProperty(value = "Price", example = "3499.99")
  private BigDecimal price;

  @ApiModelProperty(value = "TAX", example = "VAT_23")
  private Vat vat;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public InvoiceEntry(@JsonProperty("id") Long id,
      @JsonProperty("productName") String productName,
      @JsonProperty("amount") String amount,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("vat") Vat vat) {
    this.id = id;
    this.productName = productName;
    this.amount = amount;
    this.price = price;
    this.vat = vat;
  }

  public InvoiceEntry(InvoiceEntry invoiceEntry) {
    this(invoiceEntry.getId(),
        invoiceEntry.getProductName(),
        invoiceEntry.getAmount(),
        invoiceEntry.getPrice(),
        invoiceEntry.getVat());
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Vat getVat() {
    return vat;
  }

  public void setVat(Vat vat) {
    this.vat = vat;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    InvoiceEntry that = (InvoiceEntry) o;

    if (id != null ? !id.equals(that.id) : that.id != null) {
      return false;
    }
    if (productName != null ? !productName.equals(that.productName) : that.productName != null) {
      return false;
    }
    if (amount != null ? !amount.equals(that.amount) : that.amount != null) {
      return false;
    }
    if (price != null ? !price.equals(that.price) : that.price != null) {
      return false;
    }
    return vat == that.vat;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (productName != null ? productName.hashCode() : 0);
    result = 31 * result + (amount != null ? amount.hashCode() : 0);
    result = 31 * result + (price != null ? price.hashCode() : 0);
    result = 31 * result + (vat != null ? vat.hashCode() : 0);
    return result;
  }
}
