package nurseybook.logic.commands;

import static java.util.Objects.requireNonNull;

import nurseybook.logic.parser.CliSyntax;
import nurseybook.logic.commands.CommandResult.ListDisplayChange;
import nurseybook.logic.commands.exceptions.CommandException;
import nurseybook.model.Model;
import nurseybook.model.person.Elderly;

/**
 * Adds a elderly to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "addElderly";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a elderly to the address book.\n"
            + "Parameters: "
            + CliSyntax.PREFIX_NAME + "NAME "
            + CliSyntax.PREFIX_AGE + "AGE "
            + CliSyntax.PREFIX_GENDER + "GENDER "
            + CliSyntax.PREFIX_ROOM_NUM + "ROOM_NUMBER "
            + "[" + CliSyntax.PREFIX_NOK_NAME + "NOK_NAME] "
            + "[" + CliSyntax.PREFIX_RELATIONSHIP + "NOK_RELATIONSHIP] "
            + "[" + CliSyntax.PREFIX_PHONE + "NOK_PHONE_NUMBER] "
            + "[" + CliSyntax.PREFIX_EMAIL + "NOK_EMAIL] "
            + "[" + CliSyntax.PREFIX_ADDRESS + "NOK_ADDRESS] "
            + "[" + CliSyntax.PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "John Doe "
            + CliSyntax.PREFIX_AGE + "40 "
            + CliSyntax.PREFIX_GENDER + "M "
            + CliSyntax.PREFIX_ROOM_NUM + "53 "
            + CliSyntax.PREFIX_NOK_NAME + "John Beckham "
            + CliSyntax.PREFIX_RELATIONSHIP + "Father "
            + CliSyntax.PREFIX_PHONE + "98765432 "
            + CliSyntax.PREFIX_EMAIL + "mary@example.com "
            + CliSyntax.PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + CliSyntax.PREFIX_TAG + "friends "
            + CliSyntax.PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New elderly added: %1$s";
    public static final String MESSAGE_DUPLICATE_ELDERLY = "This elderly already exists in the address book";

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
