package nl.hr.energize;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

@SuppressWarnings("serial")
public class HR_EnergizeServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		App bereken = new App();
		Double test = bereken.getValue();
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Entity meting = new Entity("distributie", 1);
		meting.setProperty("gebouw", "Blaak 10");
		meting.setProperty("schatting", 0.0);
		meting.setProperty("meting", 0.0);
		meting.setProperty("status", 0);
		datastore.put(meting);
		
		meting = new Entity("distributie", 2);
		meting.setProperty("gebouw", "G.J. de Jonghweg 6");
		meting.setProperty("schatting", 0.0);
		meting.setProperty("meting", 0.0);
		meting.setProperty("status", 0);
		datastore.put(meting);
	}
}
