package nurseybook.logic.commands;

import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.Messages;
import nurseybook.commons.core.index.Index;
import nurseybook.model.task.Task;
import nurseybook.testutil.TypicalIndexes;
import nurseybook.testutil.TypicalTasks;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.UserPrefs;

public class DoneTaskCommandTest {

    private Model model = new ModelManager(TypicalTasks.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Task taskToMark = model.getFilteredTaskList().get(TypicalIndexes.INDEX_FIRST.getZeroBased());
        DoneTaskCommand doneTaskCommand = new DoneTaskCommand(TypicalIndexes.INDEX_FIRST);

        String expectedMessage = String.format(DoneTaskCommand.MESSAGE_MARK_TASK_DONE_SUCCESS, taskToMark);

        ModelManager expectedModel = new ModelManager(model.getVersionedNurseyBook(), new UserPrefs());
        expectedModel.markTaskAsDone(taskToMark);
        expectedModel.commitNurseyBook(new CommandResult(expectedMessage));

        assertCommandSuccess(doneTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DoneTaskCommand doneTaskCommand = new DoneTaskCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(doneTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_alreadyMarkedTask_throwsCommandException() {
        Task taskToMark = model.getFilteredTaskList().get(TypicalIndexes.INDEX_FIRST.getZeroBased());
        model.markTaskAsDone(taskToMark);

        DoneTaskCommand doneTaskCommand = new DoneTaskCommand(TypicalIndexes.INDEX_FIRST);
        CommandTestUtil.assertCommandFailure(doneTaskCommand, model, DoneTaskCommand.MESSAGE_TASK_ALREADY_DONE);
    }
}
