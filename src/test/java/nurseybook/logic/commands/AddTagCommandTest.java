package nurseybook.logic.commands;

import static nurseybook.logic.commands.CommandTestUtil.SET_ONE_TAG;
import static nurseybook.logic.commands.CommandTestUtil.SET_TWO_TAGS;
import static nurseybook.logic.commands.CommandTestUtil.VALID_TAG_DIABETES;
import static nurseybook.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nurseybook.testutil.TypicalElderlies.getTypicalNurseyBook;
import static nurseybook.testutil.TypicalIndexes.INDEX_FIRST;
import static nurseybook.testutil.TypicalIndexes.INDEX_SECOND;
import static nurseybook.testutil.TypicalIndexes.INDEX_THIRD;
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
import nurseybook.testutil.ElderlyBuilder;
public class AddTagCommandTest {

    private Model model = new ModelManager(getTypicalNurseyBook(), new UserPrefs());

    @Test
    public void execute_addTagUnfilteredList_success() {
        Elderly firstElderly = model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased());
        Elderly tagAddedElderly = new ElderlyBuilder(firstElderly)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_DIABETES).build();

        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST, SET_ONE_TAG);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, tagAddedElderly);

        Model expectedModel = new ModelManager(new NurseyBook(model.getVersionedNurseyBook()), new UserPrefs());
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

        Model expectedModel = new ModelManager(new NurseyBook(model.getVersionedNurseyBook()), new UserPrefs());
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

        Model expectedModel = new ModelManager(new NurseyBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(firstElderly, tagAddedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addTagFilteredList_success() {
        CommandTestUtil.showElderlyAtIndex(model, INDEX_FIRST);

        Elderly firstElderly = model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased());
        Elderly tagAddedElderly = new ElderlyBuilder(model.getFilteredElderlyList()
                .get(INDEX_FIRST.getZeroBased())).withTags(VALID_TAG_FRIEND, VALID_TAG_DIABETES).build();

        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST, SET_ONE_TAG);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, tagAddedElderly);

        Model expectedModel = new ModelManager(new NurseyBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(firstElderly, tagAddedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidElderlyIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredElderlyList().size() + 1);
        AddTagCommand addTagCommand = new AddTagCommand(outOfBoundIndex, SET_ONE_TAG);

        CommandTestUtil.assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
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

        AddTagCommand addTagCommand = new AddTagCommand(outOfBoundIndex, SET_ONE_TAG);

        CommandTestUtil.assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final AddTagCommand standardCommand = new AddTagCommand(INDEX_FIRST, SET_ONE_TAG);
        // same values -> returns true
        AddTagCommand commandWithSameValues = new AddTagCommand(INDEX_FIRST, SET_ONE_TAG);
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new AddTagCommand(INDEX_SECOND, SET_ONE_TAG)));
        // different set of tags -> returns false
        assertFalse(standardCommand.equals(new AddTagCommand(INDEX_FIRST, SET_TWO_TAGS)));
    }
}
