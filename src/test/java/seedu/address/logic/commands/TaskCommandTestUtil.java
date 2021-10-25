package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_RECURRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_TIME;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.person.Elderly;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.DescriptionContainsKeywordPredicate;
import seedu.address.model.task.Recurrence.RecurrenceType;
import seedu.address.model.task.Task;
import seedu.address.testutil.EditElderlyDescriptorBuilder;
import seedu.address.testutil.EditTaskDescriptorBuilder;

import java.util.Arrays;
import java.util.Set;

public class TaskCommandTestUtil extends CommandTestUtil {
    public static final String VALID_DESC_PAPERWORK = "Do paperwork";
    public static final String VALID_DESC_MEDICINE = "Bring insulin shots";
    public static final String VALID_DESC_COVID = "Take covid shots";
    public static final String VALID_DESC_VACCINE = "3rd shot for Pfizer";
    public static final String VALID_DATE_NOV = "2020-11-01";
    public static final String VALID_DATE_JAN = "2022-01-31";
    public static final String VALID_TIME_TENAM = "10:20";
    public static final String VALID_TIME_SEVENPM = "19:45";
    public static final String VALID_NAME_KEITH = "Keith Goh";
    public static final String VALID_NAME_ALEX = "Alex Andre";

    public static final String DESC_PAPERWORK = " " + PREFIX_TASK_DESC + VALID_DESC_PAPERWORK;
    public static final String DESC_MEDICINE = " " + PREFIX_TASK_DESC + VALID_DESC_MEDICINE;
    public static final String DESC_COVID = " " + PREFIX_TASK_DESC + VALID_DESC_COVID;
    public static final String DESC_VACCINE = " " + PREFIX_TASK_DESC + VALID_DESC_VACCINE;
    public static final String DATE_DESC_NOV = " " + PREFIX_TASK_DATE + VALID_DATE_NOV;
    public static final String DATE_DESC_JAN = " " + PREFIX_TASK_DATE + VALID_DATE_JAN;
    public static final String TIME_DESC_TENAM = " " + PREFIX_TASK_TIME + VALID_TIME_TENAM;
    public static final String TIME_DESC_SEVENPM = " " + PREFIX_TASK_TIME + VALID_TIME_SEVENPM;
    public static final String NAME_DESC_KEITH = " " + PREFIX_NAME + VALID_NAME_KEITH;
    public static final String NAME_DESC_ALEX = " " + PREFIX_NAME + VALID_NAME_ALEX;
    public static final String RECUR_NONE = " " + PREFIX_TASK_RECURRING + RecurrenceType.NONE;
    public static final String RECUR_DAY = " " + PREFIX_TASK_RECURRING + RecurrenceType.DAY;
    public static final String RECUR_WEEK = " " + PREFIX_TASK_RECURRING + RecurrenceType.WEEK;
    public static final String RECUR_MONTH = " " + PREFIX_TASK_RECURRING + RecurrenceType.MONTH;

    public static final String INVALID_DESC = " " + PREFIX_TASK_DESC;
    public static final String INVALID_DATE = " " + PREFIX_TASK_DATE + "07-10-2009"; //should be yyyy-mm-dd
    public static final String INVALID_TIME = " " + PREFIX_TASK_TIME + "1900"; //should be 19:00
    public static final String INVALID_RECUR = " " + PREFIX_TASK_RECURRING + "YEAR"; // no yearly tasks

    public static final EditTaskCommand.EditTaskDescriptor VACCINE_TASK;
    public static final EditTaskCommand.EditTaskDescriptor PAPERWORK_TASK;
    public static final Set<Name> SET_ONE_NAME;
    public static final Set<Name> SET_TWO_NAMES;

    static {
        VACCINE_TASK = new EditTaskDescriptorBuilder().withNames(VALID_NAME_AMY)
                .withDescription(VALID_DESC_VACCINE).withDate(VALID_DATE_JAN).withTime(VALID_TIME_SEVENPM)
                .withStatus("false", "false").withRecurrence(RecurrenceType.WEEK.name())
                .build();

        PAPERWORK_TASK = new EditTaskDescriptorBuilder().withNames(VALID_NAME_BOB)
                .withDescription(VALID_DESC_PAPERWORK).withDate(VALID_DATE_NOV).withTime(VALID_TIME_TENAM)
                .withStatus("false", "false").withRecurrence(RecurrenceType.NONE.name())
                .build();

        SET_ONE_NAME = Set.of(new Name(VALID_NAME_AMY));
        SET_TWO_NAMES = Set.of(new Name(VALID_NAME_AMY), new Name(VALID_NAME_BOB));
    }

    /**
     * Updates {@code model}'s filtered list to show only the task at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showTaskAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTaskList().size());

        Task task = model.getFilteredTaskList().get(targetIndex.getZeroBased());
        final String[] splitDescription = task.getDesc().value.split("\\s+");

        model.updateFilteredTaskList(new DescriptionContainsKeywordPredicate(Arrays.asList(splitDescription[0])));

        assertEquals(1, model.getFilteredTaskList().size());
    }
}
