package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.TaskIsReminderPredicate;

/**
 * Displays the tasks that are not yet completed, and coming up in the next three days
 */
public class RemindCommand extends Command {

    public static final String COMMAND_WORD = "remind";

    public static final String MESSAGE_SUCCESS = "Showing the tasks coming up in the next three days!";

    public final TaskIsReminderPredicate predicate;

    public RemindCommand(TaskIsReminderPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredTaskList(predicate);

        return new CommandResult(MESSAGE_SUCCESS, CommandResult.ListDisplayChange.TASK);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemindCommand // instanceof handles nulls
                && predicate.equals(((RemindCommand) other).predicate)); // state check
    }
}
