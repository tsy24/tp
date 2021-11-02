package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INDEX_TOO_EXTREME;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteNokCommand;

public class DeleteNokCommandParserTest {

    private DeleteNokCommandParser parser = new DeleteNokCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteNokCommand() {
        assertParseSuccess(parser, "1", new DeleteNokCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteNokCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_indexIsNotNonZeroUnsignedInteger_throwsParseException() {
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_INDEX,
                DeleteNokCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-99999999", String.format(MESSAGE_INVALID_INDEX,
                DeleteNokCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_indexTooExtreme_throwsParseException() {
        assertParseFailure(parser, "9999999999", String.format(MESSAGE_INDEX_TOO_EXTREME,
                DeleteNokCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-999999999", String.format(MESSAGE_INDEX_TOO_EXTREME,
                DeleteNokCommand.MESSAGE_USAGE));
    }
}

