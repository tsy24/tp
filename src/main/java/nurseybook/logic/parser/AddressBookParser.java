package nurseybook.logic.parser;

import static nurseybook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nurseybook.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nurseybook.logic.commands.AddCommand;
import nurseybook.logic.commands.AddTagCommand;
import nurseybook.logic.commands.AddTaskCommand;
import nurseybook.logic.commands.ClearCommand;
import nurseybook.logic.commands.Command;
import nurseybook.logic.commands.DeleteCommand;
import nurseybook.logic.commands.DeleteNokCommand;
import nurseybook.logic.commands.DeleteTagCommand;
import nurseybook.logic.commands.DeleteTaskCommand;
import nurseybook.logic.commands.DoneTaskCommand;
import nurseybook.logic.commands.EditCommand;
import nurseybook.logic.commands.EditTaskCommand;
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

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        return createCommand(commandWord, arguments);
    }

    private Command createCommand(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case AddTaskCommand.COMMAND_WORD:
            return new AddTaskCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeleteNokCommand.COMMAND_WORD:
            return new DeleteNokCommandParser().parse(arguments);

        case DeleteTaskCommand.COMMAND_WORD:
            return new DeleteTaskCommandParser().parse(arguments);

        case DoneTaskCommand.COMMAND_WORD:
            return new DoneTaskCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case EditTaskCommand.COMMAND_WORD:
            return new EditTaskCommandParser().parse(arguments);

        case FindElderlyCommand.COMMAND_WORD:
            return new FindElderlyCommandParser().parse(arguments);

        case FindTaskCommand.COMMAND_WORD:
            return new FindTaskCommandParser().parse(arguments);

        case RemarkCommand.COMMAND_WORD:
            return new RemarkCommandParser().parse(arguments);

        case RemindCommand.COMMAND_WORD:
            return new RemindCommandParser().parse(arguments);

        case ViewDetailsCommand.COMMAND_WORD:
            return new ViewDetailsCommandParser().parse(arguments);

        case ViewTasksCommand.COMMAND_WORD:
            return new ViewTasksCommand();

        case ViewElderlyCommand.COMMAND_WORD:
            return new ViewElderlyCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddTagCommand.COMMAND_WORD:
            return new AddTagCommandParser().parse(arguments);

        case DeleteTagCommand.COMMAND_WORD:
            return new DeleteTagCommandParser().parse(arguments);

        case FilterCommand.COMMAND_WORD:
            return new FilterCommandParser().parse(arguments);

        case ViewScheduleCommand.COMMAND_WORD:
            return new ViewScheduleCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
