package nurseybook.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_ELDERLIES_LISTED_OVERVIEW = "%1$d elderlies listed!";
    public static final String MESSAGE_TASKS_LISTED_OVERVIEW = "%1$d tasks listed!";
    public static final String MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX = "The elderly index provided is invalid";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid";
    public static final String MESSAGE_TASKS_ON_DATE = "%1$d tasks on indicated date!";
    public static final String MESSAGE_INVALID_TASK_DATETIME_FOR_RECURRING_TASK =
            "The task date and/or time provided cannot be before the current date and time for recurring tasks";
    public static final String MESSAGE_DUPLICATE_ELDERLY = "This elderly already exists in the nursey book.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the nursey book. \n"
            + "Hint: If you want to change the recurrence of a task, edit that specific task directly.";
    public static final String MESSAGE_NO_CHANGES = "Fields entered are the same as before.";

}
