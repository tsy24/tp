package nurseybook.model.task;

import static nurseybook.testutil.Assert.assertThrows;
import static nurseybook.testutil.TypicalTasks.ALICE_INSULIN;
import static nurseybook.testutil.TypicalTasks.APPLY_LEAVE;
import static nurseybook.testutil.TypicalTasks.APPLY_LEAVE_DAY_NEXT_RECURRENCE_GHOST;
import static nurseybook.testutil.TypicalTasks.APPLY_LEAVE_LATE_TIME;
import static nurseybook.testutil.TypicalTasks.APPLY_LEAVE_MONTH_NEXT_RECURRENCE_GHOST;
import static nurseybook.testutil.TypicalTasks.APPLY_LEAVE_WEEK_NEXT_RECURRENCE_GHOST;
import static nurseybook.testutil.TypicalTasks.DO_PAPERWORK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import nurseybook.model.person.Name;
import nurseybook.model.task.exceptions.TaskNotFoundException;
import nurseybook.testutil.TaskBuilder;

public class UniqueTaskListTest {
    private final UniqueTaskList uniqueTaskList = new UniqueTaskList();

    @Test
    public void contains_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.contains(null));
    }

    @Test
    public void has_taskNotInList_returnsFalse() {
        assertFalse(uniqueTaskList.contains(APPLY_LEAVE));
    }

    @Test
    public void contains_taskInList_returnsTrue() {
        uniqueTaskList.add(APPLY_LEAVE);
        assertTrue(uniqueTaskList.contains(APPLY_LEAVE));
    }

    @Test
    public void add_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.add(null));
    }

    @Test
    public void remove_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.remove(null));
    }

    @Test
    public void remove_taskDoesNotExist_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> uniqueTaskList.remove(DO_PAPERWORK));
    }

    @Test
    public void remove_existingTask_removesTask() {
        uniqueTaskList.add(DO_PAPERWORK);
        uniqueTaskList.remove(DO_PAPERWORK);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void mark_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.markTaskAsDone(null));
        assertThrows(NullPointerException.class, () -> uniqueTaskList.markTaskAsOverdue(null));
        assertThrows(NullPointerException.class, () -> uniqueTaskList.markTaskAsNotOverdue(null));
    }

    @Test
    public void mark_taskDoesNotExist_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> uniqueTaskList.markTaskAsDone(DO_PAPERWORK));
        assertThrows(TaskNotFoundException.class, () -> uniqueTaskList.markTaskAsOverdue(DO_PAPERWORK));
        assertThrows(TaskNotFoundException.class, () -> uniqueTaskList.markTaskAsNotOverdue(DO_PAPERWORK));
    }

    @Test
    public void setTasks_nullTaskList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setTasks((UniqueTaskList) null));
    }

    @Test
    public void setTasks_taskList_replacesOwnListWithProvidedTaskList() {
        uniqueTaskList.add(APPLY_LEAVE);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(DO_PAPERWORK);
        uniqueTaskList.setTasks(expectedUniqueTaskList);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setTasks_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setTasks((List<Task>) null));
    }

    @Test
    public void setTasks_list_replacesOwnListWithProvidedList() {
        uniqueTaskList.add(APPLY_LEAVE);
        List<Task> tList = Collections.singletonList(DO_PAPERWORK);
        uniqueTaskList.setTasks(tList);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(DO_PAPERWORK);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void updateDateOfRecurringTask_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.updateDateOfRecurringTask(null));
    }

    @Test
    public void updateDateOfRecurringTask_taskList_updatesPastDateWithNewDate() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Task t = APPLY_LEAVE_LATE_TIME;
        uniqueTaskList.add(APPLY_LEAVE_LATE_TIME);
        uniqueTaskList.updateDateOfRecurringTask(t);
        Task test = new TaskBuilder(APPLY_LEAVE_LATE_TIME)
                        .withDateTime(currentDateTime.toLocalDate().toString(),
                            t.getTime().toString()).withStatus("false", "false").build();
        assertTrue(uniqueTaskList.contains(test));
    }

    @Test
    public void deleteGhostTasks_deletesAllGhostTasks() {
        UniqueTaskList uniqueTaskList = new UniqueTaskList();
        uniqueTaskList.add(APPLY_LEAVE_DAY_NEXT_RECURRENCE_GHOST);
        uniqueTaskList.add(APPLY_LEAVE);
        uniqueTaskList.add(APPLY_LEAVE_WEEK_NEXT_RECURRENCE_GHOST);
        uniqueTaskList.add(APPLY_LEAVE_MONTH_NEXT_RECURRENCE_GHOST);
        uniqueTaskList.deleteGhostTasks();

        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(APPLY_LEAVE);

        assertEquals(uniqueTaskList, expectedUniqueTaskList);
    }

    @Test
    public void addPossibleGhostTasksWithMatchingDate_forDayRecurring() {
        Set<Name> nameSet = new HashSet<>();
        LocalDate today = LocalDate.now();
        LocalDate keyDate = LocalDate.now().plusDays(1);
        RealTask testTask = new RealTask(new Description("Task"), new DateTime(today, LocalTime.now()),
                nameSet, new Recurrence("DAY"));

        Task nextTaskRecurrence = testTask.copyToGhostTask();
        nextTaskRecurrence.setDate(keyDate); //1 day after today

        UniqueTaskList taskList = new UniqueTaskList();
        taskList.add(testTask);
        taskList.addPossibleGhostTasksWithMatchingDate(keyDate);

        UniqueTaskList expectedTaskList = new UniqueTaskList();
        expectedTaskList.add(testTask);
        expectedTaskList.add(nextTaskRecurrence);

        assertEquals(taskList, expectedTaskList);
    }

    @Test
    public void addPossibleGhostTasksWithMatchingDate_forWeekRecurring() {
        Set<Name> nameSet = new HashSet<>();
        LocalDate today = LocalDate.now();
        LocalDate keyDate = LocalDate.now().plusDays(7);
        RealTask testTask = new RealTask(new Description("Task"), new DateTime(today, LocalTime.now()),
                nameSet, new Recurrence("WEEK"));

        Task nextTaskRecurrence = testTask.copyToGhostTask();
        nextTaskRecurrence.setDate(keyDate); //7 days after today

        UniqueTaskList taskList = new UniqueTaskList();
        taskList.add(testTask);
        taskList.addPossibleGhostTasksWithMatchingDate(keyDate);

        UniqueTaskList expectedTaskList = new UniqueTaskList();
        expectedTaskList.add(testTask);
        expectedTaskList.add(nextTaskRecurrence);

        assertEquals(taskList, expectedTaskList);
    }

    @Test
    public void addPossibleGhostTasksWithMatchingDate_forMonthRecurring() {
        Set<Name> nameSet = new HashSet<>();
        LocalDate today = LocalDate.now();
        LocalDate keyDate = LocalDate.now().plusDays(28);
        RealTask testTask = new RealTask(new Description("Task"), new DateTime(today, LocalTime.now()),
                nameSet, new Recurrence("MONTH"));

        Task nextTaskRecurrence = testTask.copyToGhostTask();
        nextTaskRecurrence.setDate(keyDate); //28 days after today

        UniqueTaskList taskList = new UniqueTaskList();
        taskList.add(testTask);
        taskList.addPossibleGhostTasksWithMatchingDate(keyDate);

        UniqueTaskList expectedTaskList = new UniqueTaskList();
        expectedTaskList.add(testTask);
        expectedTaskList.add(nextTaskRecurrence);

        assertEquals(taskList, expectedTaskList);
    }

    @Test
    public void addPossibleGhostTasksWithMatchingDate_withNoMatchingDate() {
        Set<Name> nameSet = new HashSet<>();
        LocalDate today = LocalDate.now();
        LocalDate keyDate = LocalDate.now().plusDays(70);
        RealTask testTask = new RealTask(new Description("Task"), new DateTime(today, LocalTime.now()),
                nameSet, new Recurrence("MONTH"));

        Task taskOutOfBounds = testTask.copyToGhostTask();
        taskOutOfBounds.setDate(LocalDate.now().plusDays(70)); //70 days after today, out of recurrence dates


        UniqueTaskList taskList = new UniqueTaskList();
        taskList.add(testTask);
        taskList.addPossibleGhostTasksWithMatchingDate(keyDate);

        UniqueTaskList expectedTaskList = new UniqueTaskList();
        expectedTaskList.add(testTask);

        assertEquals(taskList, expectedTaskList);
    }
}
