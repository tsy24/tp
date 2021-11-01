package nurseybook.logic.commands;

import static nurseybook.logic.commands.CommandTestUtil.SET_ONE_TAG;
import static nurseybook.logic.commands.CommandTestUtil.SET_TWO_TAGS;
import static nurseybook.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static nurseybook.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nurseybook.testutil.TypicalElderlies.ALICE;
import static nurseybook.testutil.TypicalElderlies.BENSON;
import static nurseybook.testutil.TypicalElderlies.DANIEL;
import static nurseybook.testutil.TypicalElderlies.getTypicalNurseyBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.Messages;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.UserPrefs;
import nurseybook.model.tag.ElderlyHasTagPredicate;
import nurseybook.model.tag.Tag;

public class FilterCommandTest {

    private Model model = new ModelManager(getTypicalNurseyBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalNurseyBook(), new UserPrefs());

    @Test
    public void equals() {
        ElderlyHasTagPredicate firstPredicate =
                new ElderlyHasTagPredicate(SET_ONE_TAG);
        ElderlyHasTagPredicate secondPredicate =
                new ElderlyHasTagPredicate(SET_TWO_TAGS);

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same predicate -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different set of tags -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_oneFilterTag_noPersonFound() {
        String expectedMessage = String.format(Messages.MESSAGE_ELDERLIES_LISTED_OVERVIEW, 0);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage,
                CommandResult.ListDisplayChange.ELDERLY);
        ElderlyHasTagPredicate predicate = new ElderlyHasTagPredicate(Set.of(new Tag(VALID_TAG_HUSBAND)));
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredElderlyList(predicate);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredElderlyList());
    }

    @Test
    public void execute_oneFilterTag_multiplePersonsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_ELDERLIES_LISTED_OVERVIEW, 3);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage,
                CommandResult.ListDisplayChange.ELDERLY);
        ElderlyHasTagPredicate predicate = new ElderlyHasTagPredicate(Set.of(new Tag(VALID_TAG_FRIEND)));
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredElderlyList(predicate);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredElderlyList());
    }

    @Test
    public void execute_multipleFilterTags_onePersonFound() {
        String expectedMessage = String.format(Messages.MESSAGE_ELDERLIES_LISTED_OVERVIEW, 1);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage,
                CommandResult.ListDisplayChange.ELDERLY);
        ElderlyHasTagPredicate predicate = new ElderlyHasTagPredicate(SET_TWO_TAGS);
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredElderlyList(predicate);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(List.of(BENSON), model.getFilteredElderlyList());
    }
}

