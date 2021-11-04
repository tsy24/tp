package nurseybook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Set;

import nurseybook.commons.core.index.Index;
import nurseybook.logic.parser.CliSyntax;
import nurseybook.model.Model;
import nurseybook.model.person.Name;
import nurseybook.model.task.DescriptionContainsKeywordPredicate;
import nurseybook.model.task.Recurrence;
import nurseybook.model.task.Task;
import nurseybook.testutil.EditTaskDescriptorBuilder;

public class TaskCommandTestUtil extends CommandTestUtil {
    public static final String VALID_DESC_PAPERWORK = "Do paperwork";
    public static final String VALID_DESC_MEDICINE = "Bring insulin shots";
    public static final String VALID_DESC_COVID = "Take covid shots";
    public static final String VALID_DESC_VACCINE = "3rd shot for Pfizer";
    public static final String VALID_DATE_NOV = "2020-11-01";
    public static final String VALID_DATE_JAN = "2022-01-31";
    public static final String VALID_TIME_TENAM = "10:20";
    public static final String VALID_TIME_SEVENPM = "19:45";
    public static final String VALID_NAME_GEORGE = "George Best";
    public static final String VALID_NAME_ALICE = "Alice Pauline";

    public static final String DESC_PAPERWORK = " " + CliSyntax.PREFIX_TASK_DESC + VALID_DESC_PAPERWORK;
    public static final String DESC_MEDICINE = " " + CliSyntax.PREFIX_TASK_DESC + VALID_DESC_MEDICINE;
    public static final String DESC_COVID = " " + CliSyntax.PREFIX_TASK_DESC + VALID_DESC_COVID;
    public static final String DESC_VACCINE = " " + CliSyntax.PREFIX_TASK_DESC + VALID_DESC_VACCINE;
    public static final String DATE_DESC_NOV = " " + CliSyntax.PREFIX_TASK_DATE + VALID_DATE_NOV;
    public static final String DATE_DESC_JAN = " " + CliSyntax.PREFIX_TASK_DATE + VALID_DATE_JAN;
    public static final String TIME_DESC_TENAM = " " + CliSyntax.PREFIX_TASK_TIME + VALID_TIME_TENAM;
    public static final String TIME_DESC_SEVENPM = " " + CliSyntax.PREFIX_TASK_TIME + VALID_TIME_SEVENPM;
    public static final String NAME_DESC_GEORGE = " " + CliSyntax.PREFIX_NAME + VALID_NAME_GEORGE;
    public static final String NAME_DESC_ALICE = " " + CliSyntax.PREFIX_NAME + VALID_NAME_ALICE;
    public static final String RECUR_NONE = " " + CliSyntax.PREFIX_TASK_RECURRING + Recurrence.RecurrenceType.NONE;
    public static final String RECUR_DAY = " " + CliSyntax.PREFIX_TASK_RECURRING + Recurrence.RecurrenceType.DAY;
    public static final String RECUR_WEEK = " " + CliSyntax.PREFIX_TASK_RECURRING + Recurrence.RecurrenceType.WEEK;
    public static final String RECUR_MONTH = " " + CliSyntax.PREFIX_TASK_RECURRING + Recurrence.RecurrenceType.MONTH;

    public static final String INVALID_DESC = " " + CliSyntax.PREFIX_TASK_DESC;
    public static final String INVALID_DATE = " " + CliSyntax.PREFIX_TASK_DATE + "07-10-2009"; //should be yyyy-mm-dd
    public static final String INVALID_TIME = " " + CliSyntax.PREFIX_TASK_TIME + "1900"; //should be 19:00
    public static final String INVALID_RECUR = " " + CliSyntax.PREFIX_TASK_RECURRING + "YEAR"; // no yearly tasks

    public static final EditTaskCommand.EditTaskDescriptor VACCINE_TASK;
    public static final EditTaskCommand.EditTaskDescriptor PAPERWORK_TASK;
    public static final Set<Name> SET_ONE_NAME;
    public static final Set<Name> SET_TWO_NAMES;

    static {
        VACCINE_TASK = new EditTaskDescriptorBuilder().withNames(VALID_NAME_AMY)
                .withDescription(VALID_DESC_VACCINE).withDate(VALID_DATE_JAN).withTime(VALID_TIME_SEVENPM)
                .withStatus("false", "false").withRecurrence(Recurrence.RecurrenceType.WEEK.name())
                .build();

        PAPERWORK_TASK = new EditTaskDescriptorBuilder().withNames(VALID_NAME_BOB)
                .withDescription(VALID_DESC_PAPERWORK).withDate(VALID_DATE_NOV).withTime(VALID_TIME_TENAM)
                .withStatus("false", "false").withRecurrence(Recurrence.RecurrenceType.NONE.name())
                .build();

        SET_ONE_NAME = Set.of(new Name(VALID_NAME_AMY));
        SET_TWO_NAMES = Set.of(new Name(VALID_NAME_AMY), new Name(VALID_NAME_BOB));
    }

    /**
     * Updates {@code model}'s filtered list to show only the task at the given {@code targetIndex} in the
     * {@code model}'s nursey book.
     */
    public static void showTaskAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTaskList().size());

        Task task = model.getFilteredTaskList().get(targetIndex.getZeroBased());
        final String[] splitDescription = task.getDesc().value.split("\\s+");

        model.updateFilteredTaskList(new DescriptionContainsKeywordPredicate(Arrays.asList(splitDescription[0])));

        assertEquals(1, model.getFilteredTaskList().size());
    }
}
