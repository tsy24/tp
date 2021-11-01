package nurseybook.model;

import static java.util.Objects.requireNonNull;

import nurseybook.logic.commands.CommandResult;

/**
 * Represents a state of the nurseybook.
 * Contains a copy of the nurseybook at a particular state
 * and the commandResult of the command that changed the state of the nurseybook.
 */
public class NurseyBookState {

    private final ReadOnlyAddressBook nurseyBook;
    private final CommandResult commandResult;

    /**
     * Creates a NurseyBookState using the copied {@code nurseyBook} and the {@code commandResult}.
     */
    public NurseyBookState(ReadOnlyAddressBook nurseyBook, CommandResult commandResult) {
        requireNonNull(nurseyBook);
        this.nurseyBook = nurseyBook;
        this.commandResult = commandResult;
    }

    public ReadOnlyAddressBook getNurseyBook() {
        return nurseyBook;
    }

    public CommandResult getCommandResult() {
        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NurseyBookState // instanceof handles nulls
                && nurseyBook.equals(((NurseyBookState) other).nurseyBook)
                && commandResult.equals(((NurseyBookState) other).commandResult));
    }
}

