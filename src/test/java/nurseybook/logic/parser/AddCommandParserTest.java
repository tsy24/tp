package nurseybook.logic.parser;

import static nurseybook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nurseybook.logic.commands.CommandTestUtil.AGE_DESC_AMY;
import static nurseybook.logic.commands.CommandTestUtil.AGE_DESC_BOB;
import static nurseybook.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static nurseybook.logic.commands.CommandTestUtil.GENDER_DESC_BOB;
import static nurseybook.logic.commands.CommandTestUtil.INVALID_AGE_DESC;
import static nurseybook.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static nurseybook.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static nurseybook.logic.commands.CommandTestUtil.INVALID_NOK_EMAIL_DESC;
import static nurseybook.logic.commands.CommandTestUtil.INVALID_NOK_NAME_DESC;
import static nurseybook.logic.commands.CommandTestUtil.INVALID_NOK_PHONE_DESC;
import static nurseybook.logic.commands.CommandTestUtil.INVALID_NOK_RELATIONSHIP_DESC;
import static nurseybook.logic.commands.CommandTestUtil.INVALID_ROOM_NUMBER_DESC;
import static nurseybook.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static nurseybook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static nurseybook.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static nurseybook.logic.commands.CommandTestUtil.NOK_ADDRESS_DESC_AMY;
import static nurseybook.logic.commands.CommandTestUtil.NOK_ADDRESS_DESC_BOB;
import static nurseybook.logic.commands.CommandTestUtil.NOK_EMAIL_DESC_AMY;
import static nurseybook.logic.commands.CommandTestUtil.NOK_EMAIL_DESC_BOB;
import static nurseybook.logic.commands.CommandTestUtil.NOK_NAME_DESC_AMY;
import static nurseybook.logic.commands.CommandTestUtil.NOK_NAME_DESC_BOB;
import static nurseybook.logic.commands.CommandTestUtil.NOK_PHONE_DESC_AMY;
import static nurseybook.logic.commands.CommandTestUtil.NOK_PHONE_DESC_BOB;
import static nurseybook.logic.commands.CommandTestUtil.NOK_RELATIONSHIP_DESC_AMY;
import static nurseybook.logic.commands.CommandTestUtil.NOK_RELATIONSHIP_DESC_BOB;
import static nurseybook.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static nurseybook.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static nurseybook.logic.commands.CommandTestUtil.ROOM_NUMBER_DESC_AMY;
import static nurseybook.logic.commands.CommandTestUtil.ROOM_NUMBER_DESC_BOB;
import static nurseybook.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static nurseybook.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static nurseybook.logic.commands.CommandTestUtil.VALID_AGE_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_ADDRESS_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_EMAIL_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_NAME_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_PHONE_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_RELATIONSHIP_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_ROOM_NUMBER_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static nurseybook.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static nurseybook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nurseybook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static nurseybook.testutil.TypicalElderlies.AMY;
import static nurseybook.testutil.TypicalElderlies.BOB;

import org.junit.jupiter.api.Test;

import nurseybook.logic.commands.AddCommand;
import nurseybook.model.person.Age;
import nurseybook.model.person.Elderly;
import nurseybook.model.person.Email;
import nurseybook.model.person.Gender;
import nurseybook.model.person.Name;
import nurseybook.model.person.Phone;
import nurseybook.model.person.Relationship;
import nurseybook.model.person.RoomNumber;
import nurseybook.model.tag.Tag;
import nurseybook.testutil.ElderlyBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Elderly expectedElderly = new ElderlyBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + AGE_DESC_BOB
                + GENDER_DESC_BOB + ROOM_NUMBER_DESC_BOB + NOK_NAME_DESC_BOB + NOK_PHONE_DESC_BOB
                + NOK_EMAIL_DESC_BOB + NOK_ADDRESS_DESC_BOB + NOK_RELATIONSHIP_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedElderly));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + NOK_PHONE_DESC_BOB
                + AGE_DESC_BOB + GENDER_DESC_BOB + ROOM_NUMBER_DESC_BOB + NOK_NAME_DESC_BOB + NOK_RELATIONSHIP_DESC_BOB
                + NOK_EMAIL_DESC_BOB + NOK_ADDRESS_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedElderly));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + NOK_PHONE_DESC_AMY
                + NOK_PHONE_DESC_BOB + AGE_DESC_BOB + GENDER_DESC_BOB + ROOM_NUMBER_DESC_BOB + NOK_NAME_DESC_BOB
                + NOK_RELATIONSHIP_DESC_BOB + NOK_EMAIL_DESC_BOB + NOK_ADDRESS_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedElderly));

        // multiple ages - last age accepted
        assertParseSuccess(parser, NAME_DESC_BOB + NOK_PHONE_DESC_BOB + AGE_DESC_AMY
                + AGE_DESC_BOB + GENDER_DESC_BOB + ROOM_NUMBER_DESC_BOB + NOK_NAME_DESC_BOB + NOK_RELATIONSHIP_DESC_BOB
                + NOK_EMAIL_DESC_BOB + NOK_ADDRESS_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedElderly));

        // multiple genders - last gender accepted
        assertParseSuccess(parser, NAME_DESC_BOB + NOK_PHONE_DESC_BOB + AGE_DESC_BOB
                + GENDER_DESC_AMY + GENDER_DESC_BOB + ROOM_NUMBER_DESC_BOB + NOK_NAME_DESC_BOB
                + NOK_RELATIONSHIP_DESC_BOB + NOK_EMAIL_DESC_BOB + NOK_ADDRESS_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedElderly));

        // multiple nok names - last nok name accepted
        assertParseSuccess(parser, NAME_DESC_BOB + NOK_PHONE_DESC_BOB + AGE_DESC_BOB
                + GENDER_DESC_BOB + ROOM_NUMBER_DESC_BOB + NOK_NAME_DESC_AMY + NOK_NAME_DESC_BOB
                + NOK_RELATIONSHIP_DESC_BOB + NOK_EMAIL_DESC_AMY + NOK_ADDRESS_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedElderly));

        // multiple relationships - last relationship accepted
        assertParseSuccess(parser, NAME_DESC_BOB + NOK_PHONE_DESC_BOB + AGE_DESC_BOB
                + GENDER_DESC_BOB + ROOM_NUMBER_DESC_BOB + NOK_NAME_DESC_BOB + NOK_RELATIONSHIP_DESC_AMY
                + NOK_RELATIONSHIP_DESC_BOB + NOK_EMAIL_DESC_AMY + NOK_ADDRESS_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedElderly));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + NOK_PHONE_DESC_BOB + AGE_DESC_BOB
                + GENDER_DESC_BOB + ROOM_NUMBER_DESC_BOB + NOK_NAME_DESC_BOB + NOK_RELATIONSHIP_DESC_BOB
                + NOK_EMAIL_DESC_AMY + NOK_EMAIL_DESC_BOB + NOK_ADDRESS_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedElderly));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + NOK_PHONE_DESC_BOB + AGE_DESC_BOB
                + GENDER_DESC_BOB + ROOM_NUMBER_DESC_BOB + NOK_NAME_DESC_BOB + NOK_RELATIONSHIP_DESC_BOB
                + NOK_EMAIL_DESC_BOB + NOK_ADDRESS_DESC_AMY + NOK_ADDRESS_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedElderly));

        // multiple roomNumbers - last roomNumber accepted
        assertParseSuccess(parser, NAME_DESC_BOB + NOK_PHONE_DESC_BOB + AGE_DESC_BOB
                + GENDER_DESC_BOB + ROOM_NUMBER_DESC_AMY + ROOM_NUMBER_DESC_BOB + NOK_NAME_DESC_BOB
                + NOK_RELATIONSHIP_DESC_BOB + NOK_EMAIL_DESC_BOB + NOK_ADDRESS_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedElderly));

        // multiple tags - all accepted
        Elderly expectedElderlyMultipleTags = new ElderlyBuilder(BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, NAME_DESC_BOB + NOK_PHONE_DESC_BOB + AGE_DESC_BOB
                + GENDER_DESC_BOB + ROOM_NUMBER_DESC_BOB + NOK_NAME_DESC_BOB + NOK_RELATIONSHIP_DESC_BOB
                + NOK_EMAIL_DESC_BOB + NOK_ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedElderlyMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Elderly expectedElderly = new ElderlyBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + AGE_DESC_AMY + GENDER_DESC_AMY
                        + ROOM_NUMBER_DESC_AMY, new AddCommand(expectedElderly));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + NOK_PHONE_DESC_BOB + AGE_DESC_BOB
                + GENDER_DESC_BOB + ROOM_NUMBER_DESC_BOB + NOK_NAME_DESC_BOB + NOK_RELATIONSHIP_DESC_BOB
                + NOK_EMAIL_DESC_BOB + NOK_ADDRESS_DESC_BOB, expectedMessage);

        // missing age prefix
        assertParseFailure(parser, NAME_DESC_BOB + NOK_PHONE_DESC_BOB + VALID_AGE_BOB
                + GENDER_DESC_BOB + ROOM_NUMBER_DESC_BOB + NOK_NAME_DESC_BOB + NOK_RELATIONSHIP_DESC_BOB
                + NOK_EMAIL_DESC_BOB + NOK_ADDRESS_DESC_BOB, expectedMessage);

        // missing gender prefix
        assertParseFailure(parser, NAME_DESC_BOB + NOK_PHONE_DESC_BOB + AGE_DESC_BOB
                + VALID_GENDER_BOB + ROOM_NUMBER_DESC_BOB + NOK_NAME_DESC_BOB + NOK_RELATIONSHIP_DESC_BOB
                + VALID_NOK_EMAIL_BOB + NOK_ADDRESS_DESC_BOB, expectedMessage);

        // missing roomNumber prefix
        assertParseFailure(parser, NAME_DESC_BOB + NOK_PHONE_DESC_BOB + AGE_DESC_BOB
                + GENDER_DESC_BOB + VALID_ROOM_NUMBER_BOB + NOK_NAME_DESC_BOB + NOK_RELATIONSHIP_DESC_BOB
                + NOK_EMAIL_DESC_BOB + NOK_ADDRESS_DESC_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_NOK_PHONE_BOB + AGE_DESC_BOB
                + GENDER_DESC_BOB + ROOM_NUMBER_DESC_BOB + VALID_NOK_NAME_BOB + VALID_NOK_RELATIONSHIP_BOB
                + VALID_NOK_EMAIL_BOB + VALID_NOK_ADDRESS_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + NOK_PHONE_DESC_BOB + AGE_DESC_BOB
                + GENDER_DESC_BOB + ROOM_NUMBER_DESC_BOB + NOK_NAME_DESC_BOB + NOK_RELATIONSHIP_DESC_BOB
                + NOK_EMAIL_DESC_BOB + NOK_ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_NOK_PHONE_DESC + AGE_DESC_BOB
                + GENDER_DESC_BOB + ROOM_NUMBER_DESC_BOB + NOK_NAME_DESC_BOB + NOK_RELATIONSHIP_DESC_BOB
                + NOK_EMAIL_DESC_BOB + NOK_ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid age
        assertParseFailure(parser, NAME_DESC_BOB + NOK_PHONE_DESC_BOB + INVALID_AGE_DESC
                + GENDER_DESC_BOB + ROOM_NUMBER_DESC_BOB + NOK_NAME_DESC_BOB + NOK_RELATIONSHIP_DESC_BOB
                + NOK_EMAIL_DESC_BOB + NOK_ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Age.MESSAGE_CONSTRAINTS);

        // invalid gender
        assertParseFailure(parser, NAME_DESC_BOB + NOK_PHONE_DESC_BOB + AGE_DESC_BOB
                + INVALID_GENDER_DESC + ROOM_NUMBER_DESC_BOB + NOK_NAME_DESC_BOB + NOK_RELATIONSHIP_DESC_BOB
                + NOK_EMAIL_DESC_BOB + NOK_ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Gender.MESSAGE_CONSTRAINTS);

        // invalid roomNumber
        assertParseFailure(parser, NAME_DESC_BOB + NOK_PHONE_DESC_BOB + AGE_DESC_BOB + GENDER_DESC_BOB
                + INVALID_ROOM_NUMBER_DESC + NOK_NAME_DESC_BOB + NOK_RELATIONSHIP_DESC_BOB + NOK_EMAIL_DESC_BOB
                + NOK_ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, RoomNumber.MESSAGE_CONSTRAINTS);

        // invalid nokName
        assertParseFailure(parser, NAME_DESC_BOB + NOK_PHONE_DESC_BOB + AGE_DESC_BOB
                + GENDER_DESC_BOB + ROOM_NUMBER_DESC_BOB + INVALID_NOK_NAME_DESC + NOK_RELATIONSHIP_DESC_BOB
                + NOK_EMAIL_DESC_BOB + NOK_ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Name.MESSAGE_CONSTRAINTS);

        // invalid relationship
        assertParseFailure(parser, NAME_DESC_BOB + NOK_PHONE_DESC_BOB + AGE_DESC_BOB
                + GENDER_DESC_BOB + ROOM_NUMBER_DESC_BOB + NOK_NAME_DESC_BOB + INVALID_NOK_RELATIONSHIP_DESC
                + NOK_EMAIL_DESC_BOB + NOK_ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Relationship.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + NOK_PHONE_DESC_BOB + AGE_DESC_BOB
                + GENDER_DESC_BOB + ROOM_NUMBER_DESC_BOB + NOK_NAME_DESC_BOB + NOK_RELATIONSHIP_DESC_BOB
                + INVALID_NOK_EMAIL_DESC + NOK_ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Email.MESSAGE_CONSTRAINTS);

        // invalid address
        // No test for invalid address since after tokenization there is no invalid address input at this point.

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + NOK_PHONE_DESC_BOB + AGE_DESC_BOB
                + GENDER_DESC_BOB + ROOM_NUMBER_DESC_BOB + NOK_NAME_DESC_BOB + NOK_RELATIONSHIP_DESC_BOB
                + NOK_EMAIL_DESC_BOB + NOK_ADDRESS_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND,
                Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + NOK_PHONE_DESC_BOB + AGE_DESC_BOB
                + GENDER_DESC_BOB + ROOM_NUMBER_DESC_BOB + NOK_NAME_DESC_BOB + NOK_RELATIONSHIP_DESC_BOB
                + NOK_EMAIL_DESC_BOB + INVALID_NOK_PHONE_DESC, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + AGE_DESC_BOB
                + GENDER_DESC_BOB + ROOM_NUMBER_DESC_BOB + NOK_NAME_DESC_BOB + NOK_RELATIONSHIP_DESC_BOB
                + NOK_PHONE_DESC_BOB + NOK_EMAIL_DESC_BOB + NOK_ADDRESS_DESC_BOB + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
