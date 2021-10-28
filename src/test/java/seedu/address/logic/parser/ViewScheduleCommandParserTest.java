package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewScheduleCommand;
import seedu.address.model.task.DateTimeContainsDatePredicate;

public class ViewScheduleCommandParserTest {

    private ViewScheduleCommandParser parser = new ViewScheduleCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsViewScheduleCommand() {
        // no leading and trailing whitespaces
        LocalDate keyDate = LocalDate.parse("2021-11-02");
        ViewScheduleCommand expectedViewScheduleCommand =
                new ViewScheduleCommand(new DateTimeContainsDatePredicate(keyDate), keyDate);
        assertParseSuccess(parser, "2021-11-02", expectedViewScheduleCommand);
    }
}
