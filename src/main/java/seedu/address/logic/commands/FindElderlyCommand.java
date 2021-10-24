package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all elderlies in the address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindElderlyCommand extends Command {

    public static final String COMMAND_WORD = "findElderly";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all elderlies whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public FindElderlyCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredElderlyList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_ELDERLIES_LISTED_OVERVIEW, model.getFilteredElderlyList().size()),
                CommandResult.ListDisplayChange.ELDERLY);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindElderlyCommand // instanceof handles nulls
                && predicate.equals(((FindElderlyCommand) other).predicate)); // state check
    }
}
