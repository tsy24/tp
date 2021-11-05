package nurseybook.logic.commands;

import static java.util.Objects.requireNonNull;
import static nurseybook.model.Model.PREDICATE_SHOW_ALL_ELDERLIES;

import java.util.List;

import nurseybook.commons.core.Messages;
import nurseybook.commons.core.index.Index;
import nurseybook.logic.commands.exceptions.CommandException;
import nurseybook.model.Model;
import nurseybook.model.person.Elderly;
import nurseybook.model.person.Nok;

/**
 * Deletes the NoK fields of an elderly identified using it's displayed index from the nursey book.
 */
public class DeleteNokCommand extends Command {

    public static final String COMMAND_WORD = "deleteNok";
    public static final String[] PARAMETERS = { Index.VALID_INDEX_CRITERIA };

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the NoK fields of an elderly identified by the index number used in the displayed "
            + "elderly list.\n"
            + "Parameters: "
            + String.join(" ", PARAMETERS)
            + "\nExample: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ELDERLY_NOK_SUCCESS = "Deleted NoK of Elderly: %1$s";

    private final Index targetIndex;

    public DeleteNokCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Elderly> lastShownList = model.getFilteredElderlyList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
        }

        Elderly elderlyToDeleteNokFrom = lastShownList.get(targetIndex.getZeroBased());
        Elderly updatedElderly = new Elderly(
                elderlyToDeleteNokFrom.getName(), elderlyToDeleteNokFrom.getAge(),
                elderlyToDeleteNokFrom.getGender(), elderlyToDeleteNokFrom.getRoomNumber(), Nok.createDefaultNok(),
                elderlyToDeleteNokFrom.getRemark(), elderlyToDeleteNokFrom.getTags());

        model.setElderly(elderlyToDeleteNokFrom, updatedElderly);
        CommandResult result = new CommandResult(String.format(MESSAGE_DELETE_ELDERLY_NOK_SUCCESS, updatedElderly));
        model.commitNurseyBook(result);
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteNokCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteNokCommand) other).targetIndex)); // state check
    }
}
