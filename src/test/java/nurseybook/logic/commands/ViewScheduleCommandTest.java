package nurseybook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.Messages;
import nurseybook.model.task.DateTimeContainsDatePredicate;
import nurseybook.testutil.TypicalTasks;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code ViewScheduleCommand}.
 */
public class ViewScheduleCommandTest {
    private Model model = new ModelManager(TypicalTasks.getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalTasks.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        LocalDate firstKeyDate = LocalDate.parse("2021-11-02");
        LocalDate secondKeyDate = LocalDate.parse("2021-11-03");
        DateTimeContainsDatePredicate firstPredicate =
                new DateTimeContainsDatePredicate(firstKeyDate);
        DateTimeContainsDatePredicate secondPredicate =
                new DateTimeContainsDatePredicate(secondKeyDate);

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
        String expectedMessage = String.format(Messages.MESSAGE_TASKS_ON_DATE, 1);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, CommandResult.ListDisplayChange.TASK);

        LocalDate keyDate = prepareDate("2021-09-13");
        DateTimeContainsDatePredicate predicate = new DateTimeContainsDatePredicate(keyDate);
        ViewScheduleCommand command = new ViewScheduleCommand(predicate, keyDate);
        expectedModel.addPossibleGhostTasksWithMatchingDate(keyDate);
        expectedModel.updateFilteredTaskList(predicate);

        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Arrays.asList(TypicalTasks.YASMINE_PHYSIO), model.getFilteredTaskList());
    }

    /**
     * Parses {@code userInput} into a {@code LocalDate}.
     */
    private LocalDate prepareDate(String userInput) {
        return LocalDate.parse(userInput);
    }
}
