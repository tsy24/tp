package nurseybook.model.task;

import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DATE_JAN;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DATE_NOV;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DESC_MEDICINE;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DESC_VACCINE;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_NAME_ALEX;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_NAME_KEITH;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_TIME_SEVENPM;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_TIME_TENAM;
import static nurseybook.testutil.Assert.assertThrows;
import static nurseybook.testutil.TypicalTasks.ALEX_INSULIN;
import static nurseybook.testutil.TypicalTasks.DO_PAPERWORK;
import static nurseybook.testutil.TypicalTasks.KEITH_INSULIN;
import static nurseybook.testutil.TypicalTasks.KG_SC_VACCINE;
import static nurseybook.testutil.TypicalTasks.YASMINE_PHYSIO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nurseybook.testutil.TaskBuilder;
import nurseybook.testutil.TypicalTasks;


public class TaskTest {

    private final Task keithInsulin = new TaskBuilder(KEITH_INSULIN).build();
    private final Task applyLeave = new TaskBuilder(TypicalTasks.APPLY_LEAVE).build();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Task task = new TaskBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> task.getRelatedNames().remove(0));
    }

    @Test
    public void isSameTask() {
        // same object -> returns true
        assertTrue(YASMINE_PHYSIO.isSameTask(YASMINE_PHYSIO));

        // null -> returns false
        assertFalse(YASMINE_PHYSIO.isSameTask(null));

        // same description, date and time, all other attributes different -> returns true
        Task editedPhysio = new TaskBuilder(YASMINE_PHYSIO).withStatus("false", "false")
                .withRecurrence(Recurrence.RecurrenceType.WEEK.name()).build();
        assertTrue(YASMINE_PHYSIO.isSameTask(editedPhysio));

        // same description, all other attributes different -> returns false
        Task fuck = new TaskBuilder(YASMINE_PHYSIO)
                .withDate(VALID_DATE_JAN)
                .withTime(VALID_TIME_TENAM).withStatus("false", "false")
                .withRecurrence(Recurrence.RecurrenceType.WEEK.name()).build();

        assertFalse(YASMINE_PHYSIO.isSameTask(fuck));

        // same date, all other attributes different -> returns false
        editedPhysio = new TaskBuilder(YASMINE_PHYSIO).withDesc("Physiotherapy with yoga ball")
                .withTime(VALID_TIME_TENAM).withStatus("false", "false")
                .withRecurrence(Recurrence.RecurrenceType.WEEK.name()).build();
        assertFalse(YASMINE_PHYSIO.isSameTask(editedPhysio));

        // same time, all other attributes different -> returns false
        editedPhysio = new TaskBuilder(YASMINE_PHYSIO).withDesc("Physiotherapy with yoga ball")
                .withDate(VALID_DATE_JAN).withStatus("false", "false")
                .withRecurrence(Recurrence.RecurrenceType.WEEK.name()).build();
        assertFalse(YASMINE_PHYSIO.isSameTask(editedPhysio));

        // different description, date and time, all other attributes same -> returns false
        editedPhysio = new TaskBuilder(YASMINE_PHYSIO).withDesc(VALID_DESC_MEDICINE).withDate(VALID_DATE_JAN)
                .withTime(VALID_TIME_TENAM).build();
        assertFalse(YASMINE_PHYSIO.isSameTask(editedPhysio));

        // description differs in case, all other attributes same -> returns false
        Task editedVaccine = new TaskBuilder(KG_SC_VACCINE).withDesc(VALID_DESC_VACCINE.toLowerCase()).build();
        assertFalse(KG_SC_VACCINE.isSameTask(editedVaccine));

        // description has trailing spaces, all other attributes same -> returns false
        String descriptionWithTrailingSpaces = VALID_DESC_VACCINE + " ";
        editedVaccine = new TaskBuilder(KG_SC_VACCINE).withDesc(descriptionWithTrailingSpaces).build();
        assertFalse(KG_SC_VACCINE.isSameTask(editedVaccine));
    }

    @Test
    public void equals() {
        // same values -> returns true
        assertTrue(keithInsulin.equals(KEITH_INSULIN));
        Task alexToKeith = new TaskBuilder(ALEX_INSULIN).withNames(VALID_NAME_KEITH).withDesc(VALID_DESC_MEDICINE)
                .withDateTime(VALID_DATE_NOV, VALID_TIME_SEVENPM).withStatus("false", "true")
                .withRecurrence(Recurrence.RecurrenceType.NONE.name()).build();
        assertTrue(keithInsulin.equals(alexToKeith));

        // same object -> returns true
        assertTrue(keithInsulin.equals(keithInsulin));

        // null -> returns false
        assertFalse(keithInsulin.equals(null));

        // different type -> returns false
        assertFalse(keithInsulin.equals(5));

        // different name -> returns false
        Task editedTask = new TaskBuilder(keithInsulin).withNames(VALID_NAME_ALEX).build();
        assertFalse(keithInsulin.equals(editedTask));
        editedTask = new TaskBuilder(keithInsulin).withNames(VALID_NAME_KEITH, VALID_NAME_ALEX).build();
        assertFalse(keithInsulin.equals(editedTask));

        // different date -> returns false
        editedTask = new TaskBuilder(keithInsulin).withDateTime(VALID_DATE_JAN, VALID_TIME_SEVENPM).build();
        assertFalse(keithInsulin.equals(editedTask));

        // different time -> returns false
        editedTask = new TaskBuilder(keithInsulin).withDateTime(VALID_DATE_NOV, VALID_TIME_TENAM).build();
        assertFalse(keithInsulin.equals(editedTask));

        // different status -> returns false
        editedTask = new TaskBuilder(keithInsulin).withStatus("true", "false").build();
        assertFalse(keithInsulin.equals(editedTask));

        // different recurrence -> returns false
        editedTask = new TaskBuilder(keithInsulin).withRecurrence(Recurrence.RecurrenceType.MONTH.name()).build();
        assertFalse(keithInsulin.equals(editedTask));
    }

    @Test
    public void isAfter() {
        DateTime october = new DateTime("2020-10-31", "23:59");
        DateTime november = new DateTime("2020-11-01", "19:45");
        DateTime december = new DateTime("2021-12-25", "12:00");

        // keithInsulin on 2021-11-01, after 2021 October -> returns true
        assertTrue(keithInsulin.isAfter(october));

        // keithInsulin on 2021-11-01, before 2021 December -> returns false
        assertFalse(keithInsulin.isAfter(december));

        // keithInsulin on 2021-11-01, same time as november -> returns false
        assertFalse(keithInsulin.isAfter(november));
    }

    @Test
    public void isBefore() {
        DateTime october = new DateTime("2020-10-31", "23:59");
        DateTime november = new DateTime("2020-11-01", "19:45");
        DateTime december = new DateTime("2021-12-25", "12:00");

        // keithInsulin on 2021-11-01, after 2021 October -> returns false
        assertFalse(keithInsulin.isBefore(october));

        // keithInsulin on 2021-11-01, before 2021 December -> returns true
        assertTrue(keithInsulin.isBefore(december));

        // keithInsulin on 2021-11-01, same time as november -> returns false
        assertFalse(keithInsulin.isBefore(november));
    }

    @Test
    void isTaskDone() {
        assertFalse(keithInsulin.isTaskDone()); // status: isDone = "false"
        assertTrue(applyLeave.isTaskDone());
    }

    @Test
    void isTaskOverdue() {
        assertTrue(applyLeave.isTaskOverdue()); // status: isOverdue = "true"
        assertTrue(keithInsulin.isTaskOverdue()); // status: isOverdue = "true"
    }

    @Test
    void markTaskDone() {
        Task doneKeith = new TaskBuilder(keithInsulin).withStatus("true", "true").build();
        assertEquals(keithInsulin.markAsDone(), doneKeith);
    }

    @Test
    void markTaskOverdue() {
        Task overdueKeith = new TaskBuilder(keithInsulin).withStatus("false", "true").build();
        assertEquals(keithInsulin.markAsOverdue(), overdueKeith);
    }

    @Test
    void checkIfTaskRecurring() {
        //recurring task
        Assertions.assertTrue(TypicalTasks.APPLY_LEAVE.checkIfTaskRecurring());

        //non-recurring task
        assertFalse(keithInsulin.checkIfTaskRecurring());
    }

    @Test
    void checkIfRealTask() {
        //real task
        assertTrue(keithInsulin.checkIfRealTask());

        //ghost task
        Assertions.assertFalse(TypicalTasks.APPLY_LEAVE_DAY_NEXT_RECURRENCE_GHOST.checkIfRealTask());
    }

    @Test
    void checkIfTaskFallsOnDate() {
        //task falls on date
        LocalDate sameDate = LocalDate.parse("2020-11-01");
        assertTrue(keithInsulin.checkIfTaskFallsOnDate(sameDate));

        //task does not fall on date
        LocalDate differentDate = LocalDate.parse("2020-11-02");
        assertFalse(keithInsulin.checkIfTaskFallsOnDate(differentDate));
    }

    @Test
    public void compareTo() {
        Task keithInsulin = new TaskBuilder(KEITH_INSULIN).build(); // date = "2020-11-01"
        Task alexInsulin = new TaskBuilder(ALEX_INSULIN).build(); // date = "2022-01-31", time: "19:45" in 24 hrs time
        Task doPaperwork = new TaskBuilder(DO_PAPERWORK).build(); // date: "2022-01-31", time: "10:20" in 24 hrs time

        // keithInsulin before alexInsulin -> returns negative value
        assertTrue(keithInsulin.compareTo(alexInsulin) < 0);

        // alexInsulin after doPaperwork -> returns positive value
        assertTrue(alexInsulin.compareTo(doPaperwork) > 0);

        // doPaperwork before alexInsulin -> returns negative value
        assertFalse(doPaperwork.compareTo(keithInsulin) == 0);
    }

    @Test
    public void updateDateOfRecurringTasks_forDayRecurring() {
        Task task1 = TypicalTasks.APPLY_LEAVE_LATE_TIME; // date: "2021-10-01", time: "23:50"
        // (which is most probably past current time) DAY RECURRING
        task1 = task1.updateDateRecurringTask();
        LocalDateTime currentDateTime1 = LocalDateTime.now();
        assertEquals(new DateTime(currentDateTime1.toLocalDate(), LocalTime.of(23, 50)), task1.getDateTime());
        // date: "2021-10-01", time: "00:00" (before current time), recurrence: DAY
        Task task2 = TypicalTasks.APPLY_LEAVE;
        task2 = task2.updateDateRecurringTask();
        LocalDateTime currentDateTime2 = LocalDateTime.now();
        assertEquals(new DateTime(currentDateTime2.toLocalDate().plusDays(1),
                LocalTime.of(0, 0)), task2.getDateTime());
    }

    @Test
    public void changeDateOfPastRecurringTasks_forWeekRecurring() {
        LocalDate date = LocalDate.of(2021, 9, 30);
        LocalTime time = LocalTime.of(23, 50);
        // date: "2021-09-30", time: "23:50" (after current time) recurrence: WEEK
        Task task1 = TypicalTasks.APPLY_LEAVE_WEEK_RECURRENCE;
        LocalDateTime currentDateTime1 = LocalDateTime.now();
        int daysDiff = currentDateTime1.getDayOfYear() - date.getDayOfYear();
        int daysToAdd = daysDiff % 7 == 0 ? 0 : 7 - daysDiff % 7;
        task1 = task1.updateDateRecurringTask();
        assertEquals(new DateTime(currentDateTime1.toLocalDate().plusDays(daysToAdd), time),
                task1.getDateTime());
    }

    @Test
    public void changeDateOfPastRecurringTasks_forMonthRecurring() {
        LocalDate date = LocalDate.of(2021, 7, 30);
        LocalTime time = LocalTime.of(23, 50);
        // date: "2021-07-30", time: "23:50" (after current time), recurrence: MONTH
        Task task1 = TypicalTasks.APPLY_LEAVE_MONTH_RECURRENCE;
        LocalDateTime currentDateTime1 = LocalDateTime.now();
        int daysDiff = currentDateTime1.getDayOfYear() - date.getDayOfYear();
        int daysToAdd = daysDiff % 28 == 0 ? 0 : 28 - daysDiff % 28;
        task1 = task1.updateDateRecurringTask();
        assertEquals(new DateTime(currentDateTime1.toLocalDate().plusDays(daysToAdd), time),
                task1.getDateTime());
    }

    @Test
    public void changeDateOfPastRecurringTasks_changedToUndoneTasks() {
        Task task1 = TypicalTasks.APPLY_LEAVE_MONTH_RECURRENCE; // date: "2021-07-30", time: "23:50"
        // (which is most probably past current time) DAY RECURRING
        task1 = task1.updateDateRecurringTask();
        assertFalse(task1.getStatus().isDone);
    }

}
