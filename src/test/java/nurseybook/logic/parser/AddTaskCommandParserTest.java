package nurseybook.logic.parser;

import static nurseybook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nurseybook.logic.commands.AddTaskCommand.MESSAGE_USAGE;
import static nurseybook.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static nurseybook.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static nurseybook.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static nurseybook.logic.commands.TaskCommandTestUtil.DATE_DESC_JAN;
import static nurseybook.logic.commands.TaskCommandTestUtil.DATE_DESC_NOV;
import static nurseybook.logic.commands.TaskCommandTestUtil.DESC_MEDICINE;
import static nurseybook.logic.commands.TaskCommandTestUtil.DESC_PAPERWORK;
import static nurseybook.logic.commands.TaskCommandTestUtil.INVALID_DATE;
import static nurseybook.logic.commands.TaskCommandTestUtil.INVALID_DESC;
import static nurseybook.logic.commands.TaskCommandTestUtil.INVALID_TIME;
import static nurseybook.logic.commands.TaskCommandTestUtil.NAME_DESC_ALICE;
import static nurseybook.logic.commands.TaskCommandTestUtil.NAME_DESC_GEORGE;
import static nurseybook.logic.commands.TaskCommandTestUtil.RECUR_MONTH;
import static nurseybook.logic.commands.TaskCommandTestUtil.TIME_DESC_SEVENPM;
import static nurseybook.logic.commands.TaskCommandTestUtil.TIME_DESC_TENAM;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DATE_NOV;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DESC_MEDICINE;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_NAME_ALICE;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_NAME_GEORGE;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_TIME_TENAM;
import static nurseybook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nurseybook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static nurseybook.testutil.TypicalTasks.DO_PAPERWORK;
import static nurseybook.testutil.TypicalTasks.GEORGE_INSULIN;

import org.junit.jupiter.api.Test;

import nurseybook.logic.commands.AddTaskCommand;
import nurseybook.model.person.Name;
import nurseybook.model.task.DateTime;
import nurseybook.model.task.Description;
import nurseybook.model.task.Task;
import nurseybook.testutil.TaskBuilder;

public class AddTaskCommandParserTest {
    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder(GEORGE_INSULIN).withStatus("false", "true").build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_GEORGE + DATE_DESC_NOV
                + TIME_DESC_SEVENPM + DESC_MEDICINE, new AddTaskCommand(expectedTask));

        // multiple desc - last desc accepted
        assertParseSuccess(parser, NAME_DESC_GEORGE + DATE_DESC_NOV + TIME_DESC_SEVENPM
                + DESC_PAPERWORK + DESC_MEDICINE, new AddTaskCommand(expectedTask));

        // multiple dates - last date accepted
        assertParseSuccess(parser, NAME_DESC_GEORGE + DATE_DESC_JAN + DATE_DESC_NOV
                + TIME_DESC_SEVENPM + DESC_MEDICINE, new AddTaskCommand(expectedTask));

        // multiple times - last time accepted
        assertParseSuccess(parser, NAME_DESC_GEORGE + DATE_DESC_NOV + TIME_DESC_TENAM
                + TIME_DESC_SEVENPM + DESC_MEDICINE, new AddTaskCommand(expectedTask));

        // multiple names - all accepted
        expectedTask = new TaskBuilder(GEORGE_INSULIN)
                .withNames(VALID_NAME_GEORGE, VALID_NAME_ALICE)
                .withStatus("false", "true").build();
        assertParseSuccess(parser, NAME_DESC_GEORGE + NAME_DESC_ALICE + DATE_DESC_NOV
                + TIME_DESC_SEVENPM + DESC_MEDICINE, new AddTaskCommand(expectedTask));

    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Task expectedTask = new TaskBuilder(DO_PAPERWORK).build();
        assertParseSuccess(parser, DESC_PAPERWORK + DATE_DESC_JAN + TIME_DESC_TENAM
                        + RECUR_MONTH,
                new AddTaskCommand(expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_ALICE + DESC_MEDICINE + DATE_DESC_NOV + TIME_DESC_SEVENPM,
                expectedMessage);

        // missing description prefix
        assertParseFailure(parser, NAME_DESC_ALICE + VALID_DESC_MEDICINE + DATE_DESC_JAN + TIME_DESC_TENAM,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, NAME_DESC_ALICE + DESC_MEDICINE + VALID_DATE_NOV + TIME_DESC_SEVENPM,
                expectedMessage);

        // missing time prefix
        assertParseFailure(parser, NAME_DESC_ALICE + DESC_MEDICINE + DATE_DESC_NOV + VALID_TIME_TENAM,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_ALICE + VALID_DESC_MEDICINE + VALID_DATE_NOV + VALID_TIME_TENAM,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + DESC_MEDICINE + DATE_DESC_NOV + TIME_DESC_TENAM,
                Name.MESSAGE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, NAME_DESC_ALICE + INVALID_DESC + DATE_DESC_NOV
                + TIME_DESC_TENAM, Description.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, NAME_DESC_ALICE + DESC_MEDICINE + INVALID_DATE
                + TIME_DESC_TENAM, DateTime.MESSAGE_DATE_CONSTRAINTS);

        // invalid time
        assertParseFailure(parser, NAME_DESC_ALICE + DESC_MEDICINE + DATE_DESC_NOV
                + INVALID_TIME, DateTime.MESSAGE_TIME_CONSTRAINTS);
        ;

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + DESC_MEDICINE + DATE_DESC_NOV
                        + INVALID_TIME,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_ALICE + DESC_MEDICINE
                        + DATE_DESC_NOV + TIME_DESC_TENAM,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
}
