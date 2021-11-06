package nurseybook.logic.commands;

import static nurseybook.commons.core.Messages.MESSAGE_DUPLICATE_TASK;
import static nurseybook.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static nurseybook.commons.core.Messages.MESSAGE_NO_CHANGES;
import static nurseybook.commons.core.Messages.MESSAGE_NO_SUCH_ELDERLY;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandFailure;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nurseybook.logic.commands.EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS;
import static nurseybook.logic.commands.TaskCommandTestUtil.PAPERWORK_TASK;
import static nurseybook.logic.commands.TaskCommandTestUtil.VACCINE_TASK;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DATE_JAN;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DESC_VACCINE;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_TIME_SEVENPM;
import static nurseybook.logic.commands.TaskCommandTestUtil.showTaskAtIndex;
import static nurseybook.testutil.TypicalIndexes.INDEX_FIRST;
import static nurseybook.testutil.TypicalIndexes.INDEX_SECOND;
import static nurseybook.testutil.TypicalTasks.getTypicalNurseyBook;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.index.Index;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.NurseyBook;
import nurseybook.model.UserPrefs;
import nurseybook.model.task.Task;
import nurseybook.testutil.EditTaskDescriptorBuilder;
import nurseybook.testutil.TaskBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditTaskCommand.
 */
public class EditTaskCommandTest {

    private Model model = new ModelManager(getTypicalNurseyBook(), new UserPrefs());


    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        model.setVersionedNurseyBook(getTypicalNurseyBook());

        Task editedTask = new TaskBuilder().build();
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new NurseyBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setTask(model.getFilteredTaskList().get(0), editedTask);
        expectedModel.updateTasksAccordingToTime();
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        model.setVersionedNurseyBook(getTypicalNurseyBook());

        Index indexLastTask = Index.fromOneBased(model.getFilteredTaskList().size());
        Task lastTask = model.getFilteredTaskList().get(indexLastTask.getZeroBased());

        TaskBuilder taskInList = new TaskBuilder(lastTask);
        // Names and recurrence field not specified
        Task editedTask = taskInList.withDesc(VALID_DESC_VACCINE).withDate(VALID_DATE_JAN).withTime(VALID_TIME_SEVENPM)
            .build();

        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(indexLastTask, descriptor);

        String expectedMessage = String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new NurseyBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setTask(lastTask, editedTask);
        expectedModel.updateTasksAccordingToTime();
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        model.setVersionedNurseyBook(getTypicalNurseyBook());

        Task taskInFilteredList = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        Task editedTask = new TaskBuilder(taskInFilteredList).withDesc(VALID_DESC_VACCINE).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST,
                new EditTaskDescriptorBuilder().withDescription(VALID_DESC_VACCINE).build());
        String expectedMessage = String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new NurseyBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setTask(model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased()), editedTask);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTaskUnfilteredList_failure() {
        Task firstTask = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(firstTask).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editTaskCommand, model, MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_duplicateTaskFilteredList_failure() {
        showTaskAtIndex(model, INDEX_FIRST);

        // edit task in filtered list into a duplicate in the nursey book
        Task taskInList = model.getVersionedNurseyBook().getTaskList().get(INDEX_SECOND.getZeroBased());
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST,
                new EditTaskDescriptorBuilder(taskInList).build());
        assertCommandFailure(editTaskCommand, model, MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_invalidTaskIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withDescription(VALID_DESC_VACCINE).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editTaskCommand, model, MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of nursey book
     */
    @Test
    public void execute_invalidTaskIndexFilteredList_failure() {

        showTaskAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of nursey book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getVersionedNurseyBook().getTaskList().size());

        EditTaskCommand editTaskCommand = new EditTaskCommand(outOfBoundIndex,
                new EditTaskDescriptorBuilder().withDescription(VALID_DESC_VACCINE).build());

        assertCommandFailure(editTaskCommand, model, MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_sameFieldsUnfilteredList_failure() {
        Task firstTask = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(firstTask).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST, descriptor);

        assertCommandFailure(editTaskCommand, model, MESSAGE_NO_CHANGES);
    }

    @Test
    public void execute_sameFieldsFilteredList_failure() {
        showTaskAtIndex(model, INDEX_FIRST);
        Task taskInList = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST,
                new EditTaskDescriptorBuilder(taskInList).build());
        assertCommandFailure(editTaskCommand, model, MESSAGE_NO_CHANGES);
    }

    @Test
    public void execute_elderlyNotInNurseyBook_throwsCommandException() {
        showTaskAtIndex(model, INDEX_FIRST);

        Task taskInFilteredList = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        Task editedTask = new TaskBuilder(taskInFilteredList).withNames("Alex Yeoh").build();

        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST,
                new EditTaskDescriptorBuilder(editedTask).build());
        assertCommandFailure(editTaskCommand, model, MESSAGE_NO_SUCH_ELDERLY);
    }

    @Test
    public void execute_elderlyInNurseyBook_executionSuccess() {
        model.setVersionedNurseyBook(getTypicalNurseyBook());

        Task taskInFilteredList = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        Task editedTask = new TaskBuilder(taskInFilteredList).withNames("Carl Kurz").build();

        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST,
                new EditTaskDescriptorBuilder(editedTask).build());
        String expectedMessage = String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        Model expectedModel = new ModelManager(new NurseyBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setTask(taskInFilteredList, editedTask);
        expectedModel.updateTasksAccordingToTime();
        expectedModel.commitNurseyBook(expectedCommandResult);

        assertCommandSuccess(editTaskCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_editDateOfTask_reordersTaskList() {
        model.setVersionedNurseyBook(getTypicalNurseyBook());

        Task firstTaskInFilteredList = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        Task secondTaskInFilteredList = model.getFilteredTaskList().get(INDEX_SECOND.getZeroBased());

        // edits second task to have an earlier date compared to that of first task
        LocalDate date = firstTaskInFilteredList.getDateTime().getDate();
        LocalDate editedDate = date.minusDays(1);
        Task editedTask = new TaskBuilder(secondTaskInFilteredList).withDate(editedDate.toString()).build();

        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_SECOND,
                new EditTaskDescriptorBuilder(editedTask).build());
        String expectedMessage = String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        Model expectedModel = new ModelManager(new NurseyBook(model.getVersionedNurseyBook()), new UserPrefs());
        expectedModel.setTask(secondTaskInFilteredList, editedTask);
        expectedModel.updateTasksAccordingToTime();
        expectedModel.commitNurseyBook(expectedCommandResult);

        assertCommandSuccess(editTaskCommand, model, expectedCommandResult, expectedModel);

        // edits the (initial) second task to have a later date compared to that of the (initial) first task
        secondTaskInFilteredList = editedTask;
        editedDate = date.plusDays(1);
        editedTask = new TaskBuilder(secondTaskInFilteredList).withDate(editedDate.toString()).build();

        editTaskCommand = new EditTaskCommand(INDEX_FIRST,
                new EditTaskDescriptorBuilder(editedTask).build());
        expectedMessage = String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask);
        expectedCommandResult = new CommandResult(expectedMessage);

        expectedModel.setTask(secondTaskInFilteredList, editedTask);
        expectedModel.updateTasksAccordingToTime();
        expectedModel.commitNurseyBook(expectedCommandResult);

        assertCommandSuccess(editTaskCommand, model, expectedCommandResult, expectedModel);
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
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_SECOND, VACCINE_TASK)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_FIRST, PAPERWORK_TASK)));
    }

}

