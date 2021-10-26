package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.person.Elderly;
import seedu.address.model.task.Task;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedNurseyBook versionedNurseyBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Elderly> filteredElderlies;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given versionedNurseyBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook versionedNurseyBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(versionedNurseyBook, userPrefs);

        logger.fine("Initializing with address book: " + versionedNurseyBook + " and user prefs " + userPrefs);

        this.versionedNurseyBook = new VersionedNurseyBook(versionedNurseyBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredElderlies = new FilteredList<>(this.versionedNurseyBook.getElderlyList());
        filteredTasks = new FilteredList<>(this.versionedNurseyBook.getTaskList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
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
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setVersionedNurseyBook(ReadOnlyAddressBook versionedNurseyBook) {
        this.versionedNurseyBook.resetData(versionedNurseyBook);
    }

    @Override
    public ReadOnlyAddressBook getVersionedNurseyBook() {
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


    //=========== Filtered Lists Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Elderly} backed by the internal list of
     * {@code versionedAddressBook}
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
        filteredTasks.setPredicate(predicate);
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
        return versionedNurseyBook.equals(other.versionedNurseyBook)
                && userPrefs.equals(other.userPrefs)
                && filteredElderlies.equals(other.filteredElderlies);
    }

}
