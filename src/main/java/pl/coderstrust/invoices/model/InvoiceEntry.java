package pl.coderstrust.invoices.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;

@ApiModel(value = "Invoice entry", description = "Describes an invoice entry")
public class InvoiceEntry {

  private Object id;
  
  @ApiModelProperty(value = "Product name", example = "MTB bike")
  private String productName;

  @ApiModelProperty(value = "Amount", example = "2")
  private String amount;

  @ApiModelProperty(value = "Price", example = "3499.99")
  private BigDecimal price;

  @ApiModelProperty(value = "TAX", example = "VAT_23")
  private Vat vat;

  public InvoiceEntry() {
  }

  public InvoiceEntry(Object id, String productName, String amount, BigDecimal price,
      Vat vat) {
    this.id = id;
    this.productName = productName;
    this.amount = amount;
    this.price = price;
    this.vat = vat;
  }

  public Object getId() {
    return id;
  }

  public void setId(Object id) {
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
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    InvoiceEntry that = (InvoiceEntry) obj;

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
