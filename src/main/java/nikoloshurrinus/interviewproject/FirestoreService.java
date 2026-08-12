package nikoloshurrinus.interviewproject;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/*
this class is important for setting up certain functions for the program to use when wanting to interact with firestore
it contains 4 methods:
    * addItem - this method is called when wanting to add an item into firestore
    * updateItem - this method is called when wanting to alter a preexisting item
    * deleteItem - this method is called when wanting to remove a preexisting item from the database
    * getAllItems - this method is used to call up every item, it's important for creating the table in the inventory
                    screen
 */

public class FirestoreService {

    private final Firestore db = FirestoreContext.getDB();

    //this method is called when wanting to add an item into firestore
    public void addItem(Item item) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> future = db.collection("inventory").add(item);
        item.setID(future.get().getId());
    }

    //this method is called when wanting to alter a preexisting item
    public void updateItem(Item item) throws ExecutionException, InterruptedException {
        db.collection("inventory")
                .document(item.getID())
                .set(item)
                .get();
    }

    //this method is called when wanting to remove a preexisting item from the database
    public void deleteItem(String id) throws ExecutionException, InterruptedException {
        db.collection("inventory")
                .document(id)
                .delete()
                .get();
    }

    //this method is used to call up every item, it's important for creating the table in the inventory screen
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
