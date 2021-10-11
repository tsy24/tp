package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.person.Name;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Description;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;

public class TaskBuilder {

    public static final String DEFAULT_DESC = "inject 5000 mg tylenol";
    public static final String DEFAULT_DATE = "2069-06-09";
    public static final String DEFAULT_TIME = "18:09";
    public static final String DEFAULT_STATUS = "false";

    private Description desc;
    private DateTime dateTime;
    private Set<Name> names;
    private Status status;

    /**
     * Creates a {@code TaskBuilder} with the default details.
     */
    public TaskBuilder() {
        desc = new Description(DEFAULT_DESC);
        dateTime = new DateTime(DEFAULT_DATE, DEFAULT_TIME);
        names = new HashSet<>();
        status = new Status(DEFAULT_STATUS);
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        desc = taskToCopy.getDesc();
        dateTime = taskToCopy.getDateTime();
        names = new HashSet<>(taskToCopy.getRelatedNames());
        status = taskToCopy.getStatus();
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
    public TaskBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    /**
     * Returns task object created with fields of the taskBuilder
     */
    public Task build() {
        return new Task(desc, dateTime, names, status);
    }
}
