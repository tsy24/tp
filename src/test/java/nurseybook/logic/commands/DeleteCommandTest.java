package nurseybook.logic.commands;

import static nurseybook.logic.commands.CommandTestUtil.assertCommandFailure;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nurseybook.logic.commands.CommandTestUtil.showElderlyAtIndex;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.Messages;
import nurseybook.commons.core.index.Index;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.UserPrefs;
import nurseybook.model.person.Elderly;
import nurseybook.testutil.TypicalElderlies;
import nurseybook.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(TypicalElderlies.getTypicalNurseyBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Elderly elderlyToDelete = model.getFilteredElderlyList().get(TypicalIndexes.INDEX_FIRST.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ELDERLY_SUCCESS, elderlyToDelete);

        ModelManager expectedModel = new ModelManager(model.getVersionedNurseyBook(), new UserPrefs());
        expectedModel.deleteElderly(elderlyToDelete);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

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
        showElderlyAtIndex(model, TypicalIndexes.INDEX_FIRST);

        Elderly elderlyToDelete = model.getFilteredElderlyList().get(TypicalIndexes.INDEX_FIRST.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ELDERLY_SUCCESS, elderlyToDelete);

        Model expectedModel = new ModelManager(model.getVersionedNurseyBook(), new UserPrefs());
        expectedModel.deleteElderly(elderlyToDelete);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));
        showNoElderly(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showElderlyAtIndex(model, TypicalIndexes.INDEX_FIRST);

        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of nursey book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getVersionedNurseyBook().getElderlyList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST);
        DeleteCommand deleteSecondCommand = new DeleteCommand(TypicalIndexes.INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(TypicalIndexes.INDEX_FIRST);

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
