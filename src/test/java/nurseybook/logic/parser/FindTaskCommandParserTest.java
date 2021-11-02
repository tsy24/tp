package nurseybook.logic.parser;

import static nurseybook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nurseybook.logic.commands.FindTaskCommand.MESSAGE_USAGE;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import nurseybook.logic.commands.FindTaskCommand;
import nurseybook.model.task.DescriptionContainsKeywordPredicate;

public class FindTaskCommandParserTest {

    private FindTaskCommandParser parser = new FindTaskCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_USAGE));
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
