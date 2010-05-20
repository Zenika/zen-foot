package com.zenika.zenfoot;

import com.zenika.zenfoot.pages.AdminPage;
import org.apache.wicket.protocol.http.WebApplication;

import com.zenika.zenfoot.pages.HomePage;
import com.zenika.zenfoot.pages.RulesPage;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see com.zenika.zenfoot.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
    public WicketApplication() {
        mountBookmarkablePage("/regles", RulesPage.class);
        mountBookmarkablePage("/admin", AdminPage.class);
    }

    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }
}
