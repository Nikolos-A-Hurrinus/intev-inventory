package nikoloshurrinus.interviewproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class InventoryPageController {

    @FXML private TableView<Item> InventoryTable;
    @FXML private TableColumn<Item, String> NameColumn;
    @FXML private TableColumn<Item, String> CategoryColumn;
    @FXML private TableColumn<Item, Integer> QuantityColumn;
    @FXML private TableColumn<Item, Void> deleteColumn;

    @FXML private TextField NameTextField;
    @FXML private TextField CategoryTextField;
    @FXML private TextField QuantityTextField;

    private final FirestoreService firestoreService = new FirestoreService();
    private final ObservableList<Item> items = FXCollections.observableArrayList();

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

    private void addDeleteButtonColumn() {
        deleteColumn.setCellFactory(col -> new TableCell<Item, Void>() {

            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(e -> {
                    Item item = getTableView().getItems().get(getIndex());
                    deleteItem(item);
                });
            }

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

    private void deleteItem(Item item) {
        try {
            firestoreService.deleteItem(item.getID());
            items.remove(item);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Failed to delete item.");
        }
    }

    private void clearFields() {
        NameTextField.clear();
        CategoryTextField.clear();
        QuantityTextField.clear();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
