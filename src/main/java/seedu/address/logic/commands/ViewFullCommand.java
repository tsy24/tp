package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Elderly;

public class ViewFullCommand extends Command {
    public static final String COMMAND_WORD = "viewFull";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the full details of the elderly identified by the index number"
            + " used in the displayed elderly list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Showing %s's details";

    private final Index targetIndex;

    public ViewFullCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Elderly> lastShownList = model.getFilteredElderlyList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
        }

        Elderly elderlyOfInterest = lastShownList.get(targetIndex.getZeroBased());
        model.setElderlyOfInterest(elderlyOfInterest);
        return new CommandResult(String.format(MESSAGE_SUCCESS, elderlyOfInterest.getName()), true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewFullCommand // instanceof handles nulls
                && targetIndex.equals(((ViewFullCommand) other).targetIndex)); // state check
    }

}
