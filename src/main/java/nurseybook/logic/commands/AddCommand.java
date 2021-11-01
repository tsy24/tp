package nurseybook.logic.commands;

import static java.util.Objects.requireNonNull;
import static nurseybook.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static nurseybook.logic.parser.CliSyntax.PREFIX_AGE;
import static nurseybook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static nurseybook.logic.parser.CliSyntax.PREFIX_GENDER;
import static nurseybook.logic.parser.CliSyntax.PREFIX_NAME;
import static nurseybook.logic.parser.CliSyntax.PREFIX_NOK_NAME;
import static nurseybook.logic.parser.CliSyntax.PREFIX_PHONE;
import static nurseybook.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;
import static nurseybook.logic.parser.CliSyntax.PREFIX_ROOM_NUM;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TAG;

import nurseybook.logic.commands.CommandResult.ListDisplayChange;
import nurseybook.logic.commands.exceptions.CommandException;
import nurseybook.model.Model;
import nurseybook.model.person.Elderly;

/**
 * Adds a elderly to the nursey book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "addElderly";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a elderly to the nursey book.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_AGE + "AGE "
            + PREFIX_GENDER + "GENDER "
            + PREFIX_ROOM_NUM + "ROOM_NUMBER "
            + "[" + PREFIX_NOK_NAME + "NOK_NAME] "
            + "[" + PREFIX_RELATIONSHIP + "NOK_RELATIONSHIP] "
            + "[" + PREFIX_PHONE + "NOK_PHONE_NUMBER] "
            + "[" + PREFIX_EMAIL + "NOK_EMAIL] "
            + "[" + PREFIX_ADDRESS + "NOK_ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_AGE + "40 "
            + PREFIX_GENDER + "M "
            + PREFIX_ROOM_NUM + "53 "
            + PREFIX_NOK_NAME + "John Beckham "
            + PREFIX_RELATIONSHIP + "Father "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "mary@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New elderly added: %1$s";

    public static final String MESSAGE_DUPLICATE_ELDERLY = "This elderly already exists in the nursey book";


    private final Elderly toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Elderly}
     */
    public AddCommand(Elderly elderly) {
        requireNonNull(elderly);
        toAdd = elderly;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasElderly(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ELDERLY);
        }

        model.addElderly(toAdd);
        CommandResult result = new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), ListDisplayChange.ELDERLY);
        model.commitNurseyBook(result);
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
