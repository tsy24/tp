package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROOM_NUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROOM_NUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Elderly;

/**
 * A utility class containing a list of {@code Elderly} objects to be used in tests.
 */
public class TypicalElderlies {

    public static final Elderly ALICE = new ElderlyBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withAge("40").withPhone("94351253").withGender("F").withRoomNumber("15")
            .withRemark("She likes aardvarks.").withTags("friends").build();
    public static final Elderly BENSON = new ElderlyBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withRemark("He can't take beer!")
            .withAge("40").withGender("M").withEmail("johnd@example.com").withPhone("98765432")
            .withRoomNumber("21").withTags("owesMoney", "friends").build();
    public static final Elderly CARL = new ElderlyBuilder().withName("Carl Kurz").withPhone("95352563")
            .withAge("40").withGender("M").withRoomNumber("9").withEmail("heinz@example.com")
            .withAddress("wall street").build();
    public static final Elderly DANIEL = new ElderlyBuilder().withName("Daniel Meier").withPhone("87652533")
            .withAge("40").withGender("M").withRoomNumber("27").withEmail("cornelia@example.com")
            .withAddress("10th street").withTags("friends").build();
    public static final Elderly ELLE = new ElderlyBuilder().withName("Elle Meyer").withPhone("9482224")
            .withAge("40").withGender("F").withRoomNumber("11").withEmail("werner@example.com")
            .withAddress("michegan ave").build();
    public static final Elderly FIONA = new ElderlyBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withAge("40").withGender("F").withRoomNumber("6").withEmail("lydia@example.com")
            .withAddress("little tokyo").build();
    public static final Elderly GEORGE = new ElderlyBuilder().withName("George Best").withPhone("9482442")
            .withAge("40").withGender("M").withRoomNumber("299").withEmail("anna@example.com")
            .withAddress("4th street").build();

    // Manually added
    public static final Elderly HOON = new ElderlyBuilder().withName("Hoon Meier").withPhone("8482424")
            .withRoomNumber("77").withEmail("stefan@example.com").withAddress("little india").build();
    public static final Elderly IDA = new ElderlyBuilder().withName("Ida Mueller").withPhone("8482131")
            .withRoomNumber("82").withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Elderly's details found in {@code CommandTestUtil}
    public static final Elderly AMY = new ElderlyBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withAge(VALID_AGE_AMY).withGender(VALID_GENDER_AMY).withRoomNumber(VALID_ROOM_NUMBER_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Elderly BOB = new ElderlyBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withAge(VALID_AGE_BOB).withGender(VALID_GENDER_BOB).withRoomNumber(VALID_ROOM_NUMBER_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalElderlies() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical elderlies.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Elderly elderly : getTypicalElderlies()) {
            ab.addElderly(elderly);
        }
        return ab;
    }

    public static List<Elderly> getTypicalElderlies() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
