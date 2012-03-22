package nl.hr.energize.eview;

import java.io.IOException;
import java.net.URLConnection;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public abstract class ExcelReader {

	public ExcelReader(UrlConnector connector) throws IOException {
		this(connector.createURLConnection());
	}

	public ExcelReader(URLConnection urlConnection) throws IOException {
		this(new HSSFWorkbook(urlConnection.getInputStream()).getSheetAt(0));
	}

	public ExcelReader(HSSFSheet sheet) {
		processRows(sheet);
	}

	protected void processRows(HSSFSheet sheet) {
		int lastRowNumber = sheet.getLastRowNum();
		for (int i = 1; i < lastRowNumber; i++)
			processRow(sheet.getRow(i));
	}

	protected abstract void processRow(HSSFRow row);

}
