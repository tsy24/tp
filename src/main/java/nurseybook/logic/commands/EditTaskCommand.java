package nurseybook.logic.commands;

import static java.util.Objects.requireNonNull;
import static nurseybook.commons.core.Messages.MESSAGE_DUPLICATE_TASK;
import static nurseybook.commons.core.Messages.MESSAGE_INVALID_TASK_DATETIME_FOR_RECURRING_TASK;
import static nurseybook.commons.core.Messages.MESSAGE_NO_CHANGES;
import static nurseybook.commons.core.Messages.MESSAGE_NO_SUCH_ELDERLY;
import static nurseybook.logic.parser.CliSyntax.PREFIX_NAME;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TASK_DATE;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TASK_DESC;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TASK_RECURRING;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TASK_TIME;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import nurseybook.commons.core.Messages;
import nurseybook.commons.core.index.Index;
import nurseybook.commons.util.CollectionUtil;
import nurseybook.logic.commands.exceptions.CommandException;
import nurseybook.model.Model;
import nurseybook.model.person.Name;
import nurseybook.model.task.DateTime;
import nurseybook.model.task.Description;
import nurseybook.model.task.RealTask;
import nurseybook.model.task.Recurrence;
import nurseybook.model.task.Status;
import nurseybook.model.task.Task;

/**
 * Edits the details of an existing task in the nursey book.
 */
public class EditTaskCommand extends Command {
    public static final String COMMAND_WORD = "editTask";
    public static final String[] PARAMETERS = { Index.VALID_INDEX_CRITERIA, "[" + PREFIX_NAME + "NAME]...",
        PREFIX_TASK_DESC + "DESCRIPTION", PREFIX_TASK_DATE + "DATE",
        PREFIX_TASK_TIME + "TIME", "[" + PREFIX_TASK_RECURRING + "RECURRENCE_TYPE]" };

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the displayed task list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + String.join(" ", PARAMETERS)
            + "\nExample: " + COMMAND_WORD + " 1 "
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

    /**
     * Check if entered names are valid and match the casing of the name to be the same as elderly name
     * in NurseyBook's elderly list
     * @param model
     * @throws CommandException if some elderly names entered are not found in NurseyBook
     */
    private void checkAndMatchNames(Model model) throws CommandException {
        Set<Name> enteredNames = editTaskDescriptor.getNames().orElse(new HashSet<>());

        if (!model.areAllElderliesPresent(enteredNames)) {
            throw new CommandException(MESSAGE_NO_SUCH_ELDERLY);
        } else if (!enteredNames.isEmpty()) {
            Set<Name> namesWithCasesMatched = enteredNames.stream()
                    .map(n -> model.findElderlyWithName(n).getName()).collect(Collectors.toSet());
            editTaskDescriptor.setNames(namesWithCasesMatched);
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        checkAndMatchNames(model);

        Task taskToEdit = lastShownList.get(index.getZeroBased());
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

        if (editedTask.isPastCurrentDateAndRecurringTask()) {
            throw new CommandException(MESSAGE_INVALID_TASK_DATETIME_FOR_RECURRING_TASK);
        }

        if (taskToEdit.equals(editedTask)) {
            throw new CommandException(MESSAGE_NO_CHANGES);
        }

        if (!taskToEdit.isSameTask(editedTask) && model.hasTask(editedTask)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.setTask(taskToEdit, editedTask);
        CommandResult commandResult = new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask));
        model.updateTasksAccordingToTime();
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

        return new RealTask(updatedDescription, new DateTime(updatedDate, updatedTime), updatedNames, updatedStatus,
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
