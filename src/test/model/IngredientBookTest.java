package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IngredientBookTest {
    IngredientBook ingredientBook;

    @BeforeEach
    public void setUp(){
        ingredientBook = new IngredientBook("Snake Venom", 10, "Ingredient", new Ingredient("Snake Venom"));
    }

    @Test
    public void testConstructor(){
        assertEquals("Snake Venom", ingredientBook.getName());
        assertEquals(10, ingredientBook.getPrice());
        assertEquals("Ingredient", ingredientBook.getType());
        assertEquals("Snake Venom", ingredientBook.getIngredient().getName());
    }
}
