package nurseybook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.Messages;
import nurseybook.commons.core.index.Index;
import nurseybook.model.person.Elderly;
import nurseybook.testutil.ElderlyBuilder;
import nurseybook.testutil.TypicalElderlies;
import nurseybook.testutil.TypicalIndexes;
import nurseybook.model.AddressBook;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.UserPrefs;

public class DeleteTagCommandTest {

    private Model model = new ModelManager(TypicalElderlies.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_deleteTagUnfilteredList_success() {
        Elderly secondElderly = model.getFilteredElderlyList().get(TypicalIndexes.INDEX_SECOND.getZeroBased());
        Elderly tagDeletedElderly = new ElderlyBuilder(secondElderly).withTags(CommandTestUtil.VALID_TAG_FRIEND).build();

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(TypicalIndexes.INDEX_SECOND, CommandTestUtil.SET_ONE_TAG);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagDeletedElderly);

        Model expectedModel = new ModelManager(new AddressBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(secondElderly, tagDeletedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        CommandTestUtil.assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteTagsUnfilteredList_success() {
        Elderly secondElderly = model.getFilteredElderlyList().get(TypicalIndexes.INDEX_SECOND.getZeroBased());
        Elderly tagDeletedElderly = new ElderlyBuilder(secondElderly).withoutTags().build();

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(TypicalIndexes.INDEX_SECOND, CommandTestUtil.SET_TWO_TAGS);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagDeletedElderly);

        Model expectedModel = new ModelManager(new AddressBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(secondElderly, tagDeletedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        CommandTestUtil.assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteNewTagUnfilteredList_failure() {

        // Only one new tag
        DeleteTagCommand addTagCommand = new DeleteTagCommand(TypicalIndexes.INDEX_FIRST, CommandTestUtil.SET_ONE_TAG);
        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_NO_SUCH_TAG, CommandTestUtil.VALID_TAG_DIABETES);
        CommandTestUtil.assertCommandFailure(addTagCommand, model, expectedMessage);

        // Mix of new and existing tags
        addTagCommand = new DeleteTagCommand(TypicalIndexes.INDEX_FIRST, CommandTestUtil.SET_TWO_TAGS);
        expectedMessage = String.format(DeleteTagCommand.MESSAGE_NO_SUCH_TAG, CommandTestUtil.VALID_TAG_DIABETES);
        CommandTestUtil.assertCommandFailure(addTagCommand, model, expectedMessage);
    }

    @Test
    public void execute_deleteTagFilteredList_success() {
        CommandTestUtil.showElderlyAtIndex(model, TypicalIndexes.INDEX_SECOND);

        Elderly firstElderly = model.getFilteredElderlyList().get(TypicalIndexes.INDEX_FIRST.getZeroBased());
        Elderly tagDeletedElderly = new ElderlyBuilder(model.getFilteredElderlyList()
                .get(TypicalIndexes.INDEX_FIRST.getZeroBased())).withTags(CommandTestUtil.VALID_TAG_FRIEND).build();

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(TypicalIndexes.INDEX_FIRST, CommandTestUtil.SET_ONE_TAG);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagDeletedElderly);

        Model expectedModel = new ModelManager(new AddressBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(firstElderly, tagDeletedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        CommandTestUtil.assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidElderlyIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredElderlyList().size() + 1);
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(outOfBoundIndex, CommandTestUtil.SET_ONE_TAG);

        CommandTestUtil.assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
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

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(outOfBoundIndex, CommandTestUtil.SET_ONE_TAG);

        CommandTestUtil.assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final DeleteTagCommand standardCommand = new DeleteTagCommand(TypicalIndexes.INDEX_FIRST,
                CommandTestUtil.SET_ONE_TAG);
        // same values -> returns true
        DeleteTagCommand commandWithSameValues = new DeleteTagCommand(TypicalIndexes.INDEX_FIRST,
                CommandTestUtil.SET_ONE_TAG);
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new DeleteTagCommand(TypicalIndexes.INDEX_SECOND,
                CommandTestUtil.SET_ONE_TAG)));
        // different set of tags -> returns false
        assertFalse(standardCommand.equals(new DeleteTagCommand(TypicalIndexes.INDEX_FIRST,
                CommandTestUtil.SET_TWO_TAGS)));
    }
}
