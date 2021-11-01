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
import nurseybook.testutil.ElderlyBuilder;
import nurseybook.testutil.TypicalElderlies;
import nurseybook.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteNokCommand}.
 */
public class DeleteNokCommandTest {
    private Model model = new ModelManager(TypicalElderlies.getTypicalNurseyBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Elderly elderlyToDeleteNokFrom = model.getFilteredElderlyList().get(TypicalIndexes.INDEX_FIRST.getZeroBased());
        Elderly nokDeletedElderly = new ElderlyBuilder(elderlyToDeleteNokFrom)
                .withNokName("NIL").withRelationship("").withPhone("").withEmail("").withAddress("").build();

        DeleteNokCommand deleteNokCommand = new DeleteNokCommand(TypicalIndexes.INDEX_FIRST);

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
        showElderlyAtIndex(model, TypicalIndexes.INDEX_FIRST);

        Elderly elderlyToDeleteNokFrom = model.getFilteredElderlyList().get(TypicalIndexes.INDEX_FIRST.getZeroBased());
        Elderly nokDeletedElderly = new ElderlyBuilder(elderlyToDeleteNokFrom)
                .withNokName("NIL").withRelationship("").withPhone("").withEmail("").withAddress("").build();

        DeleteNokCommand deleteNokCommand = new DeleteNokCommand(TypicalIndexes.INDEX_FIRST);

        String expectedMessage = String.format(
                DeleteNokCommand.MESSAGE_DELETE_ELDERLY_NOK_SUCCESS, nokDeletedElderly);

        Model expectedModel = new ModelManager(model.getVersionedNurseyBook(), new UserPrefs());
        expectedModel.setElderly(elderlyToDeleteNokFrom, nokDeletedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(deleteNokCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showElderlyAtIndex(model, TypicalIndexes.INDEX_FIRST);

        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of nursey book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getVersionedNurseyBook().getElderlyList().size());

        DeleteNokCommand deleteNokCommand = new DeleteNokCommand(outOfBoundIndex);

        assertCommandFailure(deleteNokCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteNokCommand deleteFirstNokCommand = new DeleteNokCommand(TypicalIndexes.INDEX_FIRST);
        DeleteNokCommand deleteSecondNokCommand = new DeleteNokCommand(TypicalIndexes.INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstNokCommand.equals(deleteFirstNokCommand));

        // same values -> returns true
        DeleteNokCommand deleteFirstCommandCopy = new DeleteNokCommand(TypicalIndexes.INDEX_FIRST);

        assertTrue(deleteFirstNokCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstNokCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstNokCommand.equals(null));

        // different elderly -> returns false
        assertFalse(deleteFirstNokCommand.equals(deleteSecondNokCommand));
    }
}
