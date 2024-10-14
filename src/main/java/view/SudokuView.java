package view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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
    private TilePane gameBoard;
    private int selectedNumber;

    // GUI elements
    private MenuBar menuBar;
    private VBox mainLayout;
    private GridPane numberPane;
    private VBox leftButtons;
    private Label[][] numberTiles;
    private MenuItem clearBoardItem;
    private Button clearButton;
    private Button hintButton;
    private Button checkButton;
    private MenuItem getGameRulesItem;

    // Konstruktor och layoutinitialisering
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

        VBox numberSelector = createNmrButtons(); // Create number selection buttons
        leftButtons = createLeftButtons(); // Create left side buttons

        BorderPane root = new BorderPane();
        root.setLeft(leftButtons);
        root.setCenter(numberPane);
        root.setRight(numberSelector);
        root.setPadding(new Insets(10));

        menuBar = createMenuBar(); // Create the menu bar
        mainLayout = new VBox(menuBar, root); // Add menu bar to layout
    }

    // GUI-komponentuppbyggnad
    private VBox createNmrButtons() {
        VBox chooseNmr = new VBox(2); // 5 pixels between each button
        chooseNmr.setPadding(new Insets(10));

        for (int i = 1; i <= 10; i++) {
            if (i <= 9) {
                Button button = new Button(String.valueOf(i));
                chooseNmr.getChildren().add(button);
                button.setPrefWidth(30);
                button.setPrefHeight(30);

                int finalNumber = i;
                button.setOnAction(e -> {
                    selectedNumber = finalNumber; // Set the selected number
                });
            }
            if (i == 10) {
                clearButton = new Button("C");
                chooseNmr.getChildren().add(clearButton);
                clearButton.setPrefWidth(30);
                clearButton.setPrefHeight(30);
                clearButton.setOnAction(e -> selectedNumber = 0); // Reset selected number
            }
        }
        return chooseNmr;
    }

    private VBox createLeftButtons() {
        VBox leftSideButtons = new VBox(10);
        leftSideButtons.setPadding(new Insets(10));

        Region spacerTop = new Region();
        Region spacerBottom = new Region();
        VBox.setVgrow(spacerTop, Priority.ALWAYS);
        VBox.setVgrow(spacerBottom, Priority.ALWAYS);

        checkButton = new Button("Check");
        hintButton = new Button("Hint");

        leftSideButtons.getChildren().addAll(spacerTop, checkButton, hintButton, spacerBottom);
        return leftSideButtons;
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem loadGameItem = new MenuItem("Load game");
        MenuItem saveGameItem = new MenuItem("Save game");
        MenuItem exitItem = new MenuItem("Exit");

        Menu gameMenu = new Menu("Game");
        MenuItem restartGameItem = new MenuItem("Restart game");
        MenuItem selectDifficultyItem = new MenuItem("Select difficulty");

        Menu helpMenu = new Menu("Help");
        clearBoardItem = new MenuItem("Clear board");
        getGameRulesItem = new MenuItem("Get game rules");

        fileMenu.getItems().addAll(loadGameItem, saveGameItem, exitItem);
        gameMenu.getItems().addAll(restartGameItem, selectDifficultyItem);
        helpMenu.getItems().addAll(clearBoardItem, getGameRulesItem);
        menuBar.getMenus().addAll(fileMenu, gameMenu, helpMenu);

        return menuBar;
    }

    // Modell- och layoutuppdatering
    void updateNumberTiles() {
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

    /*
    void updateBoard() {
        int[][] currentBoard = model.getBoardState(); // Get current board from model
        gameBoard.getChildren().clear(); // Clear old cells

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Label cellLabel = new Label(currentBoard[row][col] == 0 ? "" : String.valueOf(currentBoard[row][col]));
                cellLabel.setPrefWidth(30);
                cellLabel.setPrefHeight(30);
                cellLabel.setAlignment(Pos.CENTER);
                cellLabel.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
                gameBoard.getChildren().add(cellLabel);
            }
        }
    }*/

    // Händelsehantering
    void addEventHandlers(SudokuController controller) {
        EventHandler<MouseEvent> tileClickHandler = event -> {
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    if (event.getSource() == numberTiles[row][col]) {
                        if (selectedNumber == 10) {
                            controller.clearCell(row, col);
                        } else {
                            controller.fillCell(row, col, selectedNumber);
                        }
                        return;
                    }
                }
            }
        };

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                numberTiles[row][col].setOnMouseClicked(tileClickHandler);
            }
        }

        clearBoardItem.setOnAction(e -> controller.clearAllEmptyCells());
        hintButton.setOnAction(e -> controller.getHint());
        checkButton.setOnAction(e -> controller.checkFilledNumbers());
        getGameRulesItem.setOnAction(e -> controller.getGameRules());
    }

    // Hjälpmetoder
    void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType == Alert.AlertType.INFORMATION ? "Information" : "Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Interna layoutmetoder
    private void initNumberTiles() {
        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);
        int[][] array = model.getBoardState(); // Get board from model

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Label tile = new Label(array[row][col] == 0 ? "" : String.valueOf(array[row][col]));
                tile.setPrefWidth(50);
                tile.setPrefHeight(50);
                tile.setFont(font);
                tile.setAlignment(Pos.CENTER);
                tile.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");
                numberTiles[row][col] = tile;
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

    // Getters för layout och spelkomponenter
    VBox getMainLayout() {
        return mainLayout;
    }

    TilePane getGameBoard() {
        return gameBoard;
    }

    MenuBar getMenuBar() {
        return menuBar;
    }
}
