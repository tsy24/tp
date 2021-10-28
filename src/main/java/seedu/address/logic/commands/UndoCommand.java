package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undo the last command that changed the data of the nurseybook.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undo command: ";

    public static final String MESSAGE_FAILURE = "There are no changes to the data of NurseyBook that can be undone";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.canUndoNurseyBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        CommandResult lastCommandResult = model.undoNurseyBook();
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(MESSAGE_SUCCESS + lastCommandResult.getFeedbackToUser(),
                lastCommandResult.getDisplayChange());
    }
}
