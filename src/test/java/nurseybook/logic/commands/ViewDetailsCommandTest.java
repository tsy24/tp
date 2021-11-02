package nurseybook.logic.commands;

import static nurseybook.commons.core.Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandFailure;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nurseybook.logic.commands.ViewDetailsCommand.MESSAGE_SUCCESS;
import static nurseybook.testutil.TypicalElderlies.getTypicalNurseyBook;
import static nurseybook.testutil.TypicalIndexes.INDEX_FIRST;
import static nurseybook.testutil.TypicalIndexes.INDEX_SECOND;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.index.Index;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.UserPrefs;
import nurseybook.model.person.Elderly;

class ViewDetailsCommandTest {
    private Model model = new ModelManager(getTypicalNurseyBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Elderly elderlyToView = model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased());
        ViewDetailsCommand viewDetailsCommand = new ViewDetailsCommand(INDEX_FIRST);

        String expectedMessage = String.format(MESSAGE_SUCCESS, elderlyToView.getName());

        ModelManager expectedModel = new ModelManager(model.getVersionedNurseyBook(), new UserPrefs());
        expectedModel.setElderlyOfInterest(elderlyToView);

        assertCommandSuccess(viewDetailsCommand, model, expectedMessage, true, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredElderlyList().size() + 1);
        ViewDetailsCommand viewDetailsCommand = new ViewDetailsCommand(outOfBoundIndex);

        assertCommandFailure(viewDetailsCommand, model, MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        CommandTestUtil.showElderlyAtIndex(model, INDEX_FIRST);

        Elderly elderlyToView = model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased());
        ViewDetailsCommand viewDetailsCommand = new ViewDetailsCommand(INDEX_FIRST);

        String expectedMessage = String.format(MESSAGE_SUCCESS, elderlyToView.getName());

        Model expectedModel = new ModelManager(model.getVersionedNurseyBook(), new UserPrefs());
        CommandTestUtil.showElderlyAtIndex(expectedModel, INDEX_FIRST);
        expectedModel.setElderlyOfInterest(elderlyToView);

        assertCommandSuccess(viewDetailsCommand, model, expectedMessage, true, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        CommandTestUtil.showElderlyAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of nursey book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getVersionedNurseyBook().getElderlyList().size());

        ViewDetailsCommand viewDetailsCommand = new ViewDetailsCommand(outOfBoundIndex);

        assertCommandFailure(viewDetailsCommand, model, MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }
}
