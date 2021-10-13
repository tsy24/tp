package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Elderly's relationship with Nok in the address book.
 * Guarantees: immutable; is always valid
 */
public class Relationship {
    public static final String MESSAGE_CONSTRAINTS = "Relationships cannot contain numbers, or can be left blank.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * Relationship does not accept numerical characters.
     */
    public static final String VALIDATION_REGEX = "^$|[^\\s\\d][^\\s\\d]*";

    public final String value;

    /**
     * Constructs a {@code Remark}.
     *
     * @param relationship A valid remark.
     */
    public Relationship(String relationship) {
        requireNonNull(relationship);
        value = relationship;
    }

    /**
     * Returns true if a given string is a valid relationship.
     */
    public static boolean isValidRelationship(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Relationship // instanceof handles nulls
                && value.equals(((Relationship) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
