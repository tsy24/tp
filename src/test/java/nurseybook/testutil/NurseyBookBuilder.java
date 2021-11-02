package nurseybook.testutil;

import nurseybook.model.NurseyBook;
import nurseybook.model.person.Elderly;

/**
 * A utility class to help with building Nurseybook objects.
 * Example usage: <br>
 *     {@code NurseyBook ab = new NurseyBookBuilder().withElderly("John", "Doe").build();}
 */
public class NurseyBookBuilder {

    private NurseyBook nurseyBook;

    public NurseyBookBuilder() {
        nurseyBook = new NurseyBook();
    }

    public NurseyBookBuilder(NurseyBook nurseyBook) {
        this.nurseyBook = nurseyBook;
    }

    /**
     * Adds a new {@code Elderly} to the {@code NurseyBook} that we are building.
     */
    public NurseyBookBuilder withElderly(Elderly elderly) {
        nurseyBook.addElderly(elderly);
        return this;
    }

    public NurseyBook build() {
        return nurseyBook;
    }
}
