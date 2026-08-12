package nikoloshurrinus.interviewproject;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.InputStream;

public class FirestoreContext {

    private static Firestore db;

    public static Firestore getDB() {
        try {
            if (db == null) {

                // Load key.json from resources
                InputStream serviceAccount =
                        FirestoreContext.class.getResourceAsStream("/key.json");

                if (serviceAccount == null) {
                    throw new RuntimeException("Could not find key.json in resources!");
                }

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                if (FirebaseApp.getApps().isEmpty()) {
                    FirebaseApp.initializeApp(options);
                }

                db = FirestoreClient.getFirestore();
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Firestore", e);
        }

        return db;
    }
}
