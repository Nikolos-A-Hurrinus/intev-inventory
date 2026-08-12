package nikoloshurrinus.interviewproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

/*
this class is the controller for the inventory page. it is vital in providing the important functionality of the
inventory page FXML file.
it contains these methods:
    * initialize - this method is used to set up the table and it's columns when the page is pulled up
    * loadItems - this method is for calling the getAllItems method from FirestoreService to load into the table
    * AddButtonClicked - this method is called when the user clicks the add button, submitting the text field info
    * UpdateButtonClicked - this method is called when the user clicks the update button, altering the chosen item
    * addDeleteButtonColumn - this is to add the delete button next to each entry
    * deleteItem - this give the delete button functionality, deleting the line the button corresponds to
    * clearFields - this method is called whenever the text fields need to be cleared
    * showError - this provides a popup whenever an error occurs
 */

public class InventoryPageController {

    // table elements
    @FXML private TableView<Item> InventoryTable;
    @FXML private TableColumn<Item, String> NameColumn;
    @FXML private TableColumn<Item, String> CategoryColumn;
    @FXML private TableColumn<Item, Integer> QuantityColumn;
    @FXML private TableColumn<Item, Void> deleteColumn;

    // text fields
    @FXML private TextField NameTextField;
    @FXML private TextField CategoryTextField;
    @FXML private TextField QuantityTextField;

    //firestore instance
    private final FirestoreService firestoreService = new FirestoreService();
    private final ObservableList<Item> items = FXCollections.observableArrayList();

    //this method is used to set up the table and it's columns when the page is pulled up
    @FXML
    public void initialize() {

        // Bind columns to Item fields
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        CategoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        QuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        // Add delete button column
        addDeleteButtonColumn();

        // Load items from Firestore
        loadItems();
    }

    // this method is for calling the getAllItems method from FirestoreService to load into the table
    private void loadItems() {
        try {
            List<Item> list = firestoreService.getAllItems();
            items.setAll(list);
            InventoryTable.setItems(items);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Failed to load items.");
        }
    }

    // this method is called when the user clicks the add button, submitting the text field info
    @FXML
    private void AddButtonClicked() {
        try {
            String name = NameTextField.getText();
            String category = CategoryTextField.getText();
            int quantity = Integer.parseInt(QuantityTextField.getText());

            Item item = new Item(null, name, quantity, category);
            firestoreService.addItem(item);

            items.add(item);
            clearFields();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Failed to add item.");
        }
    }

    // this method is called when the user clicks the update button, altering the chosen item
    @FXML
    private void UpdateButtonClicked() {
        Item selected = InventoryTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Select an item to update.");
            return;
        }

        try {
            selected.setName(NameTextField.getText());
            selected.setCategory(CategoryTextField.getText());
            selected.setQuantity(Integer.parseInt(QuantityTextField.getText()));

            firestoreService.updateItem(selected);
            InventoryTable.refresh();
            clearFields();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Failed to update item.");
        }
    }

    // this is to add the delete button next to each entry
    private void addDeleteButtonColumn() {
        deleteColumn.setCellFactory(col -> new TableCell<Item, Void>() {

            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(e -> {
                    Item item = getTableView().getItems().get(getIndex());
                    deleteItem(item);
                });
            }

            // this updates the column to have the button that was created
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

    // this give the delete button functionality, deleting the line the button corresponds to
    private void deleteItem(Item item) {
        try {
            firestoreService.deleteItem(item.getID());
            items.remove(item);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Failed to delete item.");
        }
    }

    // this method is called whenever the text fields need to be cleared
    private void clearFields() {
        NameTextField.clear();
        CategoryTextField.clear();
        QuantityTextField.clear();
    }

    // this provides a popup whenever an error occurs
    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
