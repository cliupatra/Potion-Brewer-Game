package model;

import org.json.JSONObject;
import persistence.Writable;

// toJson() method modeled after JsonDemo Application
// github link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// represents a money pouch with some amount money
public class MoneyPouch implements Writable {
    private int money;

    // EFFECTS: creates a money pouch with no money
    public MoneyPouch() {
        money = 0;
    }

    // MODIFIES: this
    // EFFECTS: adds specified amount to your money
    public void addMoney(int m) {
        money += m;
    }

    // MODIFIES: this
    // EFFECTS: subtracts specified amount from your money
    public void spendMoney(int m) {
        money -= m;
    }

    // setters

    public void setMoney(int money) {
        this.money = money;
    }

    // getters
    public int getMoney() {
        return money;
    }

    @Override
    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("money", money);

        return json;
    }
}
