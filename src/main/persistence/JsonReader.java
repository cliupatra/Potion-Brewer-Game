package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// class modeled after JsonDemo Application
// github link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a reader that reads potion brewer from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads potion brewer from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PotionBrewer read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePotionBrewer(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses potion brewer from JSON object and returns it
    private PotionBrewer parsePotionBrewer(JSONObject jsonObject) {
        PotionBrewer pb = new PotionBrewer();
        addIngredientList(pb, jsonObject);
        addRecipeList(pb, jsonObject);
        addPotionList(pb, jsonObject);
        addMoney(pb, jsonObject);
        addBookList(pb, jsonObject);
        return pb;
    }

    // MODIFIES: pb
    // EFFECTS: parses ingredient list from JSON object and adds them to potion brewer
    private void addIngredientList(PotionBrewer pb, JSONObject jsonObject) {
        pb.getIngredientList().clear();
        JSONArray jsonArray = jsonObject.getJSONArray("ingredient list");
        for (Object json : jsonArray) {
            JSONObject nextIngredient = (JSONObject) json;
            addIngredient(pb, nextIngredient);
        }
    }

    // MODIFIES: pb
    // EFFECTS: parses ingredient from JSON object and adds it to potion brewer
    private void addIngredient(PotionBrewer pb, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Ingredient ingredient = new Ingredient(name);
        pb.addIngredient(ingredient);
    }

    // MODIFIES: pb
    // EFFECTS: parses recipe list from JSON object and adds them to potion brewer
    private void addRecipeList(PotionBrewer pb, JSONObject jsonObject) {
        pb.getRecipeList().clear();
        JSONArray jsonArray = jsonObject.getJSONArray("recipe list");
        for (Object json : jsonArray) {
            JSONObject nextRecipe = (JSONObject) json;
            addRecipe(pb, nextRecipe);
        }
    }

    // MODIFIES: pb
    // EFFECTS: parses recipe from JSON object and adds it to potion brewer
    private void addRecipe(PotionBrewer pb, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        JSONObject ingredient = jsonObject.getJSONObject("ingredient 1");
        String ingredientName = ingredient.getString("name");
        Ingredient i1 = new Ingredient(ingredientName);

        ingredient = jsonObject.getJSONObject("ingredient 2");
        ingredientName = ingredient.getString("name");
        Ingredient i2 = new Ingredient(ingredientName);

        int cost = jsonObject.getInt("cost");

        Recipe recipe = new Recipe(name, i1, i2, cost);
        pb.addRecipe(recipe);
    }

    // MODIFIES: pb
    // EFFECTS: parses potion list from JSON object and adds them to potion brewer
    private void addPotionList(PotionBrewer pb, JSONObject jsonObject) {
        pb.getPotionList().clear();
        JSONArray jsonArray = jsonObject.getJSONArray("potion list");
        for (Object json : jsonArray) {
            JSONObject nextPotion = (JSONObject) json;
            addPotion(pb, nextPotion);
        }
    }

    // MODIFIES: pb
    // EFFECTS: parses potion from JSON object and adds it to potion brewer
    private void addPotion(PotionBrewer pb, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int sellPrice = jsonObject.getInt("sell price");
        int num = jsonObject.getInt("num");

        Potion potion = new Potion(name, sellPrice);
        potion.setNum(num);

        pb.addPotion(potion);
    }

    // MODIFIES: pb
    // EFFECTS: parses money from JSON object and adds it to potion brewer
    private void addMoney(PotionBrewer pb, JSONObject jsonObject) {
        JSONObject jsonMoney = jsonObject.getJSONObject("money");
        int money = jsonMoney.getInt("money");
        pb.getMoney().setMoney(money);
    }

    // MODIFIES: pb
    // EFFECTS: parses book list from JSON object and adds them to potion brewer
    private void addBookList(PotionBrewer pb, JSONObject jsonObject) {
        pb.getBookStore().getBookList().clear();

        JSONArray jsonArray = jsonObject.getJSONArray("book list");
        for (Object json : jsonArray) {
            JSONObject nextBook = (JSONObject) json;
            addBook(pb, nextBook);
        }
    }

    // MODIFIES: pb
    // EFFECTS: parses book from JSON object and adds it to potion brewer
    private void addBook(PotionBrewer pb, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int price = jsonObject.getInt("price");
        String type = jsonObject.getString("type");
        Book book = null;

        if (type.equals("Ingredient")) {
            JSONObject ingredient = jsonObject.getJSONObject("ingredient");
            String ingredientName = ingredient.getString("name");
            Ingredient i = new Ingredient(ingredientName);
            book = new IngredientBook(name, price, type, i);
        } else {
            JSONObject recipe = jsonObject.getJSONObject("recipe");
            JSONObject ingredient1 = recipe.getJSONObject("ingredient 1");
            String ingredientName1 = ingredient1.getString("name");
            Ingredient i1 = new Ingredient(ingredientName1);

            JSONObject ingredient2 = recipe.getJSONObject("ingredient 2");
            String ingredientName2 = ingredient2.getString("name");
            Ingredient i2 = new Ingredient(ingredientName2);

            int cost = recipe.getInt("cost");
            Recipe r = new Recipe(name, i1, i2, cost);
            book = new RecipeBook(name, price, type, r);
        }

        pb.getBookStore().getBookList().add(book);
    }
}
