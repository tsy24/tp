package nurseybook.testutil;

import nurseybook.model.person.Elderly;
import nurseybook.model.AddressBook;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withElderly("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Elderly} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withElderly(Elderly elderly) {
        addressBook.addElderly(elderly);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
