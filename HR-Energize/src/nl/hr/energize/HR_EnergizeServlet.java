package nl.hr.energize;

import java.io.IOException;
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

@SuppressWarnings("serial")
public class HR_EnergizeServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		  Calendar dateGister = Calendar.getInstance();
		  dateGister.set(Calendar.DAY_OF_MONTH, (dateGister.get(Calendar.DAY_OF_MONTH)-1));
		  
		  Calendar dateMorgen = Calendar.getInstance();
		  dateMorgen.set(Calendar.DAY_OF_MONTH, (dateMorgen.get(Calendar.DAY_OF_MONTH))+1);
		  
		  SimpleDateFormat format = new SimpleDateFormat("d-M-y H:mm:ss");
		  String metingDatum = format.format(dateGister.getTimeInMillis());
		  
		  UrlConnector connectorGister = new UrlConnector(TimeGroup.Day, dateGister);
		  UrlConnector connectorMorgen = new UrlConnector(TimeGroup.Day, dateMorgen);
		  
		  TotalReader reader;
		 
		  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		  Query q = new Query("Pand");
		  PreparedQuery pq = datastore.prepare(q);
		  
		  for(Entity result: pq.asIterable()){
			  
			  String DChoofdmeting = (String) result.getProperty("DChoofdmeting");
			  reader = new TotalReader(connectorGister.setDataChannel(DChoofdmeting));
			  
			  double Ggister = (Double) result.getProperty("Gvandaag");
			  double meting = roundTwoDecimals(reader.getTotal());
			  
			  result.setProperty("datum", metingDatum);
			  result.setProperty("meting", meting);
			  result.setProperty("Ggister", Ggister);
			  result.setProperty("Gvandaag", result.getProperty("Gmorgen")); 
			  
			  if(meting >(Ggister+(Ggister*0.10))){
				  result.setProperty("status", 3);
			  }else if(meting > (Ggister+(Ggister*0.05))){
				  result.setProperty("status", 2);
			  }else if(meting > 0.0){
				  result.setProperty("status", 1);
			  }else result.setProperty("status", 0);
			
			  reader = new TotalReader(connectorMorgen.setDataChannel(DChoofdmeting));
			  result.setProperty("Gmorgen", roundTwoDecimals(reader.getTotal()));
			  datastore.put(result);
		  }
		  
	}
	
	public double roundTwoDecimals(double getal){
		DecimalFormat df = new DecimalFormat(".##");
		String value = df.format(getal);
		value = value.replaceAll(",", ".");
		return Double.valueOf(value);
	}
}
