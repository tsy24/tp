package nurseybook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.Messages;
import nurseybook.model.person.NameContainsKeywordsPredicate;
import nurseybook.testutil.TypicalElderlies;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code FindElderlyCommand}.
 */
public class FindElderlyCommandTest {
    private Model model = new ModelManager(TypicalElderlies.getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalElderlies.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindElderlyCommand findFirstCommand = new FindElderlyCommand(firstPredicate);
        FindElderlyCommand findSecondCommand = new FindElderlyCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindElderlyCommand findFirstCommandCopy = new FindElderlyCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different command -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noElderlyFound() {
        String expectedMessage = String.format(Messages.MESSAGE_ELDERLIES_LISTED_OVERVIEW, 0);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage,
                CommandResult.ListDisplayChange.ELDERLY);

        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindElderlyCommand command = new FindElderlyCommand(predicate);
        expectedModel.updateFilteredElderlyList(predicate);

        CommandTestUtil.assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredElderlyList());
    }

    @Test
    public void execute_multipleKeywords_multipleElderliesFound() {
        String expectedMessage = String.format(Messages.MESSAGE_ELDERLIES_LISTED_OVERVIEW, 3);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage,
                CommandResult.ListDisplayChange.ELDERLY);

        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindElderlyCommand command = new FindElderlyCommand(predicate);
        expectedModel.updateFilteredElderlyList(predicate);

        CommandTestUtil.assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Arrays.asList(TypicalElderlies.CARL, TypicalElderlies.ELLE, TypicalElderlies.FIONA), model.getFilteredElderlyList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
