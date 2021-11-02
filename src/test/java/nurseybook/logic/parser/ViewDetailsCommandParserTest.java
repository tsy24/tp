package nurseybook.logic.parser;

import static nurseybook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nurseybook.logic.commands.ViewDetailsCommand.MESSAGE_USAGE;
import static nurseybook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nurseybook.logic.parser.ParserUtil.MESSAGE_INDEX_TOO_EXTREME;
import static nurseybook.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static nurseybook.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import nurseybook.logic.commands.ViewDetailsCommand;

class ViewDetailsCommandParserTest {

    private ViewDetailsCommandParser parser = new ViewDetailsCommandParser();

    @Test
    public void parse_validArgs_returnsViewDetailsCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, "1", new ViewDetailsCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
    @Test
    public void parse_indexIsNotNonZeroUnsignedInteger_throwsParseException() {
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_INDEX,
                ViewDetailsCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-99999999", String.format(MESSAGE_INVALID_INDEX,
                ViewDetailsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_indexTooExtreme_throwsParseException() {
        assertParseFailure(parser, "9999999999", String.format(MESSAGE_INDEX_TOO_EXTREME,
                ViewDetailsCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-999999999", String.format(MESSAGE_INDEX_TOO_EXTREME,
                ViewDetailsCommand.MESSAGE_USAGE));
    }
}
