package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's gender in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */
public class Gender {

    public static final String MESSAGE_CONSTRAINTS = "Gender should only be M or F";
    public static final String VALIDATION_REGEX = "[MFmf{1}]";
    public final String value;

    /**
     * Constructs a {@code Gender}.
     *
     * @param Gender A valid Gender
     */
    public Gender(String Gender) {
        requireNonNull(Gender);
        checkArgument(isValidGender(Gender), MESSAGE_CONSTRAINTS);
        value = Gender;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidGender(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Gender // instanceof handles nulls
                && value.equals(((Gender) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
