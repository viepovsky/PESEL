package org.viepovsky.pesel;

/**
 * Checked exception thrown when an invalid PESEL number is encountered.
 * Is thrown when the PESEL:
 * <ul>
 *     <li>has an invalid length (other than 11)
 *     <li>has invalid characters (characters other than digits)
 *     <li>has an invalid control number (e.g. is 5 but should be 7)
 *     <li>has an invalid birth date (e.g. the 32nd of any month)
 * </ul>
 */
public class InvalidPeselException extends Exception {
    /**
     * Constructs a {@code InvalidPeselException} with the specified
     * detail message.
     *
     * @param   message   the detail message.
     */
    InvalidPeselException(String message) {
        super(message);
    }
}
