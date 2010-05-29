package com.zenika.zenfoot.pages.common;

import com.zenika.zenfoot.model.AbstractModel;
import org.apache.wicket.model.LoadableDetachableModel;

public class DomainObjectModel<T extends AbstractModel> extends LoadableDetachableModel {
//    @SpringBean
//    private DiscountsService service;
    private final Class<T> type;
    private final Long id;

    public DomainObjectModel(Class<T> type, Long id) {
//        InjectorHolder.getInjector().inject(this);
        this.type = type;
        this.id = id;
    }

    public DomainObjectModel(T domainObject) {
        super(domainObject);
//        InjectorHolder.getInjector().inject(this);
        this.type = (Class<T>) domainObject.getClass();
        this.id = domainObject.getId();
    }

    @Override
    protected T load() {
//        return service.load(type, id);
        return null;
    }
}
