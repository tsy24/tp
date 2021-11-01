package nurseybook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.Messages;
import nurseybook.model.tag.ElderlyHasTagPredicate;
import nurseybook.model.tag.Tag;
import nurseybook.testutil.TypicalElderlies;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.UserPrefs;

public class FilterCommandTest {

    private Model model = new ModelManager(TypicalElderlies.getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalElderlies.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        ElderlyHasTagPredicate firstPredicate =
                new ElderlyHasTagPredicate(CommandTestUtil.SET_ONE_TAG);
        ElderlyHasTagPredicate secondPredicate =
                new ElderlyHasTagPredicate(CommandTestUtil.SET_TWO_TAGS);

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
        ElderlyHasTagPredicate predicate = new ElderlyHasTagPredicate(Set.of(new Tag(CommandTestUtil.VALID_TAG_HUSBAND)));
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredElderlyList(predicate);
        CommandTestUtil.assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredElderlyList());
    }

    @Test
    public void execute_oneFilterTag_multiplePersonsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_ELDERLIES_LISTED_OVERVIEW, 3);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage,
                CommandResult.ListDisplayChange.ELDERLY);
        ElderlyHasTagPredicate predicate = new ElderlyHasTagPredicate(Set.of(new Tag(CommandTestUtil.VALID_TAG_FRIEND)));
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredElderlyList(predicate);
        CommandTestUtil.assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Arrays.asList(TypicalElderlies.ALICE, TypicalElderlies.BENSON, TypicalElderlies.DANIEL), model.getFilteredElderlyList());
    }

    @Test
    public void execute_multipleFilterTags_onePersonFound() {
        String expectedMessage = String.format(Messages.MESSAGE_ELDERLIES_LISTED_OVERVIEW, 1);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage,
                CommandResult.ListDisplayChange.ELDERLY);
        ElderlyHasTagPredicate predicate = new ElderlyHasTagPredicate(CommandTestUtil.SET_TWO_TAGS);
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredElderlyList(predicate);
        CommandTestUtil.assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(List.of(TypicalElderlies.BENSON), model.getFilteredElderlyList());
    }
}

