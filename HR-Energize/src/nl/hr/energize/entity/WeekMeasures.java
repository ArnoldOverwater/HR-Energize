package nl.hr.energize.entity;

import java.util.Calendar;

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
	
	public static double getAverage(Entity entity, Calendar calendar){
		double sum = 0;
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		
		if(weekDay > Calendar.SUNDAY && weekDay < Calendar.SATURDAY){
			for(int i = Calendar.MONDAY; i <= Calendar.FRIDAY; i++){
				sum += getDayMeting(entity, i);
			}
			sum /= 5; 
		}else{
			sum += getDayMeting(entity, Calendar.SATURDAY);
			sum += getDayMeting(entity, Calendar.SUNDAY);
			sum /= 2;
		}
		return sum;
	}

}
