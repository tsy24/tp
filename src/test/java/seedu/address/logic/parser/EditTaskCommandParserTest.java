package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.TaskCommandTestUtil.DATE_DESC_JAN;
import static seedu.address.logic.commands.TaskCommandTestUtil.DATE_DESC_NOV;
import static seedu.address.logic.commands.TaskCommandTestUtil.DESC_COVID;
import static seedu.address.logic.commands.TaskCommandTestUtil.DESC_PAPERWORK;
import static seedu.address.logic.commands.TaskCommandTestUtil.INVALID_DATE;
import static seedu.address.logic.commands.TaskCommandTestUtil.INVALID_DESC;
import static seedu.address.logic.commands.TaskCommandTestUtil.INVALID_RECUR;
import static seedu.address.logic.commands.TaskCommandTestUtil.INVALID_TIME;
import static seedu.address.logic.commands.TaskCommandTestUtil.NAME_DESC_ALEX;
import static seedu.address.logic.commands.TaskCommandTestUtil.RECUR_DAY;
import static seedu.address.logic.commands.TaskCommandTestUtil.RECUR_MONTH;
import static seedu.address.logic.commands.TaskCommandTestUtil.RECUR_NONE;
import static seedu.address.logic.commands.TaskCommandTestUtil.TIME_DESC_SEVENPM;
import static seedu.address.logic.commands.TaskCommandTestUtil.TIME_DESC_TENAM;
import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_DATE_JAN;
import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_DESC_COVID;
import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_DESC_PAPERWORK;
import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_NAME_ALEX;
import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_TIME_SEVENPM;
import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_TIME_TENAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INDEX_TOO_EXTREME;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.model.person.Name;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Description;
import seedu.address.model.task.Recurrence;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskCommandParserTest {

    private static final String NAME_EMPTY = " " + PREFIX_NAME;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE);

    private EditTaskCommandParser parser = new EditTaskCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_DESC_PAPERWORK, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditTaskCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // Not non-zero unsigned index integer
        assertParseFailure(parser, "-5" + DESC_PAPERWORK, MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "0" + DESC_PAPERWORK, MESSAGE_INVALID_INDEX);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INDEX_TOO_EXTREME);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);

        // Extreme indices
        assertParseFailure(parser, "9999999999", String.format(MESSAGE_INDEX_TOO_EXTREME,
                EditTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-999999999", String.format(MESSAGE_INDEX_TOO_EXTREME,
                EditTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_DESC, Description.MESSAGE_CONSTRAINTS); // invalid description
        assertParseFailure(parser, "1" + INVALID_DATE, DateTime.MESSAGE_DATE_CONSTRAINTS); // invalid date
        assertParseFailure(parser, "1" + INVALID_TIME, DateTime.MESSAGE_TIME_CONSTRAINTS); // invalid time
        assertParseFailure(parser, "1" + INVALID_RECUR, Recurrence.MESSAGE_CONSTRAINTS); // invalid recurrence

        // invalid date followed by valid time
        assertParseFailure(parser, "1" + INVALID_DATE + INVALID_TIME, DateTime.MESSAGE_DATE_CONSTRAINTS);

        // valid date followed by invalid date. The test case for invalid date followed by valid date
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + DATE_DESC_JAN + INVALID_DATE, DateTime.MESSAGE_DATE_CONSTRAINTS);

        // invalid time followed by valid recurrence
        assertParseFailure(parser, "1" + INVALID_TIME + RECUR_DAY, DateTime.MESSAGE_TIME_CONSTRAINTS);

        // valid time followed by invalid time. The test case for invalid time followed by valid time
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + TIME_DESC_SEVENPM + INVALID_TIME, DateTime.MESSAGE_TIME_CONSTRAINTS);

        // invalid description followed by valid recurrence
        assertParseFailure(parser, "1" + INVALID_DESC + RECUR_DAY, Description.MESSAGE_CONSTRAINTS);

        // valid description followed by invalid description. The test case for invalid description
        // followed by valid description
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + DESC_PAPERWORK + INVALID_DESC, Description.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_NAME} alone will reset the tags of the {@code Task} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + NAME_DESC_AMY + NAME_DESC_ALEX + NAME_EMPTY,
                Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + NAME_DESC_AMY + NAME_EMPTY + NAME_DESC_ALEX,
                Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + NAME_EMPTY + NAME_DESC_AMY + NAME_DESC_ALEX,
                Name.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_DATE + INVALID_RECUR + DESC_PAPERWORK
                        + TIME_DESC_SEVENPM + RECUR_NONE,
                DateTime.MESSAGE_DATE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY + NAME_DESC_ALEX + DESC_PAPERWORK + DATE_DESC_JAN
                + TIME_DESC_SEVENPM + RECUR_NONE;

        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withNames(VALID_NAME_AMY, VALID_NAME_ALEX)
                .withDescription(VALID_DESC_PAPERWORK).withDate(VALID_DATE_JAN).withTime(VALID_TIME_SEVENPM)
                .withRecurrence(Recurrence.RecurrenceType.NONE.name()).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + DESC_PAPERWORK + DATE_DESC_JAN + TIME_DESC_SEVENPM;

        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withDescription(VALID_DESC_PAPERWORK).withDate(VALID_DATE_JAN).withTime(VALID_TIME_SEVENPM).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // names
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withNames(VALID_NAME_AMY).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = targetIndex.getOneBased() + DESC_PAPERWORK;
        descriptor = new EditTaskDescriptorBuilder().withDescription(VALID_DESC_PAPERWORK).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = targetIndex.getOneBased() + DATE_DESC_JAN;
        descriptor = new EditTaskDescriptorBuilder().withDate(VALID_DATE_JAN).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // time
        userInput = targetIndex.getOneBased() + TIME_DESC_SEVENPM;
        descriptor = new EditTaskDescriptorBuilder().withTime(VALID_TIME_SEVENPM).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // recurrence
        userInput = targetIndex.getOneBased() + RECUR_MONTH;
        descriptor = new EditTaskDescriptorBuilder().withRecurrence(Recurrence.RecurrenceType.MONTH.name()).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + NAME_DESC_ALEX + DATE_DESC_JAN + TIME_DESC_SEVENPM
                + DESC_PAPERWORK + RECUR_DAY

                + NAME_DESC_ALEX + DATE_DESC_NOV + TIME_DESC_TENAM
                + DESC_PAPERWORK + RECUR_MONTH

                + NAME_DESC_AMY + DATE_DESC_JAN + TIME_DESC_TENAM + DESC_COVID + RECUR_DAY;

        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withNames(VALID_NAME_AMY, VALID_NAME_ALEX)
                .withDescription(VALID_DESC_COVID).withDate(VALID_DATE_JAN).withTime(VALID_TIME_TENAM)
                .withRecurrence(Recurrence.RecurrenceType.DAY.name()).build();

        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_TIME + TIME_DESC_SEVENPM;
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withTime(VALID_TIME_SEVENPM).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + NAME_DESC_AMY + DESC_PAPERWORK
                + DATE_DESC_JAN + INVALID_TIME + TIME_DESC_SEVENPM
                + RECUR_DAY;
        descriptor = new EditTaskDescriptorBuilder().withNames(VALID_NAME_AMY)
                .withDescription(VALID_DESC_PAPERWORK).withDate(VALID_DATE_JAN).withTime(VALID_TIME_SEVENPM)
                .withRecurrence(Recurrence.RecurrenceType.DAY.name()).build();

        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetNames_success() {
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + NAME_EMPTY;
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withNames().build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

