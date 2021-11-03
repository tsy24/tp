package nurseybook.testutil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import nurseybook.logic.commands.EditTaskCommand;
import nurseybook.model.person.Name;
import nurseybook.model.task.Description;
import nurseybook.model.task.Recurrence;
import nurseybook.model.task.Status;
import nurseybook.model.task.Task;


public class EditTaskDescriptorBuilder {
    private EditTaskCommand.EditTaskDescriptor descriptor;

    public EditTaskDescriptorBuilder() {
        descriptor = new EditTaskCommand.EditTaskDescriptor();
    }

    public EditTaskDescriptorBuilder(EditTaskCommand.EditTaskDescriptor descriptor) {
        this.descriptor = new EditTaskCommand.EditTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTaskDescriptor} with fields containing {@code Task}'s details
     */
    public EditTaskDescriptorBuilder(Task task) {
        descriptor = new EditTaskCommand.EditTaskDescriptor();
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

    public EditTaskCommand.EditTaskDescriptor build() {
        return descriptor;
    }
}
