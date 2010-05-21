package com.zenika.zenfoot.model;

import junit.framework.TestCase;

public class UserTest extends TestCase {
    public void testUserAlias() {
        User user = new User("olivier.huber@zenika.com");
        assertEquals("olivier.huber", user.getAlias());
    }

    public void testUserEmailMustNotBeNull() throws AssertionError {
        try {
            new User(null);
            fail();
        } catch (AssertionError ae) {
            assertEquals("new User: email must not be null", ae.getMessage());
        }
    }
}
