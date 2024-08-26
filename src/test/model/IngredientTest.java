package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IngredientTest {

    Ingredient ingredient;

    @BeforeEach
    public void setUp(){
        ingredient = new Ingredient("Pond Water");
    }

    @Test
    public void testConstructor(){
        assertEquals("Pond Water", ingredient.getName());
    }
}
