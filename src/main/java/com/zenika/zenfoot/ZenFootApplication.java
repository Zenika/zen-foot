package com.zenika.zenfoot;

import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import com.zenika.zenfoot.pages.AdminPage;
import com.zenika.zenfoot.pages.HomePage;
import com.zenika.zenfoot.pages.RulesPage;

public class ZenFootApplication extends AuthenticatedWebApplication {
    public ZenFootApplication() {
        mountBookmarkablePage("/regles", RulesPage.class);
        mountBookmarkablePage("/admin", AdminPage.class);
    }

    @Override
    protected void init() {
    	addComponentInstantiationListener(new SpringComponentInjector(this));
    	super.init();
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
