package com.zenika.zenfoot.gae.services;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSenderService {
	private final Properties properties;
	private final Session session;
	private final Logger logger;
	private final Message message;
	private final static String FROM_EMAIL_ADDRESS = "zen-foot@zenika.com"; 
	
	public MailSenderService() {
		properties = new Properties();
		session = Session.getDefaultInstance(properties, null);
		message = new MimeMessage(session);
		logger = Logger.getLogger(MailSenderService.class.getName());
	}

	public void sendMail(String recipient, String subject, String messageContent) {
		
		try {
			message.setFrom( new InternetAddress(FROM_EMAIL_ADDRESS) );
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject(subject);
			message.setText(messageContent);
			Transport.send(message);
			
		} catch (AddressException e) {
			logger.log(Level.WARNING, e.getMessage());
		} catch (MessagingException e) {
			logger.log(Level.WARNING, e.getMessage());
		}
	}
}
