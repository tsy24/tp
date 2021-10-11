package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.tag.ElderlyHasTagPredicate;

public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all elderly with all specified tags"
            + "(case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: " + PREFIX_TAG + "TAG [" + PREFIX_TAG + "MORE_TAGS]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG + "diabetes";

    private final ElderlyHasTagPredicate predicate;

    public FilterCommand(ElderlyHasTagPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredElderlyList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_ELDERLIES_LISTED_OVERVIEW, model.getFilteredElderlyList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && predicate.equals(((FilterCommand) other).predicate)); // state check
    }
}
