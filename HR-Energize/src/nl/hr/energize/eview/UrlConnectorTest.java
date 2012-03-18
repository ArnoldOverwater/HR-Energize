package nl.hr.energize.eview;

import java.io.IOException;
import java.util.Calendar;

import nl.hr.energize.eview.UrlConnector.TimeGroup;

public class UrlConnectorTest {

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
		
		UrlConnector connector = new UrlConnector();
		
		connector.setTimeGroup(TimeGroup.Quarter).setBothTimes(date);
		System.out.printf("Begin Date: %s ; End Date: %s\n", connector.getBeginDate().getTime().toString(), connector.getEndDate().getTime().toString());
		
		connector.setTimeGroup(TimeGroup.Hour).setBothTimes(date);
		System.out.printf("Begin Date: %s ; End Date: %s\n", connector.getBeginDate().getTime().toString(), connector.getEndDate().getTime().toString());
		
		connector.setTimeGroup(TimeGroup.Day).setBothTimes(date);
		System.out.printf("Begin Date: %s ; End Date: %s\n", connector.getBeginDate().getTime().toString(), connector.getEndDate().getTime().toString());
		
		connector.setTimeGroup(TimeGroup.Week).setBothTimes(date);
		System.out.printf("Begin Date: %s ; End Date: %s\n", connector.getBeginDate().getTime().toString(), connector.getEndDate().getTime().toString());
		
		connector.setTimeGroup(TimeGroup.Month).setBothTimes(date);
		System.out.printf("Begin Date: %s ; End Date: %s\n", connector.getBeginDate().getTime().toString(), connector.getEndDate().getTime().toString());
		
		connector.setTimeGroup(TimeGroup.Year).setBothTimes(date);
		System.out.printf("Begin Date: %s ; End Date: %s\n", connector.getBeginDate().getTime().toString(), connector.getEndDate().getTime().toString());
	}

}
