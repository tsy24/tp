package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.SET_TWO_TAGS;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_DIABETES;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_EMPTY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.tag.ElderlyHasTagPredicate;
import seedu.address.model.tag.Tag;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_validTags_returnsFilterCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand = new FilterCommand(new ElderlyHasTagPredicate(SET_TWO_TAGS));
        assertParseSuccess(parser, TAG_DESC_DIABETES + TAG_DESC_FRIEND, expectedFilterCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n " + TAG_DESC_DIABETES + " \n \t " + TAG_DESC_FRIEND + "\t",
                expectedFilterCommand);
    }

    @Test
    public void parse_invalidTag_failure() {
        assertParseFailure(parser, INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag
        assertParseFailure(parser, TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS); // invalid empty tag

        // parsing it together with a valid tag results in error
        assertParseFailure(parser, TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE);

        // no Tags
        assertParseFailure(parser, "     ", expectedMessage);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "i/ string", expectedMessage);
    }

}
