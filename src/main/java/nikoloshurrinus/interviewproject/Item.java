package nikoloshurrinus.interviewproject;

public class Item {
    String ID;
    String name;
    int quantity;
    String category;

    public Item(){}

    public Item(String ID, String name, int quantity, String category){
        this.ID = ID;
        this.name = name;
        this.quantity = quantity;
        this.category=category;
    }

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
