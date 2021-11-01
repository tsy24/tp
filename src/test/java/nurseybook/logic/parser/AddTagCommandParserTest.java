package nurseybook.logic.parser;

import static nurseybook.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static nurseybook.logic.commands.CommandTestUtil.SET_ONE_TAG;
import static nurseybook.logic.commands.CommandTestUtil.TAG_DESC_DIABETES;
import static nurseybook.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static nurseybook.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static nurseybook.logic.commands.CommandTestUtil.TAG_EMPTY;
import static nurseybook.logic.commands.CommandTestUtil.VALID_TAG_DIABETES;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.Messages;
import nurseybook.commons.core.index.Index;
import nurseybook.logic.commands.AddTagCommand;
import nurseybook.model.tag.Tag;
import nurseybook.testutil.TypicalIndexes;

public class AddTagCommandParserTest {

    private AddTagCommandParser parser = new AddTagCommandParser();

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + TAG_DESC_DIABETES;
        AddTagCommand expectedCommand = new AddTagCommand(TypicalIndexes.INDEX_FIRST, SET_ONE_TAG);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE);

        // no parameters
        CommandParserTestUtil.assertParseFailure(parser, "", expectedMessage);

        // no index
        CommandParserTestUtil.assertParseFailure(parser, TAG_DESC_DIABETES, expectedMessage);

        // no tag
        CommandParserTestUtil.assertParseFailure(parser, "" + TypicalIndexes.INDEX_FIRST, expectedMessage);

    }

    @Test
    public void parse_invalidPreamble_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE);

        // negative index
        CommandParserTestUtil.assertParseFailure(parser, "-5" + VALID_TAG_DIABETES, expectedMessage);

        // zero index
        CommandParserTestUtil.assertParseFailure(parser, "0" + VALID_TAG_DIABETES, expectedMessage);

        // invalid arguments being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 some random string", expectedMessage);

        // invalid prefix being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 i/ string", expectedMessage);
    }

    @Test
    public void parse_invalidTag_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag
        CommandParserTestUtil.assertParseFailure(parser, "1" + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS); // invalid empty tag

        // parsing it together with a valid tag results in error
        CommandParserTestUtil.assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
    }
}
