package nurseybook.logic.parser;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import nurseybook.logic.commands.RemindCommand;
import nurseybook.model.task.TaskIsReminderPredicate;

public class RemindCommandParserTest {

    private RemindCommandParser parser = new RemindCommandParser();

    @Test
    public void returnsRemindCommand() {
        LocalDateTime currentDateTime = LocalDateTime.now()
                .withMinute(0).withSecond(0).withNano(0);

        RemindCommand expectedRemindCommand =
                new RemindCommand(new TaskIsReminderPredicate(currentDateTime));
        CommandParserTestUtil.assertParseSuccess(parser, " ", expectedRemindCommand);

        // user input after the `remind` keyword does not affect parser result
        CommandParserTestUtil.assertParseSuccess(parser, "Alice", expectedRemindCommand);
    }
}
