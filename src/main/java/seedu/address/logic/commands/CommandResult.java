package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    /** State list display changes to */
    public enum ListDisplayChange { ELDERLY, TASK, NONE };

    private final String feedbackToUser;

    private final ListDisplayChange displayChange;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** UI should show an elderly's full details. */
    private final boolean isViewDetails;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.displayChange = ListDisplayChange.NONE;
        this.isViewDetails = false;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, ListDisplayChange change) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = false;
        this.exit = false;
        this.displayChange = change;
        this.isViewDetails = false;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, Boolean isViewDetails) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = false;
        this.exit = false;
        this.displayChange = ListDisplayChange.NONE;
        this.isViewDetails = isViewDetails;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public ListDisplayChange getDisplayChange() {
        return displayChange;
    }

    /**
     * Returns true if command result requires a change in list displayed by UI.
     */
    public boolean isChangeList() {
        return displayChange != ListDisplayChange.NONE;
    }

    /**
     * Returns true if command result indicates list display should change to show tasks,
     * else to show elderly.
     */
    public boolean shouldChangeToTask() {
        assert(displayChange != ListDisplayChange.NONE);
        return displayChange == ListDisplayChange.TASK;
    }

    /**
     * Returns true if command result indicates UI should show full details of an elderly
     */
    public boolean isViewDetails() {
        return isViewDetails;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && displayChange.equals(otherCommandResult.displayChange)
                && isViewDetails == otherCommandResult.isViewDetails;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, displayChange, isViewDetails);
    }

}
