//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.05.23 at 07:35:12 PM CEST 
//


package pl.coderstrust.invoices.model.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CompanySoap complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CompanySoap"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="taxIdentificationNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="street" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="postalCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="town" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CompanySoap", propOrder = {
    "id",
    "name",
    "taxIdentificationNumber",
    "street",
    "postalCode",
    "town"
})
public class CompanySoap {

  protected long id;
  @XmlElement(required = true)
  protected String name;
  @XmlElement(required = true)
  protected String taxIdentificationNumber;
  @XmlElement(required = true)
  protected String street;
  @XmlElement(required = true)
  protected String postalCode;
  @XmlElement(required = true)
  protected String town;

  /**
   * Gets the value of the id property.
   */
  public long getId() {
    return id;
  }

  /**
   * Sets the value of the id property.
   */
  public void setId(long value) {
    this.id = value;
  }

  /**
   * Gets the value of the name property.
   *
   * @return possible object is {@link String }
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the value of the name property.
   *
   * @param value allowed object is {@link String }
   */
  public void setName(String value) {
    this.name = value;
  }

  /**
   * Gets the value of the taxIdentificationNumber property.
   *
   * @return possible object is {@link String }
   */
  public String getTaxIdentificationNumber() {
    return taxIdentificationNumber;
  }

  /**
   * Sets the value of the taxIdentificationNumber property.
   *
   * @param value allowed object is {@link String }
   */
  public void setTaxIdentificationNumber(String value) {
    this.taxIdentificationNumber = value;
  }

  /**
   * Gets the value of the street property.
   *
   * @return possible object is {@link String }
   */
  public String getStreet() {
    return street;
  }

  /**
   * Sets the value of the street property.
   *
   * @param value allowed object is {@link String }
   */
  public void setStreet(String value) {
    this.street = value;
  }

  /**
   * Gets the value of the postalCode property.
   *
   * @return possible object is {@link String }
   */
  public String getPostalCode() {
    return postalCode;
  }

  /**
   * Sets the value of the postalCode property.
   *
   * @param value allowed object is {@link String }
   */
  public void setPostalCode(String value) {
    this.postalCode = value;
  }

  /**
   * Gets the value of the town property.
   *
   * @return possible object is {@link String }
   */
  public String getTown() {
    return town;
  }

  /**
   * Sets the value of the town property.
   *
   * @param value allowed object is {@link String }
   */
  public void setTown(String value) {
    this.town = value;
  }

}