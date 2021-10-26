package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.ALEX_INSULIN;
import static seedu.address.testutil.TypicalTasks.APPLY_LEAVE;
import static seedu.address.testutil.TypicalTasks.APPLY_LEAVE_LATE_TIME;
import static seedu.address.testutil.TypicalTasks.APPLY_LEAVE_MONTH_RECURRENCE;
import static seedu.address.testutil.TypicalTasks.APPLY_LEAVE_WEEK_RECURRENCE;
import static seedu.address.testutil.TypicalTasks.DO_PAPERWORK;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.task.exceptions.TaskNotFoundException;

public class TaskListTest {
    private final TaskList taskList = new TaskList();

    @Test
    public void contains_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.contains(null));
    }

    @Test
    public void has_taskNotInList_returnsFalse() {
        assertFalse(taskList.contains(APPLY_LEAVE));
    }

    @Test
    public void contains_taskInList_returnsTrue() {
        taskList.add(APPLY_LEAVE);
        assertTrue(taskList.contains(APPLY_LEAVE));
    }

    @Test
    public void add_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.add(null));
    }

    @Test
    public void remove_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.remove(null));
    }

    @Test
    public void remove_taskDoesNotExist_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> taskList.remove(DO_PAPERWORK));
    }

    @Test
    public void remove_existingTask_removesTask() {
        taskList.add(DO_PAPERWORK);
        taskList.remove(DO_PAPERWORK);
        TaskList expectedTaskList = new TaskList();
        assertEquals(expectedTaskList, taskList);
    }

    @Test
    public void mark_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.markTaskAsDone(null));
        assertThrows(NullPointerException.class, () -> taskList.markTaskAsOverdue(null));
    }

    @Test
    public void mark_taskDoesNotExist_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> taskList.markTaskAsDone(DO_PAPERWORK));
        assertThrows(TaskNotFoundException.class, () -> taskList.markTaskAsOverdue(DO_PAPERWORK));
    }

    @Test
    public void setTasks_nullTaskList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.setTasks((TaskList) null));
    }

    @Test
    public void setTasks_taskList_replacesOwnListWithProvidedTaskList() {
        taskList.add(APPLY_LEAVE);
        TaskList expectedTaskList = new TaskList();
        expectedTaskList.add(DO_PAPERWORK);
        taskList.setTasks(expectedTaskList);
        assertEquals(expectedTaskList, taskList);
    }

    @Test
    public void setTasks_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.setTasks((List<Task>) null));
    }

    @Test
    public void setTasks_list_replacesOwnListWithProvidedList() {
        taskList.add(APPLY_LEAVE);
        List<Task> tList = Collections.singletonList(DO_PAPERWORK);
        taskList.setTasks(tList);
        TaskList expectedTaskList = new TaskList();
        expectedTaskList.add(DO_PAPERWORK);
        assertEquals(expectedTaskList, taskList);
    }

    @Test
    public void sortsAddedTasks_byDateTime() {
        taskList.add(ALEX_INSULIN); // date: "2022-01-31", time: "19:45"
        taskList.add(DO_PAPERWORK); // date: "2022-01-31", time: "10:20"
        taskList.add(APPLY_LEAVE); // date: "2021-10-01", time: "00:00"
        TaskList expectedTaskList = new TaskList();
        expectedTaskList.add(APPLY_LEAVE);
        expectedTaskList.add(DO_PAPERWORK);
        expectedTaskList.add(ALEX_INSULIN);
        assertEquals(expectedTaskList, taskList);
    }
}
