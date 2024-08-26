package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookStoreTest {
    BookStore bookStore;

    @BeforeEach
    public void setUp() {
        bookStore = new BookStore();
    }

    @Test
    public void testConstructor() {
        assertEquals(2, bookStore.getBookList().size());
    }

    @Test
    public void testSellBook() {
        bookStore.sellBook(new IngredientBook("Snake Venom", 10, "Ingredient",
                new Ingredient("Snake Venom")));

        assertEquals("Mixture of Toxins", bookStore.getBookList().get(0).getName());

        bookStore.sellBook(new RecipeBook("Mixture of Toxins", 10, "Recipe", new Recipe("Mixture of Toxins",
                new Ingredient("Pond Water"), new Ingredient("Snake Venom"), 10)));

        assertTrue(bookStore.getBookList().isEmpty());
    }
}
