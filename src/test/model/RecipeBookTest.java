package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecipeBookTest {

    RecipeBook  recipeBook;
    Ingredient ingredient1;
    Ingredient ingredient2;
    Recipe recipe;
    @BeforeEach
    public void setUp(){
        ingredient1 = new Ingredient("Pond Water");
        ingredient2 = new Ingredient("Snake Venom");
        recipe = new Recipe("Mixture of Toxins", ingredient1, ingredient2, 8);
        recipeBook = new RecipeBook("Mixture of Toxins", 10, "Recipe", recipe);
    }

    @Test
    public void testConstructor(){
        assertEquals("Mixture of Toxins", recipeBook.getRecipe().getName());
        assertEquals(10, recipeBook.getPrice());
        assertEquals("Recipe", recipeBook.getType());
    }
}
