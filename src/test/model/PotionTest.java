package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PotionTest {

    Potion potion;

    @BeforeEach
    public void setUp(){
        potion = new Potion("Draught of Sewage", 3);
    }

    @Test
    public void testConstructor(){
        assertEquals("Draught of Sewage", potion.getName());
        assertEquals(3, potion.getSellPrice());
        assertEquals(1, potion.getNum());
    }

    @Test
    public void testIncreaseNum(){
        potion.increaseNum();

        assertEquals(2, potion.getNum());
    }
}
