package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.commands.DoneTaskCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditElderlyDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.RemindCommand;
import seedu.address.logic.commands.ViewElderlyCommand;
import seedu.address.logic.commands.ViewTasksCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Elderly;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Remark;
import seedu.address.model.task.Task;
import seedu.address.testutil.EditElderlyDescriptorBuilder;
import seedu.address.testutil.ElderlyBuilder;
import seedu.address.testutil.ElderlyUtil;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TaskUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Elderly elderly = new ElderlyBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(ElderlyUtil.getAddCommand(elderly));
        assertEquals(new AddCommand(elderly), command);
    }

    @Test
    public void parseCommand_addTask() throws Exception {
        Task task = new TaskBuilder().build();
        AddTaskCommand command = (AddTaskCommand) parser.parseCommand(TaskUtil.getAddTaskCommand(task));
        assertEquals(new AddTaskCommand(task), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_deleteTask() throws Exception {
        DeleteTaskCommand command = (DeleteTaskCommand) parser.parseCommand(
                DeleteTaskCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteTaskCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_doneTask() throws Exception {
        DoneTaskCommand command = (DoneTaskCommand) parser.parseCommand(
                DoneTaskCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DoneTaskCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Elderly elderly = new ElderlyBuilder().build();
        EditElderlyDescriptor descriptor = new EditElderlyDescriptorBuilder(elderly).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + ElderlyUtil.getEditElderlyDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_viewElderly() throws Exception {
        assertTrue(parser.parseCommand(ViewElderlyCommand.COMMAND_WORD) instanceof ViewElderlyCommand);
        assertTrue(parser.parseCommand(ViewElderlyCommand.COMMAND_WORD + " 3") instanceof ViewElderlyCommand);
    }

    @Test
    public void parseCommand_viewTasks() throws Exception {
        assertTrue(parser.parseCommand(ViewTasksCommand.COMMAND_WORD) instanceof ViewTasksCommand);
        assertTrue(parser.parseCommand(ViewTasksCommand.COMMAND_WORD + " 3") instanceof ViewTasksCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_remark() throws Exception {
        final Remark remark = new Remark("Some remark.");
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + PREFIX_REMARK + remark.value);
        assertEquals(new RemarkCommand(INDEX_FIRST, remark), command);
    }

    @Test
    public void parseCommand_remind() throws Exception {
        assertTrue(parser.parseCommand(RemindCommand.COMMAND_WORD) instanceof RemindCommand);
        assertTrue(parser.parseCommand(RemindCommand.COMMAND_WORD + " 3") instanceof RemindCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
