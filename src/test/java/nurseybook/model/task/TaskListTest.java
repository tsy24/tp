package nurseybook.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static nurseybook.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import nurseybook.model.task.exceptions.TaskNotFoundException;
import nurseybook.testutil.Assert;
import nurseybook.testutil.TaskBuilder;
import nurseybook.testutil.TypicalTasks;

public class TaskListTest {
    private final TaskList taskList = new TaskList();

    @Test
    public void contains_nullTask_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> taskList.contains(null));
    }

    @Test
    public void has_taskNotInList_returnsFalse() {
        assertFalse(taskList.contains(TypicalTasks.APPLY_LEAVE));
    }

    @Test
    public void contains_taskInList_returnsTrue() {
        taskList.add(TypicalTasks.APPLY_LEAVE);
        assertTrue(taskList.contains(TypicalTasks.APPLY_LEAVE));
    }

    @Test
    public void add_nullTask_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> taskList.add(null));
    }

    @Test
    public void remove_nullTask_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> taskList.remove(null));
    }

    @Test
    public void remove_taskDoesNotExist_throwsTaskNotFoundException() {
        Assert.assertThrows(TaskNotFoundException.class, () -> taskList.remove(TypicalTasks.DO_PAPERWORK));
    }

    @Test
    public void remove_existingTask_removesTask() {
        taskList.add(TypicalTasks.DO_PAPERWORK);
        taskList.remove(TypicalTasks.DO_PAPERWORK);
        TaskList expectedTaskList = new TaskList();
        assertEquals(expectedTaskList, taskList);
    }

    @Test
    public void mark_nullTask_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> taskList.markTaskAsDone(null));
        Assert.assertThrows(NullPointerException.class, () -> taskList.markTaskAsOverdue(null));
    }

    @Test
    public void mark_taskDoesNotExist_throwsTaskNotFoundException() {
        Assert.assertThrows(TaskNotFoundException.class, () -> taskList.markTaskAsDone(TypicalTasks.DO_PAPERWORK));
        Assert.assertThrows(TaskNotFoundException.class, () -> taskList.markTaskAsOverdue(TypicalTasks.DO_PAPERWORK));
    }

    @Test
    public void setTasks_nullTaskList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> taskList.setTasks((TaskList) null));
    }

    @Test
    public void setTasks_taskList_replacesOwnListWithProvidedTaskList() {
        taskList.add(TypicalTasks.APPLY_LEAVE);
        TaskList expectedTaskList = new TaskList();
        expectedTaskList.add(TypicalTasks.DO_PAPERWORK);
        taskList.setTasks(expectedTaskList);
        assertEquals(expectedTaskList, taskList);
    }

    @Test
    public void setTasks_nullList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> taskList.setTasks((List<Task>) null));
    }

    @Test
    public void setTasks_list_replacesOwnListWithProvidedList() {
        taskList.add(TypicalTasks.APPLY_LEAVE);
        List<Task> tList = Collections.singletonList(TypicalTasks.DO_PAPERWORK);
        taskList.setTasks(tList);
        TaskList expectedTaskList = new TaskList();
        expectedTaskList.add(TypicalTasks.DO_PAPERWORK);
        assertEquals(expectedTaskList, taskList);
    }

    @Test
    public void sortsAddedTasks_byDateTime() {
        taskList.add(TypicalTasks.ALEX_INSULIN); // date: "2022-01-31", time: "19:45"
        taskList.add(TypicalTasks.DO_PAPERWORK); // date: "2022-01-31", time: "10:20"
        taskList.add(TypicalTasks.APPLY_LEAVE); // date: "2021-10-01", time: "00:00"
        TaskList expectedTaskList = new TaskList();
        expectedTaskList.add(TypicalTasks.APPLY_LEAVE);
        expectedTaskList.add(TypicalTasks.DO_PAPERWORK);
        expectedTaskList.add(TypicalTasks.ALEX_INSULIN);
        assertEquals(expectedTaskList, taskList);
    }

    @Test
    public void updateDateOfRecurringTask_nullTask_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> taskList.updateDateOfRecurringTask(null));
    }

    @Test
    public void updateDateOfRecurringTask_taskList_updatesPastDateWithNewDate() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Task t = TypicalTasks.APPLY_LEAVE_LATE_TIME;
        taskList.add(TypicalTasks.APPLY_LEAVE_LATE_TIME);
        taskList.updateDateOfRecurringTask(t);
        Task test = new TaskBuilder(TypicalTasks.APPLY_LEAVE_LATE_TIME)
                        .withDateTime(currentDateTime.toLocalDate().toString(),
                            t.getTime().toString()).withStatus("false", "false").build();
        assertTrue(taskList.contains(test));
    }

    @Test
    public void deleteGhostTasks_deletesAllGhostTasks() {
        TaskList taskList = new TaskList();
        taskList.add(TypicalTasks.APPLY_LEAVE_DAY_NEXT_RECURRENCE_GHOST);
        taskList.add(TypicalTasks.APPLY_LEAVE);
        taskList.add(TypicalTasks.APPLY_LEAVE_WEEK_NEXT_RECURRENCE_GHOST);
        taskList.add(TypicalTasks.APPLY_LEAVE_MONTH_NEXT_RECURRENCE_GHOST);
        taskList.deleteGhostTasks();

        TaskList expectedTaskList = new TaskList();
        expectedTaskList.add(TypicalTasks.APPLY_LEAVE);

        assertEquals(taskList, expectedTaskList);
    }
}
