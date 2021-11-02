package nurseybook.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import nurseybook.testutil.TaskBuilder;

public class DateTimeContainsDatePredicateTest {

    @Test
    public void equals() {
        LocalDate firstPredicateKeyDate = LocalDate.parse("2021-11-02");
        LocalDate secondPredicateKeyDate = LocalDate.parse("2021-11-03");

        DateTimeContainsDatePredicate firstPredicate =
                new DateTimeContainsDatePredicate(firstPredicateKeyDate);
        DateTimeContainsDatePredicate secondPredicate =
                new DateTimeContainsDatePredicate(secondPredicateKeyDate);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DateTimeContainsDatePredicate firstPredicateCopy =
                new DateTimeContainsDatePredicate(firstPredicateKeyDate);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_dateTimeContainsDate_returnsTrue() {
        LocalDate predicateKeyDate = LocalDate.parse("2021-11-02");
        LocalDate secondPredicateKeyDate = LocalDate.parse("2021-11-03");

        // valid keyDate
        DateTimeContainsDatePredicate predicate =
                new DateTimeContainsDatePredicate(predicateKeyDate);
        assertTrue(predicate.test(new TaskBuilder().withDateTime("2021-11-02", "10:30").build()));
    }
}
