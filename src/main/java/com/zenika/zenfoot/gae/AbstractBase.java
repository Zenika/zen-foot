package com.zenika.zenfoot.gae;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.logging.Logger;

public abstract class AbstractBase<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    protected Class<T> type;


    protected Logger logger = null;


    public AbstractBase() {
        this.initializeType();
        logger = Logger.getLogger(type.getName());
    }

    private void initializeType() {
        if (this.type == null) {
            this.type = (Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
        }
    }


    /**
     * @return the type
     */
    public Class<T> getType() {
        return type;
    }


}
