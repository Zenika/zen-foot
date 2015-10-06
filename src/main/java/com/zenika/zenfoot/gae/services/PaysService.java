/**
 * 
 */
package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.AbstractGenericService;
import com.zenika.zenfoot.gae.IGenericDAO;
import com.zenika.zenfoot.gae.model.Pays;

/**
 * @author vickrame
 *
 */
public class PaysService extends AbstractGenericService<Pays, Long>{

    private static final long serialVersionUID = 1L;

    public PaysService(IGenericDAO<Pays> dao) {
        super(dao);
    }
}