package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.TaskCommandTestUtil.DATE_DESC_JAN;
import static seedu.address.logic.commands.TaskCommandTestUtil.DATE_DESC_NOV;
import static seedu.address.logic.commands.TaskCommandTestUtil.DESC_MEDICINE;
import static seedu.address.logic.commands.TaskCommandTestUtil.DESC_PAPERWORK;
import static seedu.address.logic.commands.TaskCommandTestUtil.INVALID_DATE;
import static seedu.address.logic.commands.TaskCommandTestUtil.INVALID_DESC;
import static seedu.address.logic.commands.TaskCommandTestUtil.INVALID_TIME;
import static seedu.address.logic.commands.TaskCommandTestUtil.NAME_DESC_ALEX;
import static seedu.address.logic.commands.TaskCommandTestUtil.NAME_DESC_KEITH;
import static seedu.address.logic.commands.TaskCommandTestUtil.TIME_DESC_SEVENPM;
import static seedu.address.logic.commands.TaskCommandTestUtil.TIME_DESC_TENAM;
import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_DATE_NOV;
import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_DESC_MEDICINE;
import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_NAME_ALEX;
import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_NAME_KEITH;
import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_TIME_TENAM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalTasks.DO_PAPERWORK;
import static seedu.address.testutil.TypicalTasks.KEITH_INSULIN;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.person.Name;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Description;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class AddTaskCommandParserTest {
    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder(KEITH_INSULIN).withStatus("false", "true").build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_KEITH + DATE_DESC_NOV
                + TIME_DESC_SEVENPM + DESC_MEDICINE, new AddTaskCommand(expectedTask));

        // multiple desc - last desc accepted
        assertParseSuccess(parser, NAME_DESC_KEITH + DATE_DESC_NOV + TIME_DESC_SEVENPM
                + DESC_PAPERWORK + DESC_MEDICINE, new AddTaskCommand(expectedTask));

        // multiple dates - last date accepted
        assertParseSuccess(parser, NAME_DESC_KEITH + DATE_DESC_JAN + DATE_DESC_NOV
                + TIME_DESC_SEVENPM + DESC_MEDICINE, new AddTaskCommand(expectedTask));

        // multiple times - last time accepted
        assertParseSuccess(parser, NAME_DESC_KEITH + DATE_DESC_NOV + TIME_DESC_TENAM
                + TIME_DESC_SEVENPM + DESC_MEDICINE, new AddTaskCommand(expectedTask));

        // multiple names - all accepted
        expectedTask = new TaskBuilder(KEITH_INSULIN)
                .withNames(VALID_NAME_KEITH, VALID_NAME_ALEX)
                .withStatus("false", "true").build();
        assertParseSuccess(parser, NAME_DESC_KEITH + NAME_DESC_ALEX + DATE_DESC_NOV
                + TIME_DESC_SEVENPM + DESC_MEDICINE, new AddTaskCommand(expectedTask));

    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Task expectedTask = new TaskBuilder(DO_PAPERWORK).build();
        assertParseSuccess(parser, DESC_PAPERWORK + DATE_DESC_JAN + TIME_DESC_TENAM,
                new AddTaskCommand(expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_ALEX + DESC_MEDICINE + DATE_DESC_NOV + TIME_DESC_SEVENPM,
                expectedMessage);

        // missing description prefix
        assertParseFailure(parser, NAME_DESC_ALEX + VALID_DESC_MEDICINE + DATE_DESC_JAN + TIME_DESC_TENAM,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, NAME_DESC_ALEX + DESC_MEDICINE + VALID_DATE_NOV + TIME_DESC_SEVENPM,
                expectedMessage);

        // missing time prefix
        assertParseFailure(parser, NAME_DESC_ALEX + DESC_MEDICINE + DATE_DESC_NOV + VALID_TIME_TENAM,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_ALEX + VALID_DESC_MEDICINE + VALID_DATE_NOV + VALID_TIME_TENAM,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + DESC_MEDICINE + DATE_DESC_NOV + TIME_DESC_TENAM,
                Name.MESSAGE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, NAME_DESC_ALEX + INVALID_DESC + DATE_DESC_NOV
                + TIME_DESC_TENAM, Description.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, NAME_DESC_ALEX + DESC_MEDICINE + INVALID_DATE
                + TIME_DESC_TENAM, DateTime.MESSAGE_DATE_CONSTRAINTS);

        // invalid time
        assertParseFailure(parser, NAME_DESC_ALEX + DESC_MEDICINE + DATE_DESC_NOV
                + INVALID_TIME, DateTime.MESSAGE_TIME_CONSTRAINTS);
        ;

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + DESC_MEDICINE + DATE_DESC_NOV
                        + INVALID_TIME,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_ALEX + DESC_MEDICINE
                        + DATE_DESC_NOV + TIME_DESC_TENAM,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
    }
}
