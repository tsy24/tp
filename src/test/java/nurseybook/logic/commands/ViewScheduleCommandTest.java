package nurseybook.logic.commands;

import static nurseybook.commons.core.Messages.MESSAGE_TASKS_ON_DATE;
import static nurseybook.logic.commands.CommandResult.ListDisplayChange.TASK;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nurseybook.testutil.TypicalTasks.FIONA_PHYSIO;
import static nurseybook.testutil.TypicalTasks.getTypicalNurseyBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.UserPrefs;
import nurseybook.model.task.DateTimeContainsDatePredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code ViewScheduleCommand}.
 */
public class ViewScheduleCommandTest {
    private Model model = new ModelManager(getTypicalNurseyBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalNurseyBook(), new UserPrefs());

    @Test
    public void equals() {
        LocalDate firstKeyDate = LocalDate.parse("2021-11-02");
        LocalDate secondKeyDate = LocalDate.parse("2021-11-03");
        DateTimeContainsDatePredicate firstPredicate = new DateTimeContainsDatePredicate(firstKeyDate);
        DateTimeContainsDatePredicate secondPredicate = new DateTimeContainsDatePredicate(secondKeyDate);

        ViewScheduleCommand viewScheduleFirstCommand = new ViewScheduleCommand(firstPredicate, firstKeyDate);
        ViewScheduleCommand viewScheduleSecondCommand = new ViewScheduleCommand(secondPredicate, secondKeyDate);

        // same object -> returns true
        assertTrue(viewScheduleFirstCommand.equals(viewScheduleFirstCommand));

        // same values -> returns true
        ViewScheduleCommand viewScheduleFirstCommandCopy = new ViewScheduleCommand(firstPredicate, firstKeyDate);
        assertTrue(viewScheduleFirstCommand.equals(viewScheduleFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewScheduleFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewScheduleFirstCommand.equals(null));

        // different command -> returns false
        assertFalse(viewScheduleFirstCommand.equals(viewScheduleSecondCommand));
    }

    @Test
    public void execute_multipleKeywords_multipleTasksFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_ON_DATE, 1);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, TASK);

        LocalDate keyDate = prepareDate("2021-09-13");
        DateTimeContainsDatePredicate predicate = new DateTimeContainsDatePredicate(keyDate);
        ViewScheduleCommand command = new ViewScheduleCommand(predicate, keyDate);
        expectedModel.addPossibleGhostTasksWithMatchingDate(keyDate);
        expectedModel.updateFilteredTaskList(predicate);

        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Arrays.asList(FIONA_PHYSIO), model.getFilteredTaskList());
    }

    /**
     * Parses {@code userInput} into a {@code LocalDate}.
     */
    private LocalDate prepareDate(String userInput) {
        return LocalDate.parse(userInput);
    }
}
