package org.unibl.etf.application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.unibl.etf.dao.interfaces.DAOException;
import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.util.PDFCreator;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfPTable;

public class Test {
	public static void main(String[] args) throws DAOException {
		PDFCreator creator = new PDFCreator("./resources/test.pdf");
		try {
			creator.open();
			creator.addHeader();
			creator.addParagraph();
			creator.addParagraph();
			creator.addParagraph(
					"Cjenovnik za " + new SimpleDateFormat("dd.MM.yyyy.").format(Calendar.getInstance().getTime()),
					FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
			creator.addParagraph();
			PdfPTable table = creator.getTable();
			creator.populateTable(table, DAOFactory.getInstance().getPlantDAO().selectAll());
			creator.addTable(table);
			creator.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
