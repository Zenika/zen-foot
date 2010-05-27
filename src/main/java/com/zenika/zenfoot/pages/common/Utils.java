package com.zenika.zenfoot.pages.common;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class Utils {
    public static WebMarkupContainer createFlag(String id, String imageName) {
        WebMarkupContainer flag = new WebMarkupContainer(id);
        flag.add(new SimpleAttributeModifier("src", "images/flags/" + imageName));
        flag.add(new SimpleAttributeModifier("alt", "[" + imageName + "]"));
        return flag;
    }
}
