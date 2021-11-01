package nurseybook.logic.parser;

import org.junit.jupiter.api.Test;

import nurseybook.commons.core.Messages;
import nurseybook.commons.core.index.Index;
import nurseybook.logic.commands.RemarkCommand;
import nurseybook.model.person.Remark;
import nurseybook.testutil.TypicalIndexes;

public class RemarkCommandParserTest {
    private RemarkCommandParser parser = new RemarkCommandParser();
    private final String nonEmptyRemark = "Some remark.";

    @Test
    public void parse_indexSpecified_success() {
        // have remark
        Index targetIndex = TypicalIndexes.INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + " " + CliSyntax.PREFIX_REMARK + nonEmptyRemark;
        RemarkCommand expectedCommand = new RemarkCommand(TypicalIndexes.INDEX_FIRST, new Remark(nonEmptyRemark));
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // no remark
        userInput = targetIndex.getOneBased() + " " + CliSyntax.PREFIX_REMARK;
        expectedCommand = new RemarkCommand(TypicalIndexes.INDEX_FIRST, new Remark(""));
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

        // no parameters
        CommandParserTestUtil.assertParseFailure(parser, RemarkCommand.COMMAND_WORD, expectedMessage);

        // no index
        CommandParserTestUtil.assertParseFailure(parser, RemarkCommand.COMMAND_WORD + " " + nonEmptyRemark, expectedMessage);
    }
}
