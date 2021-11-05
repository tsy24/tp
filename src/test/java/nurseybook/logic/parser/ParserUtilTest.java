package nurseybook.logic.parser;

import static nurseybook.logic.parser.ParserUtil.MESSAGE_INDEX_TOO_EXTREME;
import static nurseybook.testutil.Assert.assertThrows;
import static nurseybook.testutil.TypicalIndexes.INDEX_FIRST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import nurseybook.logic.parser.exceptions.ParseException;
import nurseybook.model.person.Address;
import nurseybook.model.person.Age;
import nurseybook.model.person.Email;
import nurseybook.model.person.Name;
import nurseybook.model.person.Phone;
import nurseybook.model.person.Relationship;
import nurseybook.model.person.RoomNumber;
import nurseybook.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_AGE = "88a";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_NOK_NAME = "Rach#l";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_RELATIONSHIP = "daug1el";
    private static final String INVALID_ROOM_NUMBER = "18a";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_AGE = "88";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_NOK_NAME = "Rachel Runner";
    private static final String VALID_PHONE = "99123456";
    private static final String VALID_RELATIONSHIP = "Daughter";
    private static final String VALID_ROOM_NUMBER = "67";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";


    private static final String WHITESPACE = " \t\r\n";
    private static final String ZEROS = "00000";


    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INDEX_TOO_EXTREME, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
        assertThrows(ParseException.class, MESSAGE_INDEX_TOO_EXTREME, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MIN_VALUE - 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseAge_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAge((String) null));
    }

    @Test
    public void parseAge_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAge(INVALID_AGE));
    }

    @Test
    public void parseAge_validValueWithoutWhitespace_returnsAge() throws Exception {
        Age expectedAge = new Age(VALID_AGE);
        assertEquals(expectedAge, ParserUtil.parseAge(VALID_AGE));
    }

    @Test
    public void parseAge_validValueWithWhitespace_returnsTrimmedAge() throws Exception {
        String ageWithWhitespace = WHITESPACE + VALID_AGE + WHITESPACE;
        Age expectedAge = new Age(VALID_AGE);
        assertEquals(expectedAge, ParserUtil.parseAge(ageWithWhitespace));
    }

    @Test
    public void parseAge_validValueWithLeadingZeros_returnsTrimmedAge() throws Exception {
        String ageWithLeadingZeros = ZEROS + VALID_AGE;
        Age expectedAge = new Age(VALID_AGE);
        assertEquals(expectedAge, ParserUtil.parseAge(ageWithLeadingZeros));
    }

    @Test
    public void parseRoomNumber_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRoomNumber((String) null));
    }

    @Test
    public void parseRoomNumber_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRoomNumber(INVALID_ROOM_NUMBER));
    }

    @Test
    public void parseRoomNumber_validValueWithoutWhitespace_returnsRoomNumber() throws Exception {
        RoomNumber expectedRoomNumber = new RoomNumber(VALID_ROOM_NUMBER);
        assertEquals(expectedRoomNumber, ParserUtil.parseRoomNumber(VALID_ROOM_NUMBER));
    }

    @Test
    public void parseRoomNumber_validValueWithWhitespace_returnsTrimmedRoomNumber() throws Exception {
        String roomNumberWithWhitespace = WHITESPACE + VALID_ROOM_NUMBER + WHITESPACE;
        RoomNumber expectedRoomNumber = new RoomNumber(VALID_ROOM_NUMBER);
        assertEquals(expectedRoomNumber, ParserUtil.parseRoomNumber(roomNumberWithWhitespace));
    }

    @Test
    public void parseRoomNumber_validValueWithLeadingZeros_returnsTrimmedRoomNumber() throws Exception {
        String roomNumberWithLeadingZeros = ZEROS + VALID_ROOM_NUMBER + ZEROS;
        RoomNumber expectedRoomNumber = new RoomNumber(VALID_ROOM_NUMBER + ZEROS);
        assertEquals(expectedRoomNumber, ParserUtil.parseRoomNumber(roomNumberWithLeadingZeros));
    }

    @Test
    public void parseRoomNumber_zerosOnly_returnsTrimmedRoomNumber() throws Exception {
        String roomNumberWithZerosOnly = ZEROS;
        RoomNumber expectedRoomNumber = new RoomNumber("0");
        assertEquals(expectedRoomNumber, ParserUtil.parseRoomNumber(roomNumberWithZerosOnly));
    }

    @Test
    public void parseNokName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNokName((String) null));
    }

    @Test
    public void parseNokName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNokName(INVALID_NOK_NAME));
    }

    @Test
    public void parseNokName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedNokName = new Name(VALID_NOK_NAME);
        assertEquals(expectedNokName, ParserUtil.parseNokName(VALID_NOK_NAME));
    }

    @Test
    public void parseNokName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nokNameWithWhitespace = WHITESPACE + VALID_NOK_NAME + WHITESPACE;
        Name expectedNokName = new Name(VALID_NOK_NAME);
        assertEquals(expectedNokName, ParserUtil.parseName(nokNameWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseRelationship_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRelationship((String) null));
    }

    @Test
    public void parseRelationship_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRelationship(INVALID_RELATIONSHIP));
    }

    @Test
    public void parseRelationship_validValueWithoutWhitespace_returnsRelationship() throws Exception {
        Relationship expectedRelationship = new Relationship(VALID_RELATIONSHIP);
        assertEquals(expectedRelationship, ParserUtil.parseRelationship(VALID_RELATIONSHIP));
    }

    @Test
    public void parseRelationship_validValueWithWhitespace_returnsTrimmedRelationship() throws Exception {
        String relationshipWithWhitespace = WHITESPACE + VALID_RELATIONSHIP + WHITESPACE;
        Relationship expectedRelationship = new Relationship(VALID_RELATIONSHIP);
        assertEquals(expectedRelationship, ParserUtil.parseRelationship(relationshipWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    //No test for parseAddress_invalidValue_throwsParseException since there is no invalid address input
    //at this point.

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}
