package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.Clock;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemindCommand;
import seedu.address.model.task.TaskIsReminderPredicate;

public class RemindCommandParserTest {

    private RemindCommandParser parser = new RemindCommandParser();

    @Test
    public void returnsRemindCommand() {
        Clock cl = Clock.systemUTC();
        LocalDate currentDate = LocalDate.now(cl);

        RemindCommand expectedRemindCommand =
                new RemindCommand(new TaskIsReminderPredicate(currentDate));
        assertParseSuccess(parser, " ", expectedRemindCommand);

        // user input after the `remind` keyword does not affect parser result
        assertParseSuccess(parser, "Alice", expectedRemindCommand);
    }
}
