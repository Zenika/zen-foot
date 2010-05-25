package com.zenika.zenfoot;

import com.zenika.zenfoot.pages.AdminPage;
import org.apache.wicket.protocol.http.WebApplication;

import com.zenika.zenfoot.pages.HomePage;
import com.zenika.zenfoot.pages.RulesPage;

public class ZenFootApplication extends WebApplication {
    public ZenFootApplication() {
        mountBookmarkablePage("/regles", RulesPage.class);
        mountBookmarkablePage("/admin", AdminPage.class);
    }

    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }
}
