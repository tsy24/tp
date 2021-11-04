package nurseybook.logic.commands;

import static nurseybook.logic.commands.CommandResult.ListDisplayChange.TASK;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nurseybook.logic.commands.RemindCommand.MESSAGE_SUCCESS;
import static nurseybook.testutil.TypicalIndexes.INDEX_FIRST;
import static nurseybook.testutil.TypicalTasks.ALICE_INSULIN;
import static nurseybook.testutil.TypicalTasks.DO_PAPERWORK;
import static nurseybook.testutil.TypicalTasks.getTypicalNurseyBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.UserPrefs;
import nurseybook.model.task.Task;
import nurseybook.model.task.TaskIsReminderPredicate;

public class RemindCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalNurseyBook(), new UserPrefs());
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
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, TASK);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_pastDate_noTasksFound() {
        TaskIsReminderPredicate predicate = preparePredicate(2020, 1, 20, 11, 45);
        RemindCommand command = new RemindCommand(predicate);
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS,
                TASK);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    @Test
    public void execute_futureDate_noTasksFound() {
        TaskIsReminderPredicate predicate = preparePredicate(2022, 4, 1, 14, 10);
        RemindCommand command = new RemindCommand(predicate);
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS,
                TASK);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    @Test
    public void execute_validDate_tasksFound() {
        TaskIsReminderPredicate predicate = preparePredicate(2022, 1, 30, 16, 25);
        RemindCommand command = new RemindCommand(predicate);
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS,
                TASK);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        ObservableList<Task> temp = model.getFilteredTaskList();
        List<Task> list = Arrays.asList(DO_PAPERWORK, ALICE_INSULIN);
        assertEquals(Arrays.asList(DO_PAPERWORK, ALICE_INSULIN), model.getFilteredTaskList());
    }

    @Test
    public void execute_validDate_noTaskFound() {
        TaskIsReminderPredicate predicate = preparePredicate(2020, 11, 1, 18, 50);
        RemindCommand command = new RemindCommand(predicate);
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS,
                TASK);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);

        Task taskToMark = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
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
