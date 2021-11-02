package nurseybook.model.task;

import static java.util.Objects.requireNonNull;
import static nurseybook.commons.util.AppUtil.checkArgument;

/**
 * Represents a whether a task is a ghost task or not in the nursey book.
 * GhostTasks are temporary tasks that are not meant to be shown to the user.
 * Guarantees: immutable; is valid as declared in {@link #isValidGhostTask(String)}
 */
public class GhostTask {
    public static final String MESSAGE_CONSTRAINTS = "GhostTask can take either 'true' or 'false' as its values and"
            + "is case-insensitive. It cannot be null.";

    /**
     * The first character of the GhostTask must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^(?i)(true|false)$";

    /**
     * If a task is a GhostTask, isGhostTask will be true. Else, it will be false.
     */
    public final boolean isGhostTask;

    /**
     * Constructs a {@code GhostTask}.
     *
     * @param isGhostTask True if GhostTask; false otherwise.
     */
    public GhostTask(String isGhostTask) {
        requireNonNull(isGhostTask);
        checkArgument(isValidGhostTask(isGhostTask), MESSAGE_CONSTRAINTS);
        this.isGhostTask = Boolean.parseBoolean(isGhostTask);
    }

    /**
     * Returns true if a given string is a valid GhostTask.
     */
    public static boolean isValidGhostTask(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public boolean checkIfGhostTask() {
        return this.isGhostTask;
    }

    @Override
    public String toString() {
        return Boolean.toString(isGhostTask);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GhostTask // instanceof handles nulls
                && isGhostTask == ((GhostTask) other).isGhostTask); // state check
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(isGhostTask);
    }

}
