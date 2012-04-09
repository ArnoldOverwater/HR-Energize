package nl.hr.energize;

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
	private final static String FROM = "noreply@hrenergize.appspotmail.com";
	
	private String dateString;

	public void sendEmail(Entity gebouw)
	{
		sendEmail(gebouw, Premises.getEmail(gebouw));
	}

	public void sendEmail(Entity gebouw, String to)
	{
		String onderwerp = buildSubject(gebouw);
		String bericht = buildMessage(gebouw);

		Properties properties = System.getProperties();
		Session session = Session.getDefaultInstance(properties);

		try
		{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(FROM));
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
	
	public Mail setDateString(String dateString)
	{
		this.dateString = dateString;
		return this;
	}
	
	public String getDateString()
	{
		return dateString;
	}
	
	private String buildSubject(Entity gebouw)
	{
		return String.format("Waarschruwing - %s", Premises.getName(gebouw));
	}
	
	private String buildMessage(Entity gebouw)
	{
		String name = Premises.getName(gebouw);
		String extra = Premises.getExtra(gebouw);
		if (extra != null)
			name = String.format("%s %s", name, extra);
		String bericht = "Het gebouw <b>%s</b> heeft op <b>%s</b> 10%% of meer boven het gemiddelde aan energie verbruikt.<br /><br />" +
						 "De gemiddelde waarde was: <b>%s kWh</b><br />" +
						 "De gemeten waarde was: <b>%s kWh</b><br /><br />" +
						 "Hopende u hierbij voldoende op de hoogte te hebben gesteld.<br />" +
						 "Dit bericht is automatisch gegenereerd. U kunt niet op dit bericht reageren.<br />" +
						 "Voor een uitgebreid overzicht van alle panden gaat u naar de website " +
						 "<a href=\"http://hrenergize.appspot.com\">http://hrenergize.appspot.com</a>";
		return String.format(bericht, name, dateString, Premises.getSchatting(gebouw), Premises.getMeting(gebouw));
	}
}
