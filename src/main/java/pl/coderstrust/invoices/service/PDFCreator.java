package pl.coderstrust.invoices.service;

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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import pl.coderstrust.invoices.model.Company;
import pl.coderstrust.invoices.model.Invoice;
import pl.coderstrust.invoices.model.InvoiceEntry;
import pl.coderstrust.invoices.model.Vat;

public class PDFCreator {

  Font blueFont = FontFactory
      .getFont(FontFactory.HELVETICA, 18, Font.NORMAL, new CMYKColor(255, 0, 0, 0));
  private ByteArrayOutputStream generatedPdf = new ByteArrayOutputStream();

  public PDFCreator() {
  }

  public static void main(String[] args) {
    InvoiceEntry invoiceEntry1 = new InvoiceEntry();
    invoiceEntry1.setId(1);
    invoiceEntry1.setProductName("Entrie 1");
    invoiceEntry1.setAmount("3");
    invoiceEntry1.setPrice(BigDecimal.valueOf(100.00));
    invoiceEntry1.setVat(Vat.VAT_23);

    InvoiceEntry invoiceEntry2 = new InvoiceEntry();
    invoiceEntry2.setId(2);
    invoiceEntry2.setProductName("Entrie 2");
    invoiceEntry2.setAmount("1");
    invoiceEntry2.setPrice(BigDecimal.valueOf(29.99));
    invoiceEntry2.setVat(Vat.VAT_23);

    java.util.List<InvoiceEntry> invoiceEntryList = new ArrayList<>();
    invoiceEntryList.add(invoiceEntry1);
    invoiceEntryList.add(invoiceEntry2);

    Company buyer = Company.builder()
        .id(1L)
        .town("Buyer Town")
        .postalCode("11-111")
        .street("Buyer Street")
        .taxIdentificationNumber("111")
        .name("Buyer Company")
        .build();
    Company seller = Company.builder()
        .id(2L)
        .town("Seller Town")
        .postalCode("22-222")
        .street("Seller Street")
        .taxIdentificationNumber("222")
        .name("Seller Company")
        .build();
    Invoice invoice = Invoice.builder()
        .id(1L)
        .number("1/2019")
        .buyer(buyer)
        .issueDate(LocalDate.of(2019, 5, 27))
        .issuePlace("SomePlace")
        .sellDate(LocalDate.of(2019, 5, 27))
        .seller(seller)
        .entries(invoiceEntryList)
        .build();

    PDFCreator pdfCreator = new PDFCreator();
    pdfCreator.getPdf(invoice);
  }

  public static ByteArrayInputStream getPdf(Invoice invoice) {
    Document document = new Document();
    try {
      PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("HelloWorld.pdf"));
      document.open();

      document.addAuthor("project-10-tomasz-wiktor");
      document.addCreationDate();
      document.addCreator("Accounting System");
      document.addTitle("Invoices");
      document.addSubject("PDF");
//##################################################################################################

      PdfPTable tableHeader = new PdfPTable(4);

      tableHeader.setWidthPercentage(100); //Width 100%
      float[] columnWidths = {2f, 8f, 3f, 3f};
      tableHeader.setWidths(columnWidths);

      PdfPCell cell1 = new PdfPCell(new Paragraph("Invoice", blueFont));
      cell1.setBorder(0);
      cell1.setVerticalAlignment(Element.ALIGN_TOP);
      PdfPCell cell2 = new PdfPCell(new Paragraph(""));
      cell2.setBorder(0);
      PdfPCell cell3 = new PdfPCell(new Paragraph("Issue date: "));
      cell3.setBorder(0);
      cell3.setVerticalAlignment(Element.ALIGN_CENTER);
      PdfPCell cell4 = new PdfPCell(new Paragraph(String.valueOf(invoice.getIssueDate())));
      cell4.setBorder(0);
      PdfPCell cell5 = new PdfPCell(new Paragraph("Number: "));
      cell5.setBorder(0);
      PdfPCell cell6 = new PdfPCell(new Paragraph(invoice.getNumber()));
      cell6.setBorder(0);
      PdfPCell cell7 = new PdfPCell(new Paragraph("Issue place: "));
      cell7.setBorder(0);
      PdfPCell cell8 = new PdfPCell(new Paragraph(invoice.getIssuePlace()));
      cell8.setBorder(0);
      PdfPCell cell9 = new PdfPCell(new Paragraph(""));
      cell9.setBorder(0);
      PdfPCell cell10 = new PdfPCell(new Paragraph(""));
      cell10.setBorder(0);
      PdfPCell cell11 = new PdfPCell(new Paragraph("Sell date: "));
      cell11.setBorder(0);
      PdfPCell cell12 = new PdfPCell(new Paragraph(String.valueOf(invoice.getSellDate())));
      cell12.setBorder(0);

      tableHeader.addCell(cell1);
      tableHeader.addCell(cell2);
      tableHeader.addCell(cell3);
      tableHeader.addCell(cell4);
      tableHeader.addCell(cell5);
      tableHeader.addCell(cell6);
      tableHeader.addCell(cell7);
      tableHeader.addCell(cell8);
      tableHeader.addCell(cell9);
      tableHeader.addCell(cell10);
      tableHeader.addCell(cell11);
      tableHeader.addCell(cell12);

      document.add(tableHeader);

      PdfPTable tableCompany = new PdfPTable(4);

      tableCompany.setWidthPercentage(100); //Width 100%
      float[] columnWidths2 = {1f, 2f, 1f, 2f};
      tableCompany.setWidths(columnWidths2);

      PdfPCell cellC1 = new PdfPCell(new Paragraph("Seller: "));
      PdfPCell cellC2 = new PdfPCell(new Paragraph(""));
      cellC1.setBorder(0);
      cellC2.setBorder(0);
      PdfPCell cellC3 = new PdfPCell(new Paragraph("Buyer: "));
      PdfPCell cellC4 = new PdfPCell(new Paragraph(""));
      cellC3.setBorder(0);
      cellC4.setBorder(0);
      PdfPCell cellC5 = new PdfPCell(new Paragraph("Name: "));
      PdfPCell cellC6 = new PdfPCell(new Paragraph(
          invoice.getSeller().getName()));
      cellC5.setBorder(0);
      cellC6.setBorder(0);
      PdfPCell cellC7 = new PdfPCell(new Paragraph("Name: "));
      PdfPCell cellC8 = new PdfPCell(new Paragraph(invoice.getBuyer().getName()));
      cellC7.setBorder(0);
      cellC8.setBorder(0);
      PdfPCell cellC9 = new PdfPCell(new Paragraph("Tax identyfication number: "));
      PdfPCell cellC10 = new PdfPCell(
          new Paragraph(invoice.getSeller().getTaxIdentificationNumber()));
      cellC9.setBorder(0);
      cellC10.setBorder(0);
      PdfPCell cellC11 = new PdfPCell(new Paragraph("Tax identyfication number: "));
      PdfPCell cellC12 = new PdfPCell(
          new Paragraph(invoice.getBuyer().getTaxIdentificationNumber()));
      cellC11.setBorder(0);
      cellC12.setBorder(0);
      PdfPCell cellC13 = new PdfPCell(new Paragraph("Stret: "));
      PdfPCell cellC14 = new PdfPCell(new Paragraph(invoice.getSeller().getStreet()));
      cellC13.setBorder(0);
      cellC14.setBorder(0);
      PdfPCell cellC15 = new PdfPCell(new Paragraph("Street: "));
      PdfPCell cellC16 = new PdfPCell(new Paragraph(invoice.getBuyer().getStreet()));
      cellC15.setBorder(0);
      cellC16.setBorder(0);
      PdfPCell cellC17 = new PdfPCell(new Paragraph("Postal code: "));
      PdfPCell cellC18 = new PdfPCell(new Paragraph(invoice.getSeller().getPostalCode()));
      cellC17.setBorder(0);
      cellC18.setBorder(0);
      PdfPCell cellC19 = new PdfPCell(new Paragraph("Postal code: "));
      PdfPCell cellC20 = new PdfPCell(new Paragraph(invoice.getBuyer().getPostalCode()));
      cellC19.setBorder(0);
      cellC20.setBorder(0);
      PdfPCell cellC21 = new PdfPCell(new Paragraph("Town: "));
      PdfPCell cellC22 = new PdfPCell(new Paragraph(invoice.getSeller().getTown()));
      cellC21.setBorder(0);
      cellC22.setBorder(0);
      PdfPCell cellC23 = new PdfPCell(new Paragraph("Town: "));
      PdfPCell cellC24 = new PdfPCell(new Paragraph(invoice.getBuyer().getTown()));
      cellC23.setBorder(0);
      cellC24.setBorder(0);

      tableCompany.addCell(cellC1);
      tableCompany.addCell(cellC2);
      tableCompany.addCell(cellC3);
      tableCompany.addCell(cellC4);
      tableCompany.addCell(cellC5);
      tableCompany.addCell(cellC6);
      tableCompany.addCell(cellC7);
      tableCompany.addCell(cellC8);
      tableCompany.addCell(cellC9);
      tableCompany.addCell(cellC10);
      tableCompany.addCell(cellC11);
      tableCompany.addCell(cellC12);
      tableCompany.addCell(cellC13);
      tableCompany.addCell(cellC14);
      tableCompany.addCell(cellC15);
      tableCompany.addCell(cellC16);
      tableCompany.addCell(cellC17);
      tableCompany.addCell(cellC18);
      tableCompany.addCell(cellC19);
      tableCompany.addCell(cellC20);
      tableCompany.addCell(cellC21);
      tableCompany.addCell(cellC22);
      tableCompany.addCell(cellC23);
      tableCompany.addCell(cellC24);

      document.add(tableCompany);

      PdfPTable entriesHeader = new PdfPTable(8);
      entriesHeader.setWidthPercentage(100); //Width 100%
      float[] columnWidths3 = {1f, 2f, 2f, 2f, 2f, 2f, 2f, 2f};
      entriesHeader.setWidths(columnWidths3);
      PdfPCell ehcell1 = new PdfPCell(new Paragraph("Lp"));
      PdfPCell ehcell2 = new PdfPCell(new Paragraph("Product name"));
      PdfPCell ehcell3 = new PdfPCell(new Paragraph("Amount"));
      PdfPCell ehcell4 = new PdfPCell(new Paragraph("Net price"));
      PdfPCell ehcell5 = new PdfPCell(new Paragraph("Net amount"));
      PdfPCell ehcell6 = new PdfPCell(new Paragraph("Vat"));
      PdfPCell ehcell7 = new PdfPCell(new Paragraph("VAT amount"));
      PdfPCell ehcell8 = new PdfPCell(new Paragraph("Gross amount"));

      entriesHeader.addCell(ehcell1);
      entriesHeader.addCell(ehcell2);
      entriesHeader.addCell(ehcell3);
      entriesHeader.addCell(ehcell4);
      entriesHeader.addCell(ehcell5);
      entriesHeader.addCell(ehcell6);
      entriesHeader.addCell(ehcell7);
      entriesHeader.addCell(ehcell8);
      document.add(entriesHeader);

      PdfPTable entries = new PdfPTable(8);
      entries.setWidthPercentage(100); //Width 100%
      entries.setWidths(columnWidths3);

      BigDecimal amountTotal = BigDecimal.ZERO;
      BigDecimal netPriceTotal = BigDecimal.ZERO;
      BigDecimal netAmountTotal = BigDecimal.ZERO;
      BigDecimal vatAmountTotal = BigDecimal.ZERO;
      BigDecimal grossAmountTotal = BigDecimal.ZERO;

      for (int i = 0; i < invoice.getEntries().size(); i++) {
        PdfPCell entry1 = new PdfPCell(new Paragraph(String.valueOf(i + 1)));
        PdfPCell entry2 = new PdfPCell(new Paragraph(invoice.getEntries().get(i).getProductName()));
        Long amountLong = Long.valueOf(invoice.getEntries().get(i).getAmount());
        BigDecimal amount = BigDecimal.valueOf(amountLong);
        amountTotal = amountTotal.add(amount);
        PdfPCell entry3 = new PdfPCell(new Paragraph(String.valueOf(amount)));
        BigDecimal netPrice = invoice.getEntries().get(i).getPrice()
            .setScale(2, RoundingMode.CEILING);
        netPriceTotal = netPriceTotal.add(netPrice);
        PdfPCell entry4 = new PdfPCell(new Paragraph(String.valueOf(netPrice)));
        BigDecimal netAmount = amount.multiply(netPrice).setScale(2, RoundingMode.CEILING);
        netAmountTotal = netAmountTotal.add(netAmount);
        PdfPCell entry5 = new PdfPCell(new Paragraph(String.valueOf(netAmount)));
        PdfPCell entry6 = new PdfPCell(new Paragraph(
            String.valueOf(invoice.getEntries().get(i).getVat())));
        BigDecimal vat = Vat.VAT_23.getValue();
        BigDecimal vatAmount = netPrice.multiply(vat).multiply(amount)
            .setScale(2, RoundingMode.CEILING);
        vatAmountTotal = vatAmountTotal.add(vatAmount);
        PdfPCell entry7 = new PdfPCell(new Paragraph(String.valueOf(vatAmount)));
        BigDecimal grossAmount = netAmount.add(vatAmount).setScale(2, RoundingMode.CEILING);
        grossAmountTotal = grossAmountTotal.add(grossAmount);
        PdfPCell entry8 = new PdfPCell(new Paragraph(String.valueOf(grossAmount)));
        entries.addCell(entry1);
        entries.addCell(entry2);
        entries.addCell(entry3);
        entries.addCell(entry4);
        entries.addCell(entry5);
        entries.addCell(entry6);
        entries.addCell(entry7);
        entries.addCell(entry8);
      }
      document.add(entries);

      PdfPTable total = new PdfPTable(8);
      total.setWidthPercentage(100); //Width 100%
      total.setWidths(columnWidths3);
      PdfPCell total1 = new PdfPCell(new Paragraph("Total"));
      PdfPCell total2 = new PdfPCell(new Paragraph(""));
      PdfPCell total3 = new PdfPCell(new Paragraph(String.valueOf(amountTotal)));
      PdfPCell total4 = new PdfPCell(new Paragraph(String.valueOf(netPriceTotal)));
      PdfPCell total5 = new PdfPCell(new Paragraph(String.valueOf(netAmountTotal)));
      PdfPCell total6 = new PdfPCell(new Paragraph(""));
      PdfPCell total7 = new PdfPCell(new Paragraph(String.valueOf(vatAmountTotal)));
      PdfPCell total8 = new PdfPCell(new Paragraph(String.valueOf(grossAmountTotal)));

      total.addCell(total1);
      total.addCell(total2);
      total.addCell(total3);
      total.addCell(total4);
      total.addCell(total5);
      total.addCell(total6);
      total.addCell(total7);
      total.addCell(total8);
      document.add(total);

      document.close();
      writer.close();
    } catch (DocumentException | FileNotFoundException e) {
      e.printStackTrace();
    }
    return new ByteArrayInputStream(generatedPdf.toByteArray());
  }
}
