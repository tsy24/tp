package nurseybook.logic.commands;

import static java.util.Objects.requireNonNull;

import nurseybook.model.AddressBook;
import nurseybook.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setVersionedNurseyBook(new AddressBook());
        CommandResult result = new CommandResult(MESSAGE_SUCCESS);
        model.commitNurseyBook(result);
        return result;
    }
}
