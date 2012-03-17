package nl.hr.energize.eview;

import java.io.IOException;
import java.util.Calendar;

public class TotalReaderTest {

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
		
		TotalReader reader = new TotalReader(connector);
		
		System.out.println(reader.getTotal());
	}

}
