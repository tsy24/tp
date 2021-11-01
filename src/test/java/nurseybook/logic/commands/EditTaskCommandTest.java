package nurseybook.logic.commands;

import static nurseybook.logic.commands.CommandTestUtil.assertCommandFailure;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nurseybook.logic.commands.TaskCommandTestUtil.PAPERWORK_TASK;
import static nurseybook.logic.commands.TaskCommandTestUtil.VACCINE_TASK;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DESC_MEDICINE;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DESC_VACCINE;
import static nurseybook.logic.commands.TaskCommandTestUtil.showTaskAtIndex;
import static nurseybook.testutil.TypicalIndexes.INDEX_FIRST;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.Messages;
import nurseybook.commons.core.index.Index;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.NurseyBook;
import nurseybook.model.UserPrefs;
import nurseybook.model.task.Task;
import nurseybook.testutil.EditTaskDescriptorBuilder;
import nurseybook.testutil.TaskBuilder;
import nurseybook.testutil.TypicalIndexes;
import nurseybook.testutil.TypicalTasks;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditTaskCommand.
 */
public class EditTaskCommandTest {

    private Model model = new ModelManager(TypicalTasks.getTypicalNurseyBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Task editedTask = new TaskBuilder().build();
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new NurseyBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setTask(model.getFilteredTaskList().get(0), editedTask);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastTask = Index.fromOneBased(model.getFilteredTaskList().size());
        Task lastTask = model.getFilteredTaskList().get(indexLastTask.getZeroBased());

        TaskBuilder taskInList = new TaskBuilder(lastTask);
        // Names and recurrence field not specified
        Task editedTask = taskInList
                .build();

        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(indexLastTask, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new NurseyBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setTask(lastTask, editedTask);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST, new EditTaskCommand.EditTaskDescriptor());
        Task editedTask = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new NurseyBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showTaskAtIndex(model, INDEX_FIRST);

        Task taskInFilteredList = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        Task editedTask = new TaskBuilder(taskInFilteredList).withDesc(VALID_DESC_MEDICINE).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST,
                new EditTaskDescriptorBuilder().withDescription(VALID_DESC_MEDICINE).build());
        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new NurseyBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setTask(model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased()), editedTask);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidTaskIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withDescription(VALID_DESC_VACCINE).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of nursey book
     */
    @Test
    public void execute_invalidTaskIndexFilteredList_failure() {
        showTaskAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of nursey book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getVersionedNurseyBook().getTaskList().size());

        EditTaskCommand editTaskCommand = new EditTaskCommand(outOfBoundIndex,
                new EditTaskDescriptorBuilder().withDescription(VALID_DESC_VACCINE).build());

        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditTaskCommand standardCommand = new EditTaskCommand(INDEX_FIRST, VACCINE_TASK);

        // same values -> returns true
        EditTaskCommand.EditTaskDescriptor copyDescriptor = new EditTaskCommand.EditTaskDescriptor(VACCINE_TASK);
        EditTaskCommand commandWithSameValues = new EditTaskCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(TypicalIndexes.INDEX_SECOND, VACCINE_TASK)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_FIRST, PAPERWORK_TASK)));
    }

}

