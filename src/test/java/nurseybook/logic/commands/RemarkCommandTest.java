package nurseybook.logic.commands;

import static nurseybook.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static nurseybook.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandFailure;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nurseybook.testutil.TypicalElderlies.getTypicalNurseyBook;
import static nurseybook.testutil.TypicalIndexes.INDEX_FIRST;
import static nurseybook.testutil.TypicalIndexes.INDEX_SECOND;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.Messages;
import nurseybook.commons.core.index.Index;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.NurseyBook;
import nurseybook.model.UserPrefs;
import nurseybook.model.person.Elderly;
import nurseybook.model.person.Remark;
import nurseybook.testutil.ElderlyBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {

    private static final String REMARK_STUB = "Some remark";

    private Model model = new ModelManager(getTypicalNurseyBook(), new UserPrefs());

    @Test
    public void execute_addRemarkUnfilteredList_success() {
        Elderly firstElderly = model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased());
        Elderly editedElderly = new ElderlyBuilder(firstElderly).withRemark(REMARK_STUB).build();

        RemarkCommand remarkCommand = new RemarkCommand(
                INDEX_FIRST, new Remark(editedElderly.getRemark().value));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedElderly);

        Model expectedModel = new ModelManager(new NurseyBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRemarkUnfilteredList_success() {
        Elderly firstElderly = model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased());
        Elderly editedElderly = new ElderlyBuilder(firstElderly).withRemark("").build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST,
                new Remark(editedElderly.getRemark().toString()));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, editedElderly);

        Model expectedModel = new ModelManager(new NurseyBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(firstElderly, editedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        CommandTestUtil.showElderlyAtIndex(model, INDEX_FIRST);

        Elderly firstElderly = model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased());
        Elderly editedElderly = new ElderlyBuilder(model.getFilteredElderlyList()
                .get(INDEX_FIRST.getZeroBased())).withRemark(REMARK_STUB).build();

        RemarkCommand remarkCommand = new RemarkCommand(
                INDEX_FIRST, new Remark(editedElderly.getRemark().value));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedElderly);

        Model expectedModel = new ModelManager(new NurseyBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(firstElderly, editedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidElderlyIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredElderlyList().size() + 1);
        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, new Remark(VALID_REMARK_BOB));

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of nursey book
     */
    @Test
    public void execute_invalidElderlyIndexFilteredList_failure() {
        CommandTestUtil.showElderlyAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of nursey book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getVersionedNurseyBook().getElderlyList().size());

        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, new Remark(VALID_REMARK_BOB));

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST,
                new Remark(VALID_REMARK_AMY));
        // same values -> returns true
        RemarkCommand commandWithSameValues = new RemarkCommand(INDEX_FIRST,
                new Remark(VALID_REMARK_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_SECOND,
                new Remark(VALID_REMARK_AMY))));
        // different remark -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_FIRST,
                new Remark(VALID_REMARK_BOB))));
    }
}
