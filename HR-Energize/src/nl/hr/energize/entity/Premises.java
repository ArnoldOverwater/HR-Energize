package nl.hr.energize.entity;

import java.text.DecimalFormat;

import com.google.appengine.api.datastore.Entity;

public class Premises {
	
	private final static double ALARM_PERCENTAGE = 0.10;
	
	private final static double REASONABLE_PERCENTAGE = 0.05;

	private Premises() {
		
	}
	
	public static Status getStatusForPercentage(double percentage) {
		if (percentage > ALARM_PERCENTAGE)
			return Status.Alarm;
		else if (percentage > REASONABLE_PERCENTAGE)
			return Status.Reasonable;
		else
			return Status.AllRight;
	}
	
	public static Status getStatusForMeasures(double estimated, double actual) {
		return getStatusForPercentage((actual - estimated) / estimated);
	}
	
	public static Entity setDCHoofdMeting(Entity entity, String DCHoofdMeting) {
		entity.setProperty("DChoofdmeting", DCHoofdMeting);
		return entity;
	}
	
	public static Entity setMeting(Entity entity, double meting) {
		entity.setProperty("meting", roundTwoDecimals(meting));
		return entity;
	}
	
	public static Entity setSchatting(Entity entity, double schatting) {
		entity.setProperty("schatting", roundTwoDecimals(schatting));
		return entity;
	}
	
	public static Entity setStatus(Entity entity, Status status) {
		if (status == Status.AllRight)
			entity.setProperty("status", 1);
		else if (status == Status.Reasonable)
			entity.setProperty("status", 2);
		else if (status == Status.Alarm)
			entity.setProperty("status", 3);
		else
			entity.setProperty("status", 0);
		return entity;
	}
	
	public static Entity setStatusForPercentage(Entity entity, double percentage) {
		return setStatus(entity, getStatusForPercentage(percentage));
	}
	
	public static Entity setStatusForMeasures(Entity entity, double estimated, double actual) {
		return setStatus(entity, getStatusForMeasures(estimated, actual));
	}
	
	public static String getDCHoofdMeting(Entity entity) {
		return entity.getProperty("DChoofdmeting").toString();
	}
	
	public static String getName(Entity entity) {
		return entity.getProperty("gebouw").toString();
	}
	
	public static String getExtra(Entity entity) {
		String extra = entity.getProperty("opmerking").toString();
		if (extra.isEmpty())
			return null;
		else
			return extra;
	}
	
	public static double getMeting(Entity entity) {
		return (Double)entity.getProperty("meting");
	}
	
	public static double getSchatting(Entity entity) {
		return (Double)entity.getProperty("schatting");
	}
	
	public static String getEmail(Entity entity) {
		return entity.getProperty("e-mail").toString();
	}
	
	public static Status getStatus(Entity entity) {
		int status = (Integer)entity.getProperty("status");
		switch (status) {
		case 1 :
			return Status.AllRight;
		case 2 :
			return Status.Reasonable;
		case 3 :
			return Status.Alarm;
		default :
			return null;
		}
	}
	
	private static double roundTwoDecimals(double getal){
		DecimalFormat df = new DecimalFormat(".##");
		String value = df.format(getal);
		value = value.replaceAll(",", ".");
		return Double.parseDouble(value);
	}
	
	public enum Status {
		AllRight, Reasonable, Alarm
	}

}
