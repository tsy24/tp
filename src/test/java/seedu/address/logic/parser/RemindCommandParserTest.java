package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemindCommand;
import seedu.address.model.task.TaskIsReminderPredicate;

public class RemindCommandParserTest {

    private RemindCommandParser parser = new RemindCommandParser();

    @Test
    public void returnsRemindCommand() {
        LocalDateTime currentDateTime = LocalDateTime.now()
                .withMinute(0).withSecond(0).withNano(0);

        RemindCommand expectedRemindCommand =
                new RemindCommand(new TaskIsReminderPredicate(currentDateTime));
        assertParseSuccess(parser, " ", expectedRemindCommand);

        // user input after the `remind` keyword does not affect parser result
        assertParseSuccess(parser, "Alice", expectedRemindCommand);
    }
}
