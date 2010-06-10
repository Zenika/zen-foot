package com.zenika.zenfoot.service.email;

import com.zenika.zenfoot.util.AssertUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.MailException;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Facility to send email out of velocity templates.
 */
public class DefaultEmailService implements EmailService {

    static final private Logger logger = LoggerFactory.getLogger(DefaultEmailService.class);
    protected JavaMailSender javaMailSender;
    protected VelocityEngine velocityEngine;

    public DefaultEmailService(JavaMailSender javaMailSender, VelocityEngine velocityEngine) {
        this.javaMailSender = javaMailSender;
        this.velocityEngine = velocityEngine;
    }

    /**
     * Send an email to the given recipient. <br>
     * The templateFolder is expected to contain at least 3 UTF-8 velocity templates: <br>
     * subject_locale.vm.txt, body_locale.vm.txt and body_locale.vm.html where "_locale" string follows the same rule as for resource bundles (e.g. _fr, _FR,
     * _fr_FR, etc...). <br>
     * The best matching template will be picked up based on the current user locale. If no template can be found, it defaults to subject.vm.txt, body.vm.txt or
     * body.vm.html.<br>
     * Within the velocity templates you can access resources using ${resourceUtil.getProperty("property.code")}
     *
     * @param toEmail - the email recipient
     * @param fromEmail - the email sender
     * @param templateFolder - the path to the folder containing the 3 expected velocity email templates.
     * This path is relative to the src/main/resources/folder, it should not start with a '/'.
     * @param templateContext - context passed to the velocity engine.
     */
    @Override
    public void sendEmail(String toEmail, String fromEmail, String templateFolder, Map<String, Object> templateContext) throws MailException {
        AssertUtil.hasLength(toEmail);
        AssertUtil.hasLength(fromEmail);
        AssertUtil.hasLength(templateFolder);
        AssertUtil.notNull(templateContext);

        // select templates
        Locale locale = LocaleContextHolder.getLocale();
        String subjectTemplate = getTemplate(templateFolder, "subject", locale, ".vm.txt");
        String textBodyTemplate = getTemplate(templateFolder, "body", locale, ".vm.txt");
        String htmlBodyTemplate = getTemplate(templateFolder, "body", locale, ".vm.html");

        if (logger.isDebugEnabled()) {
            logger.debug("subjectTemplate: " + subjectTemplate);
            logger.debug("textBodyTemplate: " + textBodyTemplate);
            logger.debug("htmlBodyTemplate: " + htmlBodyTemplate);
        }

        // construct the message
        MimeMessage mm = buildMimeMessage(toEmail, fromEmail, templateContext, subjectTemplate, textBodyTemplate, htmlBodyTemplate);

        // send the prepared message
        javaMailSender.send(mm);

        if (logger.isInfoEnabled()) {
            logger.info("Email Service: email sent OK to " + toEmail + " using templates " + subjectTemplate + ":" + textBodyTemplate + ":" + htmlBodyTemplate);
        }
    }

    private MimeMessage buildMimeMessage(String toEmail, String fromEmail, Map<String, Object> templateContext, String subjectTemplate,
            String textBodyTemplate, String htmlBodyTemplate) {

        MimeMessage mm = javaMailSender.createMimeMessage();
        // prepare the message
        try {
            MimeMessageHelper mmh = new MimeMessageHelper(mm, true, "UTF-8");

            // evaluate velocity templates
            String subject = mergeTemplateIntoString(subjectTemplate, templateContext);
            String textBody = mergeTemplateIntoString(textBodyTemplate, templateContext);
            String htmlBody = mergeTemplateIntoString(htmlBodyTemplate, templateContext);

            if (logger.isDebugEnabled()) {
                logger.debug("subject: " + subject);
                logger.debug("textBody: " + textBody);
                logger.debug("htmlBody: " + htmlBody);
            }

            // create mime message
            mmh.setTo(toEmail);
            mmh.setFrom(fromEmail);
            mmh.setSubject(subject);
            mmh.setText(textBody, htmlBody);
            return mm;
        } catch (VelocityException ve) {
            throw new MailPreparationException(ve);
        } catch (MessagingException me) {
            throw new MailPreparationException(me);
        }
    }

    /**
     * Same as sendEmail but in a separate thread.
     */
    @Override
    public void sendEmailAsynchronously(final String toEmail, final String fromEmail, final String templateFolder, final Map<String, Object> templateContext) {
        (new Thread() {

            @Override
            public void run() {
                try {
                    sendEmail(toEmail, fromEmail, templateFolder, templateContext);
                } catch (Exception e) {
                    if (logger.isErrorEnabled()) {
                        logger.error("Could not send email from a separate thread. toEmail=" + toEmail, e);
                    }
                }
            }
        }).start();
    }

    protected String mergeTemplateIntoString(String templateLocation, Map<String, Object> templateContext) throws VelocityException {
        return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, "UTF-8", templateContext);
    }

    /**
     * Pick the best matching template for the given locale.
     */
    protected String getTemplate(String templateFolder, String templateBaseName, Locale locale, String templateSuffix) {
        // cleanup templateFolder
        if (!templateFolder.endsWith("/")) {
            templateFolder = templateFolder + "/";
        }

        for (String templatePrefix : getTemplatePrefixesForLocale(templateBaseName, locale)) {
            StringBuilder filename = new StringBuilder(templateFolder);
            filename.append(templatePrefix).append(templateSuffix);

            if (getClass().getResource("/" + filename.toString()) != null) {
                return filename.toString();
            }
        }

        throw new IllegalStateException("Template file is missing: " + templateFolder + ":" + templateBaseName + ":" + locale.toString() + ":" + templateSuffix);
    }

    /**
     * Returns a list of potential template prefix name sorted by relevance.
     */
    protected List<String> getTemplatePrefixesForLocale(String templateBaseName, Locale locale) {
        StringBuilder templatePrefix = new StringBuilder(templateBaseName);
        List<String> templatePrefixes = new ArrayList<String>();

        String language = locale.getLanguage();
        String country = locale.getCountry();
        String variant = locale.getVariant();

        if (language.length() > 0) {
            templatePrefix.append('_').append(language);
            templatePrefixes.add(0, templatePrefix.toString());
        }

        if (country.length() > 0) {
            templatePrefix.append('_').append(country);
            templatePrefixes.add(0, templatePrefix.toString());
        }

        if (variant.length() > 0) {
            templatePrefix.append('_').append(variant);
            templatePrefixes.add(0, templatePrefix.toString());
        }

        templatePrefixes.add(templateBaseName); // fallback

        return templatePrefixes;
    }
}
