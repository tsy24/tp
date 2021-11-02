package nurseybook.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Elderlies (Elderlies are considered duplicates if they have
 * the same identity).
 */
public class DuplicateElderlyException extends RuntimeException {
    public DuplicateElderlyException() {
        super("Operation would result in duplicate elderlies");
    }
}
