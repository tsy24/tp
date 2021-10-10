package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showElderlyAtIndex;
import static seedu.address.testutil.TypicalElderlies.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ELDERLY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ELDERLY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Elderly;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Elderly elderlyToDelete = model.getFilteredElderlyList().get(INDEX_FIRST_ELDERLY.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ELDERLY);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ELDERLY_SUCCESS, elderlyToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteElderly(elderlyToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredElderlyList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showElderlyAtIndex(model, INDEX_FIRST_ELDERLY);

        Elderly elderlyToDelete = model.getFilteredElderlyList().get(INDEX_FIRST_ELDERLY.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ELDERLY);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ELDERLY_SUCCESS, elderlyToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteElderly(elderlyToDelete);
        showNoElderly(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showElderlyAtIndex(model, INDEX_FIRST_ELDERLY);

        Index outOfBoundIndex = INDEX_SECOND_ELDERLY;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getElderlyList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_ELDERLY);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_ELDERLY);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_ELDERLY);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different elderly -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoElderly(Model model) {
        model.updateFilteredElderlyList(p -> false);

        assertTrue(model.getFilteredElderlyList().isEmpty());
    }
}
