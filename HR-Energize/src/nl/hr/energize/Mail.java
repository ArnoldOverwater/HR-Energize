package nl.hr.energize;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import nl.hr.energize.entity.Premises;

import com.google.appengine.api.datastore.Entity;

public class Mail
{
	public void sendEmail(Entity gebouw, String date)
    {
	      String to = "jhermus@upcmail.nl";
	      String from = "noreply@hrenergize.appspotmail.com";
	      String onderwerp = "Waarschruwing - " + gebouw.getProperty("gebouw");
	      String bericht = 	"Het gebouw %s heeft op %s 10%% of meer boven het gemiddelde aan energie verbruikt.<br /><br />" +
							"De gemiddelde waarde was: <b>%s kWh</b><br />" +
							"De gemeten waarde was: <b>%s kWh</b><br /><br />" +
							"Hopende u hierbij voldoende op de hoogte te hebben gesteld.<br />" +
							"Dit bericht is automatisch gegenereerd. U kunt niet op dit bericht reageren.<br />" +
							"Voor een uitgebreid overzicht van alle panden gaat u naar de website " +
							"<a href=\"http://hrenergize.appspot.com\">http://hrenergize.appspot.com</a>";
	      bericht = String.format(bericht, gebouw.getProperty("gebouw"), gebouw.getProperty("datum"), Premises.getSchatting(gebouw), Premises.getMeting(gebouw));

	      Properties properties = System.getProperties();
	      Session session = Session.getDefaultInstance(properties);

	      try
	      {
	         MimeMessage message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(from));
	         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
	         message.setSubject(onderwerp);
	         message.setContent(bericht, "text/html");
	         Transport.send(message);
	      }
	      catch (MessagingException mex) 
	      {
	         mex.printStackTrace();
	      }
    }
}
