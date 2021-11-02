package nurseybook.logic.parser;

import static nurseybook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nurseybook.logic.commands.AddTagCommand.MESSAGE_USAGE;
import static nurseybook.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static nurseybook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static nurseybook.logic.commands.CommandTestUtil.SET_ONE_TAG;
import static nurseybook.logic.commands.CommandTestUtil.TAG_DESC_DIABETES;
import static nurseybook.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static nurseybook.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static nurseybook.logic.commands.CommandTestUtil.TAG_EMPTY;
import static nurseybook.logic.commands.CommandTestUtil.VALID_TAG_DIABETES;
import static nurseybook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nurseybook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static nurseybook.model.tag.Tag.MESSAGE_CONSTRAINTS;
import static nurseybook.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.index.Index;
import nurseybook.logic.commands.AddTagCommand;

public class AddTagCommandParserTest {

    private AddTagCommandParser parser = new AddTagCommandParser();

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + TAG_DESC_DIABETES;
        AddTagCommand expectedCommand = new AddTagCommand(INDEX_FIRST, SET_ONE_TAG);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, "", expectedMessage);

        // no index
        assertParseFailure(parser, TAG_DESC_DIABETES, expectedMessage);

        // no tag
        assertParseFailure(parser, "" + INDEX_FIRST, expectedMessage);

    }

    @Test
    public void parse_invalidPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        // negative index
        assertParseFailure(parser, "-5" + VALID_TAG_DIABETES, expectedMessage);

        // zero index
        assertParseFailure(parser, "0" + VALID_TAG_DIABETES, expectedMessage);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", expectedMessage);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", expectedMessage);
    }

    @Test
    public void parse_invalidTag_failure() {
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, MESSAGE_CONSTRAINTS); // invalid tag
        assertParseFailure(parser, "1" + TAG_EMPTY, MESSAGE_CONSTRAINTS); // invalid empty tag

        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_unexpectedFieldPresent_failure() {
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + NAME_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
    }
}
