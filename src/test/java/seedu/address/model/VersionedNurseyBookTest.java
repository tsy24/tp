package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalElderlies.AMY;
import static seedu.address.testutil.TypicalElderlies.BOB;
import static seedu.address.testutil.TypicalElderlies.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.testutil.AddressBookBuilder;

public class VersionedNurseyBookTest {

    private final ReadOnlyAddressBook addressBookInitial = getTypicalAddressBook();
    private final ReadOnlyAddressBook addressBookWithAmy = new AddressBookBuilder().withElderly(AMY).build();
    private final ReadOnlyAddressBook addressBookWithBob = new AddressBookBuilder().withElderly(BOB).build();
    private final CommandResult dummyCommandResult = new CommandResult("feedback");

    @Test
    public void commit_initialState_noStatesRemovedCurrentStateSaved() {
        VersionedNurseyBook versionedNurseyBook = new VersionedNurseyBook(addressBookInitial);

        versionedNurseyBook.commit(dummyCommandResult);
        assertAddressBookListStatus(versionedNurseyBook,
                Collections.singletonList(addressBookInitial),
                addressBookInitial,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleStatesPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedNurseyBook versionedNurseyBook = initialiseMultipleStatesVersionedNurseyBook();

        versionedNurseyBook.commit(dummyCommandResult);
        assertAddressBookListStatus(versionedNurseyBook,
                Arrays.asList(addressBookInitial, addressBookWithAmy, addressBookWithBob),
                addressBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleStatesPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedNurseyBook versionedAddressBook = initialiseMultipleStatesVersionedNurseyBook();
        versionedAddressBook.undo();

        versionedAddressBook.commit(dummyCommandResult);
        assertAddressBookListStatus(versionedAddressBook,
                Arrays.asList(addressBookInitial, addressBookWithAmy),
                addressBookWithAmy,
                Collections.emptyList());
    }

    //=========== tests for undo =============================================================

    @Test
    public void canUndo_multipleStatesPointerAtEndOfStateList_returnsTrue() {
        VersionedNurseyBook versionedNurseyBook = initialiseMultipleStatesVersionedNurseyBook();

        assertTrue(versionedNurseyBook.canUndo());
    }

    @Test
    public void canUndo_multipleStatesPointerAtMiddleOfStateList_returnsTrue() {
        VersionedNurseyBook versionedNurseyBook = initialiseMultipleStatesVersionedNurseyBook();
        versionedNurseyBook.undo();

        assertTrue(versionedNurseyBook.canUndo());
    }

    @Test
    public void canUndo_initialState_returnsFalse() {
        VersionedNurseyBook versionedNurseyBook = new VersionedNurseyBook(getTypicalAddressBook());

        assertFalse(versionedNurseyBook.canUndo());
    }

    @Test
    public void canUndo_multipleStatesPointerAtStartOfStateList_returnsFalse() {
        VersionedNurseyBook versionedNurseyBook = initialiseMultipleStatesVersionedNurseyBook();
        versionedNurseyBook.undo();
        versionedNurseyBook.undo();

        assertFalse(versionedNurseyBook.canUndo());
    }

    @Test
    public void undo_multipleStatesPointerAtEndOfStateList_success() {
        VersionedNurseyBook versionedNurseyBook = initialiseMultipleStatesVersionedNurseyBook();

        versionedNurseyBook.undo();
        assertAddressBookListStatus(versionedNurseyBook,
                Collections.singletonList(addressBookInitial),
                addressBookWithAmy,
                Collections.singletonList(addressBookWithBob));
    }

    @Test
    public void undo_multipleStatesPointerNotAtStartOfStateList_success() {
        VersionedNurseyBook versionedNurseyBook = initialiseMultipleStatesVersionedNurseyBook();
        versionedNurseyBook.undo();

        versionedNurseyBook.undo();
        assertAddressBookListStatus(versionedNurseyBook,
                Collections.emptyList(),
                addressBookInitial,
                Arrays.asList(addressBookWithAmy, addressBookWithBob));
    }

    @Test
    public void undo_initialState_throwsNoUndoableStateException() {
        VersionedNurseyBook versionedNurseyBook = new VersionedNurseyBook(addressBookInitial);

        assertThrows(VersionedNurseyBook.NoUndoableStateException.class, versionedNurseyBook::undo);
    }

    @Test
    public void undo_multipleStatesPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedNurseyBook versionedNurseyBook = initialiseMultipleStatesVersionedNurseyBook();
        versionedNurseyBook.undo();
        versionedNurseyBook.undo();

        assertThrows(VersionedNurseyBook.NoUndoableStateException.class, versionedNurseyBook::undo);
    }

    //=========== tests for redo =============================================================

    @Test
    public void canRedo_multipleStatesPointerAtEndOfStateList_returnsFalse() {
        VersionedNurseyBook versionedNurseyBook = initialiseMultipleStatesVersionedNurseyBook();

        assertFalse(versionedNurseyBook.canRedo());
    }

    @Test
    public void canRedo_multipleStatesPointerAtMiddleOfStateList_returnsTrue() {
        VersionedNurseyBook versionedNurseyBook = initialiseMultipleStatesVersionedNurseyBook();
        versionedNurseyBook.undo();

        assertTrue(versionedNurseyBook.canRedo());
    }

    @Test
    public void canRedo_initialState_returnsFalse() {
        VersionedNurseyBook versionedNurseyBook = new VersionedNurseyBook(getTypicalAddressBook());

        assertFalse(versionedNurseyBook.canRedo());
    }

    @Test
    public void canRedo_multipleStatesPointerAtStartOfStateList_returnsTrue() {
        VersionedNurseyBook versionedNurseyBook = initialiseMultipleStatesVersionedNurseyBook();
        versionedNurseyBook.undo();
        versionedNurseyBook.undo();

        assertTrue(versionedNurseyBook.canRedo());
    }

    @Test
    public void redo_multipleStatesPointerAtStartOfStateList_success() {
        VersionedNurseyBook versionedNurseyBook = initialiseMultipleStatesVersionedNurseyBook();
        versionedNurseyBook.undo();
        versionedNurseyBook.undo();

        versionedNurseyBook.redo();
        assertAddressBookListStatus(versionedNurseyBook,
                Collections.singletonList(addressBookInitial),
                addressBookWithAmy,
                Collections.singletonList(addressBookWithBob));
    }

    @Test
    public void redo_multipleStatesPointerNotAtStartOfStateList_success() {
        VersionedNurseyBook versionedNurseyBook = initialiseMultipleStatesVersionedNurseyBook();
        versionedNurseyBook.undo();

        versionedNurseyBook.redo();
        assertAddressBookListStatus(versionedNurseyBook,
                Arrays.asList(addressBookInitial, addressBookWithAmy),
                addressBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_initialState_throwsNoRedoableStateException() {
        VersionedNurseyBook versionedNurseyBook = new VersionedNurseyBook(addressBookInitial);

        assertThrows(VersionedNurseyBook.NoRedoableStateException.class, versionedNurseyBook::redo);
    }

    @Test
    public void redo_multipleStatesPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedNurseyBook versionedNurseyBook = initialiseMultipleStatesVersionedNurseyBook();

        assertThrows(VersionedNurseyBook.NoRedoableStateException.class, versionedNurseyBook::redo);
    }


    private VersionedNurseyBook initialiseMultipleStatesVersionedNurseyBook() {
        VersionedNurseyBook versionedNurseyBook = new VersionedNurseyBook(getTypicalAddressBook());
        versionedNurseyBook.resetData(addressBookWithAmy);
        versionedNurseyBook.commit(dummyCommandResult);
        versionedNurseyBook.resetData(addressBookWithBob);
        versionedNurseyBook.commit(dummyCommandResult);
        return versionedNurseyBook;
    }

    @Test
    public void equals() {
        VersionedNurseyBook versionedNurseyBook = initialiseMultipleStatesVersionedNurseyBook();

        // same object -> returns true
        assertTrue(versionedNurseyBook.equals(versionedNurseyBook));

        // same values -> returns true
        VersionedNurseyBook versionedNurseyBookCopy = initialiseMultipleStatesVersionedNurseyBook();
        assertTrue(versionedNurseyBook.equals(versionedNurseyBookCopy));

        // different types -> returns false
        assertFalse(versionedNurseyBook.equals(1));

        // null -> returns false
        assertFalse(versionedNurseyBook.equals(null));

        // different nurseyBookStateList -> returns false
        VersionedNurseyBook differentVersionedNurseyBook = new VersionedNurseyBook(addressBookInitial);
        assertFalse(versionedNurseyBook.equals(differentVersionedNurseyBook));

        // different currentStateIndex -> returns false
        versionedNurseyBookCopy.undo();
        assertFalse(versionedNurseyBook.equals(versionedNurseyBookCopy));
    }

    /**
     * Asserts that {@code versionedAddressBook} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedAddressBook#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedAddressBook#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertAddressBookListStatus(VersionedNurseyBook versionedNurseyBook,
                                             List<ReadOnlyAddressBook> expectedStatesBeforePointer,
                                             ReadOnlyAddressBook expectedCurrentState,
                                             List<ReadOnlyAddressBook> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new AddressBook(versionedNurseyBook), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedNurseyBook.canUndo()) {
            versionedNurseyBook.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyAddressBook expectedAddressBook : expectedStatesBeforePointer) {
            assertEquals(expectedAddressBook, new AddressBook(versionedNurseyBook));
            versionedNurseyBook.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyAddressBook expectedAddressBook : expectedStatesAfterPointer) {
            versionedNurseyBook.redo();
            assertEquals(expectedAddressBook, new AddressBook(versionedNurseyBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedNurseyBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedNurseyBook.undo());
    }

}
