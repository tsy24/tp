package nurseybook.model.person;

import static nurseybook.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import nurseybook.model.tag.Tag;

/**
 * Represents an Elderly in the nursey book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Elderly extends Person {

    // Identity fields
    private final Age age;
    private final Gender gender;
    private final RoomNumber roomNumber;

    // Data fields
    private final Nok nok;
    private final Remark remark;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Elderly(Name name, Age age, Gender gender, RoomNumber roomNumber, Nok nok,
                   Remark remark, Set<Tag> tags) {
        super(name);
        requireAllNonNull(age, gender, tags);
        this.age = age;
        this.gender = gender;
        this.roomNumber = roomNumber;
        this.nok = nok;
        this.remark = remark;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return super.getName();
    }

    public Age getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public RoomNumber getRoomNumber() {
        return roomNumber;
    }

    public Nok getNok() {
        return nok;
    }

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both elderlies have the same name.
     * This defines a weaker notion of equality between two elderlies.
     */
    public boolean isSameElderly(Elderly otherElderly) {
        if (otherElderly == this) {
            return true;
        }

        return otherElderly != null
                && otherElderly.getName().equals(getName());
    }

    /**
     * Returns true if elderly has this name.
     */
    public boolean hasName(Name name) {
        return this.getName().equals(name);
    }

    /**
     * Returns true if both elderlies have the same identity and data fields.
     * This defines a stronger notion of equality between two elderlies.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Elderly)) {
            return false;
        }

        Elderly otherElderly = (Elderly) other;
        return otherElderly.getName().equals(getName())
                && otherElderly.getAge().equals(getAge())
                && otherElderly.getGender().equals(getGender())
                && otherElderly.getRoomNumber().equals(getRoomNumber())
                && otherElderly.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.getName(), age, gender, roomNumber, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Age: ")
                .append(getAge())
                .append("; Gender: ")
                .append(getGender())
                .append("; RoomNumber: ")
                .append(getRoomNumber())
                .append(getNok().toString())
                .append("; Remark: ")
                .append(getRemark());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }
}
