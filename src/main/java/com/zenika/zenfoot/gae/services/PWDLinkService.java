/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.AbstractGenericService;
import com.zenika.zenfoot.gae.dao.PWDLinkDAO;
import com.zenika.zenfoot.gae.utils.PWDLink;

/**
 *
 * @author nebulis
 */
public class PWDLinkService extends AbstractGenericService<PWDLink> {
    
    public PWDLinkService(PWDLinkDAO dao) {
        super(dao);
    }
}
