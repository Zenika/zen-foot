/**
 * 
 */
package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.AbstractGenericService;
import com.zenika.zenfoot.gae.IGenericDAO;
import com.zenika.zenfoot.gae.model.Country;

import java.util.List;

/**
 * @author vickrame
 *
 */
public class CountryService extends AbstractGenericService<Country, Long>{

    private static final long serialVersionUID = 1L;

    public CountryService(IGenericDAO<Country> dao) {
        super(dao);
    }

    public boolean contains(String countryName) {
        List<Country> countries = this.getDao().getAll();
        boolean toRet = false;
        if (countries != null && countries.size() > 0) {
            for(Country country : countries){
                if(country.getCountryName().equals(countryName)){
                    toRet = true;
                    break;
                }
            }
        }
        return toRet;
    }
}