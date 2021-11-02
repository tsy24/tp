package nurseybook.model;

import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_ADDRESS_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static nurseybook.testutil.Assert.assertThrows;
import static nurseybook.testutil.TypicalElderlies.ALICE;
import static nurseybook.testutil.TypicalElderlies.getTypicalNurseyBook;
import static nurseybook.testutil.TypicalTasks.DO_PAPERWORK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nurseybook.model.person.Elderly;
import nurseybook.model.person.exceptions.DuplicateElderlyException;
import nurseybook.model.task.Task;
import nurseybook.testutil.ElderlyBuilder;

public class NurseyBookTest {

    private final NurseyBook nurseyBook = new NurseyBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), nurseyBook.getElderlyList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> nurseyBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyNurseyBook_replacesData() {
        NurseyBook newData = getTypicalNurseyBook();
        nurseyBook.resetData(newData);
        assertEquals(newData, nurseyBook);
    }

    @Test
    public void resetData_withDuplicateElderlies_throwsDuplicateElderlyException() {
        // Two elderlies with the same identity fields
        Elderly editedAlice = new ElderlyBuilder(ALICE).withAddress(VALID_NOK_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Elderly> newElderlies = Arrays.asList(ALICE, editedAlice);
        NurseyBookStub newData = new NurseyBookStub(newElderlies);

        assertThrows(DuplicateElderlyException.class, () -> nurseyBook.resetData(newData));
    }

    @Test
    public void hasElderly_nullElderly_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> nurseyBook.hasElderly(null));
    }

    @Test
    public void hasElderly_elderlyNotInNurseyBook_returnsFalse() {
        assertFalse(nurseyBook.hasElderly(ALICE));
    }

    @Test
    public void hasElderly_elderlyInNurseyBook_returnsTrue() {
        nurseyBook.addElderly(ALICE);
        assertTrue(nurseyBook.hasElderly(ALICE));
    }

    @Test
    public void hasElderly_elderlyWithSameIdentityFieldsInNurseyBook_returnsTrue() {
        nurseyBook.addElderly(ALICE);
        Elderly editedAlice = new ElderlyBuilder(ALICE).withAddress(VALID_NOK_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(nurseyBook.hasElderly(editedAlice));
    }

    @Test
    public void hasTask_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> nurseyBook.hasTask(null));
    }

    @Test
    public void hasTask_taskNotInNurseyBook_returnsFalse() {
        assertFalse(nurseyBook.hasTask(DO_PAPERWORK));
    }

    @Test
    public void hasTask_taskInNurseyBook_returnsTrue() {
        nurseyBook.addTask(DO_PAPERWORK);
        assertTrue(nurseyBook.hasTask(DO_PAPERWORK));
    }

    @Test
    public void getElderlyList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> nurseyBook.getElderlyList().remove(0));
    }

    @Test
    public void getTaskList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> nurseyBook.getTaskList().remove(0));
    }

    /**
     * A stub ReadOnlyNurseyBook whose elderlies list can violate interface constraints.
     */
    private static class NurseyBookStub implements ReadOnlyNurseyBook {
        private final ObservableList<Elderly> elderlies = FXCollections.observableArrayList();
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();

        NurseyBookStub(Collection<Elderly> elderlies) {
            this.elderlies.setAll(elderlies);
        }

        @Override
        public ObservableList<Elderly> getElderlyList() {
            return elderlies;
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }

        @Override
        public ObservableList<Task> getRealTaskList() {
            //TODO Is this wrong?
            return tasks.stream().filter(Task::checkIfRealTask)
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }

    }

}
