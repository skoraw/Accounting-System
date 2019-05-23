//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.05.23 at 12:14:27 PM CEST 
//


package pl.coderstrust.invoices.model.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="issueDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="issuePlace" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="sellDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="seller" type="{http://spring.io/guides/gs-producing-web-service}company"/&gt;
 *         &lt;element name="buyer" type="{http://spring.io/guides/gs-producing-web-service}company"/&gt;
 *         &lt;element name="entries" type="{http://spring.io/guides/gs-producing-web-service}entry"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "id",
    "number",
    "issueDate",
    "issuePlace",
    "sellDate",
    "seller",
    "buyer",
    "entries"
})
@XmlRootElement(name = "addInvoiceRequest")
public class AddInvoiceRequest {

  protected long id;
  @XmlElement(required = true)
  protected String number;
  @XmlElement(required = true)
  protected String issueDate;
  @XmlElement(required = true)
  protected String issuePlace;
  @XmlElement(required = true)
  protected String sellDate;
  @XmlElement(required = true)
  protected Company seller;
  @XmlElement(required = true)
  protected Company buyer;
  @XmlElement(required = true)
  protected Entry entries;

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
   * Gets the value of the number property.
   *
   * @return possible object is {@link String }
   */
  public String getNumber() {
    return number;
  }

  /**
   * Sets the value of the number property.
   *
   * @param value allowed object is {@link String }
   */
  public void setNumber(String value) {
    this.number = value;
  }

  /**
   * Gets the value of the issueDate property.
   *
   * @return possible object is {@link String }
   */
  public String getIssueDate() {
    return issueDate;
  }

  /**
   * Sets the value of the issueDate property.
   *
   * @param value allowed object is {@link String }
   */
  public void setIssueDate(String value) {
    this.issueDate = value;
  }

  /**
   * Gets the value of the issuePlace property.
   *
   * @return possible object is {@link String }
   */
  public String getIssuePlace() {
    return issuePlace;
  }

  /**
   * Sets the value of the issuePlace property.
   *
   * @param value allowed object is {@link String }
   */
  public void setIssuePlace(String value) {
    this.issuePlace = value;
  }

  /**
   * Gets the value of the sellDate property.
   *
   * @return possible object is {@link String }
   */
  public String getSellDate() {
    return sellDate;
  }

  /**
   * Sets the value of the sellDate property.
   *
   * @param value allowed object is {@link String }
   */
  public void setSellDate(String value) {
    this.sellDate = value;
  }

  /**
   * Gets the value of the seller property.
   *
   * @return possible object is {@link Company }
   */
  public Company getSeller() {
    return seller;
  }

  /**
   * Sets the value of the seller property.
   *
   * @param value allowed object is {@link Company }
   */
  public void setSeller(Company value) {
    this.seller = value;
  }

  /**
   * Gets the value of the buyer property.
   *
   * @return possible object is {@link Company }
   */
  public Company getBuyer() {
    return buyer;
  }

  /**
   * Sets the value of the buyer property.
   *
   * @param value allowed object is {@link Company }
   */
  public void setBuyer(Company value) {
    this.buyer = value;
  }

  /**
   * Gets the value of the entries property.
   *
   * @return possible object is {@link Entry }
   */
  public Entry getEntries() {
    return entries;
  }

  /**
   * Sets the value of the entries property.
   *
   * @param value allowed object is {@link Entry }
   */
  public void setEntries(Entry value) {
    this.entries = value;
  }

}