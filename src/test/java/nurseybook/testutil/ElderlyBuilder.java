package nurseybook.testutil;

import java.util.HashSet;
import java.util.Set;

import nurseybook.model.person.Address;
import nurseybook.model.person.Age;
import nurseybook.model.person.Elderly;
import nurseybook.model.person.Email;
import nurseybook.model.person.Gender;
import nurseybook.model.person.Name;
import nurseybook.model.person.Nok;
import nurseybook.model.person.Phone;
import nurseybook.model.person.Relationship;
import nurseybook.model.person.Remark;
import nurseybook.model.person.RoomNumber;
import nurseybook.model.tag.Tag;
import nurseybook.model.util.SampleDataUtil;

/**
 * A utility class to help with building Elderly objects.
 */
public class ElderlyBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_AGE = "50";
    public static final String DEFAULT_GENDER = "F";
    public static final String DEFAULT_ROOM_NUMBER = "16";
    public static final String DEFAULT_NOK_NAME = "Amy Cee";
    public static final String DEFAULT_RELATIONSHIP = "Mother";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_REMARK = "She likes aardvarks.";

    private Name name;
    private Age age;
    private Gender gender;
    private RoomNumber roomNumber;
    private Name nokName;
    private Relationship relationship;
    private Phone phone;
    private Email email;
    private Address address;
    private Remark remark;
    private Set<Tag> tags;

    /**
     * Creates a {@code ElderlyBuilder} with the default details.
     */
    public ElderlyBuilder() {
        name = new Name(DEFAULT_NAME);
        age = new Age(DEFAULT_AGE);
        gender = new Gender(DEFAULT_GENDER);
        roomNumber = new RoomNumber(DEFAULT_ROOM_NUMBER);
        nokName = new Name(DEFAULT_NOK_NAME);
        relationship = new Relationship(DEFAULT_RELATIONSHIP);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        remark = new Remark(DEFAULT_REMARK);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ElderlyBuilder with the data of {@code elderlyToCopy}.
     */
    public ElderlyBuilder(Elderly elderlyToCopy) {
        name = elderlyToCopy.getName();
        age = elderlyToCopy.getAge();
        gender = elderlyToCopy.getGender();
        roomNumber = elderlyToCopy.getRoomNumber();
        nokName = elderlyToCopy.getNok().getName();
        relationship = elderlyToCopy.getNok().getRelationship();
        phone = elderlyToCopy.getNok().getPhone();
        email = elderlyToCopy.getNok().getEmail();
        address = elderlyToCopy.getNok().getAddress();
        remark = elderlyToCopy.getRemark();
        tags = new HashSet<>(elderlyToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Elderly} that we are building.
     */
    public ElderlyBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Nok Name} of the {@code Elderly's Nok} that we are building.
     */
    public ElderlyBuilder withNokName(String nokName) {
        this.nokName = new Name(nokName);
        return this;
    }

    /**
     * Sets the {@code Relationship} of the {@code Elderly's Nok} that we are building.
     */
    public ElderlyBuilder withRelationship(String relationship) {
        this.relationship = new Relationship(relationship);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Elderly} that we are building.
     */
    public ElderlyBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets an empty {@code Set<Tag>} to the {@code Elderly} that we are building.
     */
    public ElderlyBuilder withoutTags() {
        this.tags = new HashSet<>();
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Elderly} that we are building.
     */
    public ElderlyBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Elderly's Nok} that we are building.
     */
    public ElderlyBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Age} of the {@code Elderly} that we are building.
     */
    public ElderlyBuilder withAge(String age) {
        this.age = new Age(age);
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code Elderly} that we are building.
     */
    public ElderlyBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }

    /**
     * Sets the {@code RoomNumber} of the {@code Elderly} that we are building.
     */
    public ElderlyBuilder withRoomNumber(String roomNumber) {
        this.roomNumber = new RoomNumber(roomNumber);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Elderly's nok} that we are building.
     */
    public ElderlyBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Elderly} that we are building.
     */
    public ElderlyBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Build an {@code Elderly}.
     */
    public Elderly build() {
        return new Elderly(name, age, gender, roomNumber, new Nok(nokName, relationship, phone, email, address),
                remark, tags);
    }

}
