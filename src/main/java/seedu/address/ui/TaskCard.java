package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;

/**
 * An UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label date;
    @FXML
    private ImageView dateLogo;
    @FXML
    private Label time;
    @FXML
    private ImageView timeLogo;
    @FXML
    private FlowPane overdue;
    @FXML
    private Label recurring;
    @FXML
    private FlowPane names;
    @FXML
    private CheckBox status;

    /**
     * Creates a {@code TaskCard} with the given {@code Task} and index to display.
     */
    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        description.setText(task.getDesc().value);

        Image logo = new Image(this.getClass().getResourceAsStream("/images/calendar-blue.png"));
        date.setText(task.getDateTime().getStringDate());
        dateLogo.setImage(logo);

        logo = new Image(this.getClass().getResourceAsStream("/images/clock-blue.png"));
        time.setText(task.getDateTime().getStringTime());
        timeLogo.setImage(logo);

        if (task.isTaskOverdue()) {
            overdue.getChildren().add(new Label("Overdue"));
        } else {
            overdue.setVisible(false);
        }

        task.getRelatedNames().stream()
                .forEach(name -> names.getChildren().add(new Label(name.fullName)));

        if (task.getRelatedNames().isEmpty()) {
            names.setVisible(false);
        }

        status.setSelected(task.isTaskDone());
        status.setDisable(true);

        String recurrence = task.getRecurrence().toString();
        recurrence = recurrence.substring(0, 1) + recurrence.substring(1).toLowerCase();
        recurring.setText("Recurring: " + recurrence);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }

}
