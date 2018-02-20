package org.unibl.etf.application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.unibl.etf.dao.interfaces.DAOException;
import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Plant;
import org.unibl.etf.util.PDFCreator;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfPTable;

public class Test {
	public static void main(String[] args) throws DAOException {
		Plant p = DAOFactory.getInstance().getPlantDAO().getByPrimaryKey(2);
		System.out.println(p.getHeightMin(new BigDecimal(25)));
	}
}
