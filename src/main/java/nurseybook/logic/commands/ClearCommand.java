package nurseybook.logic.commands;

import static java.util.Objects.requireNonNull;

import nurseybook.model.Model;
import nurseybook.model.NurseyBook;

/**
 * Clears the nursey book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "NurseyBook has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setVersionedNurseyBook(new NurseyBook());
        CommandResult result = new CommandResult(MESSAGE_SUCCESS);
        model.commitNurseyBook(result);
        return result;
    }
}
