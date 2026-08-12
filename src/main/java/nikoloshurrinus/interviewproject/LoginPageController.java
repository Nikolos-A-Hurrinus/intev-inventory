package nikoloshurrinus.interviewproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
/*
this is a controller for the login page, it simply provides function to the login button and calls the inventory page
 */
public class LoginPageController {
    @FXML
    Button LoginButton;

    @FXML
    public void LoginButtonClicked(ActionEvent actionEvent) throws IOException {
        InventoryTracker.setRoot("InventoryPage");
    }
}
