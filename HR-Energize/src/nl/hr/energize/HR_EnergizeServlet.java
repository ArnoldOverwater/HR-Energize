package nl.hr.energize;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.http.*;

import nl.hr.energize.entity.Entities;
import nl.hr.energize.entity.Entities.EntityKind;
import nl.hr.energize.entity.Premises;
import nl.hr.energize.entity.WeekMeasures;
import nl.hr.energize.eview.TotalReader;
import nl.hr.energize.eview.UrlConnector;
import nl.hr.energize.eview.UrlConnector.TimeGroup;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

@SuppressWarnings("serial")
public class HR_EnergizeServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		Calendar date1 = Calendar.getInstance();
		date1.add(Calendar.DAY_OF_MONTH, -1);
		Calendar date2 = Calendar.getInstance();
		date2.add(Calendar.DAY_OF_MONTH, -2);

		SimpleDateFormat format = new SimpleDateFormat("d-M-y");

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Mail mail = new Mail().setDateString(format.format(date1.getTime()));

		for(Entity result: new Entities(datastore, EntityKind.Premises)){

			String DChoofdmeting = Premises.getDCHoofdMeting(result);

			UrlConnector connector = new UrlConnector(TimeGroup.Day, date1);
			TotalReader reader = new TotalReader(connector.setDataChannel(DChoofdmeting));

			Entity weekdag = WeekMeasures.getWeekMeasureForDataChannel(datastore, DChoofdmeting);
			WeekMeasures.setDayMeting(weekdag, date2.get(Calendar.DAY_OF_WEEK), Premises.getMeting(result));



			double meting = reader.getTotal();
			double gemiddelde = WeekMeasures.getAverage(weekdag, date1);
			Premises.setMeting(result, meting);
			Premises.setSchatting(result, gemiddelde);
			Premises.setStatusForMeasures(result, Premises.getSchatting(result), meting);


			if(Premises.getStatus(result) == Premises.Status.Alarm){
				mail.sendEmail(result, "jhermus@upcmail.nl");
			}

			datastore.put(result);
			datastore.put(weekdag);
		}

	}

}
