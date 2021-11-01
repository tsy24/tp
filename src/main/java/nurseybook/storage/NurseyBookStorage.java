package nurseybook.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import nurseybook.commons.exceptions.DataConversionException;
import nurseybook.model.NurseyBook;
import nurseybook.model.ReadOnlyNurseyBook;

/**
 * Represents a storage for {@link NurseyBook}.
 */
public interface NurseyBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getNurseyBookFilePath();

    /**
     * Returns NurseyBook data as a {@link ReadOnlyNurseyBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyNurseyBook> readNurseyBook() throws DataConversionException, IOException;

    /**
     * @see #getNurseyBookFilePath()
     */
    Optional<ReadOnlyNurseyBook> readNurseyBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyNurseyBook} to the storage.
     * @param nurseyBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveNurseyBook(ReadOnlyNurseyBook nurseyBook) throws IOException;

    /**
     * @see #saveNurseyBook(ReadOnlyNurseyBook)
     */
    void saveNurseyBook(ReadOnlyNurseyBook nurseyBook, Path filePath) throws IOException;

}
