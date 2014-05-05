package com.zenika.zenfoot.user;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import restx.security.BasicPrincipalAuthenticator;
import restx.security.RestxPrincipal;
//TODO delete and check nothing bad happens.
public class MyBasicPrincipalAuthenticator implements BasicPrincipalAuthenticator{

	@Override
	public Optional<? extends RestxPrincipal> findByName(String name) {
		return null;
	}

	@Override
	public Optional<? extends RestxPrincipal> authenticate(String name,
			String passwordHash, ImmutableMap<String, ?> principalData) {
		return null;
	} 

}
