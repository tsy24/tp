package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showElderlyAtIndex;
import static seedu.address.testutil.TypicalElderlies.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Elderly;
import seedu.address.testutil.ElderlyBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteNokCommand}.
 */
public class DeleteNokCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Elderly elderlyToDeleteNokFrom = model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased());
        Elderly nokDeletedElderly = new ElderlyBuilder(elderlyToDeleteNokFrom)
                .withNokName("NIL").withRelationship("").withPhone("").withEmail("").withAddress("").build();

        DeleteNokCommand deleteNokCommand = new DeleteNokCommand(INDEX_FIRST);

        String expectedMessage = String.format(
                DeleteNokCommand.MESSAGE_DELETE_ELDERLY_NOK_SUCCESS, nokDeletedElderly);

        ModelManager expectedModel = new ModelManager(model.getVersionedNurseyBook(), new UserPrefs());
        expectedModel.setElderly(elderlyToDeleteNokFrom, nokDeletedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(deleteNokCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredElderlyList().size() + 1);
        DeleteNokCommand deleteNokCommand = new DeleteNokCommand(outOfBoundIndex);

        assertCommandFailure(deleteNokCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showElderlyAtIndex(model, INDEX_FIRST);

        Elderly elderlyToDeleteNokFrom = model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased());
        Elderly nokDeletedElderly = new ElderlyBuilder(elderlyToDeleteNokFrom)
                .withNokName("NIL").withRelationship("").withPhone("").withEmail("").withAddress("").build();

        DeleteNokCommand deleteNokCommand = new DeleteNokCommand(INDEX_FIRST);

        String expectedMessage = String.format(
                DeleteNokCommand.MESSAGE_DELETE_ELDERLY_NOK_SUCCESS, nokDeletedElderly);

        Model expectedModel = new ModelManager(model.getVersionedNurseyBook(), new UserPrefs());
        expectedModel.setElderly(elderlyToDeleteNokFrom, nokDeletedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(deleteNokCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showElderlyAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getVersionedNurseyBook().getElderlyList().size());

        DeleteNokCommand deleteNokCommand = new DeleteNokCommand(outOfBoundIndex);

        assertCommandFailure(deleteNokCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteNokCommand deleteFirstNokCommand = new DeleteNokCommand(INDEX_FIRST);
        DeleteNokCommand deleteSecondNokCommand = new DeleteNokCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstNokCommand.equals(deleteFirstNokCommand));

        // same values -> returns true
        DeleteNokCommand deleteFirstCommandCopy = new DeleteNokCommand(INDEX_FIRST);

        assertTrue(deleteFirstNokCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstNokCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstNokCommand.equals(null));

        // different elderly -> returns false
        assertFalse(deleteFirstNokCommand.equals(deleteSecondNokCommand));
    }
}
