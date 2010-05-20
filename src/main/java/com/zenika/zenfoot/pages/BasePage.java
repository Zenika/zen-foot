package com.zenika.zenfoot.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class BasePage extends WebPage {
    private static final long serialVersionUID = 1L;

    public BasePage() {
        add(new BookmarkablePageLink("homePage", HomePage.class));
        add(new BookmarkablePageLink("rulesPage", RulesPage.class));
        add(new BookmarkablePageLink("adminPage", AdminPage.class));
    }
}
