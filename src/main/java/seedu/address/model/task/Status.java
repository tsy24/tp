package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a task's completion status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {

    public static final String MESSAGE_CONSTRAINTS = "Status can take either 'true' or 'false' as its values and"
            + "is case-insensitive. It cannot be null.";

    /*
     * The first character of the Description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^(?i)(true|false)$";

    /*
     * If a task is completed/done, isDone will be true. Else, it will be false.
     */
    public final boolean isDone;

    /**
     * Constructs an {@code Status}.
     *
     * @param completionStatus A valid completion status.
     */
    public Status(String completionStatus) {
        requireNonNull(completionStatus);
        checkArgument(isValidStatus(completionStatus), MESSAGE_CONSTRAINTS);
        isDone = Boolean.parseBoolean(completionStatus);
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return Boolean.toString(isDone);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && isDone == ((Status) other).isDone); // state check
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(isDone);
    }
}
