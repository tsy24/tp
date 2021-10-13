package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ELDERLIES;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Elderly;
import seedu.address.model.person.Nok;

/**
 * Deletes the NoK fields of an elderly identified using it's displayed index from the address book.
 */
public class DeleteNokCommand extends Command {

    public static final String COMMAND_WORD = "deleteNok";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the NoK fields of an elderly identified by the index number used in the displayed "
            + "elderly list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

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
        model.updateFilteredElderlyList(PREDICATE_SHOW_ALL_ELDERLIES);
        return new CommandResult(String.format(MESSAGE_DELETE_ELDERLY_NOK_SUCCESS, updatedElderly));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteNokCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteNokCommand) other).targetIndex)); // state check
    }
}
