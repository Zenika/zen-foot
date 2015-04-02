/**
 * 
 */
package com.zenika.zenfoot.gae;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.zenika.zenfoot.gae.dao.OfyService;

/**
 * @author vickrame
 *
 */
public class GenericDAO<T> extends AbstractBase<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Class<T> type;

	// pour forcer le typage
	public GenericDAO(Class<T> type) {
		super(type);
		this.type = type;
	}

	/**
	 * 
	 * @return
	 */
	public List<T> getAll() {
		logger.info("Creation du vo " + type.getCanonicalName());
		return ObjectifyService.ofy().load().type(type).list();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public T findById(final Object id) {
		logger.info("recherche " + type.getCanonicalName() +" par id " + id);
		if (id instanceof Long) {
			return  ObjectifyService.ofy().load().type(type).id((Long) id).now();
		} else {
			return ObjectifyService.ofy().load().type(type).id((String)id).now();
		}

	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public T findByKey(final Key<T> key) {
		logger.info("recherche " + type.getCanonicalName() +" par clef " + key);
		return ObjectifyService.ofy().load().key(key).now();
	}

	/**
	 * 
	 * @param id
	 */
	public void deleteFromId(final Object id) {
		logger.info("suppression " + type.getCanonicalName() +" par id " + id);

		if (id instanceof Long) {
			OfyService.ofy().delete().type(type).id((Long) id).now();
		} else {
			OfyService.ofy().delete().type(type).id((String) id).now();
		}
	}
	
	/**
	 * 
	 */
	public void deleteAll(){
		logger.info("suppression d'uen liste " + type.getCanonicalName() );
        List<Key<T>> keys = OfyService.ofy().load().type(type).keys().list();
        OfyService.ofy().delete().keys(keys).now();
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	public Key<T> createUpdate(T model) {
		logger.info("creation entity " + model.getClass().getCanonicalName() );
      return OfyService.ofy().save().entity(model).now();
	}
}