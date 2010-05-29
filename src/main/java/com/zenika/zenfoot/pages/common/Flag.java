package com.zenika.zenfoot.pages.common;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

public class Flag extends WebMarkupContainer {
    public Flag(String id, IModel<?> model) {
        super(id, model);
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        checkComponentTag(tag, "img");
        super.onComponentTag(tag);
        tag.put("src", "images/flags/" + getDefaultModelObjectAsString());
        tag.put("alt", "[" + getDefaultModelObjectAsString() + "]");
    }
}
