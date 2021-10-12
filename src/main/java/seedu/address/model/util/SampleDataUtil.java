package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Elderly;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nok;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Relationship;
import seedu.address.model.person.Remark;
import seedu.address.model.person.RoomNumber;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final Remark EMPTY_REMARK = new Remark("");

    public static Elderly[] getSampleElderlies() {
        return new Elderly[] {
            new Elderly(new Name("Alex Yeoh"), new Age("41"), new Gender("M"),
                new RoomNumber("3"),
                    new Nok(new Name("Khong Guan"), new Relationship("Father"),
                    new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40")),
                EMPTY_REMARK, getTagSet("friends")),
            new Elderly(new Name("Bernice Yu"), new Age("42"), new Gender("F"),
                new RoomNumber("46"), new Nok(new Name("Chai Eng"), new Relationship("Mother"), new Phone("99272758"),
                    new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18")),
                EMPTY_REMARK, getTagSet("colleagues", "friends")),
            new Elderly(new Name("Charlotte Oliveiro"), new Age("43"), new Gender("F"),
                new RoomNumber("16"), new Nok(new Name("James Pitt"), new Relationship("Sister"),
                    new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04")),
                EMPTY_REMARK, getTagSet("neighbours")),
            new Elderly(new Name("David Li"), new Age("44"), new Gender("M"),
                new RoomNumber("5"), new Nok(new Name("Rong Hao"), new Relationship("Brother"), new Phone("91031282"),
                    new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43")),
                EMPTY_REMARK, getTagSet("family")),
            new Elderly(new Name("Irfan Ibrahim"),  new Age("45"), new Gender("M"),
                new RoomNumber("18"), new Nok(new Name("Muhammad Faiz"), new Relationship("Son"), new Phone("92492021"),
                    new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35")),
                EMPTY_REMARK, getTagSet("classmates")),
            new Elderly(new Name("Roy Balakrishnan"), new Age("46"), new Gender("M"),
                new RoomNumber("32"), new Nok(new Name("Syndrapratha"), new Relationship("Daughter"), new Phone("92624417"),
                    new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31")),
                EMPTY_REMARK, getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Elderly sampleElderly : getSampleElderlies()) {
            sampleAb.addElderly(sampleElderly);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
