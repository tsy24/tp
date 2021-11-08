package nurseybook.model.person;

import static java.util.Objects.requireNonNull;
import static nurseybook.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nurseybook.model.person.exceptions.DuplicateElderlyException;
import nurseybook.model.person.exceptions.ElderlyNotFoundException;

/**
 * A list of elderlies that enforces uniqueness between its elements and does not allow nulls.
 * An elderly is considered unique by comparing using {@code Elderly#isSameElderly(Elderly)}. As such, adding and
 * updating of elderlies uses Elderly#isSameElderly(Elderly) for equality so as to ensure that the elderly being added
 * or updated is unique in terms of identity in the UniqueElderlyList. However, the removal of an elderly uses
 * Elderly#equals(Object) so as to ensure that the elderly with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Elderly#isSameElderly(Elderly)
 */
public class UniqueElderlyList implements Iterable<Elderly> {

    private final ObservableList<Elderly> internalList = FXCollections.observableArrayList();
    private final ObservableList<Elderly> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent elderly as the given argument.
     */
    public boolean contains(Elderly toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameElderly);
    }

    /**
     * Returns elderly with {@code name}
     * Elderly with {@code name} must exist in list.
     */
    public Elderly getElderly(Name name) {
        requireNonNull(name);
        try {
            return internalList.stream()
                    .filter(p -> p.hasName(name))
                    .findFirst()
                    .orElseThrow();
        } catch (NoSuchElementException e) {
            throw new ElderlyNotFoundException();
        }
    }

    /**
     * Returns elderly with {@code name}
     * Elderly with {@code name} must exist in list.
     */
    public boolean hasElderly(Name name) {
        requireNonNull(name);
        return internalList.stream()
                .anyMatch(p -> p.hasName(name));
    }

    /**
     * Adds a elderly to the list.
     * The elderly must not already exist in the list.
     */
    public void add(Elderly toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateElderlyException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the elderly {@code target} in the list with {@code editedElderly}.
     * {@code target} must exist in the list.
     * The elderly identity of {@code editedElderly} must not be the same as another existing elderly in the list.
     */
    public void setElderly(Elderly target, Elderly editedElderly) {
        requireAllNonNull(target, editedElderly);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ElderlyNotFoundException();
        }

        if (!target.isSameElderly(editedElderly) && contains(editedElderly)) {
            throw new DuplicateElderlyException();
        }

        internalList.set(index, editedElderly);
    }

    /**
     * Removes the equivalent elderly from the list.
     * The elderly must exist in the list.
     */
    public void remove(Elderly toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ElderlyNotFoundException();
        }
    }

    public void setElderlies(UniqueElderlyList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code elderlies}.
     * {@code elderlies} must not contain duplicate elderlies.
     */
    public void setElderlies(List<Elderly> elderlies) {
        requireAllNonNull(elderlies);
        if (!elderliesAreUnique(elderlies)) {
            throw new DuplicateElderlyException();
        }

        internalList.setAll(elderlies);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Elderly> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Elderly> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueElderlyList // instanceof handles nulls
                        && internalList.equals(((UniqueElderlyList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code elderlies} contains only unique elderlies.
     */
    private boolean elderliesAreUnique(List<Elderly> elderlies) {
        for (int i = 0; i < elderlies.size() - 1; i++) {
            for (int j = i + 1; j < elderlies.size(); j++) {
                if (elderlies.get(i).isSameElderly(elderlies.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
