package seedu.address.testutil;

import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_DATE_JAN;
import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_DATE_NOV;
import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_DESC_MEDICINE;
import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_DESC_PAPERWORK;
import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_NAME_ALEX;
import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_NAME_KEITH;
import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_TIME_SEVENPM;
import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_TIME_TENAM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.task.Task;

public class TypicalTasks {

    public static final Task KEITH_INSULIN = new TaskBuilder().withDesc(VALID_DESC_MEDICINE)
            .withDateTime(VALID_DATE_NOV, VALID_TIME_SEVENPM).withNames(VALID_NAME_KEITH).build();

    public static final Task ALEX_INSULIN = new TaskBuilder().withDesc(VALID_DESC_MEDICINE)
            .withDateTime(VALID_DATE_JAN, VALID_TIME_SEVENPM).withNames(VALID_NAME_ALEX).build();

    public static final Task DO_PAPERWORK = new TaskBuilder().withDesc(VALID_DESC_PAPERWORK)
            .withDateTime(VALID_DATE_JAN, VALID_TIME_TENAM).build();

    public static final Task YASMINE_PHYSIO = new TaskBuilder().withDesc("Physiotherapy with yoga ball")
            .withDateTime("2021-09-13", "15:30").withNames("Yasmine George")
            .withStatus("true", "true").build();

    public static final Task APPLY_LEAVE = new TaskBuilder().withDesc("Apply leave with HR")
            .withDateTime("2021-10-01", "00:00").withStatus("true", "true").build();

    public static final Task KG_SC_VACCINE = new TaskBuilder().withDesc("3rd shot for Pfizer")
            .withDateTime("2021-10-30", "18:00").withNames("Khong Guan", "Swee Choon").build();

    /**
     * Returns an {@code AddressBook} with all the typical tasks.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Task t : getTypicalTasks()) {
            ab.addTask(t);
        }
        return ab;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(KEITH_INSULIN, ALEX_INSULIN, DO_PAPERWORK,
                YASMINE_PHYSIO, APPLY_LEAVE, KG_SC_VACCINE));
    }
}
