package nurseybook.logic.commands;

import static nurseybook.commons.core.Messages.MESSAGE_DUPLICATE_ELDERLY;
import static nurseybook.commons.core.Messages.MESSAGE_NO_CHANGES;
import static nurseybook.logic.commands.CommandTestUtil.DESC_AMY;
import static nurseybook.logic.commands.CommandTestUtil.DESC_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_AGE_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_PHONE_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandFailure;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nurseybook.logic.commands.CommandTestUtil.showElderlyAtIndex;
import static nurseybook.logic.commands.EditCommand.MESSAGE_EDIT_ELDERLY_SUCCESS;
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
import nurseybook.testutil.EditElderlyDescriptorBuilder;
import nurseybook.testutil.ElderlyBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalNurseyBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Elderly editedElderly = new ElderlyBuilder().build();
        EditCommand.EditElderlyDescriptor descriptor = new EditElderlyDescriptorBuilder(editedElderly).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(MESSAGE_EDIT_ELDERLY_SUCCESS, editedElderly);

        Model expectedModel = new ModelManager(new NurseyBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(model.getFilteredElderlyList().get(0), editedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastElderly = Index.fromOneBased(model.getFilteredElderlyList().size());
        Elderly lastElderly = model.getFilteredElderlyList().get(indexLastElderly.getZeroBased());

        ElderlyBuilder elderlyInList = new ElderlyBuilder(lastElderly);
        Elderly editedElderly = elderlyInList.withName(VALID_NAME_BOB).withPhone(VALID_NOK_PHONE_BOB)
                .withAge(VALID_AGE_BOB).withGender(VALID_GENDER_BOB).withTags(VALID_TAG_HUSBAND).build();

        EditCommand.EditElderlyDescriptor descriptor = new EditElderlyDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_NOK_PHONE_BOB).withAge(VALID_AGE_BOB).withGender(VALID_GENDER_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastElderly, descriptor);

        String expectedMessage = String.format(MESSAGE_EDIT_ELDERLY_SUCCESS, editedElderly);

        Model expectedModel = new ModelManager(new NurseyBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(lastElderly, editedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showElderlyAtIndex(model, INDEX_FIRST);

        Elderly elderlyInFilteredList = model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased());
        Elderly editedElderly = new ElderlyBuilder(elderlyInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST,
                new EditElderlyDescriptorBuilder().withName(VALID_NAME_BOB).build());
        String expectedMessage = String.format(MESSAGE_EDIT_ELDERLY_SUCCESS, editedElderly);

        Model expectedModel = new ModelManager(new NurseyBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased()), editedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateElderlyUnfilteredList_failure() {
        Elderly firstElderly = model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased());
        EditCommand.EditElderlyDescriptor descriptor = new EditElderlyDescriptorBuilder(firstElderly).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editCommand, model, MESSAGE_DUPLICATE_ELDERLY);
    }

    @Test
    public void execute_duplicateElderlyFilteredList_failure() {
        showElderlyAtIndex(model, INDEX_FIRST);

        // edit elderly in filtered list into a duplicate in the nursey book
        Elderly elderlyInList = model.getVersionedNurseyBook().getElderlyList().get(INDEX_SECOND.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST,
                new EditElderlyDescriptorBuilder(elderlyInList).build());
        assertCommandFailure(editCommand, model, MESSAGE_DUPLICATE_ELDERLY);
    }

    @Test
    public void execute_invalidElderlyIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredElderlyList().size() + 1);
        EditCommand.EditElderlyDescriptor descriptor = new EditElderlyDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of nursey book
     */
    @Test
    public void execute_invalidElderlyIndexFilteredList_failure() {
        showElderlyAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of nursey book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getVersionedNurseyBook().getElderlyList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditElderlyDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_sameFieldsUnfilteredList_failure() {
        Elderly firstElderly = model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased());
        EditCommand.EditElderlyDescriptor descriptor = new EditElderlyDescriptorBuilder(firstElderly).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST, descriptor);

        assertCommandFailure(editCommand, model, MESSAGE_NO_CHANGES);
    }

    @Test
    public void execute_sameFieldsFilteredList_failure() {
        showElderlyAtIndex(model, INDEX_FIRST);
        Elderly elderlyInList = model.getVersionedNurseyBook().getElderlyList().get(INDEX_FIRST.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST,
                new EditElderlyDescriptorBuilder(elderlyInList).build());
        assertCommandFailure(editCommand, model, MESSAGE_NO_CHANGES);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST, DESC_AMY);

        // same values -> returns true
        EditCommand.EditElderlyDescriptor copyDescriptor = new EditCommand.EditElderlyDescriptor(DESC_AMY);
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
