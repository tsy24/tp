package nurseybook.logic.commands;

import static java.util.Objects.requireNonNull;

import nurseybook.logic.commands.exceptions.CommandException;
import nurseybook.model.Model;

/**
 * Redo the last command that was undone.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Redo command: ";

    public static final String MESSAGE_FAILURE = "There are no changes to the data of Nursey Book to redo";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.canRedoNurseyBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        CommandResult lastCommandResult = model.redoNurseyBook();
        model.updateTasksAccordingToTime();
        return new CommandResult(MESSAGE_SUCCESS + lastCommandResult.getFeedbackToUser(),
                lastCommandResult.getDisplayChange());
    }
}

