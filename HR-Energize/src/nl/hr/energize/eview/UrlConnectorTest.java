package nl.hr.energize.eview;

import java.io.IOException;
import java.net.URLConnection;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class UrlConnectorTest {

	public static void main(String[] args) throws IOException {
		Calendar date = Calendar.getInstance();
		// Leap day
		date.set(Calendar.YEAR, 2012);
		date.set(Calendar.MONTH, Calendar.FEBRUARY);
		date.set(Calendar.DAY_OF_MONTH, 29);
		
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 1); // Will be rounded to 0
		
		UrlConnector connector = new UrlConnector();
		connector.setBeginDate(date);
		date.add(Calendar.DAY_OF_YEAR, 1);
		connector.setEndDate(date);
		
		URLConnection urlConn = connector.createURLConnection();
		
		// Taken from App.java
		HSSFWorkbook workbook = new HSSFWorkbook(urlConn.getInputStream());
		HSSFSheet worksheet = workbook.getSheetAt(0);
		
		int RowIndex = 2;
		int LastRow = worksheet.getLastRowNum();
		double KWhvalue = 0.0;
		
		for(int i = 1; i < LastRow; i++){
			HSSFRow row = worksheet.getRow(i);
			HSSFCell cell = row.getCell(RowIndex);
			KWhvalue += cell.getNumericCellValue();
		}
		
		System.out.println(KWhvalue);
	}

}
