package nurseybook.logic.parser;

import static nurseybook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nurseybook.logic.commands.FindElderlyCommand.MESSAGE_USAGE;
import static nurseybook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nurseybook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import nurseybook.logic.commands.FindElderlyCommand;
import nurseybook.model.person.NameContainsKeywordsPredicate;

public class FindElderlyCommandParserTest {

    private FindElderlyCommandParser parser = new FindElderlyCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindElderlyCommand() {
        // no leading and trailing whitespaces
        FindElderlyCommand expectedFindElderlyCommand =
                new FindElderlyCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindElderlyCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindElderlyCommand);
    }

}
