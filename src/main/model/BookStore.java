package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// toJson() method modeled after JsonDemo Application
// github link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// represents a bookstore with a list of books that are available for the user to buy
public class BookStore {
    private ArrayList<Book> bookList;

    // EFFECTS: creates a bookstore with a list of recipe and ingredient books to buy
    public BookStore() {
        bookList = new ArrayList<>();
        bookList.add(new IngredientBook("Snake Venom", 10, "Ingredient",
                new Ingredient("Snake Venom")));
        bookList.add(new RecipeBook("Mixture of Toxins", 10, "Recipe", new Recipe("Mixture of Toxins",
                new Ingredient("Pond Water"), new Ingredient("Snake Venom"), 10)));
    }

    // REQUIRES: book is inside bookList
    // MODIFIES: this
    // EFFECTS: sells book from its catalogue
    public void sellBook(Book b) {
        Book tempBook = new Book("", 0, "");
        for (Book book : bookList) {
            if (book.getName().equals(b.getName())) {
                tempBook = book;
            }
        }

        bookList.remove(tempBook);
    }

    // getters
    public ArrayList<Book> getBookList() {
        return bookList;
    }

    // EFFECTS: returns this as JSON array
    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();

        for (Book b : bookList) {
            if (b.getType().equals("Ingredient")) {
                jsonArray.put(((IngredientBook) b).toJson());
            } else {
                jsonArray.put(((RecipeBook) b).toJson());
            }
        }


        return jsonArray;
    }
}
