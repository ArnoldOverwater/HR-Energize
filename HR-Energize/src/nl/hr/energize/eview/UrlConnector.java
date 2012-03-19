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
	 * The channel of data (EG which building or which meter) to retrieve from.
	 */
	private String dataChannel;
	
	/**
	 * The begin date and time to be requested.
	 */
	private long beginDate;
	
	/**
	 * The end date and time to be requested.
	 */
	private long endDate;
	
	private TimeGroup timeGroup;
	
	public UrlConnector() {
		this(null);
	}
	
	public UrlConnector(TimeGroup timeGroup) {
		this(timeGroup, Calendar.getInstance());
	}
	
	public UrlConnector(TimeGroup timeGroup, long date) {
		if (timeGroup == null)
			this.timeGroup = TimeGroup.Day;
		else
			this.timeGroup = timeGroup;
		setBothTimes(date);
	}
	
	public UrlConnector(TimeGroup timeGroup, Date date) {
		if (timeGroup == null)
			this.timeGroup = TimeGroup.Day;
		else
			this.timeGroup = timeGroup;
		setBothTimes(date);
	}
	
	public UrlConnector(TimeGroup timeGroup, Calendar date) {
		if (timeGroup == null)
			this.timeGroup = TimeGroup.Day;
		else
			this.timeGroup = timeGroup;
		setBothTimes(date);
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
		urlSpec.append(URL_DATA_CHANNEL).append('=').append(dataChannel);
		try {
			return new URL(urlSpec.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public UrlConnector setDataChannel(String dataChannel) {
		this.dataChannel = dataChannel;
		return this;
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

	/**
	 * @param timeGroup the timeGroup to set
	 */
	public UrlConnector setTimeGroup(TimeGroup timeGroup) {
		if (timeGroup != null)
			this.timeGroup = timeGroup;
		return this;
	}
	
	public UrlConnector setBothTimes(long date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		return setBothTimes(calendar);
	}
	
	public UrlConnector setBothTimes(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return setBothTimes(calendar);
	}
	
	public UrlConnector setBothTimes(Calendar date) {
		date = roundCalendar(date);
		beginDate = date.getTimeInMillis();
		switch (timeGroup) {
		case Quarter :
			date.add(Calendar.MINUTE, 15);
			break;
		case Hour :
			date.add(Calendar.HOUR_OF_DAY, 1);
			break;
		case Day :
			date.add(Calendar.DAY_OF_YEAR, 1);
			break;
		case Week :
			date.add(Calendar.WEEK_OF_YEAR, 1);
			break;
		case Month :
			date.add(Calendar.MONTH, 1);
			break;
		case Year :
			date.add(Calendar.YEAR, 1);
			break;
		}
		endDate = date.getTimeInMillis();
		return this;
	}
	
	public String getDataChannel() {
		return dataChannel;
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
	 * @return the timeGroup
	 */
	public TimeGroup getTimeGroup() {
		return timeGroup;
	}

	/**
	 * Round a calendar to a 15 minute scale.
	 * @param calendar The calendar to be rounded
	 * @return A copy of the calendar, which will be rounded.
	 */
	private Calendar roundCalendar(Calendar calendar) {
		Calendar clone = (Calendar)calendar.clone();
		switch (timeGroup) {
		case Year :
			clone.set(Calendar.MONTH, Calendar.JANUARY);
		case Month :
			clone.set(Calendar.DAY_OF_MONTH, 1);
		case Week :
			if (timeGroup == TimeGroup.Week) // Prevent repeating day set
				clone.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		case Day :
			clone.set(Calendar.HOUR_OF_DAY, 0);
		case Hour :
			clone.set(Calendar.MINUTE, 0);
		case Quarter :
			if (timeGroup == TimeGroup.Quarter) { // Prevent repeating minute set
				int minutes = calendar.get(Calendar.MINUTE);
				clone.set(Calendar.MINUTE, minutes - minutes % 15);
			}
		default :
			clone.set(Calendar.SECOND, 0);
			clone.set(Calendar.MILLISECOND, 0);
		}
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
	
	/**
	 * Used to determine at which time base to round.
	 * @author Arnold Overwater
	 */
	public enum TimeGroup {
		Quarter, Hour, Day, Week, Month, Year
	}

}
