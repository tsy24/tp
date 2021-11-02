package nurseybook.logic.commands;

import static nurseybook.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nurseybook.logic.commands.DoneTaskCommand.MESSAGE_MARK_TASK_DONE_SUCCESS;
import static nurseybook.logic.commands.DoneTaskCommand.MESSAGE_TASK_ALREADY_DONE;
import static nurseybook.testutil.TypicalIndexes.INDEX_FIRST;
import static nurseybook.testutil.TypicalTasks.getTypicalNurseyBook;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.index.Index;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.UserPrefs;
import nurseybook.model.task.Task;

public class DoneTaskCommandTest {

    private Model model = new ModelManager(getTypicalNurseyBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Task taskToMark = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        DoneTaskCommand doneTaskCommand = new DoneTaskCommand(INDEX_FIRST);

        String expectedMessage = String.format(MESSAGE_MARK_TASK_DONE_SUCCESS, taskToMark);

        ModelManager expectedModel = new ModelManager(model.getVersionedNurseyBook(), new UserPrefs());
        expectedModel.markTaskAsDone(taskToMark);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(doneTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DoneTaskCommand doneTaskCommand = new DoneTaskCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(doneTaskCommand, model, MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_alreadyMarkedTask_throwsCommandException() {
        Task taskToMark = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        model.markTaskAsDone(taskToMark);

        DoneTaskCommand doneTaskCommand = new DoneTaskCommand(INDEX_FIRST);
        CommandTestUtil.assertCommandFailure(doneTaskCommand, model, MESSAGE_TASK_ALREADY_DONE);
    }
}
