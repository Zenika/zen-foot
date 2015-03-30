/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 *
 * @author nebulis
 */
public abstract class GenericDAO<T> implements Serializable {
    
    Class<T> type = null;

    public T get(Key<T> key) {
        return ObjectifyService.ofy().load().key(key).now();
    }

    public Key<T> createUpdate(T objet){
        return ObjectifyService.ofy().save().entity(objet).now();
    }

    public List<T> getAll(){
        initializeType();
        return ObjectifyService.ofy().load().type(type).list();
    }

    public T get(Long id) {
        initializeType();
        return ObjectifyService.ofy().load().type(type).id(id).now();
    }
    
    public void delete(Long id) {
        initializeType();
        OfyService.ofy().delete().type(type).id(id).now();
    }

    private void initializeType() {
        if (this.type == null) {
            this.type = (Class<T>) ((ParameterizedType) getClass()
                            .getGenericSuperclass()).getActualTypeArguments()[0];
        }
    }
}
