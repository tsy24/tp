package nurseybook.storage;

import static nurseybook.testutil.Assert.assertThrows;
import static nurseybook.testutil.TypicalElderlies.ALICE;
import static nurseybook.testutil.TypicalElderlies.HOON;
import static nurseybook.testutil.TypicalElderlies.IDA;
import static nurseybook.testutil.TypicalTasks.KG_SC_VACCINE;
import static nurseybook.testutil.TypicalTasks.YASMINE_PHYSIO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import nurseybook.commons.exceptions.DataConversionException;
import nurseybook.model.NurseyBook;
import nurseybook.model.ReadOnlyNurseyBook;
import nurseybook.testutil.TypicalElderlies;
import nurseybook.testutil.TypicalTasks;

public class JsonNurseyBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonNurseyBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readNurseyBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readNurseyBook(null));
    }

    private java.util.Optional<ReadOnlyNurseyBook> readNurseyBook(String filePath) throws Exception {
        return new JsonNurseyBookStorage(Paths.get(filePath)).readNurseyBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readNurseyBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readNurseyBook("notJsonFormatNurseyBook.json"));
    }

    @Test
    public void readNurseyBook_invalidElderlyNurseyBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readNurseyBook("invalidElderlyNurseyBook.json"));
    }

    @Test
    public void readNurseyBook_invalidAndValidElderlyNurseyBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readNurseyBook("invalidAndValidElderlyNurseyBook.json"));
    }

    @Test
    public void readAndSavePeopleInNurseyBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempNurseyBook.json");
        NurseyBook original = TypicalElderlies.getTypicalNurseyBook();
        JsonNurseyBookStorage jsonNurseyBookStorage = new JsonNurseyBookStorage(filePath);

        // Save in new file and read back
        jsonNurseyBookStorage.saveNurseyBook(original, filePath);
        ReadOnlyNurseyBook readBack = jsonNurseyBookStorage.readNurseyBook(filePath).get();
        assertEquals(original, new NurseyBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addElderly(HOON);
        original.removeElderly(ALICE);
        jsonNurseyBookStorage.saveNurseyBook(original, filePath);
        readBack = jsonNurseyBookStorage.readNurseyBook(filePath).get();
        assertEquals(original, new NurseyBook(readBack));

        // Save and read without specifying file path
        original.addElderly(IDA);
        jsonNurseyBookStorage.saveNurseyBook(original); // file path not specified
        readBack = jsonNurseyBookStorage.readNurseyBook().get(); // file path not specified
        assertEquals(original, new NurseyBook(readBack));

    }

    @Test
    public void readAndSaveTasksInNurseyBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempNurseyBook.json");
        NurseyBook original = TypicalTasks.getTypicalNurseyBook();
        JsonNurseyBookStorage jsonNurseyBookStorage = new JsonNurseyBookStorage(filePath);

        // Save in new file and read back
        jsonNurseyBookStorage.saveNurseyBook(original, filePath);
        ReadOnlyNurseyBook readBack = jsonNurseyBookStorage.readNurseyBook(filePath).get();
        assertEquals(original, new NurseyBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addTask(YASMINE_PHYSIO);
        //original.removeTask...
        jsonNurseyBookStorage.saveNurseyBook(original, filePath);
        readBack = jsonNurseyBookStorage.readNurseyBook(filePath).get();
        assertEquals(original, new NurseyBook(readBack));

        // Save and read without specifying file path
        original.addTask(KG_SC_VACCINE);
        jsonNurseyBookStorage.saveNurseyBook(original); // file path not specified
        readBack = jsonNurseyBookStorage.readNurseyBook().get(); // file path not specified
        assertEquals(original, new NurseyBook(readBack));

    }

    @Test
    public void saveNurseyBook_nullNurseyBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveNurseyBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code nurseyBook} at the specified {@code filePath}.
     */
    private void saveNurseyBook(ReadOnlyNurseyBook nurseyBook, String filePath) {
        try {
            new JsonNurseyBookStorage(Paths.get(filePath))
                    .saveNurseyBook(nurseyBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveNurseyBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveNurseyBook(new NurseyBook(), null));
    }
}
