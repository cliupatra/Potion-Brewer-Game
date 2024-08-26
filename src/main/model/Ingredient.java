package model;

import org.json.JSONObject;
import persistence.Writable;

// toJson() method modeled after JsonDemo Application
// github link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// represents an ingredient with a name
public class Ingredient implements Writable {
    private String name;

    // EFFECTS: creates an ingredient with a name
    public Ingredient(String name) {
        this.name = name;
    }

    // getters
    public String getName() {
        return name;
    }

    @Override
    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("name", name);

        return json;
    }
}
