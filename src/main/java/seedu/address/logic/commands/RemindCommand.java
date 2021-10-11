package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Displays the tasks that are not yet completed, and coming up in the next three days
 */
public class RemindCommand extends Command {

    public static final String COMMAND_WORD = "remind";

    public static final String MESSAGE_SUCCESS = "Showing the tasks coming up in the next three days";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Predicate<Task> p = t -> t.isReminder();
        model.updateFilteredTaskList(p);

        return new CommandResult(MESSAGE_SUCCESS, CommandResult.ListDisplayChange.TASK);
    }
}
