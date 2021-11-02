package nurseybook.logic.parser;

import static nurseybook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nurseybook.logic.commands.DoneTaskCommand.MESSAGE_USAGE;
import static nurseybook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nurseybook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static nurseybook.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import nurseybook.logic.commands.DoneTaskCommand;

public class DoneTaskCommandParserTest {

    private DoneTaskCommandParser parser = new DoneTaskCommandParser();

    @Test
    public void parse_validArgs_returnsDoneCommand() {
        assertParseSuccess(parser, "1", new DoneTaskCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
}
