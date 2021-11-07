package nurseybook.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import nurseybook.model.person.Elderly;
import nurseybook.model.person.Name;
import nurseybook.model.person.UniqueElderlyList;
import nurseybook.model.task.Task;
import nurseybook.model.task.UniqueTaskList;

/**
 * Wraps all data at NurseyBook level
 * Duplicates are not allowed (by .isSameElderly comparison)
 */
public class NurseyBook implements ReadOnlyNurseyBook {

    private final UniqueElderlyList elderlies;
    private final UniqueTaskList tasks;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        elderlies = new UniqueElderlyList();
        tasks = new UniqueTaskList();
    }

    public NurseyBook() {}

    /**
     * Creates an NurseyBook using the Elderlies in the {@code toBeCopied}
     */
    public NurseyBook(ReadOnlyNurseyBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the elderly list with {@code elderlies}.
     * {@code elderlies} must not contain duplicate elderlies.
     */
    public void setElderlies(List<Elderly> elderlies) {
        this.elderlies.setElderlies(elderlies);
    }

    /**
     * Replaces the contents of the task list with {@code tasks}.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks.setTasks(tasks);
    }

    /**
     * Resets the existing data of this {@code NurseyBook} with {@code newData}.
     */
    public void resetData(ReadOnlyNurseyBook newData) {
        requireNonNull(newData);

        setElderlies(newData.getElderlyList());
        setTasks(newData.getTaskList());
    }

    //// elderly-level operations

    /**
     * Returns true if an elderly with the same identity as {@code elderly} exists in NurseyBook.
     */
    public boolean hasElderly(Elderly elderly) {
        requireNonNull(elderly);
        return elderlies.contains(elderly);
    }

    /**
     * Adds a elderly to NurseyBook.
     * The elderly must not already exist in NurseyBook.
     */
    public void addElderly(Elderly p) {
        elderlies.add(p);
    }

    /**
     * Adds a task to NurseyBook.
     */
    public void addTask(Task t) {
        tasks.add(t);
    }

    /**
     * Replaces the given elderly {@code target} in the list with {@code editedElderly}.
     * {@code target} must exist in NurseyBook.
     * The elderly identity of {@code editedElderly} must not be the same as another existing elderly in NurseyBook.
     */
    public void setElderly(Elderly target, Elderly editedElderly) {
        requireNonNull(editedElderly);

        elderlies.setElderly(target, editedElderly);
    }

    /**
     * Updates the given elderly {@code target}'s name for all tasks in the list that contains that name
     * with {@code editedElderly}'s name.
     * {@code target} must exist in NurseyBook.
     * The elderly identity of {@code editedElderly} must not be the same as another existing elderly in NurseyBook.
     */
    public void updateElderlyNameInTasks(Elderly target, Elderly editedElderly) {
        requireNonNull(editedElderly);

        tasks.updateElderlyNameInTasks(target, editedElderly);
    }

    /**
     * Deletes the given elderly {@code target}'s name for all tasks in the list that contains that name
     * with {@code editedElderly}'s name.
     * {@code target} must exist in NurseyBook.
     */
    public void deleteElderlyNameInTasks(Elderly elderlyToDelete) {
        requireNonNull(elderlyToDelete);

        tasks.deleteElderlyNameInTasks(elderlyToDelete);
    }

    /**
     * Replaces the given task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in NurseyBook.
     * The task identity of {@code editedTask} must not be the same as another existing task in the nursey
     * book.
     */
    public void setTask(Task target, Task editedTask) {
        requireNonNull(editedTask);

        tasks.setTask(target, editedTask);
    }

    /**
     * Removes {@code key} elderly from this {@code NurseyBook}.
     * {@code key} must exist in NurseyBook.
     */
    public void removeElderly(Elderly key) {
        elderlies.remove(key);
    }

    /**
     * Removes {@code key} task from this {@code NurseyBook}.
     * {@code key} must exist in NurseyBook.
     */
    public void removeTask(Task key) {
        tasks.remove(key);
    }

    /**
     * Removes all ghost tasks, if any, from this {@code NurseyBook}.
     */
    public void deleteGhostTasks() {
        tasks.deleteGhostTasks();
    }

    /**
     * Adds all possible ghost tasks that fall on the given keyDate.
     */
    public void addPossibleGhostTasksWithMatchingDate(LocalDate keyDate) {
        tasks.addPossibleGhostTasksWithMatchingDate(keyDate);
    }

    ///time-related methods

    /**
     * Recurring tasks' DateTime changes to its next occurrence once its previous DateTime has arrived/passed.
     * This functions updates recurring tasks' DateTime as needed at the current time.
     * @see UniqueTaskList#updateRecurringDates()
     */
    public void updateRecurringTasksDate() {
        tasks.updateRecurringDates();
    }

    /**
     * Check if tasks' overdue status is correct when comparing to the current time and update them accordingly.
     * @see UniqueTaskList#updateOverdueStatuses()
     */
    public void updateTasksOverdueStatus() {
        tasks.updateOverdueStatuses();
    }


    /**
     * Sorts tasks chronologically by DateTime with the earliest task at the front.
     * @see UniqueTaskList#reorderTasks()
     */
    public void reorderTasksChronologically() {
        tasks.reorderTasks();
    }

    //// util methods

    @Override
    public String toString() {
        return elderlies.asUnmodifiableObservableList().size() + " elderlies\n"
                + tasks.asUnmodifiableObservableList().size() + " tasks";
        // TODO: refine later
    }

    @Override
    public ObservableList<Elderly> getElderlyList() {
        return elderlies.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Task> getTaskList() {
        return tasks.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NurseyBook // instanceof handles nulls
                && elderlies.equals(((NurseyBook) other).elderlies)
                && tasks.equals(((NurseyBook) other).tasks));
    }

    @Override
    public int hashCode() {
        return Objects.hash(elderlies, tasks);
    }

    public boolean hasTask(Task t) {
        return tasks.contains(t);
    }

    @Override
    public ObservableList<Task> getRealTaskList() {
        UniqueTaskList realTaskList = tasks.getRealTaskList();
        return realTaskList.asUnmodifiableObservableList();
    }

    @Override
    public boolean doTasksContainValidNames() {
        return tasks.doTasksContainValidNames(elderlies);
    }

    /**
     * Marks the given task {@code target} as done.
     * {@code target} must exist in NurseyBook.
     */
    public void markTaskAsDone(Task target) {
        tasks.markTaskAsDone(target);
    }

    /**
     * Returns true if all the names in {@code names} are of some
     * elderly present currently in NurseyBook.
     */
    public boolean areAllElderliesPresent(Set<Name> names) {
        for (Name name: names) {
            if (!elderlies.hasElderly(name)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns first and only elderly in NurseyBook whose name matches {@code name} (case-insensitive matching).
     * Since only one elderly in NurseyBook is allowed to have a particular name, this returns only one elderly.
     */
    public Elderly findElderlyWithName(Name name) {
        return elderlies.getElderly(name);
    }
}
