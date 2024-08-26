package model;

import org.json.JSONObject;
import persistence.Writable;

// toJson() method modeled after JsonDemo Application
// github link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// represents a recipe book with a name, price, type, and a new recipe
public class RecipeBook extends Book implements Writable {
    private Recipe recipe;

    // creates a recipe book with a name, price, type ("Recipe") and recipe
    public RecipeBook(String name, int price, String type, Recipe r) {
        super(name, price, type);
        this.recipe = r;
    }

    // getters
    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", super.getName());
        json.put("price", super.getPrice());
        json.put("type", super.getType());
        json.put("recipe", recipe.toJson());

        return json;
    }
}
