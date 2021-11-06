package nurseybook.logic.commands;

import static nurseybook.commons.core.Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX;
import static nurseybook.logic.commands.CommandTestUtil.SET_ONE_TAG;
import static nurseybook.logic.commands.CommandTestUtil.SET_ONE_TAG_DIFFERENT_CASE;
import static nurseybook.logic.commands.CommandTestUtil.SET_TWO_TAGS;
import static nurseybook.logic.commands.CommandTestUtil.VALID_TAG_DIABETES;
import static nurseybook.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandFailure;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nurseybook.logic.commands.DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS;
import static nurseybook.testutil.TypicalElderlies.getTypicalNurseyBook;
import static nurseybook.testutil.TypicalIndexes.INDEX_FIRST;
import static nurseybook.testutil.TypicalIndexes.INDEX_SECOND;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.index.Index;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.NurseyBook;
import nurseybook.model.UserPrefs;
import nurseybook.model.person.Elderly;
import nurseybook.testutil.ElderlyBuilder;

public class DeleteTagCommandTest {

    private Model model = new ModelManager(getTypicalNurseyBook(), new UserPrefs());

    @Test
    public void execute_deleteTagUnfilteredList_success() {
        Elderly secondElderly = model.getFilteredElderlyList().get(INDEX_SECOND.getZeroBased());
        Elderly tagDeletedElderly = new ElderlyBuilder(secondElderly).withTags(VALID_TAG_FRIEND).build();

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(INDEX_SECOND, SET_ONE_TAG);

        String expectedMessage = String.format(MESSAGE_DELETE_TAG_SUCCESS, tagDeletedElderly);

        Model expectedModel = new ModelManager(new NurseyBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(secondElderly, tagDeletedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteDifferentCaseTagUnfilteredList_success() {
        Elderly secondElderly = model.getFilteredElderlyList().get(INDEX_SECOND.getZeroBased());
        Elderly tagDeletedElderly = new ElderlyBuilder(secondElderly).withTags(VALID_TAG_DIABETES).build();

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(INDEX_SECOND, SET_ONE_TAG_DIFFERENT_CASE);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagDeletedElderly);

        Model expectedModel = new ModelManager(new NurseyBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(secondElderly, tagDeletedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteTagsUnfilteredList_success() {
        Elderly secondElderly = model.getFilteredElderlyList().get(INDEX_SECOND.getZeroBased());
        Elderly tagDeletedElderly = new ElderlyBuilder(secondElderly).withoutTags().build();

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(INDEX_SECOND, SET_TWO_TAGS);

        String expectedMessage = String.format(MESSAGE_DELETE_TAG_SUCCESS, tagDeletedElderly);

        Model expectedModel = new ModelManager(new NurseyBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(secondElderly, tagDeletedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteNewTagUnfilteredList_failure() {

        // Only one new tag
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(INDEX_FIRST, SET_ONE_TAG);
        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_NO_SUCH_TAG, VALID_TAG_DIABETES);
        assertCommandFailure(deleteTagCommand, model, expectedMessage);

        // Mix of new and existing tags
        deleteTagCommand = new DeleteTagCommand(INDEX_FIRST, SET_TWO_TAGS);
        expectedMessage = String.format(DeleteTagCommand.MESSAGE_NO_SUCH_TAG, VALID_TAG_DIABETES);
        assertCommandFailure(deleteTagCommand, model, expectedMessage);
    }

    @Test
    public void execute_deleteTagFilteredList_success() {
        CommandTestUtil.showElderlyAtIndex(model, INDEX_SECOND);

        Elderly firstElderly = model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased());
        Elderly tagDeletedElderly = new ElderlyBuilder(model.getFilteredElderlyList()
                .get(INDEX_FIRST.getZeroBased())).withTags(VALID_TAG_FRIEND).build();

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(INDEX_FIRST, SET_ONE_TAG);

        String expectedMessage = String.format(MESSAGE_DELETE_TAG_SUCCESS, tagDeletedElderly);

        Model expectedModel = new ModelManager(new NurseyBook(model.getVersionedNurseyBook()), new UserPrefs());
        CommandTestUtil.showElderlyAtIndex(expectedModel, INDEX_SECOND);
        expectedModel.setElderly(firstElderly, tagDeletedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidElderlyIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredElderlyList().size() + 1);
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(outOfBoundIndex, SET_ONE_TAG);

        assertCommandFailure(deleteTagCommand, model, MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
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

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(outOfBoundIndex, SET_ONE_TAG);

        assertCommandFailure(deleteTagCommand, model, MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final DeleteTagCommand standardCommand = new DeleteTagCommand(INDEX_FIRST,
                SET_ONE_TAG);
        // same values -> returns true
        DeleteTagCommand commandWithSameValues = new DeleteTagCommand(INDEX_FIRST,
                SET_ONE_TAG);
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new DeleteTagCommand(INDEX_SECOND, SET_ONE_TAG)));
        // different set of tags -> returns false
        assertFalse(standardCommand.equals(new DeleteTagCommand(INDEX_FIRST, SET_TWO_TAGS)));
    }
}
