package nurseybook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.Messages;
import nurseybook.commons.core.index.Index;
import nurseybook.model.person.Elderly;
import nurseybook.model.person.Remark;
import nurseybook.testutil.ElderlyBuilder;
import nurseybook.testutil.TypicalElderlies;
import nurseybook.testutil.TypicalIndexes;
import nurseybook.model.AddressBook;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {

    private static final String REMARK_STUB = "Some remark";

    private Model model = new ModelManager(TypicalElderlies.getTypicalAddressBook(), new UserPrefs());

    public void execute_addRemarkUnfilteredList_success() {
        Elderly firstElderly = model.getFilteredElderlyList().get(TypicalIndexes.INDEX_FIRST.getZeroBased());
        Elderly editedElderly = new ElderlyBuilder(firstElderly).withRemark(REMARK_STUB).build();

        RemarkCommand remarkCommand = new RemarkCommand(
                TypicalIndexes.INDEX_FIRST, new Remark(editedElderly.getRemark().value));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedElderly);

        Model expectedModel = new ModelManager(new AddressBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        CommandTestUtil.assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRemarkUnfilteredList_success() {
        Elderly firstElderly = model.getFilteredElderlyList().get(TypicalIndexes.INDEX_FIRST.getZeroBased());
        Elderly editedElderly = new ElderlyBuilder(firstElderly).withRemark("").build();

        RemarkCommand remarkCommand = new RemarkCommand(TypicalIndexes.INDEX_FIRST,
                new Remark(editedElderly.getRemark().toString()));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, editedElderly);

        Model expectedModel = new ModelManager(new AddressBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(firstElderly, editedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        CommandTestUtil.assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        CommandTestUtil.showElderlyAtIndex(model, TypicalIndexes.INDEX_FIRST);

        Elderly firstElderly = model.getFilteredElderlyList().get(TypicalIndexes.INDEX_FIRST.getZeroBased());
        Elderly editedElderly = new ElderlyBuilder(model.getFilteredElderlyList()
                .get(TypicalIndexes.INDEX_FIRST.getZeroBased())).withRemark(REMARK_STUB).build();

        RemarkCommand remarkCommand = new RemarkCommand(
                TypicalIndexes.INDEX_FIRST, new Remark(editedElderly.getRemark().value));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedElderly);

        Model expectedModel = new ModelManager(new AddressBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(firstElderly, editedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        CommandTestUtil.assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidElderlyIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredElderlyList().size() + 1);
        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, new Remark(CommandTestUtil.VALID_REMARK_BOB));

        CommandTestUtil.assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidElderlyIndexFilteredList_failure() {
        CommandTestUtil.showElderlyAtIndex(model, TypicalIndexes.INDEX_FIRST);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getVersionedNurseyBook().getElderlyList().size());

        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, new Remark(CommandTestUtil.VALID_REMARK_BOB));

        CommandTestUtil.assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final RemarkCommand standardCommand = new RemarkCommand(TypicalIndexes.INDEX_FIRST,
                new Remark(CommandTestUtil.VALID_REMARK_AMY));
        // same values -> returns true
        RemarkCommand commandWithSameValues = new RemarkCommand(TypicalIndexes.INDEX_FIRST,
                new Remark(CommandTestUtil.VALID_REMARK_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(TypicalIndexes.INDEX_SECOND,
                new Remark(CommandTestUtil.VALID_REMARK_AMY))));
        // different remark -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(TypicalIndexes.INDEX_FIRST,
                new Remark(CommandTestUtil.VALID_REMARK_BOB))));
    }
}
