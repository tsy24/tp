package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalElderlies.ALICE;
import static seedu.address.testutil.TypicalElderlies.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicateElderlyException;
import seedu.address.model.person.exceptions.ElderlyNotFoundException;
import seedu.address.testutil.ElderlyBuilder;

public class UniqueElderlyListTest {

    private final UniqueElderlyList uniqueElderlyList = new UniqueElderlyList();

    @Test
    public void contains_nullElderly_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueElderlyList.contains(null));
    }

    @Test
    public void contains_elderlyNotInList_returnsFalse() {
        assertFalse(uniqueElderlyList.contains(ALICE));
    }

    @Test
    public void contains_elderlyInList_returnsTrue() {
        uniqueElderlyList.add(ALICE);
        assertTrue(uniqueElderlyList.contains(ALICE));
    }

    @Test
    public void contains_elderlyWithSameIdentityFieldsInList_returnsTrue() {
        uniqueElderlyList.add(ALICE);
        Elderly editedAlice = new ElderlyBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueElderlyList.contains(editedAlice));
    }

    @Test
    public void add_nullElderly_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueElderlyList.add(null));
    }

    @Test
    public void add_duplicateElderly_throwsDuplicateElderlyException() {
        uniqueElderlyList.add(ALICE);
        assertThrows(DuplicateElderlyException.class, () -> uniqueElderlyList.add(ALICE));
    }

    @Test
    public void setElderly_nullTargetElderly_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueElderlyList.setElderly(null, ALICE));
    }

    @Test
    public void setElderly_nullEditedElderly_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueElderlyList.setElderly(ALICE, null));
    }

    @Test
    public void setElderly_targetElderlyNotInList_throwsElderlyNotFoundException() {
        assertThrows(ElderlyNotFoundException.class, () -> uniqueElderlyList.setElderly(ALICE, ALICE));
    }

    @Test
    public void setElderly_editedElderlyIsSameElderly_success() {
        uniqueElderlyList.add(ALICE);
        uniqueElderlyList.setElderly(ALICE, ALICE);
        UniqueElderlyList expectedUniqueElderlyList = new UniqueElderlyList();
        expectedUniqueElderlyList.add(ALICE);
        assertEquals(expectedUniqueElderlyList, uniqueElderlyList);
    }

    @Test
    public void setElderly_editedElderlyHasSameIdentity_success() {
        uniqueElderlyList.add(ALICE);
        Elderly editedAlice = new ElderlyBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueElderlyList.setElderly(ALICE, editedAlice);
        UniqueElderlyList expectedUniqueElderlyList = new UniqueElderlyList();
        expectedUniqueElderlyList.add(editedAlice);
        assertEquals(expectedUniqueElderlyList, uniqueElderlyList);
    }

    @Test
    public void setElderly_editedElderlyHasDifferentIdentity_success() {
        uniqueElderlyList.add(ALICE);
        uniqueElderlyList.setElderly(ALICE, BOB);
        UniqueElderlyList expectedUniqueElderlyList = new UniqueElderlyList();
        expectedUniqueElderlyList.add(BOB);
        assertEquals(expectedUniqueElderlyList, uniqueElderlyList);
    }

    @Test
    public void setElderly_editedElderlyHasNonUniqueIdentity_throwsDuplicateElderlyException() {
        uniqueElderlyList.add(ALICE);
        uniqueElderlyList.add(BOB);
        assertThrows(DuplicateElderlyException.class, () -> uniqueElderlyList.setElderly(ALICE, BOB));
    }

    @Test
    public void remove_nullElderly_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueElderlyList.remove(null));
    }

    @Test
    public void remove_elderlyDoesNotExist_throwsElderlyNotFoundException() {
        assertThrows(ElderlyNotFoundException.class, () -> uniqueElderlyList.remove(ALICE));
    }

    @Test
    public void remove_existingElderly_removesElderly() {
        uniqueElderlyList.add(ALICE);
        uniqueElderlyList.remove(ALICE);
        UniqueElderlyList expectedUniqueElderlyList = new UniqueElderlyList();
        assertEquals(expectedUniqueElderlyList, uniqueElderlyList);
    }

    @Test
    public void setElderlies_nullUniqueElderlyList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueElderlyList.setElderlies((UniqueElderlyList) null));
    }

    @Test
    public void setElderlies_uniqueElderlyList_replacesOwnListWithProvidedUniqueElderlyList() {
        uniqueElderlyList.add(ALICE);
        UniqueElderlyList expectedUniqueElderlyList = new UniqueElderlyList();
        expectedUniqueElderlyList.add(BOB);
        uniqueElderlyList.setElderlies(expectedUniqueElderlyList);
        assertEquals(expectedUniqueElderlyList, uniqueElderlyList);
    }

    @Test
    public void setElderlies_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueElderlyList.setElderlies((List<Elderly>) null));
    }

    @Test
    public void setElderlies_list_replacesOwnListWithProvidedList() {
        uniqueElderlyList.add(ALICE);
        List<Elderly> elderlyList = Collections.singletonList(BOB);
        uniqueElderlyList.setElderlies(elderlyList);
        UniqueElderlyList expectedUniqueElderlyList = new UniqueElderlyList();
        expectedUniqueElderlyList.add(BOB);
        assertEquals(expectedUniqueElderlyList, uniqueElderlyList);
    }

    @Test
    public void setElderlies_listWithDuplicateElderlies_throwsDuplicateElderlyException() {
        List<Elderly> listWithDuplicateElderlies = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateElderlyException.class, () -> uniqueElderlyList.setElderlies(listWithDuplicateElderlies));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueElderlyList.asUnmodifiableObservableList().remove(0));
    }
}
