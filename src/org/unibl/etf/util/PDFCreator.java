package org.unibl.etf.util;
//TODO izbaciti hard coding
//TODO izbaciti dupliranje koda
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.unibl.etf.dto.Plant;
import org.unibl.etf.dto.PriceHeightRatio;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFCreator {

	public PDFCreator() {
	}

	public PDFCreator(String filePath) {
		this.filePath = filePath;
		document = new Document(PageSize.A4);
	}

	public void open() throws FileNotFoundException, DocumentException {
		PdfWriter.getInstance(document, new FileOutputStream(filePath));
		document.open();
	}

	public void close() {
		document.close();
	}

	public void addParagraph(String content, Font font) throws DocumentException {
		document.add(new Paragraph(content, font));
	}

	public void addParagraph() throws DocumentException {
		document.add(new Paragraph(System.getProperty("line.separator")));
	}

	public void addHeader() throws FileNotFoundException, IOException, DocumentException {
		Properties properties = new Properties();
		properties.load(new FileInputStream("./resources/company.properties"));
		addParagraph(properties.getProperty("name"), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
		addParagraph();
		String contact = "Vlasnik: " + properties.getProperty("owner") + System.getProperty("line.separator")
				+ "Adresa: " + properties.getProperty("address") + System.getProperty("line.separator") + "Telefon: "
				+ properties.getProperty("phone");
		addParagraph(contact, FontFactory.getFont(FontFactory.HELVETICA, 12));
	}

	public PdfPTable getTable() {
		PdfPTable table = new PdfPTable(new float[] { 32, 32, 12, 12, 12 });
		BaseColor color = new BaseColor(225, 225, 208);
		table.setWidthPercentage(100);
		PdfPCell latinHeader = new PdfPCell(new Paragraph("Ime (latinsko)"));
		latinHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
		latinHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
		latinHeader.setRowspan(2);
		latinHeader.setBackgroundColor(color);
		PdfPCell commonHeader = new PdfPCell(new Paragraph("Ime (domace)"));
		commonHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
		commonHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
		commonHeader.setRowspan(2);
		commonHeader.setBackgroundColor(color);
		PdfPCell pricesHeader = new PdfPCell(new Paragraph("Cijene"));
		pricesHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
		pricesHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pricesHeader.setColspan(3);
		pricesHeader.setBackgroundColor(color);
		PdfPCell from = new PdfPCell(new Paragraph("Od (cm)"));
		from.setHorizontalAlignment(Element.ALIGN_CENTER);
		from.setVerticalAlignment(Element.ALIGN_MIDDLE);
		from.setBackgroundColor(color);
		PdfPCell to = new PdfPCell(new Paragraph("Do (cm)"));
		to.setHorizontalAlignment(Element.ALIGN_CENTER);
		to.setVerticalAlignment(Element.ALIGN_MIDDLE);
		to.setBackgroundColor(color);
		PdfPCell price = new PdfPCell(new Paragraph("Cijena"));
		price.setHorizontalAlignment(Element.ALIGN_CENTER);
		price.setVerticalAlignment(Element.ALIGN_MIDDLE);
		price.setBackgroundColor(color);
		table.addCell(latinHeader);
		table.addCell(commonHeader);
		table.addCell(pricesHeader);
		table.addCell(from);
		table.addCell(to);
		table.addCell(price);
		return table;
	}

	public void addTable(PdfPTable table) throws DocumentException {
		document.add(table);
	}

	public void populateTable(PdfPTable table, List<Plant> plants) {
		PdfPCell latin = null;
		PdfPCell common = null;
		PdfPCell phr = null;
		for (Plant plant : plants) {
			latin = new PdfPCell(new Paragraph(plant.getScientificName()));
			latin.setRowspan(plant.getRatios().size());
			latin.setHorizontalAlignment(Element.ALIGN_CENTER);
			latin.setVerticalAlignment(Element.ALIGN_MIDDLE);
			common = new PdfPCell(new Paragraph(plant.getKnownAs()));
			common.setRowspan(plant.getRatios().size());
			common.setHorizontalAlignment(Element.ALIGN_CENTER);
			common.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(latin);
			table.addCell(common);
			for (PriceHeightRatio ratio : plant.getRatios()) {
				phr = new PdfPCell(new Paragraph(String.format("%.2f", ratio.getHeightMin())));
				phr.setHorizontalAlignment(Element.ALIGN_CENTER);
				phr.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(phr);
				phr = new PdfPCell(new Paragraph(
						ratio.getHeightMax() != null ? String.format("%.2f", ratio.getHeightMax()) : "-"));
				phr.setHorizontalAlignment(Element.ALIGN_CENTER);
				phr.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(phr);
				phr = new PdfPCell(new Paragraph(String.format("%.2f", ratio.getPrice())));
				phr.setHorizontalAlignment(Element.ALIGN_CENTER);
				phr.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(phr);
			}
		}
	}

	public void createPricelist(List<Plant> plants) throws FileNotFoundException, IOException, DocumentException {
		addHeader();
		addParagraph();
		addParagraph();
		addParagraph("Cjenovnik za " + new SimpleDateFormat("dd.MM.yyyy.").format(Calendar.getInstance().getTime()),
				FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
		addParagraph();
		PdfPTable table = getTable();
		populateTable(table, plants);
		addTable(table);
		addFooter();
	}

	public void addFooter() throws DocumentException {
		Paragraph line = new Paragraph("_____________________");
		line.setAlignment(Element.ALIGN_RIGHT);
		Paragraph signature = new Paragraph("Potpis vlasnika         ");
		signature.setAlignment(Element.ALIGN_RIGHT);
		addParagraph();
		addParagraph();
		document.add(line);
		document.add(signature);
	}

	private String filePath;
	private Document document;
}
