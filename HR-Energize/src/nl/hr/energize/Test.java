package nl.hr.energize;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

@SuppressWarnings("serial")
public class Test extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		Calendar date1 = Calendar.getInstance();
		date1.add(Calendar.DAY_OF_MONTH, -1);

		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query("Pand");
		query.addFilter("DChoofdmeting", FilterOperator.EQUAL, "DSEDS937732");
		PreparedQuery pq = datastore.prepare(query);
		Entity gebouw = pq.asSingleEntity();
		Mail mail = new Mail();
		mail.setDateString(format.format(date1.getTime()));
		mail.sendEmail(gebouw, "arnoldoverwater@gmail.com");

	}

}
