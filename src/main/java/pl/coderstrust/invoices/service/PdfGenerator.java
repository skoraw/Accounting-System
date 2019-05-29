package pl.coderstrust.invoices.service;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;
import pl.coderstrust.invoices.model.Company;
import pl.coderstrust.invoices.model.Invoice;
import pl.coderstrust.invoices.model.InvoiceEntry;
import pl.coderstrust.invoices.model.Vat;

@Service
public class PdfGenerator {

  private static final Font HEADER_FONT = FontFactory
      .getFont(BaseFont.HELVETICA, 28, Font.BOLD, new CMYKColor(255, 0, 0, 0));

  private static final Font SECTION_HEADER_FONT = FontFactory
      .getFont(BaseFont.HELVETICA, 14, new CMYKColor(255, 0, 0, 0));

  private static final Font DEFAULT_CONTENT_FONT = FontFactory
      .getFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED, 12);

  private static final Font PRODUCTS_HEADER_FONT = FontFactory
      .getFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1250, BaseFont.EMBEDDED, 10, 0,
          new CMYKColor(0, 0, 0, 255));

  private static final Font PRODUCTS_CONTENT_FONT = FontFactory
      .getFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1250, BaseFont.EMBEDDED, 8, 0,
          new CMYKColor(0, 0, 0, 75));

  private static final Font PRODUCTS_TOTAL_FONT = FontFactory
      .getFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1250, BaseFont.EMBEDDED, 10, 0,
          new CMYKColor(0, 0, 0, 255));

  private static final float padding = 5;
  private static final float paddingTop = 5;
  private static final float paddingBottom = 5;
  private static final float paddingLeft = 5;
  private static final float paddingRight = 5;

  public static ByteArrayInputStream getPdf(Invoice invoice) {
    Document document = new Document();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      PdfWriter writer = PdfWriter.getInstance(document, out);
      document.open();

      document.addAuthor("project-10-tomasz-wiktor");
      document.addCreationDate();
      document.addCreator("Accounting System");
      document.addTitle("Invoices");
      document.addSubject("PDF");

      document.add(new Paragraph("Invoice", HEADER_FONT));
      document.add(new Paragraph(""));
      document.add(Chunk.NEWLINE);
      document.add(getInvoiceHeader(invoice));
      document.add(getInvoiceCompanies(invoice.getSeller(), invoice.getBuyer()));
      document.add(Chunk.NEWLINE);
      document.add(new Paragraph("Invoice products", SECTION_HEADER_FONT));
      document.add(getInvoiceEntries(invoice.getEntries()));

      document.close();
      writer.close();
    } catch (DocumentException exception) {
      exception.printStackTrace();
    }
    return new ByteArrayInputStream(out.toByteArray());
  }

  private static Paragraph getSectionHeaderParagraph(String content) {
    return new Paragraph(content, SECTION_HEADER_FONT);
  }

  private static PdfPCell getSectionHeaderCell(String content) {
    PdfPCell cell = new PdfPCell(getSectionHeaderParagraph(content));
    cell.setVerticalAlignment(Element.ALIGN_LEFT);
    cell.setBorder(0);
    cell.setPaddingLeft(paddingLeft);
    cell.setPaddingRight(paddingRight);
    cell.setPaddingTop(paddingTop);
    cell.setPaddingBottom(paddingBottom);
    return cell;
  }

  private static Paragraph getDefaultContentParagraph(String content) {
    return new Paragraph(content, DEFAULT_CONTENT_FONT);
  }

  private static PdfPCell getDefaultContentCell(String content) {
    PdfPCell cell = new PdfPCell(getDefaultContentParagraph(content));
    cell.setBorder(0);
    cell.setVerticalAlignment(Element.ALIGN_LEFT);
    cell.setPaddingLeft(paddingLeft);
    cell.setPaddingRight(paddingRight);
    cell.setPaddingTop(paddingTop);
    cell.setPaddingBottom(paddingBottom);
    return cell;
  }

  private static Paragraph getProductsHeaderParagraph(String content) {
    return new Paragraph(content, PRODUCTS_HEADER_FONT);
  }

  private static PdfPCell getProductsHeaderCell(String content) {
    PdfPCell cell = new PdfPCell(getProductsHeaderParagraph(content));
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setPaddingLeft(paddingLeft);
    cell.setPaddingRight(paddingRight);
    cell.setPaddingTop(paddingTop);
    cell.setPaddingBottom(paddingBottom);
    return cell;
  }

  private static Paragraph getProductsContentParagraph(String content) {
    return new Paragraph(content, PRODUCTS_CONTENT_FONT);
  }

  private static PdfPCell getProductsContentCell(String content) {
    PdfPCell cell = new PdfPCell(getProductsContentParagraph(content));
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setPaddingLeft(paddingLeft);
    cell.setPaddingRight(paddingRight);
    cell.setPaddingTop(paddingTop);
    cell.setPaddingBottom(paddingBottom);
    return cell;
  }

  private static Paragraph getProductsTotalParagraph(String content) {
    return new Paragraph(content, PRODUCTS_TOTAL_FONT);
  }

  private static PdfPCell getProductsTotalCell(String content) {
    PdfPCell cell = new PdfPCell(getProductsTotalParagraph(content));
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setPaddingLeft(paddingLeft);
    cell.setPaddingRight(paddingRight);
    cell.setPaddingTop(paddingTop);
    cell.setPaddingBottom(paddingBottom);
    return cell;
  }

  private static PdfPTable getInvoiceHeader(Invoice invoice) throws DocumentException {
    PdfPTable table = new PdfPTable(4);

    table.setWidthPercentage(100);
    float[] columnWidths = {3f, 7f, 3f, 3f};
    table.setWidths(columnWidths);

    table.addCell(getDefaultContentCell("Invoice number: "));
    table.addCell(getDefaultContentCell(invoice.getNumber()));
    table.addCell(getDefaultContentCell("Issue date: "));
    table.addCell(getDefaultContentCell(String.valueOf(invoice.getIssueDate())));
    table.addCell(getDefaultContentCell(""));
    table.addCell(getDefaultContentCell(""));
    table.addCell(getDefaultContentCell("Issue place: "));
    table.addCell(getDefaultContentCell(invoice.getIssuePlace()));
    table.addCell(getDefaultContentCell(""));
    table.addCell(getDefaultContentCell(""));
    table.addCell(getDefaultContentCell("Sell date: "));
    table.addCell(getDefaultContentCell(String.valueOf(invoice.getSellDate())));

    return table;
  }

  private static PdfPTable getInvoiceCompanies(Company seller, Company buyer)
      throws DocumentException {
    PdfPTable table = new PdfPTable(2);

    table.setWidthPercentage(100);
    float[] columnWidths = {1f, 2f};
    table.setWidths(columnWidths);

    table.addCell(getSectionHeaderCell("Seller: "));
    table.addCell(getDefaultContentCell(""));
    table.addCell(getDefaultContentCell("Name: "));
    table.addCell(getDefaultContentCell(seller.getName()));
    table.addCell(getDefaultContentCell("Tax identyfication number: "));
    table.addCell(getDefaultContentCell(seller.getTaxIdentificationNumber()));
    table.addCell(getDefaultContentCell("Street: "));
    table.addCell(getDefaultContentCell(seller.getStreet()));
    table.addCell(getDefaultContentCell("Postal code: "));
    table.addCell(getDefaultContentCell(seller.getPostalCode()));
    table.addCell(getDefaultContentCell("Town: "));
    table.addCell(getDefaultContentCell(seller.getTown()));
    table.addCell(getSectionHeaderCell("Buyer: "));
    table.addCell(getDefaultContentCell(""));
    table.addCell(getDefaultContentCell("Name: "));
    table.addCell(getDefaultContentCell(buyer.getName()));
    table.addCell(getDefaultContentCell("Tax identyfication number: "));
    table.addCell(getDefaultContentCell(buyer.getTaxIdentificationNumber()));
    table.addCell(getDefaultContentCell("Street: "));
    table.addCell(getDefaultContentCell(buyer.getStreet()));
    table.addCell(getDefaultContentCell("Postal code: "));
    table.addCell(getDefaultContentCell(buyer.getPostalCode()));
    table.addCell(getDefaultContentCell("Town: "));
    table.addCell(getDefaultContentCell(buyer.getTown()));

    return table;
  }

  private static PdfPTable getInvoiceEntries(java.util.List<InvoiceEntry> invoiceEntryList)
      throws DocumentException {

    PdfPTable table = new PdfPTable(7);
    table.setWidthPercentage(100);
    float[] columnWidths = {6f, 1f, 2f, 2f, 2f, 2f, 2f};
    table.setWidths(columnWidths);
    table.setSpacingBefore(padding);

    BigDecimal amountTotal = BigDecimal.ZERO;
    BigDecimal netPriceTotal = BigDecimal.ZERO;
    BigDecimal netAmountTotal = BigDecimal.ZERO;
    BigDecimal vatAmountTotal = BigDecimal.ZERO;
    BigDecimal grossAmountTotal = BigDecimal.ZERO;

    table.addCell(getProductsHeaderCell("Product name"));
    table.addCell(getProductsHeaderCell("Qty"));
    table.addCell(getProductsHeaderCell("Net price"));
    table.addCell(getProductsHeaderCell("Net amount"));
    table.addCell(getProductsHeaderCell("Vat"));
    table.addCell(getProductsHeaderCell("VAT amount"));
    table.addCell(getProductsHeaderCell("Gross amount"));

    for (InvoiceEntry invoiceEntry : invoiceEntryList) {

      long amountLong = Long.parseLong(invoiceEntry.getAmount());
      BigDecimal amount = BigDecimal.valueOf(amountLong);
      amountTotal = amountTotal.add(amount);
      BigDecimal netPrice = invoiceEntry.getPrice().setScale(2, RoundingMode.CEILING);
      netPriceTotal = netPriceTotal.add(netPrice);
      BigDecimal netAmount = amount.multiply(netPrice).setScale(2, RoundingMode.CEILING);
      netAmountTotal = netAmountTotal.add(netAmount);
      BigDecimal vat = Vat.VAT_23.getValue();
      BigDecimal vatAmount = netPrice.multiply(vat).multiply(amount)
          .setScale(2, RoundingMode.CEILING);
      vatAmountTotal = vatAmountTotal.add(vatAmount);
      BigDecimal grossAmount = netAmount.add(vatAmount).setScale(2, RoundingMode.CEILING);
      grossAmountTotal = grossAmountTotal.add(grossAmount);

      table.addCell(getProductsContentCell(invoiceEntry.getProductName()));
      table.addCell(getProductsContentCell(String.valueOf(amount)));
      table.addCell(getProductsContentCell(String.valueOf(netPrice)));
      table.addCell(getProductsContentCell(String.valueOf(netAmount)));
      table.addCell(getProductsContentCell(String.valueOf(invoiceEntry.getVat())));
      table.addCell(getProductsContentCell(String.valueOf(vatAmount)));
      table.addCell(getProductsContentCell(String.valueOf(grossAmount)));
    }
    table.addCell(getProductsTotalCell("Total"));
    table.addCell(getProductsTotalCell(String.valueOf(amountTotal)));
    table.addCell(getProductsTotalCell(String.valueOf(netPriceTotal)));
    table.addCell(getProductsTotalCell(String.valueOf(netAmountTotal)));
    table.addCell(getProductsTotalCell(""));
    table.addCell(getProductsTotalCell(String.valueOf(vatAmountTotal)));
    table.addCell(getProductsTotalCell(String.valueOf(grossAmountTotal)));

    return table;
  }
}
