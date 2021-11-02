package nurseybook.logic.parser;

import static nurseybook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nurseybook.logic.commands.DeleteNokCommand.MESSAGE_USAGE;
import static nurseybook.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import nurseybook.logic.commands.DeleteNokCommand;

public class DeleteNokCommandParserTest {

    private DeleteNokCommandParser parser = new DeleteNokCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteNokCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, "1", new DeleteNokCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
}

