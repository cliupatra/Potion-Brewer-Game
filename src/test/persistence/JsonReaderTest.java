package persistence;

import model.PotionBrewer;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// tests modeled after JsonDemo Application
// github link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest {
    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            PotionBrewer pb = reader.read();
            fail("IOException expected");
        } catch (IOException e) {

        }
    }

    @Test
    public void testBasePotionBrewer() {
        JsonReader reader = new JsonReader("data/testReaderBasePotionBrewer.json");
        try {
            PotionBrewer pb = reader.read();
            assertEquals("Pond Water", pb.getIngredientList().get(0).getName());
            assertEquals("Sheep Horn", pb.getIngredientList().get(1).getName());
            assertEquals("Bat Vomit", pb.getIngredientList().get(2).getName());

            assertTrue(pb.getPotionList().isEmpty());

            assertEquals("Draught of Sewage", pb.getRecipeList().get(0).getName());
            assertEquals("Elixir of the Sheep", pb.getRecipeList().get(1).getName());
            assertEquals("Horned Vomit Potion", pb.getRecipeList().get(2).getName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testGeneralPotionBrewer(){
        JsonReader reader = new JsonReader("data/testReaderGeneralPotionBrewer.json");
        try {
            PotionBrewer pb = reader.read();
            assertEquals("Pond Water", pb.getIngredientList().get(0).getName());
            assertEquals("Sheep Horn", pb.getIngredientList().get(1).getName());
            assertEquals("Bat Vomit", pb.getIngredientList().get(2).getName());

            assertEquals("Elixir of the Sheep", pb.getPotionList().get(0).getName());

            assertEquals("Draught of Sewage", pb.getRecipeList().get(0).getName());
            assertEquals("Elixir of the Sheep", pb.getRecipeList().get(1).getName());
            assertEquals("Horned Vomit Potion", pb.getRecipeList().get(2).getName());
            assertEquals("Mixture of Toxins", pb.getRecipeList().get(3).getName());

            assertEquals(1, pb.getBookStore().getBookList().size());
            assertEquals("Snake Venom", pb.getBookStore().getBookList().get(0).getName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
