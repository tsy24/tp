package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ELDERLIES;

import seedu.address.model.Model;

/**
 * Lists all elderly in the address book to the user.
 */
public class ViewElderlyCommand extends Command {

    public static final String COMMAND_WORD = "viewElderly";

    public static final String MESSAGE_SUCCESS = "Showing all elderly";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredElderlyList(PREDICATE_SHOW_ALL_ELDERLIES); // to change
        return new CommandResult(MESSAGE_SUCCESS, CommandResult.ListDisplayChange.ELDERLY);
    }
}
