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

public class AddTagCommandTest {

    private Model model = new ModelManager(TypicalElderlies.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addTagUnfilteredList_success() {
        Elderly firstElderly = model.getFilteredElderlyList().get(TypicalIndexes.INDEX_FIRST.getZeroBased());
        Elderly tagAddedElderly = new ElderlyBuilder(firstElderly)
                .withTags(CommandTestUtil.VALID_TAG_FRIEND, CommandTestUtil.VALID_TAG_DIABETES).build();

        AddTagCommand addTagCommand = new AddTagCommand(TypicalIndexes.INDEX_FIRST, CommandTestUtil.SET_ONE_TAG);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, tagAddedElderly);

        Model expectedModel = new ModelManager(new AddressBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(firstElderly, tagAddedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        CommandTestUtil.assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addTagsUnfilteredList_success() {
        Elderly thirdElderly = model.getFilteredElderlyList().get(TypicalIndexes.INDEX_THIRD.getZeroBased());
        Elderly tagAddedElderly = new ElderlyBuilder(thirdElderly)
                .withTags(CommandTestUtil.VALID_TAG_DIABETES, CommandTestUtil.VALID_TAG_FRIEND).build();

        AddTagCommand addTagCommand = new AddTagCommand(TypicalIndexes.INDEX_THIRD, CommandTestUtil.SET_TWO_TAGS);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, tagAddedElderly);

        Model expectedModel = new ModelManager(new AddressBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(thirdElderly, tagAddedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        CommandTestUtil.assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addExistingTagUnfilteredList_success() {
        Elderly firstElderly = model.getFilteredElderlyList().get(TypicalIndexes.INDEX_FIRST.getZeroBased());
        Elderly tagAddedElderly = new ElderlyBuilder(firstElderly)
                .withTags(CommandTestUtil.VALID_TAG_FRIEND, CommandTestUtil.VALID_TAG_DIABETES).build();

        AddTagCommand addTagCommand = new AddTagCommand(TypicalIndexes.INDEX_FIRST, CommandTestUtil.SET_TWO_TAGS);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, tagAddedElderly);

        Model expectedModel = new ModelManager(new AddressBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(firstElderly, tagAddedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        CommandTestUtil.assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addTagFilteredList_success() {
        CommandTestUtil.showElderlyAtIndex(model, TypicalIndexes.INDEX_FIRST);

        Elderly firstElderly = model.getFilteredElderlyList().get(TypicalIndexes.INDEX_FIRST.getZeroBased());
        Elderly tagAddedElderly = new ElderlyBuilder(model.getFilteredElderlyList()
                .get(TypicalIndexes.INDEX_FIRST.getZeroBased())).withTags(CommandTestUtil.VALID_TAG_FRIEND, CommandTestUtil.VALID_TAG_DIABETES).build();

        AddTagCommand addTagCommand = new AddTagCommand(TypicalIndexes.INDEX_FIRST, CommandTestUtil.SET_ONE_TAG);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, tagAddedElderly);

        Model expectedModel = new ModelManager(new AddressBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(firstElderly, tagAddedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        CommandTestUtil.assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidElderlyIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredElderlyList().size() + 1);
        AddTagCommand addTagCommand = new AddTagCommand(outOfBoundIndex, CommandTestUtil.SET_ONE_TAG);

        CommandTestUtil.assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
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

        AddTagCommand addTagCommand = new AddTagCommand(outOfBoundIndex, CommandTestUtil.SET_ONE_TAG);

        CommandTestUtil.assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final AddTagCommand standardCommand = new AddTagCommand(TypicalIndexes.INDEX_FIRST,
                CommandTestUtil.SET_ONE_TAG);
        // same values -> returns true
        AddTagCommand commandWithSameValues = new AddTagCommand(TypicalIndexes.INDEX_FIRST,
                CommandTestUtil.SET_ONE_TAG);
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new AddTagCommand(TypicalIndexes.INDEX_SECOND,
                CommandTestUtil.SET_ONE_TAG)));
        // different set of tags -> returns false
        assertFalse(standardCommand.equals(new AddTagCommand(TypicalIndexes.INDEX_FIRST,
                CommandTestUtil.SET_TWO_TAGS)));
    }
}
