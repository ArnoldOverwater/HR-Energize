package nl.hr.energize;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.*;

import nl.hr.energize.eview.TotalReader;
import nl.hr.energize.eview.UrlConnector;
import nl.hr.energize.eview.UrlConnector.TimeGroup;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

@SuppressWarnings("serial")
public class HR_EnergizeServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		  Calendar date1 = Calendar.getInstance();
		  date1.add(Calendar.DAY_OF_MONTH, -1);
		  Calendar date2 = Calendar.getInstance();
		  date2.add(Calendar.DAY_OF_MONTH, -2);
		  
		 
		  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		  Query panden = new Query("Pand");
		  PreparedQuery pq = datastore.prepare(panden);
		  
		  for(Entity result: pq.asIterable()){
			  
			  String DChoofdmeting = (String) result.getProperty("DChoofdmeting");
			  
			  UrlConnector connector = new UrlConnector(TimeGroup.Day, date1);
			  TotalReader reader = new TotalReader(connector.setDataChannel(DChoofdmeting));
			  
			  Query metingen = new Query("Weekmeting");
			  metingen.addFilter("DChoofdmeting", FilterOperator.EQUAL, DChoofdmeting);
			  PreparedQuery pqMeting = datastore.prepare(metingen);
			  Entity weekdag = pqMeting.asSingleEntity();
			  weekdag.setProperty(""+date2.get(Calendar.DAY_OF_WEEK), result.getProperty("meting"));
			  
			 
			 
			  double meting = roundTwoDecimals(reader.getTotal());
			  double gemiddelde =  getAverage(weekdag, date1);
			  result.setProperty("meting", meting);
			  result.setProperty("schatting", gemiddelde);
			  result.setProperty("status", getStatus((Double)result.getProperty("schatting"), meting));
			  
			 
			  datastore.put(result);
			  datastore.put(weekdag);
		  }
		  
	}
	
	public double roundTwoDecimals(double getal){
		DecimalFormat df = new DecimalFormat(".##");
		String value = df.format(getal);
		value = value.replaceAll(",", ".");
		return Double.valueOf(value);
	}
	
	public double getAverage(Entity entity, Calendar calendar){
		double sum = 0;
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		
		if(weekDay > 0 && weekDay <7){
			for(int i = 2; i < 7; i++){
				sum += (Double) entity.getProperty(i+"");
			}
			sum /= 5; 
		}else{
			sum += (Double) entity.getProperty("1");
			sum += (Double) entity.getProperty("7");
			sum /= 2;
		}
		return roundTwoDecimals(sum);
	}
	
	public int getStatus(double gemiddelde, double meting){
		
		 if(meting >(gemiddelde+(gemiddelde*0.10))){
			 return 3;
		  } else if(meting > (gemiddelde+(gemiddelde*0.05))){
			  return 2;
		  }else if(meting > 0.0){
			  return 1;
		  }else return 0;
	}
}
