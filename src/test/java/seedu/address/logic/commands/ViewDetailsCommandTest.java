package seedu.address.logic.commands;

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

class ViewDetailsCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Elderly elderlyToView = model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased());
        ViewDetailsCommand viewDetailsCommand = new ViewDetailsCommand(INDEX_FIRST);

        String expectedMessage = String.format(ViewDetailsCommand.MESSAGE_SUCCESS, elderlyToView.getName());

        ModelManager expectedModel = new ModelManager(model.getVersionedNurseyBook(), new UserPrefs());
        expectedModel.setElderlyOfInterest(elderlyToView);

        assertCommandSuccess(viewDetailsCommand, model, expectedMessage, true, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredElderlyList().size() + 1);
        ViewDetailsCommand viewDetailsCommand = new ViewDetailsCommand(outOfBoundIndex);

        assertCommandFailure(viewDetailsCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showElderlyAtIndex(model, INDEX_FIRST);

        Elderly elderlyToView = model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased());
        ViewDetailsCommand viewDetailsCommand = new ViewDetailsCommand(INDEX_FIRST);

        String expectedMessage = String.format(ViewDetailsCommand.MESSAGE_SUCCESS, elderlyToView.getName());

        Model expectedModel = new ModelManager(model.getVersionedNurseyBook(), new UserPrefs());
        showElderlyAtIndex(expectedModel, INDEX_FIRST);
        expectedModel.setElderlyOfInterest(elderlyToView);

        assertCommandSuccess(viewDetailsCommand, model, expectedMessage, true, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showElderlyAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getVersionedNurseyBook().getElderlyList().size());

        ViewDetailsCommand viewDetailsCommand = new ViewDetailsCommand(outOfBoundIndex);

        assertCommandFailure(viewDetailsCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }
}
