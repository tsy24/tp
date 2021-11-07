package nurseybook.model;

import static nurseybook.commons.util.CollectionUtil.requireAllNonNull;

import nurseybook.logic.commands.CommandResult;

/**
 * Represents a state of the nursey book.
 * Contains a copy of the nursey book at a particular state
 * and the commandResult of the command that changed the state of the nursey book.
 */
public class NurseyBookState {

    private final ReadOnlyNurseyBook nurseyBook;
    private final CommandResult commandResult;

    /**
     * Creates a NurseyBookState using the copied {@code nurseyBook} and the {@code commandResult}.
     */
    public NurseyBookState(ReadOnlyNurseyBook nurseyBook, CommandResult commandResult) {
        requireAllNonNull(nurseyBook, commandResult);
        this.nurseyBook = nurseyBook;
        this.commandResult = commandResult;
    }

    public ReadOnlyNurseyBook getNurseyBook() {
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

