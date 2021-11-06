package nurseybook.testutil;

import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DATE_JAN;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DATE_NOV;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DESC_COVID;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DESC_MEDICINE;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DESC_PAPERWORK;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DESC_VACCINE;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_NAME_ALICE;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_NAME_GEORGE;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_TIME_SEVENPM;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_TIME_TENAM;
import static nurseybook.testutil.TypicalElderlies.getTypicalElderlyBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import nurseybook.model.NurseyBook;
import nurseybook.model.task.Recurrence;
import nurseybook.model.task.Task;

public class TypicalTasks {

    public static final TaskBuilder ALICE_INSULIN_BUILDER = new TaskBuilder().withDesc(VALID_DESC_COVID)
            .withDateTime(VALID_DATE_JAN, VALID_TIME_SEVENPM).withNames(VALID_NAME_ALICE)
            .withRecurrence(Recurrence.RecurrenceType.NONE.name());

    public static final TaskBuilder GEORGE_INSULIN_BUILDER = new TaskBuilder().withDesc(VALID_DESC_MEDICINE)
            .withDateTime(VALID_DATE_NOV, VALID_TIME_SEVENPM).withNames(VALID_NAME_GEORGE)
            .withStatus("false", "true")
            .withRecurrence(Recurrence.RecurrenceType.NONE.name());

    public static final TaskBuilder DO_PAPERWORK_BUILDER = new TaskBuilder().withDesc(VALID_DESC_PAPERWORK)
            .withDateTime(VALID_DATE_JAN, VALID_TIME_TENAM)
            .withRecurrence(Recurrence.RecurrenceType.MONTH.name());

    public static final TaskBuilder FIONA_PHYSIO_BUILDER = new TaskBuilder().withDesc("Physiotherapy with yoga ball")
            .withDateTime("2021-09-13", "15:30").withNames("Fiona Kunz")
            .withStatus("true", "true").withRecurrence(Recurrence.RecurrenceType.NONE.name());

    public static final TaskBuilder APPLY_LEAVE_BUILDER = new TaskBuilder().withDesc("Apply leave with HR")
            .withDateTime("2021-10-01", "00:00").withStatus("true", "true")
            .withRecurrence(Recurrence.RecurrenceType.DAY.name());

    public static final TaskBuilder KG_SC_VACCINE_BUILDER = new TaskBuilder().withDesc(VALID_DESC_VACCINE)
            .withDateTime("2021-10-30", "18:00").withNames("Elle Meyer", "Fiona Kunz")
            .withStatus("false", "true").withRecurrence(Recurrence.RecurrenceType.NONE.name());


    public static final Task ALICE_INSULIN = ALICE_INSULIN_BUILDER.build();

    public static final Task GEORGE_INSULIN = GEORGE_INSULIN_BUILDER.build();

    public static final Task DO_PAPERWORK = DO_PAPERWORK_BUILDER.build();

    public static final Task FIONA_PHYSIO = FIONA_PHYSIO_BUILDER.build();

    public static final Task APPLY_LEAVE = APPLY_LEAVE_BUILDER.build();

    public static final Task KG_SC_VACCINE = KG_SC_VACCINE_BUILDER.build();

    // Extra test cases
    public static final Task APPLY_LEAVE_LATE_TIME = new TaskBuilder().withDesc("Apply leave with HR")
            .withDateTime("2021-10-01", "23:50").withStatus("true", "true")
            .withRecurrence(Recurrence.RecurrenceType.DAY.name()).build();

    public static final Task APPLY_LEAVE_NEXT_DAY = new TaskBuilder().withDesc("Apply leave with HR")
            .withDateTime("2021-10-02", "23:50").withStatus("true", "true")
            .withRecurrence(Recurrence.RecurrenceType.DAY.name()).build();

    public static final Task APPLY_LEAVE_WEEK_RECURRENCE = new TaskBuilder().withDesc("Apply leave with HR")
            .withDateTime("2021-09-30", "23:50").withStatus("true", "true")
            .withRecurrence(Recurrence.RecurrenceType.WEEK.name()).build();

    public static final Task APPLY_LEAVE_MONTH_RECURRENCE = new TaskBuilder().withDesc("Apply leave with HR")
            .withDateTime("2021-07-30", "23:50").withStatus("true", "true")
            .withRecurrence(Recurrence.RecurrenceType.MONTH.name()).build();

    public static final Task APPLY_LEAVE_DAY_NEXT_RECURRENCE_GHOST = new TaskBuilder(false)
            .withDesc("Apply leave with HR")
            .withDateTime("2021-10-02", "23:50").withStatus("true", "true")
            .withRecurrence(Recurrence.RecurrenceType.DAY.name()).build();

    public static final Task APPLY_LEAVE_WEEK_NEXT_RECURRENCE_GHOST = new TaskBuilder(false)
            .withDesc("Apply leave with HR")
            .withDateTime("2021-10-07", "23:50").withStatus("true", "true")
            .withRecurrence(Recurrence.RecurrenceType.WEEK.name()).build();

    public static final Task APPLY_LEAVE_MONTH_NEXT_RECURRENCE_GHOST = new TaskBuilder(false)
            .withDesc("Apply leave with HR")
            .withDateTime("2021-08-27", "23:50").withStatus("true", "true")
            .withRecurrence(Recurrence.RecurrenceType.MONTH.name()).build();

    /**
     * Returns an {@code NurseyBook} with all the typical tasks and elderlies (newly created).
     */
    public static NurseyBook getTypicalNurseyBook() {
        NurseyBook nb = new NurseyBook();

        //This returns NurseyBook objects that create new copies of those default Elderly and Task objects.
        //This is to prevent state modifications from leaking from one test to another
        nb.setElderlies(getTypicalElderlyBuilders().stream().map(s -> s.build()).collect(Collectors.toList()));
        nb.setTasks(getTypicalTaskBuilders().stream().map(s -> s.build()).collect(Collectors.toList()));

        nb.updateRecurringTasksDate();
        nb.updateTasksOverdueStatus();
        nb.reorderTasksChronologically();
        return nb;
    }

    /**
     * Returns an {@code NurseyBook} with all the typical tasks and elderlies but not synchronized with time.
     */
    public static NurseyBook getTypicalUnorderedNurseyBook() {
        // this method is mainly for jsonstorage testing
        NurseyBook nb = new NurseyBook();
        nb.setElderlies(getTypicalElderlyBuilders().stream().map(s -> s.build()).collect(Collectors.toList()));
        nb.setTasks(getTypicalTaskBuilders().stream().map(s -> s.build()).collect(Collectors.toList()));
        return nb;
    }


    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(ALICE_INSULIN, GEORGE_INSULIN, DO_PAPERWORK,
                FIONA_PHYSIO, KG_SC_VACCINE));
    }

    private static List<TaskBuilder> getTypicalTaskBuilders() {
        return new ArrayList<>(Arrays.asList(ALICE_INSULIN_BUILDER, GEORGE_INSULIN_BUILDER, DO_PAPERWORK_BUILDER,
                FIONA_PHYSIO_BUILDER, KG_SC_VACCINE_BUILDER));
    }
}
