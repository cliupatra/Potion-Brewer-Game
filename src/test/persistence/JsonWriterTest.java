package persistence;

import model.Ingredient;
import model.PotionBrewer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// tests modeled after JsonDemo Application
// github link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            PotionBrewer pb = new PotionBrewer();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterBasePotionBrewer(){
        try {
            PotionBrewer pb = new PotionBrewer();
            JsonWriter writer = new JsonWriter("data/testWriterBasePotionBrewer.json");
            writer.open();
            writer.write(pb);
            writer.close();

            JsonReader reader = new JsonReader("data/testWriterBasePotionBrewer.json");
            pb = reader.read();
            assertEquals("Pond Water", pb.getIngredientList().get(0).getName());
            assertEquals("Sheep Horn", pb.getIngredientList().get(1).getName());
            assertEquals("Bat Vomit", pb.getIngredientList().get(2).getName());

            assertTrue(pb.getPotionList().isEmpty());

            assertEquals("Draught of Sewage", pb.getRecipeList().get(0).getName());
            assertEquals("Elixir of the Sheep", pb.getRecipeList().get(1).getName());
            assertEquals("Horned Vomit Potion", pb.getRecipeList().get(2).getName());

            assertEquals(2, pb.getBookStore().getBookList().size());
            assertEquals("Snake Venom", pb.getBookStore().getBookList().get(0).getName());
            assertEquals("Mixture of Toxins", pb.getBookStore().getBookList().get(1).getName());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterGeneralPotionBrewer(){
        try {
            PotionBrewer pb = new PotionBrewer();
            pb.makePotion(new Ingredient("Sheep Horn"), new Ingredient("Bat Vomit"));
            JsonWriter writer = new JsonWriter("data/testWriterGeneralPotionBrewer.json");
            writer.open();
            writer.write(pb);
            writer.close();

            JsonReader reader = new JsonReader("data/testWriterGeneralPotionBrewer.json");
            pb = reader.read();
            assertEquals("Horned Vomit Potion", pb.getPotionList().get(0).getName());

            assertEquals(2, pb.getBookStore().getBookList().size());
            assertEquals("Snake Venom", pb.getBookStore().getBookList().get(0).getName());
            assertEquals("Mixture of Toxins", pb.getBookStore().getBookList().get(1).getName());


        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
