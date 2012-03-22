package nl.hr.energize.eview;

import java.io.IOException;
import java.net.URLConnection;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public class MedianReader extends ExcelReader {

	private final static int COLUMN_INDEX = 1;
	
	private double median;

	public MedianReader(UrlConnector connector) throws IOException {
		super(connector);
	}

	public MedianReader(URLConnection urlConnection) throws IOException {
		super(urlConnection);
	}

	public MedianReader(HSSFSheet sheet) {
		super(sheet);
	}
	
	public double getMedian() {
		return median;
	}

	@Override
	protected void processRows(HSSFSheet sheet) {
		int lastRowNumber = sheet.getLastRowNum();
		int medianRowNumber = (lastRowNumber + 1) / 2;
		processRow(sheet.getRow(medianRowNumber));
	}

	@Override
	protected void processRow(HSSFRow row) {
		median = row.getCell(COLUMN_INDEX).getNumericCellValue();
	}

}
