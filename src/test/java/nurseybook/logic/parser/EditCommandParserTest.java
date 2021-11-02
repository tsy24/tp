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
import static nurseybook.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static nurseybook.logic.commands.CommandTestUtil.ROOM_NUMBER_DESC_AMY;
import static nurseybook.logic.commands.CommandTestUtil.ROOM_NUMBER_DESC_BOB;
import static nurseybook.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static nurseybook.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static nurseybook.logic.commands.CommandTestUtil.VALID_AGE_AMY;
import static nurseybook.logic.commands.CommandTestUtil.VALID_AGE_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static nurseybook.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_ADDRESS_AMY;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_ADDRESS_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_EMAIL_AMY;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_EMAIL_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_NAME_AMY;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_NAME_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_PHONE_AMY;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_PHONE_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_RELATIONSHIP_AMY;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_RELATIONSHIP_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_ROOM_NUMBER_AMY;
import static nurseybook.logic.commands.CommandTestUtil.VALID_ROOM_NUMBER_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static nurseybook.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static nurseybook.logic.commands.EditCommand.MESSAGE_NOT_EDITED;
import static nurseybook.logic.commands.EditCommand.MESSAGE_USAGE;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TAG;
import static nurseybook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nurseybook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static nurseybook.testutil.TypicalIndexes.INDEX_FIRST;
import static nurseybook.testutil.TypicalIndexes.INDEX_SECOND;
import static nurseybook.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.index.Index;
import nurseybook.logic.commands.EditCommand;
import nurseybook.model.person.Age;
import nurseybook.model.person.Email;
import nurseybook.model.person.Gender;
import nurseybook.model.person.Name;
import nurseybook.model.person.Phone;
import nurseybook.model.person.Relationship;
import nurseybook.model.person.RoomNumber;
import nurseybook.model.tag.Tag;
import nurseybook.testutil.EditElderlyDescriptorBuilder;
import nurseybook.testutil.TypicalIndexes;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_AGE_DESC, Age.MESSAGE_CONSTRAINTS); // invalid age
        assertParseFailure(parser, "1" + INVALID_GENDER_DESC, Gender.MESSAGE_CONSTRAINTS); // invalid gender
        assertParseFailure(parser, "1" + INVALID_ROOM_NUMBER_DESC, RoomNumber.MESSAGE_CONSTRAINTS); // invalid rm no
        assertParseFailure(parser, "1" + INVALID_NOK_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid nokName
        assertParseFailure(parser, "1" + INVALID_NOK_RELATIONSHIP_DESC, Relationship.MESSAGE_CONSTRAINTS); // invalid rs
        assertParseFailure(parser, "1" + INVALID_NOK_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_NOK_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email

        // invalid address
        // No test for invalid address since there is no invalid address input at this point.

        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_NOK_PHONE_DESC + NOK_EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + NOK_PHONE_DESC_BOB + INVALID_NOK_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // invalid age followed by valid email
        assertParseFailure(parser, "1" + INVALID_AGE_DESC + NOK_EMAIL_DESC_AMY, Age.MESSAGE_CONSTRAINTS);

        // valid age followed by invalid age. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + AGE_DESC_BOB + INVALID_AGE_DESC, Age.MESSAGE_CONSTRAINTS);

        // invalid gender followed by valid email
        assertParseFailure(parser, "1" + INVALID_GENDER_DESC + NOK_EMAIL_DESC_AMY, Gender.MESSAGE_CONSTRAINTS);

        // valid gender followed by invalid gender. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + GENDER_DESC_BOB + INVALID_GENDER_DESC, Gender.MESSAGE_CONSTRAINTS);


        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Elderly} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_NOK_EMAIL_DESC + VALID_NOK_ADDRESS_AMY
                + VALID_NOK_PHONE_AMY + VALID_ROOM_NUMBER_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + AGE_DESC_BOB + TAG_DESC_HUSBAND + GENDER_DESC_AMY
                + ROOM_NUMBER_DESC_BOB + NOK_PHONE_DESC_AMY + NOK_EMAIL_DESC_AMY + NOK_ADDRESS_DESC_AMY + NAME_DESC_AMY
                + NOK_NAME_DESC_AMY + NOK_RELATIONSHIP_DESC_AMY + TAG_DESC_FRIEND;

        EditCommand.EditElderlyDescriptor descriptor = new EditElderlyDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_NOK_PHONE_AMY).withAge(VALID_AGE_BOB).withGender(VALID_GENDER_AMY)
                .withRoomNumber(VALID_ROOM_NUMBER_BOB).withNokName(VALID_NOK_NAME_AMY)
                .withRelationship(VALID_NOK_RELATIONSHIP_AMY).withEmail(VALID_NOK_EMAIL_AMY)
                .withAddress(VALID_NOK_ADDRESS_AMY).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + NOK_PHONE_DESC_BOB + NOK_EMAIL_DESC_AMY;

        EditCommand.EditElderlyDescriptor descriptor = new EditElderlyDescriptorBuilder().withPhone(VALID_NOK_PHONE_BOB)
                .withEmail(VALID_NOK_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditCommand.EditElderlyDescriptor descriptor = new EditElderlyDescriptorBuilder()
                .withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // age
        userInput = targetIndex.getOneBased() + AGE_DESC_AMY;
        descriptor = new EditElderlyDescriptorBuilder().withAge(VALID_AGE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // gender
        userInput = targetIndex.getOneBased() + GENDER_DESC_AMY;
        descriptor = new EditElderlyDescriptorBuilder().withGender(VALID_GENDER_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // roomNumber
        userInput = targetIndex.getOneBased() + ROOM_NUMBER_DESC_AMY;
        descriptor = new EditElderlyDescriptorBuilder().withRoomNumber(VALID_ROOM_NUMBER_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // nokName
        userInput = targetIndex.getOneBased() + NOK_NAME_DESC_AMY;
        descriptor = new EditElderlyDescriptorBuilder().withNokName(VALID_NOK_NAME_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + NOK_PHONE_DESC_AMY;
        descriptor = new EditElderlyDescriptorBuilder().withPhone(VALID_NOK_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // relationship
        userInput = targetIndex.getOneBased() + NOK_RELATIONSHIP_DESC_AMY;
        descriptor = new EditElderlyDescriptorBuilder().withRelationship(VALID_NOK_RELATIONSHIP_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + NOK_EMAIL_DESC_AMY;
        descriptor = new EditElderlyDescriptorBuilder().withEmail(VALID_NOK_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + NOK_ADDRESS_DESC_AMY;
        descriptor = new EditElderlyDescriptorBuilder().withAddress(VALID_NOK_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditElderlyDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + NOK_PHONE_DESC_AMY + AGE_DESC_AMY + GENDER_DESC_AMY
                + ROOM_NUMBER_DESC_AMY + NOK_NAME_DESC_AMY + NOK_RELATIONSHIP_DESC_AMY + NOK_ADDRESS_DESC_AMY
                + NOK_EMAIL_DESC_AMY + TAG_DESC_FRIEND + NOK_PHONE_DESC_AMY + AGE_DESC_AMY + GENDER_DESC_AMY
                + ROOM_NUMBER_DESC_AMY + NOK_ADDRESS_DESC_AMY + NOK_EMAIL_DESC_AMY + TAG_DESC_FRIEND
                + NOK_PHONE_DESC_BOB + AGE_DESC_BOB + GENDER_DESC_BOB + ROOM_NUMBER_DESC_BOB
                + NOK_NAME_DESC_BOB + NOK_RELATIONSHIP_DESC_BOB + NOK_ADDRESS_DESC_BOB
                + NOK_EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

        EditCommand.EditElderlyDescriptor descriptor = new EditElderlyDescriptorBuilder().withPhone(VALID_NOK_PHONE_BOB)
                .withAge(VALID_AGE_BOB).withGender(VALID_GENDER_BOB).withRoomNumber(VALID_ROOM_NUMBER_BOB)
                .withNokName(VALID_NOK_NAME_BOB).withRelationship(VALID_NOK_RELATIONSHIP_BOB)
                .withEmail(VALID_NOK_EMAIL_BOB).withAddress(VALID_NOK_ADDRESS_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();

        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_NOK_PHONE_DESC + NOK_PHONE_DESC_BOB;
        EditCommand.EditElderlyDescriptor descriptor = new EditElderlyDescriptorBuilder()
                .withPhone(VALID_NOK_PHONE_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + AGE_DESC_BOB + GENDER_DESC_BOB
                + NOK_EMAIL_DESC_BOB + INVALID_NOK_PHONE_DESC + ROOM_NUMBER_DESC_BOB
                + NOK_NAME_DESC_BOB + NOK_RELATIONSHIP_DESC_BOB + NOK_ADDRESS_DESC_BOB + NOK_PHONE_DESC_BOB;
        descriptor = new EditElderlyDescriptorBuilder().withPhone(VALID_NOK_PHONE_BOB).withAge(VALID_AGE_BOB)
                .withGender(VALID_GENDER_BOB).withRoomNumber(VALID_ROOM_NUMBER_BOB).withNokName(VALID_NOK_NAME_BOB)
                .withRelationship(VALID_NOK_RELATIONSHIP_BOB).withEmail(VALID_NOK_EMAIL_BOB)
                .withAddress(VALID_NOK_ADDRESS_BOB).build();

        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = TypicalIndexes.INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;
        EditCommand.EditElderlyDescriptor descriptor = new EditElderlyDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_unexpectedFieldPresent_failure() {
        assertParseFailure(parser, "1" + NAME_DESC_AMY + AGE_DESC_AMY + GENDER_DESC_AMY + ROOM_NUMBER_DESC_AMY
                + REMARK_DESC_AMY, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }
}
