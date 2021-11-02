package nurseybook.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import nurseybook.commons.exceptions.DataConversionException;
import nurseybook.model.ReadOnlyNurseyBook;
import nurseybook.model.ReadOnlyUserPrefs;
import nurseybook.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends NurseyBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getNurseyBookFilePath();

    @Override
    Optional<ReadOnlyNurseyBook> readNurseyBook() throws DataConversionException, IOException;

    @Override
    void saveNurseyBook(ReadOnlyNurseyBook nurseyBook) throws IOException;

}
