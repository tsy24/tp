package nurseybook.model.person;

import static java.util.Objects.requireNonNull;
import static nurseybook.commons.util.AppUtil.checkArgument;

/**
 * Represents a Elderly's age in the nursey book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAge(String)}
 */
public class Age {

    public static final String MESSAGE_CONSTRAINTS =
            "Age should only contain numbers, and it should be between 21 and 145";
    public static final String VALIDATION_REGEX = "\\d+";
    public final int value;

    /**
     * Constructs a {@code Age}.
     *
     * @param age A valid age
     */
    public Age(String age) {
        requireNonNull(age);
        checkArgument(isValidAge(age), MESSAGE_CONSTRAINTS);
        value = Integer.parseInt(age);
    }

    /**
     * Returns true if a given string is a valid age.
     */
    public static boolean isValidAge(String test) {
        if (test.matches(VALIDATION_REGEX)) {
            int age = Integer.parseInt(test);
            return 21 <= age && age <= 145;
        }
        return false;
    }

    @Override
    public String toString() {
        return "" + value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Age // instanceof handles nulls
                && value == ((Age) other).value); // state check
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

}
