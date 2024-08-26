package model;

import org.json.JSONObject;
import persistence.Writable;

// toJson() method modeled after JsonDemo Application
// github link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// represents a recipe with a name, two ingredients that are required, and the cost of making the potion
public class Recipe implements Writable {
    private Ingredient i1;
    private Ingredient i2;
    private String name;
    private int cost;


    // EFFECTS: creates a recipe with a name, two ingredients, and cost
    public Recipe(String name, Ingredient i1, Ingredient i2, int cost) {
        this.name = name;
        this.i1 = i1;
        this.i2 = i2;
        this.cost = cost;
    }

    // getters
    public String getName() {
        return name;
    }

    public Ingredient getI1() {
        return i1;
    }

    public Ingredient getI2() {
        return i2;
    }

    public int getCost() {
        return cost;
    }

    @Override
    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("ingredient 1", i1.toJson());
        json.put("ingredient 2", i2.toJson());
        json.put("cost", cost);

        return json;
    }
}
