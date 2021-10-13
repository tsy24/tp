package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOK_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showElderlyAtIndex;
import static seedu.address.testutil.TypicalElderlies.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditElderlyDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Elderly;
import seedu.address.testutil.EditElderlyDescriptorBuilder;
import seedu.address.testutil.ElderlyBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Elderly editedElderly = new ElderlyBuilder().build();
        EditElderlyDescriptor descriptor = new EditElderlyDescriptorBuilder(editedElderly).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ELDERLY_SUCCESS, editedElderly);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setElderly(model.getFilteredElderlyList().get(0), editedElderly);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastElderly = Index.fromOneBased(model.getFilteredElderlyList().size());
        Elderly lastElderly = model.getFilteredElderlyList().get(indexLastElderly.getZeroBased());

        ElderlyBuilder elderlyInList = new ElderlyBuilder(lastElderly);
        Elderly editedElderly = elderlyInList.withName(VALID_NAME_BOB).withPhone(VALID_NOK_PHONE_BOB)
                .withAge(VALID_AGE_BOB).withGender(VALID_GENDER_BOB).withTags(VALID_TAG_HUSBAND).build();

        EditElderlyDescriptor descriptor = new EditElderlyDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_NOK_PHONE_BOB).withAge(VALID_AGE_BOB).withGender(VALID_GENDER_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastElderly, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ELDERLY_SUCCESS, editedElderly);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setElderly(lastElderly, editedElderly);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST, new EditCommand.EditElderlyDescriptor());
        Elderly editedElderly = model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ELDERLY_SUCCESS, editedElderly);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showElderlyAtIndex(model, INDEX_FIRST);

        Elderly elderlyInFilteredList = model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased());
        Elderly editedElderly = new ElderlyBuilder(elderlyInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST,
                new EditElderlyDescriptorBuilder().withName(VALID_NAME_BOB).build());
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ELDERLY_SUCCESS, editedElderly);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setElderly(model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased()), editedElderly);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateElderlyUnfilteredList_failure() {
        Elderly firstElderly = model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased());
        EditElderlyDescriptor descriptor = new EditElderlyDescriptorBuilder(firstElderly).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_ELDERLY);
    }

    @Test
    public void execute_duplicateElderlyFilteredList_failure() {
        showElderlyAtIndex(model, INDEX_FIRST);

        // edit elderly in filtered list into a duplicate in address book
        Elderly elderlyInList = model.getAddressBook().getElderlyList().get(INDEX_SECOND.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST,
                new EditElderlyDescriptorBuilder(elderlyInList).build());
        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_ELDERLY);
    }

    @Test
    public void execute_invalidElderlyIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredElderlyList().size() + 1);
        EditElderlyDescriptor descriptor = new EditElderlyDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
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
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getElderlyList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditElderlyDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST, DESC_AMY);

        // same values -> returns true
        EditElderlyDescriptor copyDescriptor = new EditElderlyDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST, DESC_BOB)));
    }

}
