package nurseybook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.Messages;
import nurseybook.commons.core.index.Index;
import nurseybook.model.person.Elderly;
import nurseybook.testutil.EditElderlyDescriptorBuilder;
import nurseybook.testutil.ElderlyBuilder;
import nurseybook.testutil.TypicalElderlies;
import nurseybook.testutil.TypicalIndexes;
import nurseybook.model.AddressBook;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(TypicalElderlies.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Elderly editedElderly = new ElderlyBuilder().build();
        EditCommand.EditElderlyDescriptor descriptor = new EditElderlyDescriptorBuilder(editedElderly).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ELDERLY_SUCCESS, editedElderly);

        Model expectedModel = new ModelManager(new AddressBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(model.getFilteredElderlyList().get(0), editedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastElderly = Index.fromOneBased(model.getFilteredElderlyList().size());
        Elderly lastElderly = model.getFilteredElderlyList().get(indexLastElderly.getZeroBased());

        ElderlyBuilder elderlyInList = new ElderlyBuilder(lastElderly);
        Elderly editedElderly = elderlyInList.withName(CommandTestUtil.VALID_NAME_BOB).withPhone(CommandTestUtil.VALID_NOK_PHONE_BOB)
                .withAge(CommandTestUtil.VALID_AGE_BOB).withGender(CommandTestUtil.VALID_GENDER_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();

        EditCommand.EditElderlyDescriptor descriptor = new EditElderlyDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB)
                .withPhone(CommandTestUtil.VALID_NOK_PHONE_BOB).withAge(CommandTestUtil.VALID_AGE_BOB).withGender(CommandTestUtil.VALID_GENDER_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastElderly, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ELDERLY_SUCCESS, editedElderly);

        Model expectedModel = new ModelManager(new AddressBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(lastElderly, editedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST, new EditCommand.EditElderlyDescriptor());
        Elderly editedElderly = model.getFilteredElderlyList().get(TypicalIndexes.INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ELDERLY_SUCCESS, editedElderly);

        Model expectedModel = new ModelManager(new AddressBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        CommandTestUtil.showElderlyAtIndex(model, TypicalIndexes.INDEX_FIRST);

        Elderly elderlyInFilteredList = model.getFilteredElderlyList().get(TypicalIndexes.INDEX_FIRST.getZeroBased());
        Elderly editedElderly = new ElderlyBuilder(elderlyInFilteredList).withName(CommandTestUtil.VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST,
                new EditElderlyDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB).build());
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ELDERLY_SUCCESS, editedElderly);

        Model expectedModel = new ModelManager(new AddressBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(model.getFilteredElderlyList().get(TypicalIndexes.INDEX_FIRST.getZeroBased()), editedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateElderlyUnfilteredList_failure() {
        Elderly firstElderly = model.getFilteredElderlyList().get(TypicalIndexes.INDEX_FIRST.getZeroBased());
        EditCommand.EditElderlyDescriptor descriptor = new EditElderlyDescriptorBuilder(firstElderly).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_SECOND, descriptor);

        CommandTestUtil.assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_ELDERLY);
    }

    @Test
    public void execute_duplicateElderlyFilteredList_failure() {
        CommandTestUtil.showElderlyAtIndex(model, TypicalIndexes.INDEX_FIRST);

        // edit elderly in filtered list into a duplicate in the address book
        Elderly elderlyInList = model.getVersionedNurseyBook().getElderlyList().get(TypicalIndexes.INDEX_SECOND.getZeroBased());
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST,
                new EditElderlyDescriptorBuilder(elderlyInList).build());
        CommandTestUtil.assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_ELDERLY);
    }

    @Test
    public void execute_invalidElderlyIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredElderlyList().size() + 1);
        EditCommand.EditElderlyDescriptor descriptor = new EditElderlyDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        CommandTestUtil.assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
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

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditElderlyDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB).build());

        CommandTestUtil.assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(TypicalIndexes.INDEX_FIRST, CommandTestUtil.DESC_AMY);

        // same values -> returns true
        EditCommand.EditElderlyDescriptor copyDescriptor = new EditCommand.EditElderlyDescriptor(CommandTestUtil.DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(TypicalIndexes.INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(TypicalIndexes.INDEX_SECOND, CommandTestUtil.DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(TypicalIndexes.INDEX_FIRST, CommandTestUtil.DESC_BOB)));
    }

}
