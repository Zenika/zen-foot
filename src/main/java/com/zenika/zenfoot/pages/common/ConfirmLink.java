package com.zenika.zenfoot.pages.common;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

public abstract class ConfirmLink<T> extends Link<T> {
    public ConfirmLink(String id, IModel<T> model) {
        super(id, model);
        add(new SimpleAttributeModifier("onclick", "return confirm('Etes vous vraiment sur de ce que vous faite ?');"));
    }

    public ConfirmLink(String id) {
        this(id, null);
    }
}
