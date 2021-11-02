package nurseybook.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import nurseybook.model.person.Elderly;

/**
 * Panel containing the full details of the specified elderly.
 */
public class ElderlyDetailsPanel extends UiPart<Region> {

    private static final String FXML = "ElderlyDetailsPanel.fxml";
    public final Elderly elderly;

    @FXML
    private Label name;
    @FXML
    private Label roomNumber;
    @FXML
    private FlowPane tags;
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
     * Creates a {@code ElderlyDetailsPanel} with the given {@code Elderly} to display.
     */
    public ElderlyDetailsPanel(Elderly elderly) {
        super(FXML);

        this.elderly = elderly;
        name.setText(elderly.getName().fullName);
        roomNumber.setText("Room No.: " + elderly.getRoomNumber().value);
        elderly.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        age.setText(elderly.getAge().value + " years old");
        gender.setText(elderly.getGender().value);
        nokName.setText(elderly.getNok().getName().fullName);
        relationship.setText(elderly.getNok().getRelationship().value);
        phone.setText(elderly.getNok().getPhone().value);
        email.setText(elderly.getNok().getEmail().value);
        address.setText(elderly.getNok().getAddress().value);
        remark.setText(elderly.getRemark().value);
    }
}
