package com.zenika.zenfoot.gae.rest;

import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.model.Country;
import com.zenika.zenfoot.gae.services.*;
import restx.WebException;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.http.HttpStatus;
import restx.security.PermitAll;
import restx.security.RolesAllowed;

import javax.inject.Named;
import java.util.List;

/**
 * Created by mathias on 01/06/16.
 */
@Component
@RestxResource
public class CountryResource {

    final private CountryService countryService;

    public CountryResource(CountryService countryService, @Named("sessioninfo")SessionInfo sessionInfo) {
        this.countryService = countryService;
    }

    @GET("/countries")
    @PermitAll
    public List<Country> countries() {
        return countryService.getAll();
    }

    @POST("/countries")
    @RolesAllowed(Roles.ADMIN)
    public Country createCountry(Country country) {
        if (countryService.contains(country.getCode())) {
            throw new WebException(HttpStatus.BAD_REQUEST);
        }
        return countryService.getFromKey(
                countryService.createOrUpdate(country));
    }
}
