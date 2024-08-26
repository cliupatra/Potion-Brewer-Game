package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTest {
    Book book1;
    Book book2;

    @BeforeEach
    public void setUp(){
        book1 = new Book("Snake Venom", 10, "Ingredient");
        book2 = new Book("Mixture of Toxins", 10, "Recipe");
    }

    @Test
    public void testConstructor(){
        assertEquals("Snake Venom", book1.getName());
        assertEquals(10, book1.getPrice());
        assertEquals("Ingredient", book1.getType());

        assertEquals("Mixture of Toxins", book2.getName());
        assertEquals(10, book2.getPrice());
        assertEquals("Recipe", book2.getType());
    }
}
