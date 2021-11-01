package nurseybook.logic.parser;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.Messages;
import nurseybook.logic.commands.FindTaskCommand;
import nurseybook.model.task.DescriptionContainsKeywordPredicate;

public class FindTaskCommandParserTest {

    private FindTaskCommandParser parser = new FindTaskCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "     ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                FindTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindTaskCommand() {
        // no leading and trailing whitespaces
        FindTaskCommand expectedFindTaskCommand =
                new FindTaskCommand(new DescriptionContainsKeywordPredicate(Arrays.asList("Apply", "Pfizer")));
        CommandParserTestUtil.assertParseSuccess(parser, "Apply Pfizer", expectedFindTaskCommand);

        // multiple white spaces between keywords
        CommandParserTestUtil.assertParseSuccess(parser, " \n Apply \n \t Pfizer  \t", expectedFindTaskCommand);
    }
}
