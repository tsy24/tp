package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.task.Task;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.TaskBuilder;

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

        assertEquals(String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
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
     * A Model stub that always accept the task being added.
     */
    private class ModelStubAcceptingTaskAdded extends ModelStub {
        final ArrayList<Task> tasksAdded = new ArrayList<>();

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
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
