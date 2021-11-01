package nurseybook.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import nurseybook.logic.commands.CommandTestUtil;
import nurseybook.testutil.ElderlyBuilder;

public class ElderlyHasTagPredicateTest {

    @Test
    public void equals() {

        ElderlyHasTagPredicate firstPredicate = new ElderlyHasTagPredicate(CommandTestUtil.SET_ONE_TAG);
        ElderlyHasTagPredicate secondPredicate = new ElderlyHasTagPredicate(CommandTestUtil.SET_TWO_TAGS);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ElderlyHasTagPredicate firstPredicateCopy = new ElderlyHasTagPredicate(CommandTestUtil.SET_ONE_TAG);
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
        ElderlyHasTagPredicate predicate = new ElderlyHasTagPredicate(CommandTestUtil.SET_ONE_TAG);
        assertTrue(predicate.test(new ElderlyBuilder().withTags(CommandTestUtil.VALID_TAG_DIABETES).build()));

        // Multiple tags
        predicate = new ElderlyHasTagPredicate(CommandTestUtil.SET_TWO_TAGS);
        assertTrue(predicate.test(new ElderlyBuilder().withTags(CommandTestUtil.VALID_TAG_DIABETES, CommandTestUtil.VALID_TAG_FRIEND).build()));

        // Mixed-case tag
        predicate = new ElderlyHasTagPredicate(Set.of(new Tag("DiaBeteS")));
        assertTrue(predicate.test(new ElderlyBuilder().withTags(CommandTestUtil.VALID_TAG_DIABETES).build()));
    }

    @Test
    public void test_tagsDoNotMatch_returnsFalse() {
        // No matching tag
        ElderlyHasTagPredicate predicate = new ElderlyHasTagPredicate(CommandTestUtil.SET_ONE_TAG);
        assertFalse(predicate.test(new ElderlyBuilder().build()));

        // Keywords match name but does not match tag
        predicate = new ElderlyHasTagPredicate(Set.of(new Tag("Alice")));
        assertFalse(predicate.test(new ElderlyBuilder().withName("Alice").withTags(CommandTestUtil.VALID_TAG_DIABETES).build()));

        // Only one matching tag
        predicate = new ElderlyHasTagPredicate(CommandTestUtil.SET_TWO_TAGS);
        assertFalse(predicate.test(new ElderlyBuilder().withTags(CommandTestUtil.VALID_TAG_DIABETES).build()));
    }
}
