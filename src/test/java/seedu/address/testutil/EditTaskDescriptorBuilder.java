package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.model.person.Name;
import seedu.address.model.task.Description;
import seedu.address.model.task.Recurrence;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;


public class EditTaskDescriptorBuilder {
    private EditTaskDescriptor descriptor;

    public EditTaskDescriptorBuilder() {
        descriptor = new EditTaskDescriptor();
    }

    public EditTaskDescriptorBuilder(EditTaskDescriptor descriptor) {
        this.descriptor = new EditTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditElderlyDescriptor} with fields containing {@code elderly}'s details
     */
    public EditTaskDescriptorBuilder(Task task) {
        descriptor = new EditTaskDescriptor();
        descriptor.setNames(task.getRelatedNames());
        descriptor.setDescription(task.getDesc());
        descriptor.setDate(task.getDate());
        descriptor.setTime(task.getTime());
        descriptor.setStatus(task.getStatus());
        descriptor.setRecurrence(task.getRecurrence());
    }

    /**
     * Sets the {@code description} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDate(String date) {
        descriptor.setDate(LocalDate.parse(date));
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withTime(String time) {
        descriptor.setTime(LocalTime.parse(time));
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withStatus(String completionStatus, String overdueStatus) {
        descriptor.setStatus(new Status(completionStatus, overdueStatus));
        return this;
    }

    /**
     * Sets the {@code Recurrence} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withRecurrence(String recurrence) {
        descriptor.setRecurrence(new Recurrence(recurrence));
        return this;
    }

    /**
     * Parses the {@code names} into a {@code Set<Name>} and set it to the {@code EditTaskDescriptor}
     * that we are building.
     */
    public EditTaskDescriptorBuilder withNames(String... names) {
        Set<Name> nameSet = Stream.of(names).map(Name::new).collect(Collectors.toSet());
        descriptor.setNames(nameSet);
        return this;
    }

    public EditTaskDescriptor build() {
        return descriptor;
    }
}
