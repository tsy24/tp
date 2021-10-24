package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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
    private Label time;
    @FXML
    private Label overdue;
    @FXML
    private FlowPane names;
    @FXML
    private CheckBox status;

    /**
     * Creates a {@code ElderlyCode} with the given {@code Elderly} and index to display.
     */
    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        description.setText(task.getDesc().value);
        date.setText(task.getDateTime().getStringDate());
        time.setText(task.getDateTime().getStringTime());

        if (task.isTaskOverdue()) {
            overdue = new Label("Overdue");
            overdue.getStyleClass().add("overdue");
            names.getChildren().add(overdue);
        }

        task.getRelatedNames().stream()
                .forEach(name -> names.getChildren().add(new Label(name.fullName)));
        status.setSelected(task.isTaskDone());
        status.setDisable(true);
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
