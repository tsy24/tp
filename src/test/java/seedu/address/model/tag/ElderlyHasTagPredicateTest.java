package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.SET_ONE_TAG;
import static seedu.address.logic.commands.CommandTestUtil.SET_TWO_TAGS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DIABETES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ElderlyBuilder;

public class ElderlyHasTagPredicateTest {

    @Test
    public void equals() {

        ElderlyHasTagPredicate firstPredicate = new ElderlyHasTagPredicate(SET_ONE_TAG);
        ElderlyHasTagPredicate secondPredicate = new ElderlyHasTagPredicate(SET_TWO_TAGS);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ElderlyHasTagPredicate firstPredicateCopy = new ElderlyHasTagPredicate(SET_ONE_TAG);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different elderly -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_elderlyHasTags_returnsTrue() {
        // One tag
        ElderlyHasTagPredicate predicate = new ElderlyHasTagPredicate(SET_ONE_TAG);
        assertTrue(predicate.test(new ElderlyBuilder().withTags(VALID_TAG_DIABETES).build()));

        // Multiple tags
        predicate = new ElderlyHasTagPredicate(SET_TWO_TAGS);
        assertTrue(predicate.test(new ElderlyBuilder().withTags(VALID_TAG_DIABETES, VALID_TAG_FRIEND).build()));

        // Mixed-case tag
        predicate = new ElderlyHasTagPredicate(Set.of(new Tag("DiaBeteS")));
        assertTrue(predicate.test(new ElderlyBuilder().withTags(VALID_TAG_DIABETES).build()));
    }

    @Test
    public void test_tagsDoNotMatch_returnsFalse() {
        // No matching tag
        ElderlyHasTagPredicate predicate = new ElderlyHasTagPredicate(SET_ONE_TAG);
        assertFalse(predicate.test(new ElderlyBuilder().build()));

        // Keywords match name but does not match tag
        predicate = new ElderlyHasTagPredicate(Set.of(new Tag("Alice")));
        assertFalse(predicate.test(new ElderlyBuilder().withName("Alice").withTags(VALID_TAG_DIABETES).build()));

        // Only one matching tag
        predicate = new ElderlyHasTagPredicate(SET_TWO_TAGS);
        assertFalse(predicate.test(new ElderlyBuilder().withTags(VALID_TAG_DIABETES).build()));
    }
}
