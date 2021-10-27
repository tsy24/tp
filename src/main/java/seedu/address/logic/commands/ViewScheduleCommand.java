package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ELDERLIES;

import java.time.LocalDate;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.DateTimeContainsDatePredicate;
import seedu.address.model.task.Recurrence.RecurrenceType;
import seedu.address.model.task.Task;

/**
 * Displays all tasks on a particular day. Supports checking up to 12 weeks in advance.
 */
public class ViewScheduleCommand extends Command {

    public static final String COMMAND_WORD = "viewSchedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays all tasks on the indicated day, within the next 12 weeks. "
            + "Accounts for recurring tasks as well."
            + DateTime.MESSAGE_DATE_CONSTRAINTS
            + ".\n"
            + "Parameters: "
            + "DATE \n"
            + "Example: " + COMMAND_WORD + " "
            + "2021-10-10";

    //No. of days to check for recurring tasks in the future. Set to 12 weeks.
    private static final int NUMBER_OF_DAYS_TO_CHECK = 84;

    private final DateTimeContainsDatePredicate predicate;
    private final LocalDate keyDate;

    public ViewScheduleCommand(DateTimeContainsDatePredicate predicate, LocalDate keyDate) {
        this.predicate = predicate;
        this.keyDate = keyDate;
    }

    //TODO maybe change to show the date of the schedule
    public static final String MESSAGE_VIEW_SCHEDULE_SUCCESS = "Showing all tasks on indicated day";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
//        List<Task> lastShownList = model.getFilteredTaskList();

        model.deleteGhostTasks();

        ReadOnlyAddressBook addressBook= model.getAddressBook();
        addPossibleGhostTasksWithMatchingDate(addressBook, model);


        model.updateFilteredTaskList(predicate); // to change
        return new CommandResult(MESSAGE_VIEW_SCHEDULE_SUCCESS);
    }

    /**
     * Loops through all tasks in TaskList and checks for real, recurring tasks. If there is a real and recurring task,
     * all possible future occurrences of the task are compared against the given keyDate. If any future task's date
     * matches with the given keyDate, the future task is added as a ghost task to the TaskList.
     */
    private void addPossibleGhostTasksWithMatchingDate(ReadOnlyAddressBook addressBook, Model model) {
        for(Task task : addressBook.getTaskList()) {
            if (task.isTaskRecurring() && task.isRealTask()) {
                addFutureGhostTasksWithMatchingDate(model, task);
            }
        }
    }

    /**
     * Checks if any of the given recurring task's future occurrences coincide with the given keyDate. If it does,
     * the future task is added as a ghost task to the TaskList.
     */
    private void addFutureGhostTasksWithMatchingDate(Model model, Task task) {
        RecurrenceType taskRecurrenceType = task.getRecurrence().getRecurrenceType();
        int interval; //interval between task occurrences depending on RecurrenceType.
        if (taskRecurrenceType == RecurrenceType.DAY) {
            interval = 1;
        } else if (taskRecurrenceType == RecurrenceType.WEEK) {
            interval = 7;
        } else { //taskRecurrenceType == RecurrenceType.MONTH
            interval = 28;
        }

        Task nextTaskOccurrence = task.createNextTaskOccurrence();
        nextTaskOccurrence.setGhostTask();
        Task currTask = nextTaskOccurrence;

        int daysLeftToCheck = NUMBER_OF_DAYS_TO_CHECK - interval;

        while (daysLeftToCheck > 0) {
            System.out.println("test");
            if (currTask.isSameDateTask(keyDate) && !model.hasTask(currTask)) {
                System.out.println("cool");
                model.addTask(currTask);
                break;
            }

            currTask = currTask.createNextTaskOccurrence();
            daysLeftToCheck -= interval;
        }
    }
}