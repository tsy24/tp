package nurseybook.logic.parser;

import static nurseybook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nurseybook.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static nurseybook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static nurseybook.logic.commands.CommandTestUtil.SET_ONE_TAG;
import static nurseybook.logic.commands.CommandTestUtil.TAG_DESC_DIABETES;
import static nurseybook.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static nurseybook.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static nurseybook.logic.commands.CommandTestUtil.TAG_EMPTY;
import static nurseybook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nurseybook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static nurseybook.logic.parser.ParserUtil.MESSAGE_INDEX_TOO_EXTREME;
import static nurseybook.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static nurseybook.model.tag.Tag.MESSAGE_CONSTRAINTS;
import static nurseybook.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.index.Index;
import nurseybook.logic.commands.AddTagCommand;

public class AddTagCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE);
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
        // no parameters
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // no index
        assertParseFailure(parser, TAG_DESC_DIABETES, MESSAGE_INVALID_FORMAT);

        // no tag
        assertParseFailure(parser, "" + INDEX_FIRST, MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_invalidPreamble_failure() {
        // Not non-zero unsigned index integer
        assertParseFailure(parser, "-5" + " " + TAG_DESC_DIABETES, MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "0" + " " + TAG_DESC_DIABETES, MESSAGE_INVALID_INDEX);

        // non-integer being parsed as preamble
        assertParseFailure(parser, "ad" + TAG_DESC_DIABETES, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);

        // Extreme indices
        assertParseFailure(parser, "9999999999" + " " + TAG_DESC_DIABETES, MESSAGE_INDEX_TOO_EXTREME);
        assertParseFailure(parser, "-999999999" + " " + TAG_DESC_DIABETES, MESSAGE_INDEX_TOO_EXTREME);
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
