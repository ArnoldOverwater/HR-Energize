package nl.hr.energize.eview;

import java.io.IOException;
import java.util.Calendar;

import nl.hr.energize.eview.UrlConnector.TimeGroup;

public class MedianReaderTest {

	public static void main(String[] args) throws IOException {
		Calendar date = Calendar.getInstance();
		// Leap day
		date.set(Calendar.YEAR, 2012);
		date.set(Calendar.MONTH, Calendar.FEBRUARY);
		date.set(Calendar.DAY_OF_MONTH, 29);
		
		UrlConnector connector = new UrlConnector(TimeGroup.Day, date).setDataChannel("DSEDS888823");
		
		MedianReader reader = new MedianReader(connector);
		System.out.println(reader.getMedian());
	}

}
