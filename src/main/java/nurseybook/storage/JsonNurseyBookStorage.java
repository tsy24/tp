package nurseybook.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import nurseybook.commons.core.LogsCenter;
import nurseybook.commons.exceptions.DataConversionException;
import nurseybook.commons.exceptions.IllegalValueException;
import nurseybook.commons.util.FileUtil;
import nurseybook.commons.util.JsonUtil;
import nurseybook.model.ReadOnlyNurseyBook;

/**
 * A class to access NurseyBook data stored as a json file on the hard disk.
 */
public class JsonNurseyBookStorage implements NurseyBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonNurseyBookStorage.class);

    private Path filePath;

    public JsonNurseyBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getNurseyBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyNurseyBook> readNurseyBook() throws DataConversionException {
        return readNurseyBook(filePath);
    }

    /**
     * Similar to {@link #readNurseyBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyNurseyBook> readNurseyBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableNurseyBook> jsonNurseyBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableNurseyBook.class);
        if (!jsonNurseyBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonNurseyBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveNurseyBook(ReadOnlyNurseyBook nurseyBook) throws IOException {
        saveNurseyBook(nurseyBook, filePath);
    }

    /**
     * Similar to {@link #saveNurseyBook(ReadOnlyNurseyBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveNurseyBook(ReadOnlyNurseyBook nurseyBook, Path filePath) throws IOException {
        requireNonNull(nurseyBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableNurseyBook(nurseyBook), filePath);
    }

}
