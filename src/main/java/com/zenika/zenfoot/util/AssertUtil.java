package com.zenika.zenfoot.util;



/**
 * Assertion utility class that assists in chekcing bean configuration.
 * Useful for identifying configuration errors early in the developpment cycle.
 *
 * <p>For example, if a bean needs the transactionManager, AssertUtil can be used
 * in the bean's init method to check that the transactionManager reference is not null.
 *
 * <p>Spring framework provides a very nice Assert class, but to limit dependencies
 * with Spring, it is recommended to use this class internally.
 */
public abstract class AssertUtil {

    /**
     * Assert that an object is not <code>null</code> .
     * <pre class="code">AssertUtil.notNull(transactionManager, "transactionManager should not be null");</pre>
     * @param object the object to check
     * @param message the exception message
     * @throws IllegalArgumentException if the object is <code>null</code>
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that an object is not <code>null</code> .
     * <pre class="code">AssertUtil.notNull(transactionManager);</pre>
     * @param object the object to check
     * @throws IllegalArgumentException if the object is <code>null</code>
     */
    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - this argument is required; it must not be null.");
    }

    /**
     * Assert that a string is not empty; that is, it must not be <code>null</code> and not empty.
     * <pre class="code">AssertUtil.hasLength(name, "name must not be null or empty");</pre>
     * @param text the string to check
     * @param message the exception message to use if the assertion fails
     */
    public static void hasLength(String text, String message) {
        if (text == null || text.length() == 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that a string is not empty; that is, it must not be <code>null</code> and not empty.
     * <pre class="code">AssertUtil.hasLength(name);</pre>
     * @param text the string to check
     */
    public static void hasLength(String text) {
        hasLength(text, "[Assertion failed] - this String argument must have length; it must not be <code>null</code> or empty");
    }

    /**
     * Assert that a Boolean is True; that is, it must not be <code>null</code> and not false.
     * <pre class="code">AssertUtil.isTrue(myBool, "");</pre>
     * @param bool the boolean to check
     * @param message the exception message to use if the assertion fails
     */
    public static void isTrue(Boolean bool, String message) {
        if (bool == null || !bool) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that a Boolean is True; that is, it must not be <code>null</code> and not false.
     * @param bool the boolean to check
     */
    public static void isTrue(Boolean bool) {
        isTrue(bool, "[Assertion failed] - this Boolean argument must be true; it must not be <code>null</code> or false");
    }

    /**
     * Assert that a Boolean is False; that is, it must not be <code>null</code> and not be true.
     * @param bool the boolean to check
     * @param message the exception message to use if the assertion fails
     */
    public static void isFalse(Boolean bool, String message) {
        if (bool == null || bool) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that a Boolean is False; that is, it must not be <code>null</code> and not be true.
     * @param bool the boolean to check
     */
    public static void isFalse(Boolean bool) {
        isFalse(bool, "[Assertion failed] - this Boolean argument must be false; it must not be <code>null</code> or true");
    }


}
