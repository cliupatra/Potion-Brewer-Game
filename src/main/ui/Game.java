package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// loadGame() and saveGame() methods modeled after JsonDemo Application
// github link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// console application that allows users to do different things based on their input and allows users to save and load
// progress in the game
public class Game {
    private PotionBrewer potionBrewer;
    private Scanner scanner;
    private ArrayList<Ingredient> ingredientList;
    private ArrayList<Potion> potionList;
    private ArrayList<Recipe> recipeList;
    private BookStore bookStore;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String DESTINATION = "data/potionBrewer.json";

    // EFFECTS: creates a game with a potion brewer that has a given list of ingredients, potions, and recipes.
    //          the game also comes with a bookstore and a scanner for user input
    public Game(PotionBrewer potionBrewer) {
        this.potionBrewer = potionBrewer;
        ingredientList = potionBrewer.getIngredientList();
        potionList = potionBrewer.getPotionList();
        recipeList = potionBrewer.getRecipeList();
        bookStore = potionBrewer.getBookStore();
        scanner = new Scanner(System.in);
    }

    // MODIFIES: this
    // EFFECTS: loads the current values of the potion brewer into the lists and the bookstore
    public void loadValues() {
        ingredientList = potionBrewer.getIngredientList();
        potionList = potionBrewer.getPotionList();
        recipeList = potionBrewer.getRecipeList();
        bookStore = potionBrewer.getBookStore();
    }

    // MODIFIES: this
    // EFFECTS: starts the game and does different things based on user input
    public void playGame() {
        int input;
        intro();
        do {
            menu();
            input = scanner.nextInt();
            if (input == 0) {
                System.out.println("Exiting game...");
                break;
            } else {
                loadFunction(input);
            }
        } while (!(input == '0'));
    }

    // MODIFIES: this
    // EFFECTS: based on input, different functions are called
    public void loadFunction(int input) {
        if (input == 1) {
            brew();
        } else if (input == 2) {
            sellPotions();
        } else if (input == 3) {
            displayPotions();
        } else if (input == 4) {
            displayRecipes();
        } else if (input == 5) {
            displayIngredients();
        } else if (input == 6) {
            displayBookStore();
        } else if (input == 7) {
            saveGame();
        } else if (input == 8) {
            loadGame();
        }
    }

    // EFFECTS: prints out a simple game introduction
    public void intro() {
        System.out.println("Welcome to your newly opened potion brewing store!");
        System.out.println("You will be able to brew potions by using ingredients and discovering new recipes!");
        System.out.println("Since you come from a poor background, you only have 20 coins so make sure you spend "
                + "it wisely.");
        System.out.println("Luckily, you managed to scavenge a few ingredients and because of your magical garden ");
        System.out.println("that happened to come with the store, you won't ever run out of these ingredients!");
        System.out.println("For now, just use your current recipes to create potions and earn more coins to buy "
                + "new ones!");
        System.out.println("Now let's start brewing!");
    }

    // EFFECTS: prints out a menu of things users can do
    public void menu() {
        System.out.println("What would you like to do? ");
        System.out.println("[1] Brew potions");
        System.out.println("[2] Sell potions");
        System.out.println("[3] View potion list");
        System.out.println("[4] View recipe list");
        System.out.println("[5] View ingredient list");
        System.out.println("[6] Go to the bookstore");
        System.out.println("[7] Save game");
        System.out.println("[8] Load game");
        System.out.println("[0] Quit game");

    }

    // MODIFIES: this
    // EFFECTS: brews a potion from ingredients chosen by user as long as there is enough money
    // and the combination matches a recipe
    public void brew() {
        displayIngredients();

        System.out.println("Which ingredient would you like to use? (press the number that "
                + "corresponds with the ingredient): ");
        int num1 = makeCorrectInput(ingredientList.size());

        displayIngredients();

        System.out.println("Which ingredient would you like to use? (press the number that "
                + "corresponds with the ingredient): ");
        int num2 = makeCorrectInput(ingredientList.size());

        Recipe recipe = potionBrewer.findRecipe(ingredientList.get(num1 - 1), ingredientList.get(num2 - 1));

        if (recipe == null) {
            System.out.println("Chosen ingredients don't match any of your recipes!");
        } else {
            if (haveEnoughMoney(recipe.getCost())) {
                System.out.println("Mixing " + ingredientList.get(num1 - 1).getName() + " and "
                        + ingredientList.get(num2 - 1).getName());

                potionBrewer.makePotion(ingredientList.get(num1 - 1), ingredientList.get(num2 - 1));

                System.out.println("Made the " + recipe.getName() + "!");

                checkMoney();
            } else {
                System.out.println("You don't have enough coins to brew the potion!");
            }
        }
    }

    // EFFECTS: makes sure inputted num is within the range of 1 - listSize
    public int makeCorrectInput(int listSize) {
        int num;
        num = scanner.nextInt();
        while (num >= listSize + 1 || num <= 0) {
            System.out.println("Incorrect Input! Please enter again: ");
            num = scanner.nextInt();
        }

        return num;
    }

    // EFFECTS: prints out ingredient list
    public void displayIngredients() {
        int count = 1;
        for (Ingredient i : ingredientList) {
            System.out.println("[" + count + "]" + " " + i.getName());
            count++;
        }
    }

    // EFFECTS: prints out how much money is left
    public void checkMoney() {
        System.out.println("You have " + potionBrewer.getMoney().getMoney() + " coins left.");
    }

    // MODIFIES: this
    // EFFECTS: sells a potion of user's choice
    public void sellPotions() {
        int count;
        displayPotions();

        if (potionList.isEmpty()) {
            System.out.println("No potions to sell");
        } else {
            System.out.println("Which potion would you like to sell? ");
            count = makeCorrectInput(potionList.size());
            System.out.println("Selling " + potionList.get(count - 1).getName() + " for "
                    + potionList.get(count - 1).getSellPrice() + " coins.");

            potionBrewer.sellPotion(potionList.get(count - 1).getName());

            checkMoney();

        }
    }

    // EFFECTS: prints out the potion list
    public void displayPotions() {
        int count = 1;
        System.out.println("Here is your list of potions: ");
        if (potionList.isEmpty()) {
            System.out.println("You have no potions left!");
        } else {
            for (Potion p : potionList) {
                System.out.println("[" + count + "]" + " " + p.getName() + "    " + "x" + p.getNum());
                count++;
            }
        }
    }

    // EFFECTS: prints out the recipe list
    public void displayRecipes() {
        if (recipeList.isEmpty()) {
            System.out.println("No recipes!");
        } else {
            int count = 1;
            for (Recipe r : recipeList) {
                System.out.println("[" + count + "]" + " " + r.getName() + " recipe");
                count++;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: sells books to user based on user input
    public void displayBookStore() {
        int input;

        System.out.println("Welcome to the bookstore!");
        System.out.println("Here are the books we sell:");

        if (bookStore.getBookList().isEmpty()) {
            System.out.println("No more books on sale!");
        } else {
            displayBookList();
            System.out.println("Which book would you like to buy? (press 0 to exit) ");
            input = checkBookStoreInput(bookStore.getBookList().size());

            if (input == 0) {
                System.out.println("Exiting bookstore.");
            } else {
                Book book = bookStore.getBookList().get(input - 1);

                if (haveEnoughMoney(book.getPrice())) {
                    bookStore.sellBook(book);
                    potionBrewer.buyBook(book);
                    potionBrewer.readBook(book);
                    checkMoney();
                } else {
                    System.out.println("Not enough money to buy!");
                }
            }
        }
    }

    // EFFECTS: checks if input is between 0 - listSize
    public int checkBookStoreInput(int listSize) {
        int num;
        num = scanner.nextInt();
        while (num >= listSize + 1 || num < 0) {
            System.out.println("Incorrect Input! Please enter again: ");
            num = scanner.nextInt();
        }

        return num;
    }

    // EFFECTS: prints out a list of books in the bookstore
    public void displayBookList() {
        int count = 1;
        for (Book b : bookStore.getBookList()) {
            System.out.println("[" + count + "] " + b.getName() + " (" + b.getType() + " book)" + ": "
                    + b.getPrice());
            count++;
        }
    }

    // EFFECTS: returns true if there is enough money to cover the cost
    public Boolean haveEnoughMoney(int cost) {
        if (cost > potionBrewer.getMoney().getMoney()) {
            return false;
        }
        return true;
    }

    // EFFECTS: saves the potion brewer to file
    public void saveGame() {
        jsonWriter = new JsonWriter(DESTINATION);

        try {
            jsonWriter.open();
            jsonWriter.write(potionBrewer);
            jsonWriter.close();
            System.out.println("Saved game to " + DESTINATION);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + DESTINATION);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the potion brewer from file
    public void loadGame() {
        jsonReader = new JsonReader(DESTINATION);
        try {
            potionBrewer = jsonReader.read();
            loadValues();
            System.out.println("Loaded game from " + DESTINATION);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + DESTINATION);
        }
    }
}
