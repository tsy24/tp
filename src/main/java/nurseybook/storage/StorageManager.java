package nurseybook.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import nurseybook.commons.core.LogsCenter;
import nurseybook.commons.exceptions.DataConversionException;
import nurseybook.model.ReadOnlyNurseyBook;
import nurseybook.model.ReadOnlyUserPrefs;
import nurseybook.model.UserPrefs;

/**
 * Manages storage of NurseyBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private NurseyBookStorage nurseyBookStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code NurseyBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(NurseyBookStorage nurseyBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.nurseyBookStorage = nurseyBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ NurseyBook methods ==============================

    @Override
    public Path getNurseyBookFilePath() {
        return nurseyBookStorage.getNurseyBookFilePath();
    }

    @Override
    public Optional<ReadOnlyNurseyBook> readNurseyBook() throws DataConversionException, IOException {
        return readNurseyBook(nurseyBookStorage.getNurseyBookFilePath());
    }

    @Override
    public Optional<ReadOnlyNurseyBook> readNurseyBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return nurseyBookStorage.readNurseyBook(filePath);
    }

    @Override
    public void saveNurseyBook(ReadOnlyNurseyBook nurseyBook) throws IOException {
        saveNurseyBook(nurseyBook, nurseyBookStorage.getNurseyBookFilePath());
    }

    @Override
    public void saveNurseyBook(ReadOnlyNurseyBook nurseyBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        nurseyBookStorage.saveNurseyBook(nurseyBook, filePath);
    }

}
