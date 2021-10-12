package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_ELDERLIES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.SET_ONE_TAG;
import static seedu.address.logic.commands.CommandTestUtil.SET_TWO_TAGS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalElderlies.ALICE;
import static seedu.address.testutil.TypicalElderlies.BENSON;
import static seedu.address.testutil.TypicalElderlies.DANIEL;
import static seedu.address.testutil.TypicalElderlies.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.ElderlyHasTagPredicate;
import seedu.address.model.tag.Tag;

public class FilterCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

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
        String expectedMessage = String.format(MESSAGE_ELDERLIES_LISTED_OVERVIEW, 0);
        ElderlyHasTagPredicate predicate = new ElderlyHasTagPredicate(Set.of(new Tag(VALID_TAG_HUSBAND)));
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredElderlyList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredElderlyList());
    }

    @Test
    public void execute_oneFilterTag_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_ELDERLIES_LISTED_OVERVIEW, 3);
        ElderlyHasTagPredicate predicate = new ElderlyHasTagPredicate(Set.of(new Tag(VALID_TAG_FRIEND)));
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredElderlyList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredElderlyList());
    }

    @Test
    public void execute_multipleFilterTags_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_ELDERLIES_LISTED_OVERVIEW, 1);
        ElderlyHasTagPredicate predicate = new ElderlyHasTagPredicate(SET_TWO_TAGS);
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredElderlyList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(BENSON), model.getFilteredElderlyList());
    }
}

