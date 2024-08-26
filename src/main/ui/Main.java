package ui;


import model.PotionBrewer;

// starts the potion brewer game where users can do different things based on their input such as
// brewing or selling potions. Users can also save and load their game.
public class Main {
    public static void main(String[] args) {
        PotionBrewer potionBrewer = new PotionBrewer();
        new PotionBrewerGUI(potionBrewer);
    }
}
