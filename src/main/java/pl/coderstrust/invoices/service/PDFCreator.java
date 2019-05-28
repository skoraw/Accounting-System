package pl.coderstrust.invoices.service;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
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
public class PDFCreator {

  private static final Font BLUE_FONT = FontFactory
      .getFont(FontFactory.HELVETICA, 32, Font.NORMAL, new CMYKColor(255, 0, 0, 0));
  private ByteArrayOutputStream generatedPdf = new ByteArrayOutputStream();

  private static PdfPCell getCell(Paragraph paragraph) {
    PdfPCell cell = new PdfPCell(paragraph);
    cell.setVerticalAlignment(Element.ALIGN_LEFT);
    cell.setBorder(0);
    return cell;
  }

  private static PdfPCell getEntryCell(Paragraph paragraph) {
    PdfPCell cell = new PdfPCell(paragraph);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    return cell;
  }

  private static PdfPTable getInvoiceHeader(Invoice invoice) throws DocumentException {
    PdfPTable table = new PdfPTable(4);

    table.setWidthPercentage(100);
    float[] columnWidths = {3f, 7f, 3f, 3f};
    table.setWidths(columnWidths);

    table.addCell(getCell(new Paragraph("Invoice number: ")));
    table.addCell(getCell(new Paragraph(invoice.getNumber())));
    table.addCell(getCell(new Paragraph("Issue date: ")));
    table.addCell(getCell(new Paragraph(String.valueOf(invoice.getIssueDate()))));
    table.addCell(getCell(new Paragraph("")));
    table.addCell(getCell(new Paragraph("")));
    table.addCell(getCell(new Paragraph("Issue place: ")));
    table.addCell(getCell(new Paragraph(invoice.getIssuePlace())));
    table.addCell(getCell(new Paragraph("")));
    table.addCell(getCell(new Paragraph("")));
    table.addCell(getCell(new Paragraph("Sell date: ")));
    table.addCell(getCell(new Paragraph(String.valueOf(invoice.getSellDate()))));

    return table;
  }

  private static PdfPTable getInvoiceCompanies(Company seller, Company buyer)
      throws DocumentException {
    PdfPTable table = new PdfPTable(4);

    table.setWidthPercentage(100);
    float[] columnWidths = {1f, 2f, 1f, 2f};
    table.setWidths(columnWidths);

    table.addCell(getCell(new Paragraph("Seller: ")));
    table.addCell(getCell(new Paragraph("")));
    table.addCell(getCell(new Paragraph("Buyer: ")));
    table.addCell(getCell(new Paragraph("")));
    table.addCell(getCell(new Paragraph("Name: ")));
    table.addCell(getCell(new Paragraph(seller.getName())));
    table.addCell(getCell(new Paragraph("Name: ")));
    table.addCell(getCell(new Paragraph(buyer.getName())));
    table.addCell(getCell(new Paragraph("Tax identyfication number: ")));
    table.addCell(getCell(new Paragraph(seller.getTaxIdentificationNumber())));
    table.addCell(getCell(new Paragraph("Tax identyfication number: ")));
    table.addCell(getCell(new Paragraph(buyer.getTaxIdentificationNumber())));
    table.addCell(getCell(new Paragraph("Stret: ")));
    table.addCell(getCell(new Paragraph(seller.getStreet())));
    table.addCell(getCell(new Paragraph("Street: ")));
    table.addCell(getCell(new Paragraph(buyer.getStreet())));
    table.addCell(getCell(new Paragraph("Postal code: ")));
    table.addCell(getCell(new Paragraph(seller.getPostalCode())));
    table.addCell(getCell(new Paragraph("Postal code: ")));
    table.addCell(getCell(new Paragraph(buyer.getPostalCode())));
    table.addCell(getCell(new Paragraph("Town: ")));
    table.addCell(getCell(new Paragraph(seller.getTown())));
    table.addCell(getCell(new Paragraph("Town: ")));
    table.addCell(getCell(new Paragraph(buyer.getTown())));

    return table;
  }

  private static PdfPTable getInvoiceEntries(java.util.List<InvoiceEntry> invoiceEntryList)
      throws DocumentException {

    PdfPTable table = new PdfPTable(8);
    table.setWidthPercentage(100);
    float[] columnWidths = {1f, 4f, 1f, 1f, 1f, 1f, 1f, 1f};
    table.setWidths(columnWidths);

    BigDecimal amountTotal = BigDecimal.ZERO;
    BigDecimal netPriceTotal = BigDecimal.ZERO;
    BigDecimal netAmountTotal = BigDecimal.ZERO;
    BigDecimal vatAmountTotal = BigDecimal.ZERO;
    BigDecimal grossAmountTotal = BigDecimal.ZERO;

    table.addCell(getEntryCell(new Paragraph("Lp")));
    table.addCell(getEntryCell(new Paragraph("Product name")));
    table.addCell(getEntryCell(new Paragraph("Amount")));
    table.addCell(getEntryCell(new Paragraph("Net price")));
    table.addCell(getEntryCell(new Paragraph("Net amount")));
    table.addCell(getEntryCell(new Paragraph("Vat")));
    table.addCell(getEntryCell(new Paragraph("VAT amount")));
    table.addCell(getEntryCell(new Paragraph("Gross amount")));

    for (int i = 0; i < invoiceEntryList.size(); i++) {

      Long amountLong = Long.valueOf(invoiceEntryList.get(i).getAmount());
      BigDecimal amount = BigDecimal.valueOf(amountLong);
      amountTotal = amountTotal.add(amount);
      BigDecimal netPrice = invoiceEntryList.get(i).getPrice().setScale(2, RoundingMode.CEILING);
      netPriceTotal = netPriceTotal.add(netPrice);
      BigDecimal netAmount = amount.multiply(netPrice).setScale(2, RoundingMode.CEILING);
      netAmountTotal = netAmountTotal.add(netAmount);
      BigDecimal vat = Vat.VAT_23.getValue();
      BigDecimal vatAmount = netPrice.multiply(vat).multiply(amount)
          .setScale(2, RoundingMode.CEILING);
      vatAmountTotal = vatAmountTotal.add(vatAmount);
      BigDecimal grossAmount = netAmount.add(vatAmount).setScale(2, RoundingMode.CEILING);
      grossAmountTotal = grossAmountTotal.add(grossAmount);

      table.addCell(getEntryCell(new Paragraph(String.valueOf(i + 1))));
      table.addCell(getEntryCell(new Paragraph(invoiceEntryList.get(i).getProductName())));
      table.addCell(getEntryCell(new Paragraph(String.valueOf(amount))));
      table.addCell(getEntryCell(new Paragraph(String.valueOf(netPrice))));
      table.addCell(getEntryCell(new Paragraph(String.valueOf(netAmount))));
      table.addCell(getEntryCell(new Paragraph(String.valueOf(invoiceEntryList.get(i).getVat()))));
      table.addCell(getEntryCell(new Paragraph(String.valueOf(vatAmount))));
      table.addCell(getEntryCell(new Paragraph(String.valueOf(grossAmount))));
    }
    table.addCell(getEntryCell(new Paragraph("Total")));
    table.addCell(getEntryCell(new Paragraph("")));
    table.addCell(getEntryCell(new Paragraph(String.valueOf(amountTotal))));
    table.addCell(getEntryCell(new Paragraph(String.valueOf(netPriceTotal))));
    table.addCell(getEntryCell(new Paragraph(String.valueOf(netAmountTotal))));
    table.addCell(getEntryCell(new Paragraph("")));
    table.addCell(getEntryCell(new Paragraph(String.valueOf(vatAmountTotal))));
    table.addCell(getEntryCell(new Paragraph(String.valueOf(grossAmountTotal))));

    return table;
  }

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

      document.add(new Paragraph("Invoice", BLUE_FONT));
      document.add(new Paragraph(""));
      document.add(Chunk.NEWLINE);
      document.add(getInvoiceHeader(invoice));
      document.add(Chunk.NEWLINE);
      document.add(getInvoiceCompanies(invoice.getSeller(), invoice.getBuyer()));
      document.add(Chunk.NEWLINE);
      document.add(getInvoiceEntries(invoice.getEntries()));

      document.close();
      writer.close();
    } catch (DocumentException exception) {
      exception.printStackTrace();
    }
    return new ByteArrayInputStream(out.toByteArray());
  }
}
