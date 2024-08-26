package model;


// represents a book with a name, price, and type that is either "Ingredient" or "Recipe"
public class Book {
    private String name;
    private int price;
    private String type;

    // EFFECTS: creates a book with a name, price, and type
    public Book(String name, int price, String type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    // getters
    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

}
