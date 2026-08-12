package nikoloshurrinus.interviewproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class LoginPageController {
    @FXML
    Button LoginButton;

    @FXML
    public void LoginButtonClicked(ActionEvent actionEvent) throws IOException {
        InventoryTracker.setRoot("InventoryPage");
    }
}
