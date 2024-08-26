package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// toJson() method modeled after JsonDemo Application
// github link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// represents a potion brewer with an ingredient list, potion list, recipe list, book list, and money
public class PotionBrewer implements Writable {

    private ArrayList<Ingredient> ingredientList;
    private ArrayList<Potion> potionList;
    private ArrayList<Recipe> recipeList;
    private MoneyPouch money;
    private BookStore bookStore;


    // EFFECTS: creates a PotionBrewer with starting ingredients, recipes and
    //          an empty potion list. Also comes with 20 coins and an empty book list.
    public PotionBrewer() {
        ingredientList = new ArrayList<>();
        recipeList = new ArrayList<>();
        money = new MoneyPouch();
        bookStore = new BookStore();
        initializeLists();
    }

    // MODIFIES: this
    // EFFECTS: if the ingredients match those specified in the recipe,
    //          make the potion and add it to your list;
    //          making potions costs money
    public Boolean makePotion(Ingredient i1, Ingredient i2) {
        Recipe recipe = findRecipe(i1, i2);
        if (recipe == null) {
            return false;
        } else {
            addPotion(new Potion(recipe.getName(), recipe.getCost() + 2));
            money.spendMoney(recipe.getCost());
            return true;
        }
    }


    // EFFECTS: returns the recipe that matches the chosen ingredients
    // returns null otherwise
    public Recipe findRecipe(Ingredient i1, Ingredient i2) {
        for (Recipe r : recipeList) {
            if (i1.getName().equals(r.getI1().getName()) && i2.getName().equals(r.getI2().getName())
                    || i1.getName().equals(r.getI2().getName()) && i2.getName().equals(r.getI1().getName())) {
                return r;
            }
        }

        return null;
    }

    // MODIFIES: this
    // EFFECTS: adds potion to the list or increases its number if it is already in list
    public void addPotion(Potion p) {
        Boolean isFound = false;
        for (Potion potion : potionList) {
            if (potion.getName().equals(p.getName())) {
                isFound = true;
                potion.increaseNum();
            }
        }

        if (!isFound) {
            potionList.add(p);
        }
    }

    // MODIFIES: this
    // EFFECTS: if you have the potion, use it
    //          if you don't have the potion, don't do anything
    public Boolean sellPotion(String potionName) {
        for (Potion p : potionList) {
            if (p.getName().equals(potionName)) {
                if (p.getNum() == 1) {
                    potionList.remove(p);
                } else {
                    p.decreaseNum();
                }
                money.addMoney(p.getSellPrice());
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: adds ingredient to your ingredient list
    public void addIngredient(Ingredient i) {
        ingredientList.add(i);
    }

    // MODIFIES: this
    // EFFECTS: adds recipe to your recipe list
    public void addRecipe(Recipe r) {
        recipeList.add(r);

    }

    // REQUIRES: book has not been read before
    // MODIFIES: this
    // EFFECTS: adds new ingredient or recipe to your ingredient or recipe lists
    public Boolean readBook(Book b) {
        if (b.getType().equals("Ingredient")) {
            addIngredient(((IngredientBook) b).getIngredient());
        } else {
            addRecipe(((RecipeBook) b).getRecipe());
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: spend money to buy a book
    public Boolean buyBook(Book b) {
        if (money.getMoney() >= b.getPrice()) {
            money.spendMoney(b.getPrice());
            return true;
        }
        return false;
    }


    // getters
    public ArrayList<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public ArrayList<Potion> getPotionList() {
        return potionList;
    }

    public ArrayList<Recipe> getRecipeList() {
        return recipeList;
    }

    public BookStore getBookStore() {
        return bookStore;
    }

    public MoneyPouch getMoney() {
        return money;
    }

    // MODIFIES: this
    // EFFECTS: provides the starter ingredients and recipes for the player.
    //          potion list will be empty
    //          starts off with 20 coins.
    public void initializeLists() {
        ingredientList.add(new Ingredient("Pond Water"));
        ingredientList.add(new Ingredient("Sheep Horn"));
        ingredientList.add(new Ingredient("Bat Vomit"));

        potionList = new ArrayList<>();

        money.addMoney(20);

        recipeList.add(new Recipe("Draught of Sewage", new Ingredient("Pond Water"),
                new Ingredient("Bat Vomit"), 3));
        recipeList.add(new Recipe("Elixir of the Sheep",
                new Ingredient("Pond Water"), new Ingredient("Sheep Horn"), 5));
        recipeList.add(new Recipe("Horned Vomit Potion",
                new Ingredient("Sheep Horn"), new Ingredient("Bat Vomit"), 7));
    }


    @Override
    // EFFECTS:  // EFFECTS: returns potion brewer as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("ingredient list", ingredientListToJson());
        json.put("potion list", potionListToJson());
        json.put("recipe list", recipeListToJson());
        json.put("money", money.toJson());
        json.put("book list", bookStore.toJson());
        return json;
    }

    // EFFECTS: returns ingredientList as JSON array
    public JSONArray ingredientListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Ingredient i : ingredientList) {
            jsonArray.put(i.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns potionList as JSON array
    public JSONArray potionListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Potion p : potionList) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns potionList as JSON array
    public JSONArray recipeListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Recipe r : recipeList) {
            jsonArray.put(r.toJson());
        }

        return jsonArray;
    }

}
