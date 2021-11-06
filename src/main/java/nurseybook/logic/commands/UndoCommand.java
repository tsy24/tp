package nurseybook.logic.commands;

import static java.util.Objects.requireNonNull;

import nurseybook.logic.commands.exceptions.CommandException;
import nurseybook.model.Model;

/**
 * Undo the last command that changed the data of the nursey book.
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
        model.updateTasksAccordingToTime();
        return new CommandResult(MESSAGE_SUCCESS + lastCommandResult.getFeedbackToUser(),
                lastCommandResult.getDisplayChange());
    }
}
