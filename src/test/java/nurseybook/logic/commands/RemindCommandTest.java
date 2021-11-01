package nurseybook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import nurseybook.model.task.Task;
import nurseybook.model.task.TaskIsReminderPredicate;
import nurseybook.testutil.TypicalIndexes;
import nurseybook.testutil.TypicalTasks;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.UserPrefs;

public class RemindCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalTasks.getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getVersionedNurseyBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        TaskIsReminderPredicate firstPredicate =
                new TaskIsReminderPredicate(LocalDateTime.of(2021, 10, 31, 10, 30));
        TaskIsReminderPredicate secondPredicate =
                new TaskIsReminderPredicate(LocalDateTime.of(2021, 12, 25, 11, 45));

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

        // different reminder -> returns false
        assertFalse(remindFirstCommand.equals(remindSecondCommand));
    }

    @Test
    public void execute_remind_success() {
        TaskIsReminderPredicate predicate = preparePredicate(2021, 10, 31, 10, 0);
        RemindCommand command = new RemindCommand(predicate);
        CommandResult expectedCommandResult = new CommandResult(RemindCommand.MESSAGE_SUCCESS, CommandResult.ListDisplayChange.TASK);
        expectedModel.updateFilteredTaskList(predicate);
        CommandTestUtil.assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_pastDate_noTasksFound() {
        TaskIsReminderPredicate predicate = preparePredicate(2020, 1, 20, 11, 45);
        RemindCommand command = new RemindCommand(predicate);
        CommandResult expectedCommandResult = new CommandResult(RemindCommand.MESSAGE_SUCCESS, CommandResult.ListDisplayChange.TASK);
        expectedModel.updateFilteredTaskList(predicate);
        CommandTestUtil.assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    @Test
    public void execute_futureDate_noTasksFound() {
        TaskIsReminderPredicate predicate = preparePredicate(2022, 4, 1, 14, 10);
        RemindCommand command = new RemindCommand(predicate);
        CommandResult expectedCommandResult = new CommandResult(RemindCommand.MESSAGE_SUCCESS, CommandResult.ListDisplayChange.TASK);
        expectedModel.updateFilteredTaskList(predicate);
        CommandTestUtil.assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    @Test
    public void execute_validDate_tasksFound() {
        TaskIsReminderPredicate predicate = preparePredicate(2022, 1, 30, 16, 25);
        RemindCommand command = new RemindCommand(predicate);
        CommandResult expectedCommandResult = new CommandResult(RemindCommand.MESSAGE_SUCCESS, CommandResult.ListDisplayChange.TASK);
        expectedModel.updateFilteredTaskList(predicate);
        CommandTestUtil.assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        ObservableList<Task> temp = model.getFilteredTaskList();
        List<Task> list = Arrays.asList(TypicalTasks.DO_PAPERWORK, TypicalTasks.ALEX_INSULIN);
        assertEquals(Arrays.asList(TypicalTasks.DO_PAPERWORK, TypicalTasks.ALEX_INSULIN), model.getFilteredTaskList());
    }

    @Test
    public void execute_validDate_noTaskFound() {
        TaskIsReminderPredicate predicate = preparePredicate(2020, 11, 1, 18, 50);
        RemindCommand command = new RemindCommand(predicate);
        CommandResult expectedCommandResult = new CommandResult(RemindCommand.MESSAGE_SUCCESS, CommandResult.ListDisplayChange.TASK);
        expectedModel.updateFilteredTaskList(predicate);
        CommandTestUtil.assertCommandSuccess(command, model, expectedCommandResult, expectedModel);

        Task taskToMark = model.getFilteredTaskList().get(TypicalIndexes.INDEX_FIRST.getZeroBased());
        model.markTaskAsDone(taskToMark);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    /**
     * Parses a given date into a {@code TaskIsReminderPredicate}.
     */
    private TaskIsReminderPredicate preparePredicate(int year, int month, int day, int hour, int minute) {
        return new TaskIsReminderPredicate(LocalDateTime.of(year, month, day, hour, minute));
    }
}
