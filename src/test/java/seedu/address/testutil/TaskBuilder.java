package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.person.Name;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Description;
import seedu.address.model.task.Recurrence;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;

public class TaskBuilder {

    public static final String DEFAULT_DESC = "inject 5000 mg tylenol";
    public static final String DEFAULT_DATE = "2069-06-09";
    public static final String DEFAULT_TIME = "18:09";
    public static final List<String> DEFAULT_STATUS = Arrays.asList("false", "false");
    public static final String DEFAULT_RECURRENCE = Recurrence.RecurrenceType.NONE.name();

    private Description desc;
    private DateTime dateTime;
    private LocalDate date;
    private LocalTime time;
    private Set<Name> names;
    private Status status;
    private Recurrence recurrence;

    /**
     * Creates a {@code TaskBuilder} with the default details.
     */
    public TaskBuilder() {
        desc = new Description(DEFAULT_DESC);
        dateTime = new DateTime(DEFAULT_DATE, DEFAULT_TIME);
        names = new HashSet<>();

        String isDone = DEFAULT_STATUS.get(0);
        String isOverdue = DEFAULT_STATUS.get(1);
        status = new Status(isDone, isOverdue);

        recurrence = new Recurrence(DEFAULT_RECURRENCE);
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        desc = taskToCopy.getDesc();
        dateTime = taskToCopy.getDateTime();
        names = new HashSet<>(taskToCopy.getRelatedNames());
        status = taskToCopy.getStatus();
        recurrence = taskToCopy.getRecurrence();
    }

    /**
     * Sets the {@code Description} of the {@code Task} that we are building.
     */
    public TaskBuilder withDesc(String desc) {
        this.desc = new Description(desc);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Task} that we are building.
     */
    public TaskBuilder withDateTime(String date, String time) {
        this.dateTime = new DateTime(date, time);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Task} that we are building.
     */
    public TaskBuilder withDate(String date) {
        this.date = LocalDate.parse(date);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Task} that we are building.
     */
    public TaskBuilder withTime(String time) {
        this.time = LocalTime.parse(time);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Task} that we are building.
     */
    public TaskBuilder withNames(String... names) {
        this.names = Arrays.stream(names)
                .map(name -> new Name(name))
                .collect(Collectors.toSet());
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Task} that we are building.
     */
    public TaskBuilder withStatus(String isDone, String isOverdue) {
        this.status = new Status(isDone, isOverdue);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Task} that we are building.
     */
    public TaskBuilder withRecurrence(String recurrence) {
        this.recurrence = new Recurrence(recurrence);
        return this;
    }

    /**
     * Returns task object created with fields of the taskBuilder
     */
    public Task build() {
        return new Task(desc, dateTime, names, status, recurrence);
    }
}
