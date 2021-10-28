package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a task's completion status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {

    public static final String MESSAGE_CONSTRAINTS = "Status can take either 'true' or 'false' as its values and"
            + "is case-insensitive. It cannot be null.";

    /**
     * The first character of the Description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^(?i)(true|false)$";

    /**
     * If a task is completed/done, isDone will be true. Else, it will be false.
     */
    public final boolean isDone;

    /**
     * If a task is overdue, isOverdue will be true. Else, it will be false.
     */
    public final boolean isOverdue;

    /**
     * Constructs an {@code Status}.
     *
     * @param completionStatus A valid completion status in String.
     * @param overdueStatus A valid overdue status in String.
     */
    public Status(String completionStatus, String overdueStatus) {
        requireNonNull(completionStatus);

        checkArgument(isValidStatus(completionStatus), MESSAGE_CONSTRAINTS);
        checkArgument(isValidStatus(overdueStatus), MESSAGE_CONSTRAINTS);

        isDone = Boolean.parseBoolean(completionStatus);
        isOverdue = Boolean.parseBoolean(overdueStatus);
    }

    /**
     * Constructs an {@code Status}.
     *
     * @param completionStatus A valid completion status in boolean.
     * @param isOverdue A valid overdue status in boolean.
     */
    public Status(Boolean completionStatus, boolean isOverdue) {
        requireNonNull(completionStatus);
        this.isDone = completionStatus;
        this.isOverdue = isOverdue;
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(isDone)
                .append("; ")
                .append(isOverdue);

        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && isDone == ((Status) other).isDone
                && isOverdue == ((Status) other).isOverdue); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(isDone, isOverdue);
    }
}
