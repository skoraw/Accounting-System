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
 *         &lt;element name="invoice" type="{http://spring.io/guides/gs-producing-web-service}InvoiceSoap"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "invoice"
})
@XmlRootElement(name = "addInvoiceResponse")
public class AddInvoiceResponse {

  @XmlElement(required = true)
  protected InvoiceSoap invoice;

  /**
   * Gets the value of the invoice property.
   *
   * @return possible object is {@link InvoiceSoap }
   */
  public InvoiceSoap getInvoice() {
    return invoice;
  }

  /**
   * Sets the value of the invoice property.
   *
   * @param value allowed object is {@link InvoiceSoap }
   */
  public void setInvoice(InvoiceSoap value) {
    this.invoice = value;
  }

}
