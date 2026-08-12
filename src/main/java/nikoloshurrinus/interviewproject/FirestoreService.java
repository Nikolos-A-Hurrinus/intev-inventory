package nikoloshurrinus.interviewproject;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FirestoreService {

    private final Firestore db = FirestoreContext.getDB();

    public void addItem(Item item) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> future = db.collection("inventory").add(item);
        item.setID(future.get().getId());
    }

    public void updateItem(Item item) throws ExecutionException, InterruptedException {
        db.collection("inventory")
                .document(item.getID())
                .set(item)
                .get();
    }

    public void deleteItem(String id) throws ExecutionException, InterruptedException {
        db.collection("inventory")
                .document(id)
                .delete()
                .get();
    }

    public List<Item> getAllItems() throws ExecutionException, InterruptedException {
        List<Item> items = new ArrayList<>();

        ApiFuture<QuerySnapshot> future = db.collection("inventory").get();
        for (DocumentSnapshot doc : future.get().getDocuments()) {
            Item item = doc.toObject(Item.class);
            item.setID(doc.getId());
            items.add(item);
        }

        return items;
    }
}
