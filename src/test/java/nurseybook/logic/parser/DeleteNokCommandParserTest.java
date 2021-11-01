package nurseybook.logic.parser;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.Messages;
import nurseybook.logic.commands.DeleteNokCommand;
import nurseybook.testutil.TypicalIndexes;

public class DeleteNokCommandParserTest {

    private DeleteNokCommandParser parser = new DeleteNokCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteNokCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, "1", new DeleteNokCommand(TypicalIndexes.INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "a",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteNokCommand.MESSAGE_USAGE));
    }
}

