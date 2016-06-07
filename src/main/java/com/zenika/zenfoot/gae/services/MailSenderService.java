package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.AppSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restx.factory.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Component
public class MailSenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailSenderService.class);
    private final AppSettings appSettings;

    public MailSenderService(AppSettings appSettings) {
        this.appSettings = appSettings;
    }

    /**
     * Send mail, encoding subject and text to UTF-8
     * @param recipient
     * @param subject
     * @param messageContent
     * @throws MessagingException
     */
    public void sendMail(String recipient, String subject, String messageContent) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(appSettings.mailFromAddress(), appSettings.mailFromName()));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(recipient));
            msg.setSubject(subject, "UTF-8");
            msg.setContent(messageContent, "text/html; charset=utf-8");
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("Send mail from {}<{}> to {}.", appSettings.mailFromAddress(), appSettings.mailFromName(), recipient);
                LOGGER.debug("Subject: {}", msg.getSubject());
                LOGGER.debug("Content:\n{}", messageContent);
            }
            Transport.send(msg);
        } catch (UnsupportedEncodingException e) {
            throw new MessagingException(e.getMessage(), e);
        }
    }
}
