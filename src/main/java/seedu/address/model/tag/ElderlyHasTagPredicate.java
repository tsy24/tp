package seedu.address.model.tag;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Elderly;

public class ElderlyHasTagPredicate implements Predicate<Elderly> {

    private final Set<Tag> keyTags;

    public ElderlyHasTagPredicate(Set<Tag> keywords) {
        this.keyTags = keywords;
    }

    @Override
    public boolean test(Elderly person) {
        return keyTags.stream()
                .allMatch(keyTag -> testTag(person, keyTag));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ElderlyHasTagPredicate // instanceof handles nulls
                && keyTags.equals(((ElderlyHasTagPredicate) other).keyTags)); // state check
    }

    private boolean testTag(Elderly person, Tag keyTag) {
        return person.getTags().stream()
                .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyTag.tagName));
    }

}
