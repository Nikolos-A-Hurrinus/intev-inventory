package nikoloshurrinus.interviewproject;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/*
this is the application itself. It calls firestore and loads up the FXML to begin the program
 */

//launches the application
public class InventoryTracker extends Application {
    public static Scene scene;

    public static Firestore fstore;
    public static FirebaseAuth fauth;

    // this calls firestore methods to set up firestore and then calls up login page
    @Override
    public void start(Stage stage) throws IOException {
        fstore = FirestoreContext.getDB();
        fauth = FirebaseAuth.getInstance();

        scene = new Scene(loadFXML("LoginPage")); //sets login page as starting page
        stage.setScene(scene);
        stage.show();
    }

    // this method is used to change what page is to be called during the running of the program
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    // this allows FXML to work and be called
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryTracker.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
