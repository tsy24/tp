package nurseybook.storage;

import static nurseybook.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import nurseybook.commons.exceptions.IllegalValueException;
import nurseybook.commons.util.JsonUtil;
import nurseybook.model.NurseyBook;
import nurseybook.testutil.TypicalElderlies;
import nurseybook.testutil.TypicalTasks;

public class JsonSerializableNurseyBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableNurseyBookTest");
    private static final Path TYPICAL_ELDERLY_FILE = TEST_DATA_FOLDER.resolve("typicalElderliesNurseyBook.json");
    private static final Path INVALID_ELDERLY_FILE = TEST_DATA_FOLDER.resolve("invalidElderlyNurseyBook.json");
    private static final Path DUPLICATE_ELDERLY_FILE = TEST_DATA_FOLDER.resolve("duplicateElderlyNurseyBook.json");
    private static final Path TYPICAL_TASKS_FILE = TEST_DATA_FOLDER.resolve("typicalTasksNurseyBook.json");
    private static final Path INVALID_TASK_FILE = TEST_DATA_FOLDER.resolve("invalidTaskNurseyBook.json");

    @Test
    public void toModelType_typicalElderliesFile_success() throws Exception {
        JsonSerializableNurseyBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_ELDERLY_FILE,
                JsonSerializableNurseyBook.class).get();
        NurseyBook nurseyBookFromFile = dataFromFile.toModelType();
        NurseyBook typicalElderliesNurseyBook = TypicalElderlies.getTypicalNurseyBook();
        assertEquals(nurseyBookFromFile, typicalElderliesNurseyBook);
    }

    @Test
    public void toModelType_invalidElderlyFile_throwsIllegalValueException() throws Exception {
        JsonSerializableNurseyBook dataFromFile = JsonUtil.readJsonFile(INVALID_ELDERLY_FILE,
                JsonSerializableNurseyBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateElderlies_throwsIllegalValueException() throws Exception {
        JsonSerializableNurseyBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_ELDERLY_FILE,
                JsonSerializableNurseyBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableNurseyBook.MESSAGE_DUPLICATE_ELDERLY,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_typicalTasksFile_success() throws Exception {
        JsonSerializableNurseyBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_TASKS_FILE,
                JsonSerializableNurseyBook.class).get();
        NurseyBook nurseyBookFromFile = dataFromFile.toModelType();
        NurseyBook typicalTasksNurseyBook = TypicalTasks.getTypicalNurseyBook();
        assertEquals(nurseyBookFromFile, typicalTasksNurseyBook);
    }

    @Test
    public void toModelType_invalidTaskFile_throwsIllegalValueException() throws Exception {
        JsonSerializableNurseyBook dataFromFile = JsonUtil.readJsonFile(INVALID_TASK_FILE,
                JsonSerializableNurseyBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

}
