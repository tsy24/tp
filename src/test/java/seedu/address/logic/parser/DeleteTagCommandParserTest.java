package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.SET_ONE_TAG;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_DIABETES;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DIABETES;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ELDERLY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.model.tag.Tag;

public class DeleteTagCommandParserTest {

    private DeleteTagCommandParser parser = new DeleteTagCommandParser();

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ELDERLY;
        String userInput = targetIndex.getOneBased() + TAG_DESC_DIABETES;
        DeleteTagCommand expectedCommand = new DeleteTagCommand(INDEX_FIRST_ELDERLY, SET_ONE_TAG);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, "", expectedMessage);

        // no index
        assertParseFailure(parser, TAG_DESC_DIABETES, expectedMessage);

        // no tag
        assertParseFailure(parser, "" + INDEX_FIRST_ELDERLY, expectedMessage);

    }

    @Test
    public void parse_invalidPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE);

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
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag
        assertParseFailure(parser, "1" + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS); // invalid empty tag

        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
    }
}
