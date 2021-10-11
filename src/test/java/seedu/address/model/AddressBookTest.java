package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalElderlies.ALICE;
import static seedu.address.testutil.TypicalElderlies.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Elderly;
import seedu.address.model.person.exceptions.DuplicateElderlyException;
import seedu.address.model.task.Task;
import seedu.address.testutil.ElderlyBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getElderlyList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateElderlies_throwsDuplicateElderlyException() {
        // Two elderlies with the same identity fields
        Elderly editedAlice = new ElderlyBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Elderly> newElderlies = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newElderlies);

        assertThrows(DuplicateElderlyException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasElderly_nullElderly_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasElderly(null));
    }

    @Test
    public void hasElderly_elderlyNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasElderly(ALICE));
    }

    @Test
    public void hasElderly_elderlyInAddressBook_returnsTrue() {
        addressBook.addElderly(ALICE);
        assertTrue(addressBook.hasElderly(ALICE));
    }

    @Test
    public void hasElderly_elderlyWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addElderly(ALICE);
        Elderly editedAlice = new ElderlyBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasElderly(editedAlice));
    }

    @Test
    public void getElderlyList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getElderlyList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose elderlies list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Elderly> elderlies = FXCollections.observableArrayList();
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();

        AddressBookStub(Collection<Elderly> elderlies) {
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

    }

}
