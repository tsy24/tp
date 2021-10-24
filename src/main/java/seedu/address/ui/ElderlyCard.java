package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
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
    private TitledPane titledPane;

    /**
     * Creates a {@code ElderlyCard} with the given {@code Elderly} and index to display.
     */
    public ElderlyCard(Elderly elderly, int displayedIndex) {
        super(FXML);

        this.elderly = elderly;
        titledPane.setGraphic(new ElderlyPartialCard(displayedIndex).getRoot());
        titledPane.setContent(new ElderlyFullCard().getRoot());
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
        return elderly.equals(card.elderly);
    }

    class ElderlyPartialCard extends UiPart<Region> {

        private static final String FXML = "ElderlyPartialCard.fxml";

        @FXML
        private HBox partialCard;
        @FXML
        private Label name;
        @FXML
        private Label id;
        @FXML
        private Label roomNumber;
        @FXML
        private FlowPane tags;

        /**
         * Creates a {@code StatusBarFooter} with the given {@code Path}.
         */
        public ElderlyPartialCard(int displayedIndex) {
            super(FXML);
            id.setText(displayedIndex + ". ");
            name.setText(elderly.getName().fullName);
            roomNumber.setText("Room " + elderly.getRoomNumber().value);
            elderly.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        }
    }


    class ElderlyFullCard extends UiPart<Region> {

        private static final String FXML = "ElderlyFullCard.fxml";

        @FXML
        private HBox fullCard;
        @FXML
        private Label age;
        @FXML
        private Label gender;
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

        /**
         * Creates a {@code StatusBarFooter} with the given {@code Path}.
         */
        public ElderlyFullCard() {
            super(FXML);
            age.setText(elderly.getAge().value + " years old");
            gender.setText(elderly.getGender().value);
            nokName.setText(elderly.getNok().getName().fullName);
            relationship.setText(elderly.getNok().getRelationship().value);
            phone.setText(elderly.getNok().getPhone().value);
            email.setText(elderly.getNok().getEmail().value);
            address.setText(elderly.getNok().getAddress().value);
            remark.setText(elderly.getRemark().value);
            remark.setMaxWidth(Double.MAX_VALUE);
        }
    }
}
