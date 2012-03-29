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
public class cleanServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		  Query q = new Query("Pand");
		  PreparedQuery pq = datastore.prepare(q);
		  
		  for(Entity result: pq.asIterable()){  
			  result.setProperty("datum", 0.0);
			  result.setProperty("meting", 0.0);
			  result.setProperty("Ggister", 0.0);
			  result.setProperty("Gvandaag", 0.0);
			  result.setProperty("Gmorgen", 0.0);
			  result.setProperty("status", 0);
			  datastore.put(result);
		  }
		  
	}
}
