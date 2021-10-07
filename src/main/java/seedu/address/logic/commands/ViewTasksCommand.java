package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import seedu.address.logic.commands.CommandResult.ListDisplayChange;
import seedu.address.model.Model;

public class ViewTasksCommand extends Command {
    public static final String COMMAND_WORD = "viewTasks";

    public static final String MESSAGE_SUCCESS = "Showing all tasks";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(MESSAGE_SUCCESS, ListDisplayChange.TASK);
    }
}
