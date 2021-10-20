package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TaskBuilder;

public class DescriptionContainsKeywordPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        DescriptionContainsKeywordPredicate firstPredicate =
                new DescriptionContainsKeywordPredicate(firstPredicateKeywordList);
        DescriptionContainsKeywordPredicate secondPredicate =
                new DescriptionContainsKeywordPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DescriptionContainsKeywordPredicate firstPredicateCopy =
                new DescriptionContainsKeywordPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_descriptionContainsKeywords_returnsTrue() {
        // One keyword
        DescriptionContainsKeywordPredicate predicate =
                new DescriptionContainsKeywordPredicate(Collections.singletonList("Pfizer"));
        assertTrue(predicate.test(new TaskBuilder().withDesc("Get 2nd Pfizer shot").build()));

        // Multiple keywords
        predicate = new DescriptionContainsKeywordPredicate(Arrays.asList("Pfizer", "shot"));
        assertTrue(predicate.test(new TaskBuilder().withDesc("Get 2nd Pfizer shot").build()));

        // Only one matching keyword
        predicate = new DescriptionContainsKeywordPredicate(Arrays.asList("Pfizer", "Yoga"));
        assertTrue(predicate.test(new TaskBuilder().withDesc("Get 2nd Pfizer shot").build()));

        // Mixed-case keywords
        predicate = new DescriptionContainsKeywordPredicate(Arrays.asList("pfIzeR", "sHot"));
        assertTrue(predicate.test(new TaskBuilder().withDesc("Get 2nd Pfizer shot").build()));
    }

    @Test
    public void test_descriptionDoesNotContainsKeywords_returnsFalse() {
        // Zero keywords
        DescriptionContainsKeywordPredicate predicate =
                new DescriptionContainsKeywordPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TaskBuilder().withDesc("Get 2nd Pfizer shot").build()));

        // Non-matching keyword
        predicate = new DescriptionContainsKeywordPredicate(Arrays.asList("Yoga"));
        assertFalse(predicate.test(new TaskBuilder().withDesc("Get 2nd Pfizer shot").build()));

        // Keywords match elderly name, date and time, but does not match description
        predicate = new DescriptionContainsKeywordPredicate(Arrays.asList("Khong", "Guan", "2021-11-11", "15:30"));
        assertFalse(predicate.test(new TaskBuilder().withNames("Khong Guan").withDesc("Get 2nd Pfizer shot")
                .withDateTime("2021-11-11", "15:30").build()));
    }
}
