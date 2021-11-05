package nurseybook.model;

import static java.util.Objects.requireNonNull;
import static nurseybook.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import nurseybook.commons.core.GuiSettings;
import nurseybook.commons.core.LogsCenter;
import nurseybook.logic.commands.CommandResult;
import nurseybook.model.person.Elderly;
import nurseybook.model.person.Name;
import nurseybook.model.task.Task;
import nurseybook.model.task.TaskIsNotOverduePredicate;
import nurseybook.model.task.TaskIsOverduePredicate;
import nurseybook.model.task.TaskIsRecurringAndOverduePredicate;

/**
 * Represents the in-memory model of the nursey book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedNurseyBook versionedNurseyBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Elderly> filteredElderlies;
    private final FilteredList<Task> filteredTasks;
    private Elderly elderlyOfInterest;

    /**
     * Initializes a ModelManager with the given versionedNurseyBook and userPrefs.
     */
    public ModelManager(ReadOnlyNurseyBook versionedNurseyBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(versionedNurseyBook, userPrefs);

        logger.fine("Initializing with nursey book: " + versionedNurseyBook + " and user prefs " + userPrefs);

        this.versionedNurseyBook = new VersionedNurseyBook(versionedNurseyBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredElderlies = new FilteredList<>(this.versionedNurseyBook.getElderlyList());
        filteredTasks = new FilteredList<>(this.versionedNurseyBook.getTaskList());
        this.elderlyOfInterest = null;
    }

    public ModelManager() {
        this(new NurseyBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getNurseyBookFilePath() {
        return userPrefs.getNurseyBookFilePath();
    }

    @Override
    public void setNurseyBookFilePath(Path nurseyBookFilePath) {
        requireNonNull(nurseyBookFilePath);
        userPrefs.setNurseyBookFilePath(nurseyBookFilePath);
    }

    //=========== NurseyBook ================================================================================

    @Override
    public void setVersionedNurseyBook(ReadOnlyNurseyBook versionedNurseyBook) {
        this.versionedNurseyBook.resetData(versionedNurseyBook);
    }

    @Override
    public ReadOnlyNurseyBook getVersionedNurseyBook() {
        return versionedNurseyBook;
    }

    @Override
    public boolean hasElderly(Elderly elderly) {
        requireNonNull(elderly);
        return versionedNurseyBook.hasElderly(elderly);
    }

    @Override
    public boolean hasTask(Task t) {
        requireNonNull(t);
        return versionedNurseyBook.hasTask(t);
    }

    @Override
    public void markTaskAsDone(Task target) {
        versionedNurseyBook.markTaskAsDone(target);
    }

    @Override
    public void markTaskAsOverdue(Task target) {
        versionedNurseyBook.markTaskAsOverdue(target);
    }

    @Override
    public void markTaskAsNotOverdue(Task target) {
        versionedNurseyBook.markTaskAsNotOverdue(target);
    }

    @Override
    public boolean areAllElderliesPresent(Set<Name> names) {
        for (Name name: names) {
            boolean hasElderly = filteredElderlies.stream().anyMatch(
                elderly -> elderly.getName().caseInsensitiveEquals(name));
            if (!hasElderly) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void deleteElderly(Elderly target) {
        versionedNurseyBook.removeElderly(target);
    }

    @Override
    public void deleteTask(Task target) {
        versionedNurseyBook.removeTask(target);
    }

    @Override
    public void addElderly(Elderly elderly) {
        versionedNurseyBook.addElderly(elderly);
        updateFilteredElderlyList(PREDICATE_SHOW_ALL_ELDERLIES);
    }

    @Override
    public void addTask(Task task) {
        versionedNurseyBook.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void setElderly(Elderly target, Elderly editedElderly) {
        requireAllNonNull(target, editedElderly);
        versionedNurseyBook.setElderly(target, editedElderly);
    }

    @Override
    public void updateElderlyNameInTasks(Elderly target, Elderly editedElderly) {
        requireAllNonNull(target, editedElderly);
        versionedNurseyBook.updateElderlyNameInTasks(target, editedElderly);
    }

    @Override
    public void deleteElderlyNameInTasks(Elderly elderlyToDelete) {
        requireNonNull(elderlyToDelete);
        versionedNurseyBook.deleteElderlyNameInTasks(elderlyToDelete);
    }


    @Override
    public void deleteGhostTasks() {
        versionedNurseyBook.deleteGhostTasks();
    }

    @Override
    public void addPossibleGhostTasksWithMatchingDate(LocalDate keyDate) {
        versionedNurseyBook.addPossibleGhostTasksWithMatchingDate(keyDate);
    }

    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);
        versionedNurseyBook.setTask(target, editedTask);
    }

    @Override
    public void commitNurseyBook(CommandResult commandResult) {
        versionedNurseyBook.commit(commandResult);
    }

    @Override
    public CommandResult undoNurseyBook() {
        CommandResult commandResult = versionedNurseyBook.getCurrentCommandResult();
        versionedNurseyBook.undo();
        return commandResult;
    }

    @Override
    public boolean canUndoNurseyBook() {
        return versionedNurseyBook.canUndo();
    }

    @Override
    public CommandResult redoNurseyBook() {
        versionedNurseyBook.redo();
        return versionedNurseyBook.getCurrentCommandResult();
    }

    @Override
    public boolean canRedoNurseyBook() {
        return versionedNurseyBook.canRedo();
    }

    //=========== Elderly of interest Accessors =============================================================
    public void setElderlyOfInterest(Elderly e) {
        requireNonNull(e);
        this.elderlyOfInterest = e;
    }

    public Elderly getElderlyOfInterest() {
        return elderlyOfInterest;
    }

    //=========== Filtered Lists Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Elderly} backed by the internal list of
     * {@code versionedNurseyBook}
     */
    @Override
    public ObservableList<Elderly> getFilteredElderlyList() {
        return filteredElderlies;
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return filteredTasks;
    }

    @Override
    public void updateFilteredElderlyList(Predicate<Elderly> predicate) {
        requireNonNull(predicate);
        filteredElderlies.setPredicate(predicate);
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        updateOverdueTaskList();
        updateNotOverdueTaskList();
        updateDateRecurringTaskList();
        filteredTasks.setPredicate(predicate);
        filteredTasks.setPredicate(predicate);
    }
    @Override
    public void updateOverdueTaskList() {
        Predicate<Task> predicate = new TaskIsOverduePredicate();
        filteredTasks.setPredicate(predicate);
        filteredTasks.forEach(this::markTaskAsOverdue);

    }

    @Override
    public void updateNotOverdueTaskList() {
        Predicate<Task> predicate = new TaskIsNotOverduePredicate();
        filteredTasks.setPredicate(predicate);
        filteredTasks.forEach(this::markTaskAsNotOverdue);
    }

    @Override
    public void updateDateRecurringTaskList() {
        Predicate<Task> predicate = new TaskIsRecurringAndOverduePredicate();
        filteredTasks.setPredicate(predicate);

        // The below for loop is not replaceable with enhanced for loop because changes made to the datetime of the
        // task will cause it to disappear from filteredTask, leading to some error.
        // anyone is welcome to fix this bug :)
        for (int i = 0; i < filteredTasks.size(); i++) {
            Task t = filteredTasks.get(i);
            if (t.isTaskRecurringAndOverdue()) {
                versionedNurseyBook.updateDateRecurringTask(t);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;

        boolean areElderlyOfInterestsEqual = elderlyOfInterest == null && other.elderlyOfInterest == null
                ? true
                : elderlyOfInterest == null || other.elderlyOfInterest == null
                    ? false
                    : elderlyOfInterest.equals(other.elderlyOfInterest);
        return areElderlyOfInterestsEqual
                && versionedNurseyBook.equals(other.versionedNurseyBook)
                && userPrefs.equals(other.userPrefs)
                && filteredElderlies.equals(other.filteredElderlies)
                && filteredTasks.equals(other.filteredTasks);
    }

}
