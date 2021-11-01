package nurseybook.logic.parser;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.Messages;
import nurseybook.logic.commands.ViewDetailsCommand;
import nurseybook.testutil.TypicalIndexes;

class ViewDetailsCommandParserTest {

    private ViewDetailsCommandParser parser = new ViewDetailsCommandParser();

    @Test
    public void parse_validArgs_returnsViewDetailsCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, "1", new ViewDetailsCommand(TypicalIndexes.INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "a",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ViewDetailsCommand.MESSAGE_USAGE));
    }

}
