package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PotionBrewerTest {
    PotionBrewer pb;
    RecipeBook recipeBook;
    IngredientBook ingBook;


    @BeforeEach
    public void setUp() {
        pb = new PotionBrewer();
        recipeBook = new RecipeBook("Boar's Ointment", 30, "Recipe",
                new Recipe("Boar's Ointment", new Ingredient("Boar Tusk"),
                        new Ingredient("Mysterious cream"), 20));

        ingBook = new IngredientBook("Snake Skin", 15, "Ingredient", new Ingredient("Snake Skin"));
    }

    @Test
    public void testConstructor() {

        assertEquals("Pond Water", pb.getIngredientList().get(0).getName());
        assertEquals("Sheep Horn", pb.getIngredientList().get(1).getName());
        assertEquals("Bat Vomit", pb.getIngredientList().get(2).getName());

        assertTrue(pb.getPotionList().isEmpty());

        assertEquals("Draught of Sewage", pb.getRecipeList().get(0).getName());
        assertEquals("Elixir of the Sheep", pb.getRecipeList().get(1).getName());
        assertEquals("Horned Vomit Potion", pb.getRecipeList().get(2).getName());
    }

    @Test
    public void testMakePotion() {
        assertTrue(pb.makePotion(new Ingredient("Pond Water"), new Ingredient("Bat Vomit")));
        assertEquals(17, pb.getMoney().getMoney());

        assertTrue(pb.makePotion(new Ingredient("Pond Water"), new Ingredient("Sheep Horn")));
        assertEquals(12, pb.getMoney().getMoney());

        assertTrue(pb.makePotion(new Ingredient("Pond Water"), new Ingredient("Bat Vomit")));
        assertEquals(9, pb.getMoney().getMoney());

        assertTrue(pb.makePotion(new Ingredient("Sheep Horn"), new Ingredient("Bat Vomit")));
        assertEquals(2, pb.getMoney().getMoney());

    }

    @Test
    public void testMakePotionWithBadIngredients() {
        assertFalse(pb.makePotion(new Ingredient("A"), new Ingredient("Bat Vomit")));
        assertFalse(pb.makePotion(new Ingredient("A"), new Ingredient("B")));
        assertFalse(pb.makePotion(new Ingredient("Bat Vomit"), new Ingredient("")));

        assertEquals(20, pb.getMoney().getMoney());
    }

    @Test
    public void testFindRecipe() {
        assertEquals("Draught of Sewage", pb.findRecipe(new Ingredient("Pond Water"), new Ingredient("Bat Vomit")).getName());
        assertEquals("Draught of Sewage", pb.findRecipe(new Ingredient("Bat Vomit"), new Ingredient("Pond Water")).getName());
        assertEquals("Horned Vomit Potion", pb.findRecipe(new Ingredient("Sheep Horn"), new Ingredient("Bat Vomit")).getName());
        assertEquals(null, pb.findRecipe(new Ingredient("A"), new Ingredient("Bat Vomit")));
    }

    @Test
    public void testAddPotion() {
        pb.getPotionList().add(new Potion("Draught of Sewage", 3));
        pb.getPotionList().add(new Potion("Elixir of the Sheep", 5));
        pb.makePotion(new Ingredient("Pond Water"), new Ingredient("Bat Vomit"));


        assertEquals(2, pb.getPotionList().get(0).getNum());
        assertEquals(1, pb.getPotionList().get(1).getNum());
    }

    @Test
    public void testSellPotion() {
        pb.makePotion(new Ingredient("Pond Water"), new Ingredient("Bat Vomit"));
        pb.makePotion(new Ingredient("Pond Water"), new Ingredient("Bat Vomit"));
        assertEquals(2, pb.getPotionList().get(0).getNum());

        assertEquals(14, pb.getMoney().getMoney());

        assertTrue(pb.sellPotion("Draught of Sewage"));
        assertEquals(19, pb.getMoney().getMoney());
        assertEquals(1, pb.getPotionList().get(0).getNum());

        assertFalse(pb.sellPotion("Horned Vomit Potion"));
        assertEquals(19, pb.getMoney().getMoney());

        assertFalse(pb.getPotionList().isEmpty());

        assertTrue(pb.sellPotion("Draught of Sewage"));
        assertTrue(pb.getPotionList().isEmpty());

    }

    @Test
    public void testSellBadPotion(){
        pb.makePotion(new Ingredient("Pond Water"), new Ingredient("Bat Vomit"));

        assertFalse(pb.sellPotion("A"));
        assertEquals(17, pb.getMoney().getMoney());
    }

    @Test
    public void testAddIngredient() {
        pb.addIngredient(new Ingredient("Snake Skin"));

        assertEquals("Snake Skin", pb.getIngredientList().get(3).getName());
    }

    @Test
    public void testAddRecipe() {
        pb.addRecipe(new Recipe("Boar's Ointment", new Ingredient("Boar Tusk"),
                new Ingredient("Mysterious cream"), 20));

        assertEquals("Boar's Ointment", pb.getRecipeList().get(3).getName());
    }

    @Test
    public void testReadBook() {
        pb.readBook(recipeBook);

        pb.readBook(ingBook);

        assertEquals("Boar's Ointment", pb.getRecipeList().get(3).getName());
        assertEquals("Snake Skin", pb.getIngredientList().get(3).getName());
    }

    @Test
    public void testBook() {
        pb.getMoney().addMoney(30);
        pb.buyBook(recipeBook);

        assertEquals(20, pb.getMoney().getMoney());

        pb.buyBook(ingBook);

        assertEquals(5, pb.getMoney().getMoney());
    }

    @Test
    public void testBuyBookWithNotEnoughMoney() {
        pb.buyBook(recipeBook);

        pb.buyBook(ingBook);
        assertEquals(5, pb.getMoney().getMoney());
    }

    @Test
    public void testInitializeLists() {
        assertEquals(3, pb.getRecipeList().size());
        assertEquals(3, pb.getIngredientList().size());
        assertEquals(0, pb.getPotionList().size());
        assertEquals(20, pb.getMoney().getMoney());
    }
}
