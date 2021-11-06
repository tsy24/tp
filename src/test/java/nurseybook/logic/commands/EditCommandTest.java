package nurseybook.logic.commands;

import static nurseybook.commons.core.Messages.MESSAGE_DUPLICATE_ELDERLY;
import static nurseybook.commons.core.Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX;
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
import static nurseybook.logic.commands.TaskCommandTestUtil.showTaskAtIndex;
import static nurseybook.logic.commands.EditCommand.MESSAGE_EDIT_ELDERLY_SUCCESS;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DATE_JAN;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DESC_COVID;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_NAME_ALICE;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_TIME_SEVENPM;
import static nurseybook.testutil.TypicalElderlies.getTypicalElderlyBuilders;
import static nurseybook.testutil.TypicalElderlies.getTypicalNurseyBook;
import static nurseybook.testutil.TypicalIndexes.INDEX_FIRST;
import static nurseybook.testutil.TypicalIndexes.INDEX_SECOND;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.index.Index;
import nurseybook.logic.commands.exceptions.CommandException;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.NurseyBook;
import nurseybook.model.UserPrefs;
import nurseybook.model.person.Elderly;
import nurseybook.model.task.Recurrence;
import nurseybook.model.task.Task;
import nurseybook.testutil.EditElderlyDescriptorBuilder;
import nurseybook.testutil.ElderlyBuilder;
import nurseybook.testutil.TaskBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalNurseyBook(), new UserPrefs());
    //this can only be used for invalid tests as any modifications to the static final tasks/elderlies would affect
    //other tests

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        NurseyBook nb = new NurseyBook();
        nb.setElderlies(getTypicalElderlyBuilders().stream().map(s -> s.build()).collect(Collectors.toList()));
        Model model = new ModelManager(nb, new UserPrefs());
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
        NurseyBook nb = new NurseyBook();
        nb.setElderlies(getTypicalElderlyBuilders().stream().map(s -> s.build()).collect(Collectors.toList()));
        Model model = new ModelManager(nb, new UserPrefs());

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
        NurseyBook nb = new NurseyBook();
        nb.setElderlies(getTypicalElderlyBuilders().stream().map(s -> s.build()).collect(Collectors.toList()));
        Model model = new ModelManager(nb, new UserPrefs());

        showElderlyAtIndex(model, INDEX_FIRST);

        Elderly elderlyInFilteredList = model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased());
        Elderly editedElderly = new ElderlyBuilder(elderlyInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST,
                new EditElderlyDescriptorBuilder().withName(VALID_NAME_BOB).build());
        String expectedMessage = String.format(MESSAGE_EDIT_ELDERLY_SUCCESS, editedElderly);

        Model expectedModel = new ModelManager(new NurseyBook(model.getVersionedNurseyBook()), new UserPrefs());
        showElderlyAtIndex(expectedModel, INDEX_FIRST);
        expectedModel.setElderly(expectedModel.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased()), editedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editElderlyNameCaseChange_success() {
        showElderlyAtIndex(model, INDEX_FIRST);

        Elderly elderlyInFilteredList = model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased());
        Elderly editedElderly = new ElderlyBuilder(elderlyInFilteredList)
                .withName(VALID_NAME_ALICE.toLowerCase()).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST,
                new EditElderlyDescriptorBuilder().withName(VALID_NAME_ALICE.toLowerCase()).build());
        String expectedMessage = String.format(MESSAGE_EDIT_ELDERLY_SUCCESS, editedElderly);

        Model expectedModel = new ModelManager(new NurseyBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setElderly(model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased()), editedElderly);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editAllInstancesOfElderlyWithNameInTasks_success() throws CommandException {
        NurseyBook nb = new NurseyBook();
        nb.setElderlies(getTypicalElderlyBuilders().stream().map(s -> s.build()).collect(Collectors.toList()));
        Model model = new ModelManager(nb, new UserPrefs());
        // there is some issue with state when using the static final task when running all the tests
        // together, so just leave the taskbuilder here- otherwise the test fails
        Task t = new TaskBuilder().withDesc(VALID_DESC_COVID)
                .withDateTime(VALID_DATE_JAN, VALID_TIME_SEVENPM).withNames(VALID_NAME_ALICE)
                .withRecurrence(Recurrence.RecurrenceType.NONE.name()).build();
        model.addTask(t);
        Elderly editedElderly = new ElderlyBuilder(nb.getElderlyList().get(INDEX_FIRST.getZeroBased()))
                .withName("Alex Yeoh").build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST, new EditElderlyDescriptorBuilder(editedElderly).build());
        editCommand.execute(model);

        Task taskInFilteredList = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        assertTrue(taskInFilteredList.getRelatedNames().contains(editedElderly.getName()));
    }

    @Test
    public void execute_duplicateElderlyUnfilteredList_failure() {
        //name case all the same
        Elderly firstElderly = model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased());
        EditCommand.EditElderlyDescriptor descriptor = new EditElderlyDescriptorBuilder(firstElderly).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editCommand, model, MESSAGE_DUPLICATE_ELDERLY);

        //name case different
        descriptor = new EditElderlyDescriptorBuilder(firstElderly)
                .withName("alIcE pauline").build(); //first elderly is Alice Pauline
        editCommand = new EditCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editCommand, model, MESSAGE_DUPLICATE_ELDERLY);
    }

    @Test
    public void execute_duplicateElderlyFilteredList_failure() {
        // edit elderly in filtered list into a duplicate in the nursey book
        Elderly elderlyInList = model.getFilteredElderlyList().get(INDEX_SECOND.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST,
                new EditElderlyDescriptorBuilder(elderlyInList).build());
        assertCommandFailure(editCommand, model, MESSAGE_DUPLICATE_ELDERLY);

        // still editing into duplicate but name casing is different
        editCommand = new EditCommand(INDEX_FIRST,
                new EditElderlyDescriptorBuilder(elderlyInList).withName("benson meier").build());
        assertCommandFailure(editCommand, model, MESSAGE_DUPLICATE_ELDERLY);
    }

    @Test
    public void execute_invalidElderlyIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredElderlyList().size() + 1);
        EditCommand.EditElderlyDescriptor descriptor = new EditElderlyDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
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

        assertCommandFailure(editCommand, model, MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
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
        Elderly elderlyInList = model.getFilteredElderlyList().get(INDEX_FIRST.getZeroBased());
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
