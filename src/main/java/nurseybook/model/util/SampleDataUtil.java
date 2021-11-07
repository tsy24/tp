package nurseybook.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import nurseybook.model.NurseyBook;
import nurseybook.model.ReadOnlyNurseyBook;
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
import nurseybook.model.task.DateTime;
import nurseybook.model.task.Description;
import nurseybook.model.task.RealTask;
import nurseybook.model.task.Recurrence;
import nurseybook.model.task.Status;
import nurseybook.model.task.Task;

/**
 * Contains utility methods for populating {@code NurseyBook} with sample data.
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
                EMPTY_REMARK, getTagSet("diabetes")),
            new Elderly(new Name("Bernice Yu"), new Age("42"), new Gender("F"),
                new RoomNumber("46"), new Nok(new Name("Chai Eng"), new Relationship("Mother"), new Phone("99272758"),
                    new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18")),
                EMPTY_REMARK, getTagSet("fever", "diabetes")),
            new Elderly(new Name("Charlotte Oliveiro"), new Age("43"), new Gender("F"),
                new RoomNumber("16"), new Nok(new Name("James Pitt"), new Relationship("Sister"),
                    new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04")),
                EMPTY_REMARK, getTagSet("depression")),
            new Elderly(new Name("David Li"), new Age("64"), new Gender("M"),
                new RoomNumber("5"), new Nok(new Name("Rong Hao"), new Relationship("Brother"), new Phone("91031282"),
                    new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43")),
                EMPTY_REMARK, getTagSet("vegetarian")),
            new Elderly(new Name("Irfan Ibrahim"), new Age("45"), new Gender("M"),
                new RoomNumber("18"), new Nok(new Name("Muhammad Faiz"), new Relationship("Son"), new Phone("92492021"),
                    new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35")),
                EMPTY_REMARK, getTagSet("vegetarian")),
            new Elderly(new Name("Roy Balakrishnan"), new Age("76"), new Gender("M"),
                new RoomNumber("32"), new Nok(new Name("Syndrapratha"), new Relationship("Daughter"),
                    new Phone("92624417"),
                    new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31")),
                EMPTY_REMARK, getTagSet("fever"))
        };
    }

    public static Task[] getSampleTasks() {
        return new Task[] {
            new RealTask(new Description("Submit leave request"), new DateTime("2021-10-20", "19:00"),
                getNameSet(), new Status("false", "true"),
                new Recurrence(Recurrence.RecurrenceType.NONE.name())),
            new RealTask(new Description("Administer medication"), new DateTime("2021-12-04", "14:50"),
                getNameSet("Alex Yeoh", "David Li"),
                new Status("false", "false"),
                new Recurrence(Recurrence.RecurrenceType.NONE.name())),
            new RealTask(new Description("Accompany for walk"), new DateTime("2021-12-04", "19:00"),
                getNameSet("Charlotte Oliveiro"), new Status("false", "false"),
                new Recurrence(Recurrence.RecurrenceType.DAY.name())),
            new RealTask(new Description("Write up daily report"), new DateTime("2021-12-04", "10:00"),
                getNameSet(), new Status("false", "false"),
                new Recurrence(Recurrence.RecurrenceType.DAY.name())),
            new RealTask(new Description("Clean working area"), new DateTime("2021-12-13", "11:00"),
                getNameSet(), new Status("false", "false"),
                new Recurrence(Recurrence.RecurrenceType.WEEK.name()))
        };
    }

    public static ReadOnlyNurseyBook getSampleNurseyBook() {
        NurseyBook sampleNb = new NurseyBook();
        for (Elderly sampleElderly : getSampleElderlies()) {
            sampleNb.addElderly(sampleElderly);
        }

        for (Task sampleTask : getSampleTasks()) {
            sampleNb.addTask(sampleTask);
        }

        return sampleNb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
    /**
     * Returns a name set containing the list of strings given.
     */
    private static Set<Name> getNameSet(String... strings) {
        return Arrays.stream(strings)
                .map(Name::new)
                .collect(Collectors.toSet());
    }

}
