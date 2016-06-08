package com.zenika.zenfoot.gae.mapper;

import com.zenika.zenfoot.gae.dto.CountryDTO;
import com.zenika.zenfoot.gae.model.Country;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import restx.factory.Component;

/**
 * Created by mathias on 06/06/16.
 */
@Component
public class CountryDtoToCountryMapper extends CustomMapper<CountryDTO, Country> {

    @Override
    public void mapAtoB(CountryDTO a, Country b, MappingContext context) {
        b.setDisplayName(a.getDisplayName());
        b.setCode(a.getCode());
    }

    @Override
    public void mapBtoA(Country a, CountryDTO b, MappingContext context) {
        a.setDisplayName(b.getDisplayName());
        a.setCode(b.getCode());
    }
}
