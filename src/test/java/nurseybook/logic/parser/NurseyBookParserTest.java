package nurseybook.logic.parser;

import static nurseybook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nurseybook.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static nurseybook.logic.commands.CommandTestUtil.SET_ONE_TAG;
import static nurseybook.logic.commands.CommandTestUtil.TAG_DESC_DIABETES;
import static nurseybook.testutil.Assert.assertThrows;
import static nurseybook.testutil.ElderlyUtil.getEditElderlyDescriptorDetails;
import static nurseybook.testutil.TypicalIndexes.INDEX_FIRST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import nurseybook.logic.commands.AddCommand;
import nurseybook.logic.commands.AddTagCommand;
import nurseybook.logic.commands.AddTaskCommand;
import nurseybook.logic.commands.ClearCommand;
import nurseybook.logic.commands.DeleteCommand;
import nurseybook.logic.commands.DeleteNokCommand;
import nurseybook.logic.commands.DeleteTagCommand;
import nurseybook.logic.commands.DeleteTaskCommand;
import nurseybook.logic.commands.DoneTaskCommand;
import nurseybook.logic.commands.EditCommand;
import nurseybook.logic.commands.ExitCommand;
import nurseybook.logic.commands.FilterCommand;
import nurseybook.logic.commands.FindElderlyCommand;
import nurseybook.logic.commands.FindTaskCommand;
import nurseybook.logic.commands.HelpCommand;
import nurseybook.logic.commands.RedoCommand;
import nurseybook.logic.commands.RemarkCommand;
import nurseybook.logic.commands.RemindCommand;
import nurseybook.logic.commands.UndoCommand;
import nurseybook.logic.commands.ViewDetailsCommand;
import nurseybook.logic.commands.ViewElderlyCommand;
import nurseybook.logic.commands.ViewScheduleCommand;
import nurseybook.logic.commands.ViewTasksCommand;
import nurseybook.logic.parser.exceptions.ParseException;
import nurseybook.model.person.Elderly;
import nurseybook.model.person.NameContainsKeywordsPredicate;
import nurseybook.model.person.Remark;
import nurseybook.model.tag.ElderlyHasTagPredicate;
import nurseybook.model.task.DateTimeContainsDatePredicate;
import nurseybook.model.task.DescriptionContainsKeywordPredicate;
import nurseybook.model.task.Task;
import nurseybook.testutil.EditElderlyDescriptorBuilder;
import nurseybook.testutil.ElderlyBuilder;
import nurseybook.testutil.ElderlyUtil;
import nurseybook.testutil.TaskBuilder;
import nurseybook.testutil.TaskUtil;

public class NurseyBookParserTest {

    private final NurseyBookParser parser = new NurseyBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Elderly elderly = new ElderlyBuilder().withRemark("").build();
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
    public void parseCommand_editElderly() throws Exception {
        Elderly elderly = new ElderlyBuilder().build();
        EditCommand.EditElderlyDescriptor descriptor = new EditElderlyDescriptorBuilder(elderly).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + getEditElderlyDescriptorDetails(descriptor));
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
    public void parseCommand_viewDetails() throws Exception {
        ViewDetailsCommand command = (ViewDetailsCommand) parser.parseCommand(
                ViewDetailsCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new ViewDetailsCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindElderlyCommand command = (FindElderlyCommand) parser.parseCommand(
                FindElderlyCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindElderlyCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findTask() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindTaskCommand command = (FindTaskCommand) parser.parseCommand(
                FindTaskCommand.COMMAND_WORD + " "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindTaskCommand(new DescriptionContainsKeywordPredicate(keywords)), command);
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
                + INDEX_FIRST.getOneBased() + " " + CliSyntax.PREFIX_REMARK + remark.value);
        assertEquals(new RemarkCommand(INDEX_FIRST, remark), command);
    }

    @Test
    public void parseCommand_remind() throws Exception {
        assertTrue(parser.parseCommand(RemindCommand.COMMAND_WORD) instanceof RemindCommand);
        assertTrue(parser.parseCommand(RemindCommand.COMMAND_WORD + " 3") instanceof RemindCommand);
    }

    @Test
    public void parseCommand_addTag() throws Exception {
        AddTagCommand addTagCommand = (AddTagCommand) parser.parseCommand(AddTagCommand.COMMAND_WORD + " "
            + INDEX_FIRST.getOneBased() + " " + TAG_DESC_DIABETES);
        assertEquals(new AddTagCommand(INDEX_FIRST, SET_ONE_TAG), addTagCommand);
    }

    @Test
    public void parseCommand_deleteTag() throws Exception {
        DeleteTagCommand deleteTagCommand = (DeleteTagCommand) parser.parseCommand(DeleteTagCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + TAG_DESC_DIABETES);
        assertEquals(new DeleteTagCommand(INDEX_FIRST, SET_ONE_TAG), deleteTagCommand);
    }

    @Test
    public void parseCommand_filter() throws Exception {
        FilterCommand filterCommand = (FilterCommand) parser.parseCommand(FilterCommand.COMMAND_WORD + " "
                + TAG_DESC_DIABETES);
        assertEquals(new FilterCommand(new ElderlyHasTagPredicate(SET_ONE_TAG)), filterCommand);
    }

    @Test
    public void parseCommand_deleteNok() throws Exception {
        DeleteNokCommand deleteNokCommand = (DeleteNokCommand) parser.parseCommand(DeleteNokCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteNokCommand(INDEX_FIRST), deleteNokCommand);
    }

    @Test
    public void parseCommand_viewSchedule() throws Exception {
        LocalDate todayDate = LocalDate.now();
        String todayDateString = todayDate.toString();

        //check that regex of todayDateString is in the form of yyyy-mm-dd
        assert todayDateString.matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$");

        ViewScheduleCommand viewScheduleCommand = (ViewScheduleCommand) parser.parseCommand(
                ViewScheduleCommand.COMMAND_WORD + " " + todayDateString);
        LocalDate keyDate = LocalDate.parse(todayDateString);
        assertEquals(new ViewScheduleCommand(new DateTimeContainsDatePredicate(keyDate), keyDate), viewScheduleCommand);
    }

    @Test
    public void parseCommand_undo() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD + " 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_redo() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD + " 3") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class,
                MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
