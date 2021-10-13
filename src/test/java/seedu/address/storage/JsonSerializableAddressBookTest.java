package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalElderlies;
import seedu.address.testutil.TypicalTasks;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_ELDERLY_FILE = TEST_DATA_FOLDER.resolve("typicalElderliesAddressBook.json");
    private static final Path INVALID_ELDERLY_FILE = TEST_DATA_FOLDER.resolve("invalidElderlyAddressBook.json");
    private static final Path DUPLICATE_ELDERLY_FILE = TEST_DATA_FOLDER.resolve("duplicateElderlyAddressBook.json");
    private static final Path TYPICAL_TASKS_FILE = TEST_DATA_FOLDER.resolve("typicalTasksAddressBook.json");
    private static final Path INVALID_TASK_FILE = TEST_DATA_FOLDER.resolve("invalidTaskAddressBook.json");

    @Test
    public void toModelType_typicalElderliesFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_ELDERLY_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalElderliesAddressBook = TypicalElderlies.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalElderliesAddressBook);
    }

    @Test
    public void toModelType_invalidElderlyFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_ELDERLY_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateElderlies_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_ELDERLY_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_ELDERLY,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_typicalTasksFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_TASKS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalTasksAddressBook = TypicalTasks.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalTasksAddressBook);
    }

    @Test
    public void toModelType_invalidTaskFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_TASK_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

}
