package nurseybook.logic.commands;

import static java.util.Objects.requireNonNull;
import static nurseybook.commons.core.Messages.MESSAGE_INVALID_TASK_DATETIME_FOR_RECURRING_TASK;

import nurseybook.logic.parser.CliSyntax;
import nurseybook.logic.commands.exceptions.CommandException;
import nurseybook.model.Model;
import nurseybook.model.task.Task;

/**
 * Adds a task to the address book.
 */
public class AddTaskCommand extends Command {
    public static final String COMMAND_WORD = "addTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the address book.\n"
            + "Parameters: "
            + "[" + CliSyntax.PREFIX_NAME + "NAME]..."
            + CliSyntax.PREFIX_TASK_DESC + "DESCRIPTION "
            + CliSyntax.PREFIX_TASK_DATE + "DATE "
            + CliSyntax.PREFIX_TASK_TIME + "TIME "
            + "[" + CliSyntax.PREFIX_TASK_RECURRING + "RECURRENCE_TYPE]\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "Khong Guan "
            + CliSyntax.PREFIX_NAME + "Swee Choon "
            + CliSyntax.PREFIX_TASK_DESC + "Weekly Taiji "
            + CliSyntax.PREFIX_TASK_DATE + "2021-10-10 "
            + CliSyntax.PREFIX_TASK_TIME + "14:30 "
            + CliSyntax.PREFIX_TASK_RECURRING + "week";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";

    private final Task toAdd;

    /**
     * Creates an AddTaskCommand to add the specified {@code Task}
     */
    public AddTaskCommand(Task t) {
        requireNonNull(t);
        toAdd = t;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (toAdd.isPastCurrentDateAndRecurringTask()) {
            throw new CommandException(MESSAGE_INVALID_TASK_DATETIME_FOR_RECURRING_TASK);
        }

        model.addTask(toAdd);
        CommandResult result = new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), CommandResult.ListDisplayChange.TASK);
        model.commitNurseyBook(result);
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles nulls
                && toAdd.equals(((AddTaskCommand) other).toAdd));
    }
}
