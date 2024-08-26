package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// Image Links
// https://www.vectorstock.com/royalty-free-vector/bookstore-or-shop-with-books-vector-24559535
// https://www.kickassfacts.com/askreaders-if-life-was-a-video-game-what-tips-would-appear-on-the-loading-screen/
// https://www.facebook.com/savegameonline/
// https://www.google.com/search?sca_esv=27b000d398e4e776&sxsrf=ACQVn0-J-RMjJOV4S7IrtlsLrDbTbPCAVA:1711320549639&q=potion+brewing+background&uds=AMwkrPuHg33dywq06rO2P3P076cYM4YxcUwXsP9xzY942OQGiq3dacsAU7xY7GHdBwP0FD4a6NYPvcogY73T8HaRLWfPcgBAVAjjJVZL6gB5o5e68Ny4JCvfyJPmDtL8Kgf4MqN2q72jO6kZYPOwg8-ooRb5iTrs4f1sEa02bYx_3iTUNe6FlY5AiDBDBgoGSUHHAEPtJveFHHb8Zlvrc_EvSFSwjaOcNHICPicice95F-Jk5bPj6rQvtPGgGq4UQpVPR2GOV86A6NHvkKJKEyUgnkrhOVTwSrUDf5OIEu-H609OviEmrci3ugT1wcs0-5iBms09nGUu&udm=2&prmd=isvnmbtz&sa=X&ved=2ahUKEwjv1tTr_Y2FAxVoMDQIHWSbCeMQtKgLegQIDhAB&biw=1280&bih=631&dpr=1.5#vhid=oSwPk14fZOOVQM&vssid=mosaic
// https://www.behance.net/search/projects/potion%20bottle%20game%20art
// https://www.behance.net/gallery/106292859/2D-Game-Assets-Fantasy-Money-Bags?locale=de_DE


// graphical user interface for potion brewer game that allows users to see a window pop up with various
// buttons that can be clicked to do different things such as brewing or selling potions. Also allows users
// to save and load their game
public class PotionBrewerGUI extends JFrame {
    private static int WIDTH = 600;
    private static int HEIGHT = 600;
    private PotionBrewer potionBrewer;
    private JFrame gameWindow;
    private JButton ingredientList;
    private JButton recipeList;
    private JButton potionList;
    private JButton brewPotion;
    private JButton sellPotion;
    private JButton bookStore;
    private JButton saveGame;
    private JButton loadGame;
    private Ingredient i1 = null;
    private Ingredient i2 = null;
    private ImageIcon defaultBackground;
    private JLabel defaultBackgroundLabel;
    private JPanel panel;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String DESTINATION = "data/potionBrewer.json";

    // EFFECTS: creates a potion brewer gui with a potion brewer that has an ingredient list, potion list,
    // and recipe list. Also creates a frame for which the game can take place and buttons that can be clicked
    // that can cause different things to happen
    public PotionBrewerGUI(PotionBrewer pb) {
        potionBrewer = pb;
        gameWindow = new JFrame("Potion Brewer Game");
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setSize(WIDTH, HEIGHT);
        gameWindow.setLocationRelativeTo(null);

        panel = new JPanel();

        initializeButtons();

        addButtonsToPanel();

        defaultBackground = new ImageIcon("data/brew potion background.jpg");
        defaultBackgroundLabel = new JLabel(defaultBackground);
        panel.add(defaultBackgroundLabel);

        jsonWriter = new JsonWriter(DESTINATION);
        jsonReader = new JsonReader(DESTINATION);

        gameWindow.add(panel, BorderLayout.CENTER);
        gameWindow.setVisible(true);
    }

    // EFFECTS: creates all the necessary game buttons that users need to play the game
    public void initializeButtons() {
        ingredientList = createIngredientListButton("Ingredient List");
        recipeList = createRecipeListButton("Recipe List");
        potionList = createPotionListButton("Potion List");
        brewPotion = createBrewPotionButton("Brew Potion");
        sellPotion = createSellPotionButton("Sell Potion");
        bookStore = createBookstoreButton("Bookstore");
        saveGame = createSaveGameButton("Save Game");
        loadGame = createLoadGameButton("Load Game");
    }

    // MODIFIES: this
    // EFFECTS: adds all the buttons to the panel
    public void addButtonsToPanel() {
        panel.add(ingredientList);
        panel.add(recipeList);
        panel.add(potionList);
        panel.add(brewPotion);
        panel.add(sellPotion);
        panel.add(bookStore);
        panel.add(saveGame);
        panel.add(loadGame);
    }

    // EFFECTS: creates a button where users can see their ingredient list after pressing
    public JButton createIngredientListButton(String name) {
        JButton button = new JButton(name);
        button.setFocusable(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame ingredientFrame = new JFrame(name);
                JPanel ingredientPanel = new JPanel();
                ingredientPanel.setLayout(new BoxLayout(ingredientPanel, BoxLayout.Y_AXIS));

                for (Ingredient ingredient : potionBrewer.getIngredientList()) {
                    JLabel label = new JLabel(ingredient.getName());
                    ingredientPanel.add(label);
                }
                ingredientFrame.add(ingredientPanel);
                ingredientFrame.setSize(300, 300);
                ingredientFrame.setLocationRelativeTo(null);
                ingredientFrame.setVisible(true);
            }
        });

        return button;
    }

    // EFFECTS: creates a button where users can see their recipe list after pressing
    public JButton createRecipeListButton(String name) {
        JButton button = new JButton(name);
        button.setFocusable(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame recipeFrame = new JFrame(name);
                JPanel recipePanel = new JPanel();
                recipePanel.setLayout(new BoxLayout(recipePanel, BoxLayout.Y_AXIS));

                for (Recipe recipe : potionBrewer.getRecipeList()) {
                    JButton r = createRecipeButton(recipe.getName() + " Recipe", recipe);
                    recipePanel.add(r);
                }
                recipeFrame.add(recipePanel);
                recipeFrame.setSize(300, 300);
                recipeFrame.setLocationRelativeTo(null);
                recipeFrame.setVisible(true);
            }
        });

        return button;
    }

    // EFFECTS: creates a recipe button that displays the required ingredients
    public JButton createRecipeButton(String name, Recipe r) {
        JButton button = new JButton(name);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame recipeIngredientsFrame = new JFrame();
                JPanel recipeIngredientsPanel = new JPanel();
                recipeIngredientsPanel.setLayout(new BoxLayout(recipeIngredientsPanel, BoxLayout.Y_AXIS));
                JLabel label = new JLabel("Ingredients Needed: ");
                JLabel i1 = new JLabel(r.getI1().getName());
                JLabel i2 = new JLabel(r.getI2().getName());

                recipeIngredientsPanel.add(label);
                recipeIngredientsPanel.add(i1);
                recipeIngredientsPanel.add(i2);
                recipeIngredientsFrame.add(recipeIngredientsPanel);
                recipeIngredientsFrame.setSize(200, 200);
                recipeIngredientsFrame.setLocationRelativeTo(null);
                recipeIngredientsFrame.setVisible(true);
            }
        });

        return button;
    }

    // EFFECTS: creates a button where users can see their potion list after pressing
    public JButton createPotionListButton(String name) {
        JButton button = new JButton(name);
        button.setFocusable(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame potionFrame = new JFrame(name);
                JPanel potionPanel = new JPanel();
                potionPanel.setLayout(new BoxLayout(potionPanel, BoxLayout.Y_AXIS));

                if (potionBrewer.getPotionList().isEmpty()) {
                    JLabel label = new JLabel("You have no potions!");
                    potionPanel.add(label);
                } else {
                    displayPotionList(potionPanel);
                }
                potionFrame.add(potionPanel);
                potionFrame.setSize(300, 300);
                potionFrame.setLocationRelativeTo(null);
                potionFrame.setVisible(true);
            }
        });

        return button;
    }

    // EFFECTS: displays each potion in the potion list and their corresponding amount
    public void displayPotionList(JPanel potionPanel) {
        for (Potion potion : potionBrewer.getPotionList()) {
            JLabel label = new JLabel(potion.getName() + "  x" + potion.getNum());
            potionPanel.add(label);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a button that allows users to brew potions based on
    // which ingredients they choose
    public JButton createBrewPotionButton(String name) {
        JButton button = new JButton(name);
        button.setFocusable(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame brewPotionFrame = new JFrame(name);
                JPanel brewPotionPanel = makePanel("data/potion.jpg");
                brewPotionPanel.setLayout(new BoxLayout(brewPotionPanel, BoxLayout.Y_AXIS));

                for (Ingredient ingredient : potionBrewer.getIngredientList()) {
                    JButton i = createIngredientButton(ingredient.getName(), brewPotionPanel);
                    brewPotionPanel.add(i);
                }
                brewPotionFrame.add(brewPotionPanel);
                brewPotionFrame.setSize(300, 300);
                brewPotionFrame.setLocationRelativeTo(null);
                brewPotionFrame.setVisible(true);
            }
        });

        return button;
    }

    // EFFECTS: creates ingredient buttons that users can press to brew potions
    public JButton createIngredientButton(String name, JPanel brewPotionPanel) {
        JButton button = new JButton(name);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitIngredientButton(name, brewPotionPanel);
            }
        });
        return button;
    }

    // MODIFIES: this
    // EFFECTS: based on user input, potions will be brewed
    // if there is bad input or not enough money, potions will not be brewed
    public void hitIngredientButton(String name, JPanel brewPotionPanel) {
        if (i1 == null) {
            i1 = new Ingredient(name);
            updateDisplay(brewPotionPanel, "Chose " + name + " !");

        } else if (i2 == null) {
            i2 = new Ingredient(name);
            updateDisplay(brewPotionPanel, "Chose " + name + " !");

            Recipe r = potionBrewer.findRecipe(i1, i2);
            if (r == null) {
                updateDisplay(brewPotionPanel, "Unable to make a potion!");
            } else {
                if (potionBrewer.getMoney().getMoney() < r.getCost()) {
                    updateDisplay(brewPotionPanel, "Not enough money to make potion!");
                } else {
                    potionBrewer.makePotion(i1, i2);
                    updateDisplay(brewPotionPanel, "Brewed " + r.getName() + " !");
                    updateDisplay(brewPotionPanel, "You have " + potionBrewer.getMoney().getMoney()
                            + " coins left!");
                }
            }
            i1 = null;
            i2 = null;
        }
    }

    // EFFECTS: creates a new label and adds it to the given panel
    public void updateDisplay(JPanel panel, String message) {
        JLabel label = new JLabel(message);
        label.setForeground(Color.white);
        panel.add(label);
        panel.revalidate();
        panel.repaint();
    }

    // MODIFIES: this
    // EFFECTS: creates a button where users can save their file when pressed
    public JButton createSaveGameButton(String name) {
        JButton button = new JButton(name);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame savedFrame = new JFrame();
                JPanel savedPanel = makePanel("data/save game.jpg");
                try {
                    jsonWriter.open();
                    jsonWriter.write(potionBrewer);
                    jsonWriter.close();
                    updateDisplay(savedPanel, "Saved game to " + DESTINATION);
                } catch (FileNotFoundException exception) {
                    updateDisplay(savedPanel, "Unable to write to file: " + DESTINATION);
                }

                savedFrame.add(savedPanel);
                savedFrame.setSize(300, 300);
                savedFrame.setLocationRelativeTo(null);
                savedFrame.setVisible(true);

            }
        });
        return button;
    }

    // MODIFIES: this
    // EFFECTS: creates a button where users can load their file when pressed
    public JButton createLoadGameButton(String name) {
        JButton button = new JButton(name);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame loadFrame = new JFrame();
                JPanel loadPanel = makePanel("data/loading game.jpg");

                try {
                    potionBrewer = jsonReader.read();
                    updateDisplay(loadPanel, "Loaded game to " + DESTINATION);
                    System.out.println("Loaded game from " + DESTINATION);
                } catch (IOException exception) {
                    updateDisplay(loadPanel, "Unable to write to file: " + DESTINATION);
                }

                loadFrame.add(loadPanel);
                loadFrame.setSize(300, 300);
                loadFrame.setLocationRelativeTo(null);
                loadFrame.setVisible(true);

            }
        });
        return button;
    }

    // EFFECTS: creates a button where users can visit the bookstore to buy books when pressed
    public JButton createBookstoreButton(String name) {
        JButton button = new JButton(name);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame bookStoreFrame = new JFrame();
                JPanel bookStorePanel = makePanel("data/bookstore.jpg");

                for (Book b : potionBrewer.getBookStore().getBookList()) {
                    JButton book = createBookButton(b, bookStorePanel);
                    bookStorePanel.add(book);
                }

                bookStoreFrame.add(bookStorePanel);
                bookStoreFrame.setSize(300, 300);
                bookStoreFrame.setLocationRelativeTo(null);
                bookStoreFrame.setVisible(true);

            }
        });
        return button;
    }

    // MODIFIES: this
    // EFFECTS: creates a button where users can buy a book from the bookstore when pressed
    // if users don't have enough money, the book cannot be bought
    public JButton createBookButton(Book b, JPanel bookStorePanel) {
        JButton button = new JButton(b.getName());

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (potionBrewer.getMoney().getMoney() >= b.getPrice()) {
                    potionBrewer.getMoney().spendMoney(b.getPrice());
                    potionBrewer.readBook(b);
                    potionBrewer.getBookStore().sellBook(b);
                    updateDisplay(bookStorePanel, "Bought the book!");

                    bookStorePanel.remove(button);
                    bookStorePanel.revalidate();
                    bookStorePanel.repaint();
                } else {
                    updateDisplay(bookStorePanel, "Not enough money to buy the book.");
                }
            }
        });
        return button;
    }

    // EFFECTS: creates a button where users can sell their potions when pressed
    public JButton createSellPotionButton(String name) {
        JButton button = new JButton(name);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame sellPotionFrame = new JFrame();
                JPanel sellPotionPanel = makePanel("data/sell potion image.jpg");

                for (Potion p : potionBrewer.getPotionList()) {
                    JButton potion = createPotionButton(p, sellPotionPanel);
                    sellPotionPanel.add(potion);
                }

                sellPotionFrame.add(sellPotionPanel);
                sellPotionFrame.setSize(500, 500);
                sellPotionFrame.setLocationRelativeTo(null);
                sellPotionFrame.setVisible(true);
            }
        });
        return button;
    }

    // EFFECTS: returns a panel that has a background image
    public JPanel makePanel(String imageFile) {
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon(imageFile);
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        return backgroundPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates a button where users can sell the specified potion when pressed
    // when a potion is sold, the button will update accordingly
    public JButton createPotionButton(Potion p, JPanel sellPotionPanel) {
        JButton button = new JButton(p.getName() + "  x" + p.getNum());

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (p.getNum() == 1) {
                    sellPotionPanel.remove(button);
                    potionBrewer.sellPotion(p.getName());
                } else {
                    potionBrewer.sellPotion(p.getName());
                    button.setText(p.getName() + "  x" + p.getNum());
                }
                updateDisplay(sellPotionPanel, "Sold the potion!");
                updateDisplay(sellPotionPanel, "You now have " + potionBrewer.getMoney().getMoney() + " coins!");

            }
        });
        return button;
    }
}
