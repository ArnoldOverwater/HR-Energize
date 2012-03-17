package nl.hr.energize.eview;

import java.io.IOException;
import java.net.URLConnection;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public class TotalReader extends ExcelReader {

	private final static int COLUMN_INDEX = 2;
	
	private double total;
	
	public TotalReader(UrlConnector connector) throws IOException {
		super(connector);
	}

	public TotalReader(URLConnection urlConnection) throws IOException {
		super(urlConnection);
	}

	public TotalReader(HSSFSheet sheet) {
		super(sheet);
	}
	
	public double getTotal() {
		return total;
	}

	@Override
	protected void processRow(HSSFRow row) {
		total += row.getCell(COLUMN_INDEX).getNumericCellValue();
	}

}
