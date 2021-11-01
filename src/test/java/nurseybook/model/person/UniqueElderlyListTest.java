package nurseybook.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static nurseybook.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import nurseybook.logic.commands.CommandTestUtil;
import nurseybook.model.person.exceptions.DuplicateElderlyException;
import nurseybook.model.person.exceptions.ElderlyNotFoundException;
import nurseybook.testutil.Assert;
import nurseybook.testutil.ElderlyBuilder;
import nurseybook.testutil.TypicalElderlies;

public class UniqueElderlyListTest {

    private final UniqueElderlyList uniqueElderlyList = new UniqueElderlyList();

    @Test
    public void contains_nullElderly_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueElderlyList.contains(null));
    }

    @Test
    public void contains_elderlyNotInList_returnsFalse() {
        assertFalse(uniqueElderlyList.contains(TypicalElderlies.ALICE));
    }

    @Test
    public void contains_elderlyInList_returnsTrue() {
        uniqueElderlyList.add(TypicalElderlies.ALICE);
        assertTrue(uniqueElderlyList.contains(TypicalElderlies.ALICE));
    }

    @Test
    public void contains_elderlyWithSameIdentityFieldsInList_returnsTrue() {
        uniqueElderlyList.add(TypicalElderlies.ALICE);
        Elderly editedAlice = new ElderlyBuilder(TypicalElderlies.ALICE).withAddress(CommandTestUtil.VALID_NOK_ADDRESS_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueElderlyList.contains(editedAlice));
    }

    @Test
    public void add_nullElderly_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueElderlyList.add(null));
    }

    @Test
    public void add_duplicateElderly_throwsDuplicateElderlyException() {
        uniqueElderlyList.add(TypicalElderlies.ALICE);
        Assert.assertThrows(DuplicateElderlyException.class, () -> uniqueElderlyList.add(TypicalElderlies.ALICE));
    }

    @Test
    public void setElderly_nullTargetElderly_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueElderlyList.setElderly(null, TypicalElderlies.ALICE));
    }

    @Test
    public void setElderly_nullEditedElderly_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueElderlyList.setElderly(TypicalElderlies.ALICE, null));
    }

    @Test
    public void setElderly_targetElderlyNotInList_throwsElderlyNotFoundException() {
        Assert.assertThrows(ElderlyNotFoundException.class, () -> uniqueElderlyList.setElderly(TypicalElderlies.ALICE, TypicalElderlies.ALICE));
    }

    @Test
    public void setElderly_editedElderlyIsSameElderly_success() {
        uniqueElderlyList.add(TypicalElderlies.ALICE);
        uniqueElderlyList.setElderly(TypicalElderlies.ALICE, TypicalElderlies.ALICE);
        UniqueElderlyList expectedUniqueElderlyList = new UniqueElderlyList();
        expectedUniqueElderlyList.add(TypicalElderlies.ALICE);
        assertEquals(expectedUniqueElderlyList, uniqueElderlyList);
    }

    @Test
    public void setElderly_editedElderlyHasSameIdentity_success() {
        uniqueElderlyList.add(TypicalElderlies.ALICE);
        Elderly editedAlice = new ElderlyBuilder(TypicalElderlies.ALICE).withAddress(CommandTestUtil.VALID_NOK_ADDRESS_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        uniqueElderlyList.setElderly(TypicalElderlies.ALICE, editedAlice);
        UniqueElderlyList expectedUniqueElderlyList = new UniqueElderlyList();
        expectedUniqueElderlyList.add(editedAlice);
        assertEquals(expectedUniqueElderlyList, uniqueElderlyList);
    }

    @Test
    public void setElderly_editedElderlyHasDifferentIdentity_success() {
        uniqueElderlyList.add(TypicalElderlies.ALICE);
        uniqueElderlyList.setElderly(TypicalElderlies.ALICE, TypicalElderlies.BOB);
        UniqueElderlyList expectedUniqueElderlyList = new UniqueElderlyList();
        expectedUniqueElderlyList.add(TypicalElderlies.BOB);
        assertEquals(expectedUniqueElderlyList, uniqueElderlyList);
    }

    @Test
    public void setElderly_editedElderlyHasNonUniqueIdentity_throwsDuplicateElderlyException() {
        uniqueElderlyList.add(TypicalElderlies.ALICE);
        uniqueElderlyList.add(TypicalElderlies.BOB);
        Assert.assertThrows(DuplicateElderlyException.class, () -> uniqueElderlyList.setElderly(TypicalElderlies.ALICE, TypicalElderlies.BOB));
    }

    @Test
    public void remove_nullElderly_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueElderlyList.remove(null));
    }

    @Test
    public void remove_elderlyDoesNotExist_throwsElderlyNotFoundException() {
        Assert.assertThrows(ElderlyNotFoundException.class, () -> uniqueElderlyList.remove(TypicalElderlies.ALICE));
    }

    @Test
    public void remove_existingElderly_removesElderly() {
        uniqueElderlyList.add(TypicalElderlies.ALICE);
        uniqueElderlyList.remove(TypicalElderlies.ALICE);
        UniqueElderlyList expectedUniqueElderlyList = new UniqueElderlyList();
        assertEquals(expectedUniqueElderlyList, uniqueElderlyList);
    }

    @Test
    public void setElderlies_nullUniqueElderlyList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueElderlyList.setElderlies((UniqueElderlyList) null));
    }

    @Test
    public void setElderlies_uniqueElderlyList_replacesOwnListWithProvidedUniqueElderlyList() {
        uniqueElderlyList.add(TypicalElderlies.ALICE);
        UniqueElderlyList expectedUniqueElderlyList = new UniqueElderlyList();
        expectedUniqueElderlyList.add(TypicalElderlies.BOB);
        uniqueElderlyList.setElderlies(expectedUniqueElderlyList);
        assertEquals(expectedUniqueElderlyList, uniqueElderlyList);
    }

    @Test
    public void setElderlies_nullList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueElderlyList.setElderlies((List<Elderly>) null));
    }

    @Test
    public void setElderlies_list_replacesOwnListWithProvidedList() {
        uniqueElderlyList.add(TypicalElderlies.ALICE);
        List<Elderly> elderlyList = Collections.singletonList(TypicalElderlies.BOB);
        uniqueElderlyList.setElderlies(elderlyList);
        UniqueElderlyList expectedUniqueElderlyList = new UniqueElderlyList();
        expectedUniqueElderlyList.add(TypicalElderlies.BOB);
        assertEquals(expectedUniqueElderlyList, uniqueElderlyList);
    }

    @Test
    public void setElderlies_listWithDuplicateElderlies_throwsDuplicateElderlyException() {
        List<Elderly> listWithDuplicateElderlies = Arrays.asList(TypicalElderlies.ALICE, TypicalElderlies.ALICE);
        Assert.assertThrows(DuplicateElderlyException.class, () -> uniqueElderlyList.setElderlies(listWithDuplicateElderlies));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, ()
            -> uniqueElderlyList.asUnmodifiableObservableList().remove(0));
    }
}
