/**
 * 
 */
package com.zenika.zenfoot.gae;

import java.util.List;

import com.googlecode.objectify.Key;

/**
 * 
 * @author vickrame
 *
 * @param <T> le nom du service 
 * @param <Y> le model
 */
public class AbstractGenericService<T, Y> extends AbstractBase<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GenericDAO<Y> unDAO;
	
	public AbstractGenericService(Class<T> type) {
		super(type);
		logger.info("Appel au service metiers " + getType().getName());
	}

	public AbstractGenericService(Class<T> type,GenericDAO<Y> dao) {
		super(type);

		logger.info("Appel au service metiers " + getType().getName());
		this.unDAO = dao;

		logger.info("Appel au service metiers " + dao.getClass().getName());
	}

	public void delete(Long id) {
		logger.info("Appel service " + getType().getName() + " pour suppression");
		this.unDAO.deleteFromId(id);
	}

	public Y getFromID(Long id) {
		logger.info("Appel service " + getType().getName() + " pour rechercher en fonction de la clef");
		return this.unDAO.findById(id);
	}

	public List<Y> getAll() {
		logger.info("Appel service " + getType().getName() + " pour charger une liste d'entité");		
		return this.unDAO.getAll();
	}

	public void deleteAll() {
		logger.info("Appel service " + getType().getName() + " pour supprimer une liste d'entité");		
		this.unDAO.deleteAll();
	}
	
	public Key<Y> createOrUpdate(Y model) {
		System.out.println("TFIN CREARTION  ");
		logger.info("Appel service " + getType().getName() + " pour creation une liste d'entité");		
		return this.unDAO.createUpdate(model);
	}	
}
