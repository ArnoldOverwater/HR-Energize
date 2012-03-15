package nl.hr.energize.eview;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UrlConnector {

	private final static String URL_BASE = "http://www.eview.nl/DownloadData.ashx";
	
	private final static String URL_BEGIN_DATE = "FromDate";
	
	private final static String URL_END_DATE = "TillDate";
	
	private final static String URL_DATA_CHANNEL = "Datachannel";
	
	/**
	 * The begin date and time to be requested.
	 */
	private long beginDate;
	
	/**
	 * The end date and time to be requested.
	 */
	private long endDate;
	
	/**
	 * Sets the requested date to be requested to yesterday
	 */
	public UrlConnector() {
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DAY_OF_YEAR, -1);
		yesterday.set(Calendar.HOUR_OF_DAY, 0);
		yesterday.set(Calendar.MINUTE, 0);
		yesterday.set(Calendar.SECOND, 0);
		yesterday.set(Calendar.MILLISECOND, 0);
		beginDate = yesterday.getTimeInMillis();
		yesterday.add(Calendar.DAY_OF_YEAR, 1);
		endDate = yesterday.getTimeInMillis();
	}
	
	/**
	 * Create an URLConnection by using createURL() with the COOKIE constant in it.
	 * @return The URLConnection.
	 */
	public URLConnection createURLConnection() {
		return createURLConnection(createURL());
	}
	
	/**
	 * Create an URL based on the set values of this object.
	 * The data channel is hard coded for now.
	 * @return The URL.
	 */
	public URL createURL() {
		StringBuilder urlSpec = new StringBuilder(URL_BASE);
		urlSpec.append('?');
		urlSpec.append(URL_BEGIN_DATE).append('=').append(getUrlFragment(beginDate));
		urlSpec.append('&');
		urlSpec.append(URL_END_DATE).append('=').append(getUrlFragment(endDate));
		urlSpec.append('&');
		urlSpec.append(URL_DATA_CHANNEL).append('=').append("DSEDS937732");//Hard coded for now
		try {
			return new URL(urlSpec.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public UrlConnector setBeginDate(long date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		return setBeginDate(calendar);
	}
	
	public UrlConnector setBeginDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return setBeginDate(calendar);
	}
	
	public UrlConnector setBeginDate(Calendar date) {
		beginDate = roundCalendar(date).getTimeInMillis();
		return this;
	}
	
	public UrlConnector setEndDate(long date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		return setEndDate(calendar);
	}
	
	public UrlConnector setEndDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return setEndDate(calendar);
	}
	
	public UrlConnector setEndDate(Calendar date) {
		endDate = roundCalendar(date).getTimeInMillis();
		return this;
	}
	
	public Calendar getBeginDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(beginDate);
		return calendar;
	}
	
	public Calendar getEndDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(endDate);
		return calendar;
	}
	
	/**
	 * Round a calendar to a 15 minute scale.
	 * @param calendar The calendar to be rounded
	 * @return A copy of the calendar, which will be rounded.
	 */
	private Calendar roundCalendar(Calendar calendar) {
		int minutes = calendar.get(Calendar.MINUTE);
		Calendar clone = (Calendar)calendar.clone();
		clone.set(Calendar.MINUTE, minutes - minutes % 15);
		clone.set(Calendar.SECOND, 0);
		clone.set(Calendar.MILLISECOND, 0);
		return clone;
	}
	
	public static String getUrlFragment(long date) {
		return getUrlFragment(new Date(date));
	}
	
	public static String getUrlFragment(Calendar date) {
		return getUrlFragment(date.getTime());
	}
	
	public static String getUrlFragment(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("d-M-y H:mm:ss");
		try {
			return URLEncoder.encode(format.format(date), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static URLConnection createURLConnection(URL url) {
		try {
			URLConnection connection = url.openConnection();
			connection.addRequestProperty("Cookie", Cookie.COOKIE_NAME + "=" + Cookie.COOKIE);
			return connection;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
