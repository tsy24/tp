package nurseybook.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";
    private static final String SUCCESS_IMG = "/images/tick.png";
    private static final String FAILURE_IMG = "/images/remove.png";

    @FXML
    private ImageView resultStatus;
    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
    }

    public void setFeedbackToUser(boolean isSuccess, String feedbackToUser) {
        requireNonNull(feedbackToUser);
        requireNonNull(isSuccess);

        Image img = isSuccess
                ? new Image(this.getClass().getResourceAsStream(SUCCESS_IMG))
                : new Image(this.getClass().getResourceAsStream(FAILURE_IMG));
        resultStatus.setImage(img);
        resultDisplay.setText(feedbackToUser);
    }
}
