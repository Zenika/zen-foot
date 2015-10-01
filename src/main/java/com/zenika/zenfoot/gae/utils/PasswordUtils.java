package com.zenika.zenfoot.gae.utils;

import com.google.common.hash.Hashing;

public class PasswordUtils {

    public static String getPasswordHash(String password) {
        String passwordHash = Hashing.sha256()
                .hashUnencodedChars(password)
                .toString();

//        LOGGER.debug("Password \"{}\" hashed into \"{}\"", password, passwordHash);
        return passwordHash;
    }
}
