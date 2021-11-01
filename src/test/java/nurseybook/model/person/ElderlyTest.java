package nurseybook.model.person;

import static nurseybook.logic.commands.CommandTestUtil.VALID_AGE_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_ADDRESS_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_EMAIL_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_NAME_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_PHONE_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_RELATIONSHIP_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_ROOM_NUMBER_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static nurseybook.testutil.Assert.assertThrows;
import static nurseybook.testutil.TypicalElderlies.ALICE;
import static nurseybook.testutil.TypicalElderlies.BOB;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nurseybook.testutil.ElderlyBuilder;

public class ElderlyTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Elderly elderly = new ElderlyBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> elderly.getTags().remove(0));
    }

    @Test
    public void isSameElderly() {
        // same object -> returns true
        assertTrue(ALICE.isSameElderly(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameElderly(null));

        // same name, all other attributes different -> returns true
        Elderly editedAlice = new ElderlyBuilder(ALICE).withAge(VALID_AGE_BOB).withGender(VALID_GENDER_BOB)
                .withRoomNumber(VALID_ROOM_NUMBER_BOB).withNokName(VALID_NOK_NAME_BOB)
                .withRelationship(VALID_NOK_RELATIONSHIP_BOB).withPhone(VALID_NOK_PHONE_BOB)
                .withEmail(VALID_NOK_EMAIL_BOB).withAddress(VALID_NOK_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameElderly(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new ElderlyBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameElderly(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Elderly editedBob = new ElderlyBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameElderly(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new ElderlyBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameElderly(editedBob));
    }

    @Test
    public void hasName() {
        assertTrue(ALICE.hasName(new Name("Alice Pauline")));
        assertTrue(BOB.hasName(new Name("Bob Choo")));

        assertFalse(ALICE.hasName(new Name("Ronald Wes")));
        assertFalse(ALICE.hasName(new Name("Alice Paltrow")));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Elderly aliceCopy = new ElderlyBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different elderly -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Elderly editedAlice = new ElderlyBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different roomNumber -> returns false
        editedAlice = new ElderlyBuilder(ALICE).withRoomNumber(VALID_ROOM_NUMBER_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ElderlyBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
