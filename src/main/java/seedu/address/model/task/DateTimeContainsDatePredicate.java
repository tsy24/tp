package seedu.address.model.task;

import java.time.LocalDate;
import java.util.function.Predicate;

/**
 * Tests that a {@code Task}'s {@code DateTime} is on the same date as the given date, within the next 12 weeks.
 */
public class DateTimeContainsDatePredicate implements Predicate<Task> {

    private final LocalDate keyDate;

    public DateTimeContainsDatePredicate(LocalDate keyDate) {
        this.keyDate = keyDate;
    }

    @Override
    public boolean test(Task task) {
        return task.isSameDateTask(keyDate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTimeContainsDatePredicate // instanceof handles nulls
                && keyDate.equals(((DateTimeContainsDatePredicate) other).keyDate)); // state check
    }
}
