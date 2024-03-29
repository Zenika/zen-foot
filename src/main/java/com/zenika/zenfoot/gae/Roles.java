package com.zenika.zenfoot.gae;

/**
 * A list of roles for the application.
 * <p/>
 * We don't use an enum here because it must be used inside an annotation.
 */
public final class Roles {
    public static final String HELLO_ROLE = "HELLO";

    /**
     * A basic gambler, the lowest level on the user hierarchy
     */
    public static final String GAMBLER = "GAMBLER";
    public static final String ADMIN = "ADMIN";

}
