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
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Elderly;
import seedu.address.testutil.ElderlyBuilder;

public class AddTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addTagUnfilteredList_success() {
        Elderly firstElderly = model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased());
        Elderly tagAddedElderly = new ElderlyBuilder(firstElderly)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_DIABETES).build();

        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST, SET_ONE_TAG);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, tagAddedElderly);

        Model expectedModel = new ModelManager(new AddressBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(firstElderly, tagAddedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addTagsUnfilteredList_success() {
        Elderly thirdElderly = model.getFilteredElderlyList().get(INDEX_THIRD.getZeroBased());
        Elderly tagAddedElderly = new ElderlyBuilder(thirdElderly)
                .withTags(VALID_TAG_DIABETES, VALID_TAG_FRIEND).build();

        AddTagCommand addTagCommand = new AddTagCommand(INDEX_THIRD, SET_TWO_TAGS);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, tagAddedElderly);

        Model expectedModel = new ModelManager(new AddressBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(thirdElderly, tagAddedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addExistingTagUnfilteredList_success() {
        Elderly firstElderly = model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased());
        Elderly tagAddedElderly = new ElderlyBuilder(firstElderly)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_DIABETES).build();

        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST, SET_TWO_TAGS);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, tagAddedElderly);

        Model expectedModel = new ModelManager(new AddressBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(firstElderly, tagAddedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addTagFilteredList_success() {
        showElderlyAtIndex(model, INDEX_FIRST);

        Elderly firstElderly = model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased());
        Elderly tagAddedElderly = new ElderlyBuilder(model.getFilteredElderlyList()
                .get(INDEX_FIRST.getZeroBased())).withTags(VALID_TAG_FRIEND, VALID_TAG_DIABETES).build();

        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST, SET_ONE_TAG);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, tagAddedElderly);

        Model expectedModel = new ModelManager(new AddressBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(firstElderly, tagAddedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidElderlyIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredElderlyList().size() + 1);
        AddTagCommand addTagCommand = new AddTagCommand(outOfBoundIndex, SET_ONE_TAG);

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidElderlyIndexFilteredList_failure() {
        showElderlyAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getVersionedNurseyBook().getElderlyList().size());

        AddTagCommand addTagCommand = new AddTagCommand(outOfBoundIndex, SET_ONE_TAG);

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final AddTagCommand standardCommand = new AddTagCommand(INDEX_FIRST,
                SET_ONE_TAG);
        // same values -> returns true
        AddTagCommand commandWithSameValues = new AddTagCommand(INDEX_FIRST,
                SET_ONE_TAG);
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new AddTagCommand(INDEX_SECOND,
                SET_ONE_TAG)));
        // different set of tags -> returns false
        assertFalse(standardCommand.equals(new AddTagCommand(INDEX_FIRST,
                SET_TWO_TAGS)));
    }
}
