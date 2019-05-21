package pl.coderstrust.invoices.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Company", description = "Describes a company")
public class Company {

  @ApiModelProperty(value = "Company ID", dataType = "java.lang.Long", example = "3")
  private Object id;
  
  @ApiModelProperty(value = "Company name", example = "CodersTrust")
  private String name;

  @ApiModelProperty(value = "Tax ID Number", example = "555-444-12-32")
  private String taxIdentificationNumber;

  @ApiModelProperty(value = "Street", example = "Niepodległosci 23")
  private String street;

  @ApiModelProperty(value = "Postal code", example = "45-789")
  private String postalCode;

  @ApiModelProperty(value = "City", example = "Kraków")
  private String town;
  
  public Company() {
  }

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  private Company(@JsonProperty("id") Object id,
      @JsonProperty("name") String name,
      @JsonProperty("taxIdentificationNumber") String taxIdentificationNumber,
      @JsonProperty("street") String street,
      @JsonProperty("postalCode") String postalCode,
      @JsonProperty("town") String town) {
    this.id = id;
    this.name = name;
    this.taxIdentificationNumber = taxIdentificationNumber;
    this.street = street;
    this.postalCode = postalCode;
    this.town = town;
  }

  public Object getId() {
    return id;
  }

  public static CompanyBuilder builder() {
    return new CompanyBuilder();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Company company = (Company) obj;

    if (id != null ? !id.equals(company.id) : company.id != null) {
      return false;
    }

    if (name != null ? !name.equals(company.name) : company.name != null) {
      return false;
    }
    if (taxIdentificationNumber != null ? !taxIdentificationNumber
        .equals(company.taxIdentificationNumber) : company.taxIdentificationNumber != null) {
      return false;
    }
    if (street != null ? !street.equals(company.street) : company.street != null) {
      return false;
    }
    if (postalCode != null ? !postalCode.equals(company.postalCode) : company.postalCode != null) {
      return false;
    }
    return town != null ? town.equals(company.town) : company.town == null;
  }

  public String getName() {
    return name;
  }

  public String getTaxIdentificationNumber() {
    return taxIdentificationNumber;
  }

  public String getStreet() {
    return street;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public String getTown() {
    return town;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result =
        31 * result + (taxIdentificationNumber != null ? taxIdentificationNumber.hashCode() : 0);
    result = 31 * result + (street != null ? street.hashCode() : 0);
    result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
    result = 31 * result + (town != null ? town.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Company{"
        + "id='" + id + '\''
        + "name='" + name + '\''
        + ", taxIdentificationNumber='" + taxIdentificationNumber + '\''
        + ", street='" + street + '\''
        + ", postalCode='" + postalCode + '\''
        + ", town='" + town + '\''
        + '}';
  }

  public static class CompanyBuilder {

    private Object id;
    private String name;
    private String taxIdentificationNumber;
    private String street;
    private String postalCode;
    private String town;

    public CompanyBuilder id(Long id) {
      this.id = id;
      return this;
    }

    public CompanyBuilder name(String name) {
      this.name = name;
      return this;
    }

    public CompanyBuilder taxIdentificationNumber(String taxIdentificationNumber) {
      this.taxIdentificationNumber = taxIdentificationNumber;
      return this;
    }

    public CompanyBuilder street(String street) {
      this.street = street;
      return this;
    }

    public CompanyBuilder postalCode(String postalCode) {
      this.postalCode = postalCode;
      return this;
    }

    public CompanyBuilder town(String town) {
      this.town = town;
      return this;
    }

    public Company build() {
      return new Company(id, name, taxIdentificationNumber, street, postalCode, town);
    }
  }
}
