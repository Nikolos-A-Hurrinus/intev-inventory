package nikoloshurrinus.interviewproject;

/*
this class is to provide the item object and its possible information for use within the inventory system.
it contains 2 constructors and setters and getters
 */

public class Item {
    String ID;
    String name;
    int quantity;
    String category;

    // empty constructor
    public Item(){}

    //constructor with informations
    public Item(String ID, String name, int quantity, String category){
        this.ID = ID;
        this.name = name;
        this.quantity = quantity;
        this.category=category;
    }

    //setters
    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    //getters
    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }
}
