package persistence;

import org.json.JSONObject;

// interface method modeled after JsonDemo Application
// github link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// interface for all classes that want to convert their data into a Json object
public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
