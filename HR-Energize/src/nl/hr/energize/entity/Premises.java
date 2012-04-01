package nl.hr.energize.entity;

import com.google.appengine.api.datastore.Entity;

public class Premises {

	private Premises() {
		
	}
	
	public static Entity setDCHoofdMeting(Entity entity, String DCHoofdMeting) {
		entity.setProperty("DChoofdmeting", DCHoofdMeting);
		return entity;
	}
	
	public static Entity setMeting(Entity entity, double meting) {
		entity.setProperty("meting", meting);
		return entity;
	}
	
	public static Entity setSchatting(Entity entity, double schatting) {
		entity.setProperty("schatting", schatting);
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
	
	public static String getDCHoofdMeting(Entity entity) {
		return entity.getProperty("DChoofdmeting").toString();
	}
	
	public static double getMeting(Entity entity) {
		return (Double)entity.getProperty("meting");
	}
	
	public static double getSchatting(Entity entity) {
		return (Double)entity.getProperty("schatting");
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
	
	public enum Status {
		AllRight, Reasonable, Alarm
	}

}
