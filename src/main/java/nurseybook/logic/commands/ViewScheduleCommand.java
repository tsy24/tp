package nurseybook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import nurseybook.commons.core.Messages;
import nurseybook.model.Model;
import nurseybook.model.task.DateTime;
import nurseybook.model.task.DateTimeContainsDatePredicate;

/**
 * Displays all tasks on a particular day. Supports checking up to 12 weeks in advance.
 */
public class ViewScheduleCommand extends Command {

    public static final String COMMAND_WORD = "viewSchedule";
    public static final String[] PARAMETERS = { "DATE" };

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays all tasks on the indicated day, within the next 12 weeks. "
            + "Accounts for recurring tasks as well. "
            + DateTime.MESSAGE_DATE_CONSTRAINTS
            + ".\n"
            + "Parameters: "
            + String.join(" ", PARAMETERS)
            + "\nExample: " + COMMAND_WORD + " "
            + "2021-10-10";

    private final DateTimeContainsDatePredicate predicate;
    private final LocalDate keyDate;

    /**
     * Creates a ViewSchedule Command.
     *
     * @param predicate Predicate to check for DateTimes that contain the indicated date.
     * @param keyDate The indicated date.
     */
    public ViewScheduleCommand(DateTimeContainsDatePredicate predicate, LocalDate keyDate) {
        this.predicate = predicate;
        this.keyDate = keyDate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.addPossibleGhostTasksWithMatchingDate(keyDate);

        model.updateFilteredTaskList(predicate);
        return new CommandResult(String.format(Messages.MESSAGE_TASKS_ON_DATE, model.getFilteredTaskList().size()),
                CommandResult.ListDisplayChange.TASK);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewScheduleCommand // instanceof handles nulls
                && predicate.equals(((ViewScheduleCommand) other).predicate)); // state check
    }

}
