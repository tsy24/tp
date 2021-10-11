package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.SET_ONE_TAG;
import static seedu.address.logic.commands.CommandTestUtil.SET_TWO_TAGS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DIABETES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showElderlyAtIndex;
import static seedu.address.testutil.TypicalElderlies.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ELDERLY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ELDERLY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Elderly;
import seedu.address.testutil.ElderlyBuilder;

public class DeleteTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_deleteTagUnfilteredList_success() {
        Elderly secondPerson = model.getFilteredElderlyList().get(INDEX_SECOND_ELDERLY.getZeroBased());
        Elderly tagDeletedPerson = new ElderlyBuilder(secondPerson).withTags(VALID_TAG_FRIEND).build();

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(INDEX_SECOND_ELDERLY, SET_ONE_TAG);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagDeletedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setElderly(secondPerson, tagDeletedPerson);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteTagsUnfilteredList_success() {
        Elderly secondElderly = model.getFilteredElderlyList().get(INDEX_SECOND_ELDERLY.getZeroBased());
        Elderly tagDeletedElderly = new ElderlyBuilder(secondElderly).withoutTags().build();

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(INDEX_SECOND_ELDERLY, SET_TWO_TAGS);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagDeletedElderly);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setElderly(secondElderly, tagDeletedElderly);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteNewTagUnfilteredList_failure() {

        // Only one new tag
        DeleteTagCommand addTagCommand = new DeleteTagCommand(INDEX_FIRST_ELDERLY, SET_ONE_TAG);
        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_NO_SUCH_TAG, VALID_TAG_DIABETES);
        assertCommandFailure(addTagCommand, model, expectedMessage);

        // Mix of new and existing tags
        addTagCommand = new DeleteTagCommand(INDEX_FIRST_ELDERLY, SET_TWO_TAGS);
        expectedMessage = String.format(DeleteTagCommand.MESSAGE_NO_SUCH_TAG, VALID_TAG_DIABETES);
        assertCommandFailure(addTagCommand, model, expectedMessage);
    }

    @Test
    public void execute_deleteTagFilteredList_success() {
        showElderlyAtIndex(model, INDEX_SECOND_ELDERLY);

        Elderly firstElderly = model.getFilteredElderlyList().get(INDEX_FIRST_ELDERLY.getZeroBased());
        Elderly tagDeletedElderly = new ElderlyBuilder(model.getFilteredElderlyList()
                .get(INDEX_FIRST_ELDERLY.getZeroBased())).withTags(VALID_TAG_FRIEND).build();

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(INDEX_FIRST_ELDERLY, SET_ONE_TAG);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagDeletedElderly);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setElderly(firstElderly, tagDeletedElderly);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredElderlyList().size() + 1);
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(outOfBoundIndex, SET_ONE_TAG);

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidElderlyIndexFilteredList_failure() {
        showElderlyAtIndex(model, INDEX_FIRST_ELDERLY);
        Index outOfBoundIndex = INDEX_SECOND_ELDERLY;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getElderlyList().size());

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(outOfBoundIndex, SET_ONE_TAG);

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final DeleteTagCommand standardCommand = new DeleteTagCommand(INDEX_FIRST_ELDERLY,
                SET_ONE_TAG);
        // same values -> returns true
        DeleteTagCommand commandWithSameValues = new DeleteTagCommand(INDEX_FIRST_ELDERLY,
                SET_ONE_TAG);
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new DeleteTagCommand(INDEX_SECOND_ELDERLY,
                SET_ONE_TAG)));
        // different set of tags -> returns false
        assertFalse(standardCommand.equals(new DeleteTagCommand(INDEX_FIRST_ELDERLY,
                SET_TWO_TAGS)));
    }
}
