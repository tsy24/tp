package nurseybook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import nurseybook.commons.core.Messages;
import nurseybook.commons.core.index.Index;
import nurseybook.logic.commands.exceptions.CommandException;
import nurseybook.model.Model;
import nurseybook.model.person.Elderly;

/**
 * Deletes an elderly identified using it's displayed index from the nursey book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "deleteElderly";
    public static final String[] PARAMETERS = { Index.VALID_INDEX_CRITERIA };

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the elderly identified by the index number used in the displayed elderly list.\n"
            + "Parameters: "
            + String.join(" ", PARAMETERS)
            + "\nExample: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ELDERLY_SUCCESS = "Deleted Elderly: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Elderly> lastShownList = model.getFilteredElderlyList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
        }

        Elderly elderlyToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteElderly(elderlyToDelete);
        CommandResult result = new CommandResult(String.format(MESSAGE_DELETE_ELDERLY_SUCCESS, elderlyToDelete));
        model.commitNurseyBook(result);
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
