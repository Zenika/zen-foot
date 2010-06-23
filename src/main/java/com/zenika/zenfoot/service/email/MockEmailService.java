package com.zenika.zenfoot.service.email;

import com.zenika.zenfoot.util.AssertUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.mail.internet.MimeMessage;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.MailException;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class MockEmailService implements EmailService {
    static final private Logger logger = LoggerFactory.getLogger(MockEmailService.class);

    @Autowired
    public MockEmailService() {
    }

    @Override
    public void sendEmail(String toEmail, String fromEmail, String replyTo, String templateFolder, Map<String, Object> templateContext) throws MailException {
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

        if (logger.isInfoEnabled()) {
            logger.info("Email Service: email sent OK to " + toEmail + " using templates " + subjectTemplate + ":" + textBodyTemplate + ":" + htmlBodyTemplate);
        }
    }

    private MimeMessage buildMimeMessage(String toEmail, String fromEmail, Map<String, Object> templateContext, String subjectTemplate,
            String textBodyTemplate, String htmlBodyTemplate) {

        // prepare the message
        // evaluate velocity templates
        String subject = mergeTemplateIntoString(subjectTemplate, templateContext);
        String textBody = mergeTemplateIntoString(textBodyTemplate, templateContext);
        String htmlBody = mergeTemplateIntoString(htmlBodyTemplate, templateContext);

        if (logger.isDebugEnabled()) {
            logger.debug("subject: " + subject);
            logger.debug("textBody: " + textBody);
            logger.debug("htmlBody: " + htmlBody);
        }
        return null;
    }

    /**
     * Same as sendEmail but in a separate thread.
     */
    @Override
    public void sendEmailAsynchronously(final String toEmail, final String fromEmail, final String replyTo, final String templateFolder, final Map<String, Object> templateContext) {
        (new Thread() {
            @Override
            public void run() {
                try {
                    sendEmail(toEmail, fromEmail, replyTo, templateFolder, templateContext);
                } catch (Exception e) {
                    if (logger.isErrorEnabled()) {
                        logger.error("Could not send email from a separate thread. toEmail=" + toEmail, e);
                    }
                }
            }
        }).start();
    }

    protected String mergeTemplateIntoString(String templateLocation, Map<String, Object> templateContext) throws VelocityException {
        final VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty("resource.loader", "class");
        velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.setProperty("class.resource.loader.cache", "true");
        velocityEngine.setProperty("class.resource.loader.modificationCheckInterval", "10");
        velocityEngine.setProperty("input.encoding", "UTF-8");
        velocityEngine.setProperty("output.encoding", "UTF-8");
        return VelocityEngineUtils.mergeTemplateIntoString( velocityEngine, templateLocation, "UTF-8", templateContext);
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
