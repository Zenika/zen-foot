/**
 * 
 */
package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.AbstractGenericService;
import com.zenika.zenfoot.gae.GenericDAO;
import com.zenika.zenfoot.gae.model.Pays;

/**
 * @author vickrame
 *
 */
public class PaysService extends AbstractGenericService<Pays>{

    private static final long serialVersionUID = 1L;

    public PaysService(GenericDAO<Pays> dao) {
        super(dao);
    }
}