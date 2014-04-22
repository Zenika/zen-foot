package com.zenika.zenfoot.gae;

/**
 * A list of roles for the application.
 *
 * We don't use an enum here because it must be used inside an annotation.
 */
public final class Roles {
    public static final String HELLO_ROLE = "hello";
   
    /**
     * A basic gambler, the lowest level on the user hierarchy
     */
	public static final String GAMBLER="gambler";
    public static final String ADMIN="ADMIN";
}
