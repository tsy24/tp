package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Elderly;

/**
 * An UI component that displays information of a {@code Elderly}.
 */
public class ElderlyCard extends UiPart<Region> {

    private static final String FXML = "ElderlyListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Elderly elderly;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label age;
    @FXML
    private Label gender;
    @FXML
    private Label roomNumber;
    @FXML
    private Label nokName;
    @FXML
    private Label relationship;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code ElderlyCode} with the given {@code Elderly} and index to display.
     */
    public ElderlyCard(Elderly elderly, int displayedIndex) {
        super(FXML);
        this.elderly = elderly;
        id.setText(displayedIndex + ". ");
        name.setText(elderly.getName().fullName);
        age.setText("Age: " + elderly.getAge().value + " years old");
        gender.setText("Gender: " + elderly.getGender().value);
        roomNumber.setText("Room No.: " + elderly.getRoomNumber().value);
        nokName.setText("Nok Name: " + elderly.getNok().getName().fullName);
        relationship.setText("Relationship: " + elderly.getNok().getRelationship().value);
        phone.setText("Nok Phone No.: " + elderly.getNok().getPhone().value);
        email.setText("Nok Email: " + elderly.getNok().getEmail().value);
        address.setText("Nok Address: " + elderly.getNok().getAddress().value);
        remark.setText("Remarks: " + elderly.getRemark().value);

        elderly.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ElderlyCard)) {
            return false;
        }

        // state check
        ElderlyCard card = (ElderlyCard) other;
        return id.getText().equals(card.id.getText())
                && elderly.equals(card.elderly);
    }
}
