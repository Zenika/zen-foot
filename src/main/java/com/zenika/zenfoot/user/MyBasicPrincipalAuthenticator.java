package com.zenika.zenfoot.user;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import restx.security.BasicPrincipalAuthenticator;
import restx.security.RestxPrincipal;

public class MyBasicPrincipalAuthenticator implements BasicPrincipalAuthenticator{

	@Override
	public Optional<? extends RestxPrincipal> findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<? extends RestxPrincipal> authenticate(String name,
			String passwordHash, ImmutableMap<String, ?> principalData) {
		// TODO Auto-generated method stub
		return null;
	} 

}
