package nurseybook.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static nurseybook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nurseybook.logic.commands.CommandTestUtil;
import nurseybook.testutil.Assert;
import nurseybook.testutil.ElderlyBuilder;
import nurseybook.testutil.TypicalElderlies;

public class ElderlyTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Elderly elderly = new ElderlyBuilder().build();
        Assert.assertThrows(UnsupportedOperationException.class, () -> elderly.getTags().remove(0));
    }

    @Test
    public void isSameElderly() {
        // same object -> returns true
        Assertions.assertTrue(TypicalElderlies.ALICE.isSameElderly(TypicalElderlies.ALICE));

        // null -> returns false
        Assertions.assertFalse(TypicalElderlies.ALICE.isSameElderly(null));

        // same name, all other attributes different -> returns true
        Elderly editedAlice = new ElderlyBuilder(TypicalElderlies.ALICE).withAge(CommandTestUtil.VALID_AGE_BOB).withGender(CommandTestUtil.VALID_GENDER_BOB)
                .withRoomNumber(CommandTestUtil.VALID_ROOM_NUMBER_BOB).withNokName(CommandTestUtil.VALID_NOK_NAME_BOB)
                .withRelationship(CommandTestUtil.VALID_NOK_RELATIONSHIP_BOB).withPhone(CommandTestUtil.VALID_NOK_PHONE_BOB)
                .withEmail(CommandTestUtil.VALID_NOK_EMAIL_BOB).withAddress(CommandTestUtil.VALID_NOK_ADDRESS_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        Assertions.assertTrue(TypicalElderlies.ALICE.isSameElderly(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new ElderlyBuilder(TypicalElderlies.ALICE).withName(CommandTestUtil.VALID_NAME_BOB).build();
        Assertions.assertFalse(TypicalElderlies.ALICE.isSameElderly(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Elderly editedBob = new ElderlyBuilder(TypicalElderlies.BOB).withName(CommandTestUtil.VALID_NAME_BOB.toLowerCase()).build();
        Assertions.assertFalse(TypicalElderlies.BOB.isSameElderly(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = CommandTestUtil.VALID_NAME_BOB + " ";
        editedBob = new ElderlyBuilder(TypicalElderlies.BOB).withName(nameWithTrailingSpaces).build();
        Assertions.assertFalse(TypicalElderlies.BOB.isSameElderly(editedBob));
    }

    @Test
    public void hasName() {
        Assertions.assertTrue(TypicalElderlies.ALICE.hasName(new Name("Alice Pauline")));
        Assertions.assertTrue(TypicalElderlies.BOB.hasName(new Name("Bob Choo")));

        Assertions.assertFalse(TypicalElderlies.ALICE.hasName(new Name("Ronald Wes")));
        Assertions.assertFalse(TypicalElderlies.ALICE.hasName(new Name("Alice Paltrow")));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Elderly aliceCopy = new ElderlyBuilder(TypicalElderlies.ALICE).build();
        Assertions.assertTrue(TypicalElderlies.ALICE.equals(aliceCopy));

        // same object -> returns true
        Assertions.assertTrue(TypicalElderlies.ALICE.equals(TypicalElderlies.ALICE));

        // null -> returns false
        Assertions.assertFalse(TypicalElderlies.ALICE.equals(null));

        // different type -> returns false
        Assertions.assertFalse(TypicalElderlies.ALICE.equals(5));

        // different elderly -> returns false
        Assertions.assertFalse(TypicalElderlies.ALICE.equals(TypicalElderlies.BOB));

        // different name -> returns false
        Elderly editedAlice = new ElderlyBuilder(TypicalElderlies.ALICE).withName(CommandTestUtil.VALID_NAME_BOB).build();
        Assertions.assertFalse(TypicalElderlies.ALICE.equals(editedAlice));

        // different roomNumber -> returns false
        editedAlice = new ElderlyBuilder(TypicalElderlies.ALICE).withRoomNumber(CommandTestUtil.VALID_ROOM_NUMBER_BOB).build();
        Assertions.assertFalse(TypicalElderlies.ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ElderlyBuilder(TypicalElderlies.ALICE).withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        Assertions.assertFalse(TypicalElderlies.ALICE.equals(editedAlice));
    }
}
