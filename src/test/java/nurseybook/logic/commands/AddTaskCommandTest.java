package nurseybook.logic.commands;

import static java.util.Objects.requireNonNull;
import static nurseybook.commons.core.Messages.MESSAGE_DUPLICATE_TASK;
import static nurseybook.commons.core.Messages.MESSAGE_NO_SUCH_ELDERLY;
import static nurseybook.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import nurseybook.logic.commands.exceptions.CommandException;
import nurseybook.model.NurseyBook;
import nurseybook.model.ReadOnlyNurseyBook;
import nurseybook.model.VersionedNurseyBook;
import nurseybook.model.task.Task;
import nurseybook.testutil.ModelStub;
import nurseybook.testutil.NurseyBookBuilder;
import nurseybook.testutil.TaskBuilder;

public class AddTaskCommandTest {
    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTaskCommand(null));
    }

    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().build();

        CommandResult commandResult = new AddTaskCommand(validTask).execute(modelStub);
        CommandResult expectedCommand = new CommandResult(String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask),
                CommandResult.ListDisplayChange.TASK);

        assertEquals(expectedCommand, commandResult);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() {
        Task validTask = new TaskBuilder().build();
        AddTaskCommand addTaskCommand = new AddTaskCommand(validTask);
        ModelStub modelStub = new ModelStubWithTask(validTask);
        assertThrows(CommandException.class, MESSAGE_DUPLICATE_TASK, () -> addTaskCommand.execute(modelStub));
    }

    @Test
    public void execute_elderlyNotInNurseyBook_throwsCommandException() {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().withNames("Alex Yeoh").build();
        AddTaskCommand addTaskCommand = new AddTaskCommand(validTask);
        assertThrows(CommandException.class, MESSAGE_NO_SUCH_ELDERLY, () -> addTaskCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Task insulin = new TaskBuilder().withDesc("Give insulin shots").build();
        Task leave = new TaskBuilder().withDesc("Apply leave").build();
        AddTaskCommand addInsulinTask = new AddTaskCommand(insulin);
        AddTaskCommand addLeaveTask = new AddTaskCommand(leave);

        // same object -> returns true
        assertTrue(addInsulinTask.equals(addInsulinTask));

        // same values -> returns true
        AddTaskCommand addInsulinCopy = new AddTaskCommand(insulin);
        assertTrue(addInsulinTask.equals(addInsulinCopy));

        // different types -> returns false
        assertFalse(addInsulinTask.equals(1));

        // null -> returns false
        assertFalse(addInsulinTask.equals(null));

        // different description -> returns false
        assertFalse(addInsulinTask.equals(addLeaveTask));
    }

    /**
     * A Model stub that contains a single task.
     */
    private class ModelStubWithTask extends ModelStub {
        private final Task task;

        ModelStubWithTask(Task task) {
            requireNonNull(task);
            this.task = task;
        }

        @Override
        public boolean hasTask(Task task) {
            requireNonNull(task);
            return this.task.isSameTask(task);
        }
    }

    /**
     * A Model stub that always accept the task being added.
     */
    private class ModelStubAcceptingTaskAdded extends ModelStub {
        final ArrayList<Task> tasksAdded = new ArrayList<>();
        final VersionedNurseyBook versionedNurseyBook = new VersionedNurseyBook(new NurseyBookBuilder().build());

        @Override
        public boolean hasTask(Task t) {
            requireNonNull(t);
            return tasksAdded.contains(t);
        }

        @Override
        public void addTask(Task t) {
            requireNonNull(t);
            tasksAdded.add(t);
        }

        @Override
        public void commitNurseyBook(CommandResult commandResult) {
            versionedNurseyBook.commit(commandResult);
        }

        @Override
        public ReadOnlyNurseyBook getVersionedNurseyBook() {
            return new NurseyBook();
        }
    }

}
