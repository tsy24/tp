package nurseybook.logic.commands;

import static java.util.Objects.requireNonNull;

import nurseybook.commons.core.Messages;
import nurseybook.model.Model;
import nurseybook.model.task.DescriptionContainsKeywordPredicate;

/**
 * Finds and lists all tasks in the nursey book which description contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindTaskCommand extends Command {

    public static final String COMMAND_WORD = "findTask";
    public static final String[] PARAMETERS = { "KEYWORD", "[MORE_KEYWORDS]..." };

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks which description contains any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + String.join(" ", PARAMETERS)
            + "\nExample: " + COMMAND_WORD
            + " " + "Dental check-up at Ng Teng Fong General Hospital";

    private final DescriptionContainsKeywordPredicate predicate;

    public FindTaskCommand(DescriptionContainsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateTasksAccordingToTime();
        model.updateFilteredTaskList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, model.getFilteredTaskList().size()),
                CommandResult.ListDisplayChange.TASK);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTaskCommand // instanceof handles nulls
                && predicate.equals(((FindTaskCommand) other).predicate)); // state check
    }

}
