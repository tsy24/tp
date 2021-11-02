package seedu.address.logic.parser;

import static nurseybook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nurseybook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nurseybook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static nurseybook.logic.parser.ParserUtil.MESSAGE_INDEX_TOO_EXTREME;
import static nurseybook.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static nurseybook.testutil.TypicalIndexes.INDEX_FIRST;

import nurseybook.logic.parser.DoneTaskCommandParser;
import org.junit.jupiter.api.Test;

import nurseybook.logic.commands.DoneTaskCommand;

public class DoneTaskCommandParserTest {

    private final DoneTaskCommandParser parser = new DoneTaskCommandParser();

    @Test
    public void parse_validArgs_returnsDoneCommand() {
        assertParseSuccess(parser, "1", new DoneTaskCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DoneTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_indexIsNotNonZeroUnsignedInteger_throwsParseException() {
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_INDEX,
                DoneTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-99999999", String.format(MESSAGE_INVALID_INDEX,
                DoneTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_indexTooExtreme_throwsParseException() {
        assertParseFailure(parser, "9999999999", String.format(MESSAGE_INDEX_TOO_EXTREME,
                DoneTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-999999999", String.format(MESSAGE_INDEX_TOO_EXTREME,
                DoneTaskCommand.MESSAGE_USAGE));
    }
}
