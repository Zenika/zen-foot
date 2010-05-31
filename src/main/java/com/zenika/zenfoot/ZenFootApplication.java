package com.zenika.zenfoot;

import com.zenika.zenfoot.pages.AdminPage;

import com.zenika.zenfoot.pages.HomePage;
import com.zenika.zenfoot.pages.RulesPage;
import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;

public class ZenFootApplication extends AuthenticatedWebApplication {
    public ZenFootApplication() {
        mountBookmarkablePage("/regles", RulesPage.class);
        mountBookmarkablePage("/admin", AdminPage.class);
    }

    @Override
    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }

    @Override
    protected Class<? extends AuthenticatedWebSession> getWebSessionClass() {
        return ZenFootSession.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return HomePage.class;
    }
}
