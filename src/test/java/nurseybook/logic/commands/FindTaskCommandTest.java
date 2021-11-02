package nurseybook.logic.commands;

import static nurseybook.commons.core.Messages.MESSAGE_TASKS_LISTED_OVERVIEW;
import static nurseybook.logic.commands.CommandResult.ListDisplayChange.TASK;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nurseybook.testutil.TypicalTasks.KG_SC_VACCINE;
import static nurseybook.testutil.TypicalTasks.YASMINE_PHYSIO;
import static nurseybook.testutil.TypicalTasks.getTypicalNurseyBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.UserPrefs;
import nurseybook.model.task.DescriptionContainsKeywordPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindTaskCommand}.
 */
public class FindTaskCommandTest {
    private Model model = new ModelManager(getTypicalNurseyBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalNurseyBook(), new UserPrefs());

    @Test
    public void equals() {
        DescriptionContainsKeywordPredicate firstPredicate =
                new DescriptionContainsKeywordPredicate(Collections.singletonList("first"));
        DescriptionContainsKeywordPredicate secondPredicate =
                new DescriptionContainsKeywordPredicate(Collections.singletonList("second"));

        FindTaskCommand findTaskFirstCommand = new FindTaskCommand(firstPredicate);
        FindTaskCommand findTaskSecondCommand = new FindTaskCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findTaskFirstCommand.equals(findTaskFirstCommand));

        // same values -> returns true
        FindTaskCommand findTaskFirstCommandCopy = new FindTaskCommand(firstPredicate);
        assertTrue(findTaskFirstCommand.equals(findTaskFirstCommandCopy));

        // different types -> returns false
        assertFalse(findTaskFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findTaskFirstCommand.equals(null));

        // different command -> returns false
        assertFalse(findTaskFirstCommand.equals(findTaskSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noTasksFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 0);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, TASK);

        DescriptionContainsKeywordPredicate predicate = preparePredicate(" ");
        FindTaskCommand command = new FindTaskCommand(predicate);
        expectedModel.updateFilteredTaskList(predicate);

        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    @Test
    public void execute_multipleKeywords_multipleTasksFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 2);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, TASK);

        DescriptionContainsKeywordPredicate predicate = preparePredicate("Yoga Leave Pfizer");
        FindTaskCommand command = new FindTaskCommand(predicate);
        expectedModel.updateFilteredTaskList(predicate);

        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Arrays.asList(YASMINE_PHYSIO, KG_SC_VACCINE), model.getFilteredTaskList());
    }

    /**
     * Parses {@code userInput} into a {@code DescriptionContainsKeywordPredicate}.
     */
    private DescriptionContainsKeywordPredicate preparePredicate(String userInput) {
        return new DescriptionContainsKeywordPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
