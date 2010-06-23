package com.zenika.zenfoot.service.email;

import java.util.Map;
import org.springframework.mail.MailException;

/**
 * Facility to send email out of velocity templates.
 */
public interface EmailService {
    /**
     * Send an email to the given recipient.
     * The templateFolder is expected to contain at least 3 UTF-8 velocity templates: <br/>
     * subject_locale.vm.txt, body_locale.vm.txt and body_locale.vm.html
     * where "_locale" string follows the same rule as for resource bundles
     * (e.g. _fr, _FR, _fr_FR, etc...).
     * The best matching template will be picked up based on the current user locale.
     * If no template can be found, it defaults to subject.vm.txt, body.vm.txt or body.vm.html.
     *
     * @param toEmail - the email recipient
     * @param fromEmail - the email sender
     * @param templateFolder - the path to the folder containing the 3 expected velocity email templates.
     *                         This path is relative to the src/main/resources/folder, it should not start with a '/'.
     * @param templateContext - context passed to the velocity engine.
     * @throws EmailNotSentException
     */
    void sendEmail(String toEmail, String fromEmail, String replyTo, String templateFolder, Map<String, Object> templateContext) throws MailException;

    /**
     * Same as sendEmail but launch a separate thread.
     *
     * @param toEmail
     * @param fromEmail
     * @param templateFolder
     * @param templateContext
     * @throws EmailNotSentException
     */
    void sendEmailAsynchronously(String toEmail, String fromEmail, String replyTo, String templateFolder, Map<String, Object> templateContext);
}
