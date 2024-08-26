package model;

import org.json.JSONObject;
import persistence.Writable;

// toJson() method modeled after JsonDemo Application
// github link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// represents an ingredient book with a name, price, type, and a new ingredient
public class IngredientBook extends Book implements Writable {
    private Ingredient ingredient;

    // EFFECTS: creates a book that has a name, price, type ("Ingredient"), and an ingredient
    public IngredientBook(String name, int price, String type, Ingredient ingredient) {
        super(name, price, type);
        this.ingredient = ingredient;
    }

    // getters
    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", super.getName());
        json.put("price", super.getPrice());
        json.put("type", super.getType());
        json.put("ingredient", ingredient.toJson());

        return json;
    }
}
