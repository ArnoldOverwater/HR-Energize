package nl.hr.energize.eview;

import java.io.IOException;
import java.util.Calendar;

import nl.hr.energize.eview.UrlConnector.TimeGroup;

public class TotalReaderTest {

	public static void main(String[] args) throws IOException {
		Calendar date = Calendar.getInstance();
		// Leap day
		date.set(Calendar.YEAR, 2012);
		date.set(Calendar.MONTH, Calendar.FEBRUARY);
		date.set(Calendar.DAY_OF_MONTH, 29);
		
		/*
		UrlConnector connector = new UrlConnector();
		connector.setBeginDate(date);
		date.add(Calendar.DAY_OF_YEAR, 1);
		connector.setEndDate(date);
		*/
		
		UrlConnector connector = new UrlConnector(TimeGroup.Day, date);
		
		TotalReader reader;
		
		reader = new TotalReader(connector.setDataChannel("DSEDS937734"));
		System.out.println(reader.getTotal());
		
		reader = new TotalReader(connector.setDataChannel("DSEDS937735"));
		System.out.println(reader.getTotal());
		
		reader = new TotalReader(connector.setDataChannel("DSEDS937744"));
		System.out.println(reader.getTotal());
	}

}
