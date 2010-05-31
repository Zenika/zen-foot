package com.zenika.zenfoot.service.email;

import com.zenika.zenfoot.util.AssertUtil;
import com.zenika.zenfoot.util.StringUtil;
import java.util.Properties;
import java.io.IOException;

import javax.mail.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.Resource;

/**
 * JavaMailSender that reads and set javamail properties from a properties file.
 */
public  class JavaMailSenderWithConfig extends JavaMailSenderImpl {

    private Log logger = LogFactory.getLog(JavaMailSenderWithConfig.class);

    private Resource configLocation;

    /**
     * Set javamail properties using a properties file and automatically set Host, Port, Username and Password property.
     * @param configLocation path to the javamail properties file, for exemple "/WEB-INF/mail.properties"
     */
    public void setConfigLocation(Resource configLocation) {
        AssertUtil.notNull(configLocation);
        this.configLocation = configLocation;

        Properties props;
        if (logger.isInfoEnabled()) {
            logger.info("Loading JavaMailSenderWithConfigImpl config from [" + this.configLocation + "]");
        }

        try {
            props = PropertiesLoaderUtils.loadProperties(this.configLocation);
            Session s = Session.getInstance(props, null);
            s.setDebug(StringUtil.isTrue(props.getProperty("javamail.debug")));
            setSession(s);
            setHost(props.getProperty("mail.smtp.host"));
            setPort(new Integer(props.getProperty("mail.smtp.port")));
            setUsername(props.getProperty("mail.smtp.user"));
            setPassword(props.getProperty("mail.smtp.pass"));
        } catch (IOException ioe) {
            if (logger.isErrorEnabled()) {
                logger.error("Could not load JavaMailSenderWithConfigImpl config from [" + this.configLocation + "]");
            }
        }
    }

    public Resource getConfigLocation() {
        return configLocation;
    }
}
