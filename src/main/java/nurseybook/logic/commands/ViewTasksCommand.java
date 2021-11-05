package nurseybook.logic.commands;

import static java.util.Objects.requireNonNull;
import static nurseybook.model.Model.PREDICATE_SHOW_ALL_TASKS;

import nurseybook.logic.commands.CommandResult.ListDisplayChange;
import nurseybook.model.Model;

/**
 * Lists all tasks in the nursey book to the user.
 */
public class ViewTasksCommand extends Command {
    public static final String COMMAND_WORD = "viewTasks";

    public static final String MESSAGE_SUCCESS = "Showing all tasks";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateTasksAccordingToTime();
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(MESSAGE_SUCCESS, ListDisplayChange.TASK);
    }
}
