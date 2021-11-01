package nurseybook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.Messages;
import nurseybook.commons.core.index.Index;
import nurseybook.model.person.Elderly;
import nurseybook.testutil.TypicalElderlies;
import nurseybook.testutil.TypicalIndexes;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.UserPrefs;

class ViewDetailsCommandTest {
    private Model model = new ModelManager(TypicalElderlies.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Elderly elderlyToView = model.getFilteredElderlyList().get(TypicalIndexes.INDEX_FIRST.getZeroBased());
        ViewDetailsCommand viewDetailsCommand = new ViewDetailsCommand(TypicalIndexes.INDEX_FIRST);

        String expectedMessage = String.format(ViewDetailsCommand.MESSAGE_SUCCESS, elderlyToView.getName());

        ModelManager expectedModel = new ModelManager(model.getVersionedNurseyBook(), new UserPrefs());
        expectedModel.setElderlyOfInterest(elderlyToView);

        CommandTestUtil.assertCommandSuccess(viewDetailsCommand, model, expectedMessage, true, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredElderlyList().size() + 1);
        ViewDetailsCommand viewDetailsCommand = new ViewDetailsCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(viewDetailsCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        CommandTestUtil.showElderlyAtIndex(model, TypicalIndexes.INDEX_FIRST);

        Elderly elderlyToView = model.getFilteredElderlyList().get(TypicalIndexes.INDEX_FIRST.getZeroBased());
        ViewDetailsCommand viewDetailsCommand = new ViewDetailsCommand(TypicalIndexes.INDEX_FIRST);

        String expectedMessage = String.format(ViewDetailsCommand.MESSAGE_SUCCESS, elderlyToView.getName());

        Model expectedModel = new ModelManager(model.getVersionedNurseyBook(), new UserPrefs());
        CommandTestUtil.showElderlyAtIndex(expectedModel, TypicalIndexes.INDEX_FIRST);
        expectedModel.setElderlyOfInterest(elderlyToView);

        CommandTestUtil.assertCommandSuccess(viewDetailsCommand, model, expectedMessage, true, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        CommandTestUtil.showElderlyAtIndex(model, TypicalIndexes.INDEX_FIRST);

        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getVersionedNurseyBook().getElderlyList().size());

        ViewDetailsCommand viewDetailsCommand = new ViewDetailsCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(viewDetailsCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }
}
