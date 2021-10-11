package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.RemindCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalTasks.ALEX_INSULIN;
import static seedu.address.testutil.TypicalTasks.DO_PAPERWORK;
import static seedu.address.testutil.TypicalTasks.getTypicalAddressBook;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskIsReminderPredicate;

public class RemindCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        TaskIsReminderPredicate firstPredicate =
                new TaskIsReminderPredicate(LocalDate.of(2021, 10, 31));
        TaskIsReminderPredicate secondPredicate =
                new TaskIsReminderPredicate(LocalDate.of(2021, 12, 25));

        RemindCommand remindFirstCommand = new RemindCommand(firstPredicate);
        RemindCommand remindSecondCommand = new RemindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(remindFirstCommand.equals(remindFirstCommand));

        // same values -> returns true
        RemindCommand remindFirstCommandCopy = new RemindCommand(firstPredicate);
        assertTrue(remindFirstCommand.equals(remindFirstCommandCopy));

        // different types -> returns false
        assertFalse(remindFirstCommand.equals(1));

        // null -> returns false
        assertFalse(remindFirstCommand.equals(null));

        // different elderly -> returns false
        assertFalse(remindFirstCommand.equals(remindSecondCommand));
    }

    @Test
    public void execute_remind_success() {
        TaskIsReminderPredicate predicate = preparePredicate(2021, 10, 31);
        RemindCommand command = new RemindCommand(predicate);
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, CommandResult.ListDisplayChange.TASK);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_pastDate_noTasksFound() {
        TaskIsReminderPredicate predicate = preparePredicate(2020, 1, 20);
        RemindCommand command = new RemindCommand(predicate);
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, CommandResult.ListDisplayChange.TASK);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    @Test
    public void execute_futureDate_noTasksFound() {
        TaskIsReminderPredicate predicate = preparePredicate(2022, 4, 1);
        RemindCommand command = new RemindCommand(predicate);
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, CommandResult.ListDisplayChange.TASK);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    @Test
    public void execute_validDate_tasksFound() {
        TaskIsReminderPredicate predicate = preparePredicate(2022, 1, 30);
        RemindCommand command = new RemindCommand(predicate);
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, CommandResult.ListDisplayChange.TASK);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Arrays.asList(DO_PAPERWORK, ALEX_INSULIN), model.getFilteredTaskList());
    }

    @Test
    public void execute_validDate_noTaskFound() {
        TaskIsReminderPredicate predicate = preparePredicate(2020, 11, 1);
        RemindCommand command = new RemindCommand(predicate);
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, CommandResult.ListDisplayChange.TASK);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);

        Task taskToMark = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        model.markTaskAsDone(taskToMark);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    /**
     * Parses a given date into a {@code TaskIsReminderPredicate}.
     */
    private TaskIsReminderPredicate preparePredicate(int year, int month, int day) {
        return new TaskIsReminderPredicate(LocalDate.of(year, month, day));
    }
}
