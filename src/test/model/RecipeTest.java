package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecipeTest {

    Recipe recipe;
    Ingredient ingredient1;
    Ingredient ingredient2;

    @BeforeEach
    public void setUp(){
        ingredient1 = new Ingredient("Pond Water");
        ingredient2 = new Ingredient("Sheep Horn");
        recipe = new Recipe("Elixir of the Sheep", ingredient1, ingredient2, 5);
    }

    @Test
    public void testConstructor(){
        assertEquals("Elixir of the Sheep", recipe.getName());
        assertEquals(5, recipe.getCost());
        assertEquals("Pond Water", recipe.getI1().getName());
        assertEquals("Sheep Horn", recipe.getI2().getName());
    }

}
