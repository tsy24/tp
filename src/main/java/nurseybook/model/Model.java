package nurseybook.model;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import nurseybook.commons.core.GuiSettings;
import nurseybook.logic.commands.CommandResult;
import nurseybook.model.person.Elderly;
import nurseybook.model.person.Name;
import nurseybook.model.task.Task;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} for elderly lists that always evaluate to true */
    Predicate<Elderly> PREDICATE_SHOW_ALL_ELDERLIES = unused -> true;

    /** {@code Predicate} for task lists that evaluate to true for real tasks */
    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = task -> task.isRealTask();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' nursey book file path.
     */
    Path getNurseyBookFilePath();

    /**
     * Sets the user prefs' nursey book file path.
     */
    void setNurseyBookFilePath(Path nurseyBookFilePath);

    /**
     * Replaces nursey book data with the data in {@code versionedNurseyBook}.
     */
    void setVersionedNurseyBook(ReadOnlyNurseyBook versionedNurseyBook);

    /**
     * Returns the NurseyBook
     */
    ReadOnlyNurseyBook getVersionedNurseyBook();

    /**
     * Returns true if a elderly with the same identity as {@code elderly} exists in the nursey book.
     */
    boolean hasElderly(Elderly elderly);

    /**
     * Returns true if a task {@code task} exists in the nursey book.
     */
    boolean hasTask(Task t);

    /**
     * Mark the given task {@code target} as done.
     * {@code target} must exist in the nursey book.
     */
    void markTaskAsDone(Task target);

    /**
     * Returns true if all the names in {@code names} are of some
     * elderly present currently in NurseyBook.
     */
    boolean areAllElderliesPresent(Set<Name> names);

    void updateElderlyNameInTasks(Elderly target, Elderly editedElderly);

    void deleteElderlyNameInTasks(Elderly elderlyToDelete);

    /**
     * Deletes the given elderly.
     * The elderly must exist in the nursey book.
     */
    void deleteElderly(Elderly target);

    /**
     * Adds the given elderly.
     * {@code elderly} must not already exist in the nursey book.
     */
    void addElderly(Elderly elderly);

    /**
     * Set given elderly as the elderly whose full details are to be displayed.
     * The elderly must exist in the nursey book.
     */
    void setElderlyOfInterest(Elderly e);

    /**
     * Returns elderly whose full details are to be displayed.
     */
    Elderly getElderlyOfInterest();

    /**
     * Adds the given task.
     */
    void addTask(Task task);

    /**
     * Replaces the given elderly {@code target} with {@code editedElderly}.
     * {@code target} must exist in the nursey book.
     * The elderly identity of {@code editedElderly} must not be the same as another existing elderly
     * in the nursey book.
     */
    void setElderly(Elderly target, Elderly editedElderly);


    /** Deletes all tasks that are not real in NurseyBook. */
    void deleteGhostTasks();

    /**
     * Replaces the given task {@code target} with {@code editedTask}.
     * {@code target} must exist in the nursey book.
     * The task identity of {@code editedTask} must not be the same as another existing task
     * in the nursey book.
     */
    void setTask(Task target, Task editedTask);

    /** Returns an unmodifiable view of the filtered elderly list */
    ObservableList<Elderly> getFilteredElderlyList();

    /** Returns an unmodifiable view of the filtered task list */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Updates the filter of the filtered elderly list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredElderlyList(Predicate<Elderly> predicate);

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<Task> predicate);

    /**
     * Updates all time sensitive data of tasks-
     * this includes: updating recurring task dates, overdue statuses, ensuring tasklist is sorted chronologically
     */
    void updateTasksAccordingToTime();

    /**
     * Deletes the given task.
     * The task must exist in the nursey book.
     */
    void deleteTask(Task taskToDelete);

    /**
     * Saves the change in data of NurseyBook.
     */
    void commitNurseyBook(CommandResult commandResult);

    /**
     * Undoes the previous command and returns the {@code CommandResult} of the command being undone.
     */
    CommandResult undoNurseyBook();

    /**
     * Returns true if there are changes to the NurseyBook that can be undone.
     */
    boolean canUndoNurseyBook();

    /**
     * Undoes the previous command and returns the {@code CommandResult} of the command being undone.
     */
    CommandResult redoNurseyBook();

    /**
     * Returns true if there are changes to the NurseyBook that can be redone.
     */
    boolean canRedoNurseyBook();

    /**
     * Adds ghost tasks on the specified keyDate, if any of the current recurring tasks' future occurrences
     * coincide with the given keydate.
     *
     * @param keyDate Date to compare future occurrences against.
     */
    void addPossibleGhostTasksWithMatchingDate(LocalDate keyDate);

}
