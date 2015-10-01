package com.zenika.zenfoot.gae;

import com.google.appengine.api.utils.SystemProperty;
import restx.factory.Component;

/**
 * General app's information
 */
@Component
public class AppInfoService {

    /**
     * Returns app's url
     * @return application's url
     */
    public String getAppUrl(){
        String url = "http://";

        if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development) {
            url += "localhost:9000";
        } else {
            url += SystemProperty.applicationId.get() + ".appspot.com";
        }
        return url;
    }
}
