package pl.coderstrust.invoices.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Company {

  @Id
  @GeneratedValue
  private Long id;
  private final String name;
  private final String taxIdentificationNumber;
  private final String street;
  private final String postalCode;
  private final String town;

  private Company(Long id, String name, String taxIdentificationNumber, String street,
      String postalCode, String town) {
    this.id = id;
    this.name = name;
    this.taxIdentificationNumber = taxIdentificationNumber;
    this.street = street;
    this.postalCode = postalCode;
    this.town = town;
  }

  public Long getId() {
    return id;
  }

  public static CompanyBuilder builder() {
    return new CompanyBuilder();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Company company = (Company) o;

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

    private Long id;
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
