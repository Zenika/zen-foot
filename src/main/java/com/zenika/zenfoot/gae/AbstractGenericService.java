/**
 *
 */
package com.zenika.zenfoot.gae;

import java.util.List;

import com.googlecode.objectify.Key;

/**
 * @param <T>
 * @author vickrame
 */
public class AbstractGenericService<T, ID> extends AbstractBase<T> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private IGenericDAO<T> unDAO;

    public AbstractGenericService() {
        super();
        logger.info("Appel au service metiers " + this.getClass().getName());
    }

    public AbstractGenericService(IGenericDAO<T> dao) {
        super();
        logger.info("Appel au service metiers " + this.getClass().getName());
        this.unDAO = dao;
    }

    public void delete(ID id) {
        logger.info("Appel service " + this.getClass().getName() + " pour suppression");
        this.unDAO.deleteFromId(id);
    }

    /**
     * Remove an object given a key
     * @param key
     */
    public void deleteFromKey(Key<T> key) {
        logger.info("Appel service " + this.getClass().getName() + " pour supprimer en fonction de la clé");
        this.unDAO.deleteFromKey(key);
    }

    public T getFromKey(Key<T> key) {
        logger.info("Appel service " + this.getClass().getName() + " pour rechercher en fonction de la clé");
        return this.unDAO.findByKey(key);
    }

    public T getFromID(ID id) {
        logger.info("Appel service " + this.getClass().getName() + " pour rechercher en fonction de l'id");
        return this.unDAO.findById(id);
    }

    public List<T> getAll() {
        logger.info("Appel service " + this.getClass().getName() + " pour charger une liste d'entité");
        return this.unDAO.getAll();
    }

    public void deleteAll() {
        logger.info("Appel service " + this.getClass().getName() + " pour supprimer une liste d'entité");
        this.unDAO.deleteAll();
    }

    public Key<T> createOrUpdate(T model) {
        logger.info("Appel service " + this.getClass().getName() + " pour creation une liste d'entité");
        return this.unDAO.createUpdate(model);
    }

    public T createOrUpdateAndReturn(T model) {
        return this.getFromKey(this.unDAO.createUpdate(model));
    }

    public IGenericDAO<T> getDao() {
        return this.unDAO;
    }
}

