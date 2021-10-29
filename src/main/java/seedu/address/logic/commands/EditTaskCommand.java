package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_DATETIME_FOR_RECURRING_TASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_RECURRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Description;
import seedu.address.model.task.Recurrence;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;

/**
 * Edits the details of an existing task in the address book.
 */
public class EditTaskCommand extends Command {
    public static final String COMMAND_WORD = "editTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the displayed task list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_TASK_DESC + "DESCRIPTION] "
            + "[" + PREFIX_TASK_DATE + "DATE] "
            + "[" + PREFIX_TASK_TIME + "TIME] "
            + "[" + PREFIX_TASK_RECURRING + "RECURRENCE_TYPE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Khong Guan "
            + PREFIX_NAME + "John Doe "
            + PREFIX_TASK_RECURRING + "WEEK";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param index of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public EditTaskCommand(Index index, EditTaskDescriptor editTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(editTaskDescriptor);

        this.index = index;
        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToEdit = lastShownList.get(index.getZeroBased());
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

        if (editedTask.isPastCurrentDateAndRecurringTask()) {
            throw new CommandException(MESSAGE_INVALID_TASK_DATETIME_FOR_RECURRING_TASK);
        }

        model.setTask(taskToEdit, editedTask);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        CommandResult commandResult = new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask));
        model.commitNurseyBook(commandResult);
        return commandResult;
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(Task taskToEdit, EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Set<Name> updatedNames = editTaskDescriptor.getNames().orElse(taskToEdit.getRelatedNames());
        Description updatedDescription = editTaskDescriptor.getDescription().orElse(taskToEdit.getDesc());
        LocalDate updatedDate = editTaskDescriptor.getDate().orElse(taskToEdit.getDate());
        LocalTime updatedTime = editTaskDescriptor.getTime().orElse(taskToEdit.getTime());
        Status updatedStatus = editTaskDescriptor.getStatus().orElse(taskToEdit.getStatus());
        Recurrence updatedRecurrence = editTaskDescriptor.getRecurrence().orElse(taskToEdit.getRecurrence());

        return new Task(updatedDescription, new DateTime(updatedDate, updatedTime), updatedNames, updatedStatus,
                updatedRecurrence);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTaskCommand)) {
            return false;
        }

        // state check
        EditTaskCommand e = (EditTaskCommand) other;
        return index.equals(e.index)
                && editTaskDescriptor.equals(e.editTaskDescriptor);
    }

    /**
     * Stores the details to edit the elderly with. Each non-empty field value will replace the
     * corresponding field value of the elderly.
     */
    public static class EditTaskDescriptor {
        private Set<Name> names;
        private Description description;
        private LocalDate date;
        private LocalTime time;
        private Status status;
        private Recurrence recurrence;

        public EditTaskDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code names} is used internally.
         */
        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            setNames(toCopy.names);
            setDescription(toCopy.description);
            setDate(toCopy.date);
            setTime(toCopy.time);
            setStatus(toCopy.status);
            setRecurrence(toCopy.recurrence);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(names, description, date, time, recurrence);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public Optional<LocalDate> getDate() {
            return Optional.ofNullable(date);
        }

        public void setTime(LocalTime time) {
            this.time = time;
        }

        public Optional<LocalTime> getTime() {
            return Optional.ofNullable(time);
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        public void setRecurrence(Recurrence recurrence) {
            this.recurrence = recurrence;
        }

        public Optional<Recurrence> getRecurrence() {
            return Optional.ofNullable(recurrence);
        }

        /**
         * Sets {@code names} to this object's {@code names}.
         * A defensive copy of {@code names} is used internally.
         */
        public void setNames(Set<Name> names) {
            this.names = (names != null) ? new HashSet<>(names) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Name>> getNames() {
            return (names != null) ? Optional.of(Collections.unmodifiableSet(names)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTaskDescriptor)) {
                return false;
            }

            // state check
            EditTaskDescriptor e = (EditTaskDescriptor) other;

            return getNames().equals(e.getNames())
                    && getDescription().equals(e.getDescription())
                    && getDate().equals(e.getDate())
                    && getTime().equals(e.getTime())
                    && getStatus().equals(e.getStatus())
                    && getRecurrence().equals(e.getRecurrence());
        }
    }
}