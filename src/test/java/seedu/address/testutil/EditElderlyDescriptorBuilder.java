package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Elderly;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RoomNumber;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditElderlyDescriptor objects.
 */
public class EditElderlyDescriptorBuilder {

    private EditCommand.EditElderlyDescriptor descriptor;

    public EditElderlyDescriptorBuilder() {
        descriptor = new EditCommand.EditElderlyDescriptor();
    }

    public EditElderlyDescriptorBuilder(EditCommand.EditElderlyDescriptor descriptor) {
        this.descriptor = new EditCommand.EditElderlyDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditElderlyDescriptor} with fields containing {@code elderly}'s details
     */
    public EditElderlyDescriptorBuilder(Elderly elderly) {
        descriptor = new EditCommand.EditElderlyDescriptor();
        descriptor.setName(elderly.getName());
        descriptor.setPhone(elderly.getPhone());
        descriptor.setAge(elderly.getAge());
        descriptor.setGender(elderly.getGender());
        descriptor.setRoomNumber(elderly.getRoomNumber());
        descriptor.setEmail(elderly.getEmail());
        descriptor.setAddress(elderly.getAddress());
        descriptor.setTags(elderly.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditElderlyDescriptor} that we are building.
     */
    public EditElderlyDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditElderlyDescriptor} that we are building.
     */
    public EditElderlyDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
<<<<<<< HEAD
     * Sets the {@code Age} of the {@code EditElderlyDescriptor} that we are building.
     */
    public EditElderlyDescriptorBuilder withAge(String age) {
        descriptor.setAge(new Age(age));
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code EditElderlyDescriptor} that we are building.
     */
    public EditElderlyDescriptorBuilder withGender(String gender) {
        descriptor.setGender(new Gender(gender));
        return this;
    }

    /**
     * Sets the {@code RoomNumber} of the {@code EditElderlyDescriptor} that we are building.
     */
    public EditElderlyDescriptorBuilder withRoomNumber(String roomNumber) {
        descriptor.setRoomNumber(new RoomNumber(roomNumber));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditElderlyDescriptor} that we are building.
     */
    public EditElderlyDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditElderlyDescriptor} that we are building.
     */
    public EditElderlyDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditElderlyDescriptor}
     * that we are building.
     */
    public EditElderlyDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCommand.EditElderlyDescriptor build() {
        return descriptor;
    }
}
