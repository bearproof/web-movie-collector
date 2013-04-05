package ro.isdc.utils;
import java.io.IOException;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import org.apache.log4j.Logger;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * Class used for sending user confirmation messages via email 
 * 
 * @author Paul.Tamas
 *
 */
public class EmailSender {
	
	private Properties properties = null;
	
	private static final Logger logger = Logger.getLogger(EmailSender.class);

	/**
	 * Static method used to send user registration confirmation via email
	 * @throws IOException 
	 */
	public void sendConfirmationEmail(String emailAddress, String url) throws IOException{
			
		Resource resource = new ClassPathResource("/mailregistration.properties");
		Session mailSession = null;
		Locale locale = LocaleContextHolder.getLocale();
		String lang = locale.toString();
		if(lang==null){
			lang = Locale.getDefault().getLanguage().toString();
		}
		properties = System.getProperties();
		PropertiesLoaderUtils.fillProperties(properties,resource);
		
		if(!properties.getProperty("mail.smtp.username").equals("") || properties.getProperty("mail.smtp.username")!=null){			
			Authenticator auth = new SMTPAuthenticator();
			mailSession  = Session.getDefaultInstance(properties,auth);	      
		}else{
			mailSession  = Session.getDefaultInstance(properties);	      			
		}
	
		try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(mailSession);
	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(properties.getProperty("mail.smtp.sender")));
	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO,new InternetAddress(emailAddress));
	         // Set Subject: header field
	         message.setSubject(properties.getProperty("mail.subject."+lang));	         
	         // Now set the actual message
	         message.setContent("<p>"+properties.getProperty("mail.message."+lang)+" <a href = '"+url+"'>"+properties.getProperty("mail.activation.link."+lang)+"</a> </p>", "text/html");
	         // Send message
	         Transport.send(message, message.getRecipients(Message.RecipientType.TO));
	         logger.debug("Message sent successfully....");
	    }catch (MessagingException mex) {
	         mex.printStackTrace();
	    }
	}
	
	private class SMTPAuthenticator extends javax.mail.Authenticator{
		
		public PasswordAuthentication getPasswordAuthentication(){
			String username = properties.getProperty("mail.smtp.username");
			String password = properties.getProperty("mail.smtp.pwd");
			if(username!=null && password!=null){				
				return new PasswordAuthentication(username,password);
			}
			return null;
		}
	}
	
}
