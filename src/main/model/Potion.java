package model;

import org.json.JSONObject;
import persistence.Writable;

// toJson() method modeled after JsonDemo Application
// github link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// represents a potion with a name, sell price, and number of potions you have of it
public class Potion implements Writable {
    private String name;
    private int sellPrice;
    private int num;

    // EFFECTS: creates a potion with a name, sell price, and a number of 1 potion you have of this type
    public Potion(String name, int sellPrice) {
        this.name = name;
        this.sellPrice = sellPrice;
        num = 1;
    }

    // MODIFIES: this
    // EFFECTS: increases num by 1
    public void increaseNum() {
        num++;
    }

    // MODIFIES: this
    // EFFECTS: decreases num by 1
    public void decreaseNum() {
        num--;
    }

    // setters

    public void setNum(int num) {
        this.num = num;
    }

    // getters
    public String getName() {
        return name;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public int getNum() {
        return num;
    }

    @Override
    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("sell price", sellPrice);
        json.put("num", num);

        return json;
    }
}
