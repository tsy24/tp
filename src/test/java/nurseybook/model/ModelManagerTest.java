package nurseybook.model;

import static nurseybook.model.Model.PREDICATE_SHOW_ALL_ELDERLIES;
import static nurseybook.testutil.Assert.assertThrows;
import static nurseybook.testutil.TypicalElderlies.ALICE;
import static nurseybook.testutil.TypicalElderlies.BENSON;
import static nurseybook.testutil.TypicalElderlies.BOB;
import static nurseybook.testutil.TypicalTasks.GEORGE_INSULIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.GuiSettings;
import nurseybook.model.person.Name;
import nurseybook.model.person.NameContainsKeywordsPredicate;
import nurseybook.testutil.NurseyBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new NurseyBook(), new NurseyBook(modelManager.getVersionedNurseyBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setNurseyBookFilePath(Paths.get("nurseybook/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setNurseyBookFilePath(Paths.get("new/nurseybook/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setNurseyBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setNurseyBookFilePath(null));
    }

    @Test
    public void setNurseyBookFilePath_validPath_setsNurseyBookFilePath() {
        Path path = Paths.get("nurseybook/book/file/path");
        modelManager.setNurseyBookFilePath(path);
        assertEquals(path, modelManager.getNurseyBookFilePath());
    }

    @Test
    public void hasElderly_nullElderly_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasElderly(null));
    }

    @Test
    public void hasTask_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasTask(null));
    }

    @Test
    public void hasElderly_elderlyNotInNurseyBook_returnsFalse() {
        assertFalse(modelManager.hasElderly(ALICE));
    }

    @Test
    public void hasTask_taskNotInNurseyBook_returnsFalse() {
        assertFalse(modelManager.hasTask(GEORGE_INSULIN));
    }

    @Test
    public void hasElderly_elderlyInNurseyBook_returnsTrue() {
        modelManager = new ModelManager();
        modelManager.addElderly(ALICE);
        assertTrue(modelManager.hasElderly(ALICE));
    }

    @Test
    public void hasTask_taskInNurseyBook_returnsTrue() {
        modelManager.addTask(GEORGE_INSULIN);
        assertTrue(modelManager.hasTask(GEORGE_INSULIN));
    }

    @Test
    public void areAllElderliesPresent_elderliesPresent_returnsTrue() {
        //name is different casing as the elderly entry
        modelManager = new ModelManager();
        modelManager.addElderly(ALICE);
        modelManager.addElderly(BOB);

        Set<Name> namesToTest = new HashSet<>();
        namesToTest.add(new Name("alICE paULIne"));
        namesToTest.add(new Name("bob CHOO"));
        assertTrue(modelManager.areAllElderliesPresent(namesToTest));
    }

    @Test
    public void areAllElderliesPresent_elderliesAbsent_returnsFalse() {
        //name is different casing as the elderly entry
        modelManager = new ModelManager();
        Set<Name> namesToTest = new HashSet<>();
        namesToTest.add(new Name("claire"));
        assertFalse(modelManager.areAllElderliesPresent(namesToTest));
    }

    @Test
    public void findElderlyWithName_nameDiffCase_returnsElderly() {
        //name is different casing as the elderly entry
        modelManager = new ModelManager();
        modelManager.addElderly(ALICE);
        assertTrue(modelManager.findElderlyWithName(new Name("alICe paULine")).equals(ALICE));
    }

    @Test
    public void findElderlyWithName_differentName_returnsNull() {
        //name is does not match any elderly
        modelManager = new ModelManager();
        assertTrue(modelManager.findElderlyWithName(new Name("george")) == null);
    }

    @Test
    public void getFilteredElderlyList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredElderlyList().remove(0));
    }

    @Test
    public void getFilteredTaskList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredTaskList().remove(0));
    }

    @Test
    public void equals() {
        NurseyBook nurseyBook = new NurseyBookBuilder().withElderly(ALICE).withElderly(BENSON).build();
        NurseyBook differentNurseyBook = new NurseyBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(nurseyBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(nurseyBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different nurseyBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentNurseyBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredElderlyList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(nurseyBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredElderlyList(PREDICATE_SHOW_ALL_ELDERLIES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setNurseyBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(nurseyBook, differentUserPrefs)));
    }
}
