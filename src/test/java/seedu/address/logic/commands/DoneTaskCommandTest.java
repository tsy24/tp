package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalTasks.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;

public class DoneTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Task taskToMark = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        DoneTaskCommand doneTaskCommand = new DoneTaskCommand(INDEX_FIRST);

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

        assertCommandFailure(doneTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_alreadyMarkedTask_throwsCommandException() {
        Task taskToMark = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        model.markTaskAsDone(taskToMark);

        DoneTaskCommand doneTaskCommand = new DoneTaskCommand(INDEX_FIRST);
        assertCommandFailure(doneTaskCommand, model, DoneTaskCommand.MESSAGE_TASK_ALREADY_DONE);
    }
}
