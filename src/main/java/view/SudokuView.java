package view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.SudokuModel;

public class SudokuView extends Parent {
    private static final int GRID_SIZE = 9;
    private static final int SECTION_SIZE = 3;
    private static final int SECTIONS_PER_ROW = 3;

    private SudokuModel model;
    private TilePane gameBoard; // TilePane for the game cells
    private int selectedNumber;

    // GUI elements
    private MenuBar menuBar;
    private VBox mainLayout;
    private GridPane numberPane;
    private VBox leftButtons;
    private Label[][] numberTiles;

    public SudokuView(SudokuModel model) {
        this.model = model;
        this.numberTiles = new Label[GRID_SIZE][GRID_SIZE]; // Initialize number tiles
        this.gameBoard = new TilePane();
        this.gameBoard.setPrefColumns(9);
        this.gameBoard.setMaxWidth(270); // Use 9 columns for the Sudoku board

        initLayout(); // Initialize layout components
    }

    private void initLayout() {
        initNumberTiles(); // Initialize number tiles
        numberPane = makeNumberPane(); // Create number pane
        numberPane.setPrefWidth(100); // Adjust to appropriate size

        // Create buttons
        VBox numberSelector = createButtons(); // Create number selection buttons
        leftButtons = createLeftButtons(); // Create left side buttons

        // Create the root layout
        BorderPane root = new BorderPane();
        root.setLeft(leftButtons);
        root.setCenter(numberPane);
        root.setRight(numberSelector);
        root.setPadding(new Insets(10));

        // Create the menu bar
        menuBar = createMenuBar();
        mainLayout = new VBox(menuBar, root); // Add menu bar to layout
    }

    private VBox createButtons() {
        VBox chooseNmr = new VBox(2); // 5 pixels between each button
        chooseNmr.setPadding(new Insets(10));

        // Create buttons from 1 to 9
        for (int i = 1; i <= 9; i++) {
            Button button = new Button(String.valueOf(i));
            chooseNmr.getChildren().add(button); // Add button to VBox
            button.setPrefWidth(30);
            button.setPrefHeight(30);

            // Add event handler for each button
            int number = i; // Avoid scoping issues
            button.setOnAction(e -> {
                selectedNumber = number; // Set the selected number
            });
        }

        // Create and add clear button
        Button clearButton = new Button("C");
        chooseNmr.getChildren().add(clearButton);
        clearButton.setPrefWidth(30);
        clearButton.setPrefHeight(30);

        // Event handler for Clear button
        clearButton.setOnAction(e -> {
            selectedNumber = 0; // Reset the selected number
        });

        return chooseNmr;
    }

    private VBox createLeftButtons() {
        VBox leftSideButtons = new VBox(10);
        leftSideButtons.setPadding(new Insets(10));

        // Create a spacer for vertical alignment
        Region spacerTop = new Region();
        Region spacerBottom = new Region();
        VBox.setVgrow(spacerTop, Priority.ALWAYS); // Fill space above
        VBox.setVgrow(spacerBottom, Priority.ALWAYS); // Fill space below

        Button checkButton = new Button("Check");
        Button hintButton = new Button("Hint");

        leftSideButtons.getChildren().addAll(spacerTop, checkButton, hintButton, spacerBottom);
        return leftSideButtons;
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar(); // Create a menu bar

        Menu fileMenu = new Menu("File"); // Create a file menu
        MenuItem loadGameItem = new MenuItem("Load game");
        MenuItem saveGameItem = new MenuItem("Save game");
        MenuItem exitItem = new MenuItem("Exit");

        Menu gameMenu = new Menu("Game");
        MenuItem restartGameItem = new MenuItem("Restart game");
        MenuItem selectDifficultyItem = new MenuItem("Select difficulty");

        Menu helpMenu = new Menu("Help");
        MenuItem clearBoardItem = new MenuItem("Clear board"); // Create menu item to clear board
        MenuItem getGameRulesItem = new MenuItem("Get game rules");

        // Add event handler for clearing the board
        clearBoardItem.setOnAction(e -> {
            model.clearAllEmptyCells(); // Call model method to clear all empty cells
            updateNumberTiles(); // Update the view
        });

        // Add menu items to menus
        fileMenu.getItems().addAll(loadGameItem, saveGameItem, exitItem);
        gameMenu.getItems().addAll(restartGameItem, selectDifficultyItem);
        helpMenu.getItems().addAll(clearBoardItem, getGameRulesItem);
        menuBar.getMenus().addAll(fileMenu, gameMenu, helpMenu); // Add menus to menu bar

        return menuBar; // Return the created menu bar
    }

    private void initNumberTiles() {
        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);
        int[][] array = model.getBoardState(); // Get board from model
        Label tile;

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (array[row][col] == 0) {
                    tile = new Label(); // Create an empty label
                } else {
                    tile = new Label(String.valueOf(array[row][col])); // Create label for each number
                }
                tile.setPrefWidth(50);
                tile.setPrefHeight(50);
                tile.setFont(font);
                tile.setAlignment(Pos.CENTER);
                tile.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");
                numberTiles[row][col] = tile;

                // Add event handler for clicking on the tiles
                int finalRow = row; // To avoid scoping issues
                int finalCol = col; // To avoid scoping issues
                tile.setOnMouseClicked(e -> {
                    if (selectedNumber != 0 && array[finalRow][finalCol] == 0) {
                        // If the tile is empty, fill it with the selected number
                        model.updateCell(finalRow, finalCol, selectedNumber);
                        updateNumberTiles(); // Update the tiles to reflect the change
                    }
                });
            }
        }
    }

    // Update UI after model board has changed
    private void updateNumberTiles() {
        int[][] boardState = model.getBoardState(); // Get current state

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (boardState[row][col] == 0) {
                    numberTiles[row][col].setText(""); // Set cell to empty
                } else {
                    numberTiles[row][col].setText(String.valueOf(boardState[row][col])); // Update number
                }
            }
        }
    }

    private GridPane makeNumberPane() {
        GridPane root = new GridPane();
        root.setStyle("-fx-border-color: black; -fx-border-width: 1.0px; -fx-background-color: white;");

        for (int srow = 0; srow < SECTIONS_PER_ROW; srow++) {
            for (int scol = 0; scol < SECTIONS_PER_ROW; scol++) {
                GridPane section = new GridPane();
                section.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");

                for (int row = 0; row < SECTION_SIZE; row++) {
                    for (int col = 0; col < SECTION_SIZE; col++) {
                        section.add(numberTiles[srow * SECTION_SIZE + row][scol * SECTION_SIZE + col], col, row);
                    }
                }
                root.add(section, scol, srow);
            }
        }
        return root;
    }

    // Updates the game board with current data from the model
    public void updateBoard() {
        int[][] currentBoard = model.getBoardState(); // Get current board from model

        gameBoard.getChildren().clear(); // Clear old cells

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Label cellLabel;

                if (currentBoard[row][col] == 0) {
                    cellLabel = new Label("");  // Empty cell if value is 0
                } else {
                    cellLabel = new Label(String.valueOf(currentBoard[row][col])); // Show number
                }

                cellLabel.setPrefWidth(30);
                cellLabel.setPrefHeight(30);
                cellLabel.setAlignment(Pos.CENTER);  // Center text
                cellLabel.setStyle("-fx-border-color: black; -fx-border-width: 1px;");

                gameBoard.getChildren().add(cellLabel); // Add cell to the game
            }
        }
    }

    // Returns the main visual component (VBox)
    public VBox getMainLayout() {
        return mainLayout;
    }

    // Return the game board (TilePane)
    public TilePane getGameBoard() {
        return gameBoard;
    }
}
