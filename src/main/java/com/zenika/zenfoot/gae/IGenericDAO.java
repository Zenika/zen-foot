/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zenika.zenfoot.gae;

import com.googlecode.objectify.Key;
import java.util.List;

/**
 *
 * @author nebulis
 */
public interface IGenericDAO<T> {
    List<T> getAll();
    T findById(final Object id);
    T findByKey(final Key<T> key);
    void deleteFromId(final Object id);
    void deleteAll();
    Key<T> createUpdate(T model);
    
}
