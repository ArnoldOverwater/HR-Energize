package nl.hr.energize.entity;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class WeekMeasures {

	private WeekMeasures() {
		
	}
	
	public static Entity setDayMeting(Entity entity, int day, double value) {
		entity.setProperty(Integer.toString(day), value);
		return entity;
	}
	
	public static double getDayMeting(Entity entity, int day) {
		return (Double)entity.getProperty(Integer.toString(day));
	}
	
	public static Entity getWeekMeasureForDataChannel(DatastoreService datastore, String dataChannel) {
		Query query = new Query(Entities.EntityKind.WeekMeasures.getKindName());
		query.addFilter("DChoofdmeting", FilterOperator.EQUAL, dataChannel);
		PreparedQuery prepared = datastore.prepare(query);
		return prepared.asSingleEntity();
	}

}