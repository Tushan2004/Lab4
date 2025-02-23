package view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.SudokuModel;
import model.SudokuUtilities;
import javafx.application.Platform;


import java.io.File;
import java.io.IOException;

public class SudokuView  {
    private static final int GRID_SIZE = 9;
    private static final int SECTION_SIZE = 3;
    private static final int SECTIONS_PER_ROW = 3;

    private SudokuModel model;
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
    private MenuItem newGame;
    private Menu selectDifficultyMenu;
    private MenuItem easy, medium, hard;
    private MenuItem loadGameItem, saveGameItem, exitItem;

    // Konstruktor och layoutinitialisering
    public SudokuView(SudokuModel model) {
        this.model = model;
        this.numberTiles = new Label[GRID_SIZE][GRID_SIZE]; // Initialize number tiles

        initLayout(); // Initialize layout components
    }

    private void initLayout() {
        updateNumberTiles();
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
        loadGameItem = new MenuItem("Load game");
        saveGameItem = new MenuItem("Save game");
        exitItem = new MenuItem("Exit");

        Menu gameMenu = new Menu("Game");
        newGame = new MenuItem("New game");
        selectDifficultyMenu = new Menu("Select difficulty");
        easy = new MenuItem("Easy");
        medium = new MenuItem("Medium");
        hard = new MenuItem("Hard");

        Menu helpMenu = new Menu("Help");
        clearBoardItem = new MenuItem("Clear board");
        getGameRulesItem = new MenuItem("Get game rules");

        fileMenu.getItems().addAll(loadGameItem, saveGameItem, exitItem);
        gameMenu.getItems().addAll(newGame, selectDifficultyMenu);
        helpMenu.getItems().addAll(clearBoardItem, getGameRulesItem);
        menuBar.getMenus().addAll(fileMenu, gameMenu, helpMenu);
        selectDifficultyMenu.getItems().addAll(easy, medium, hard);

        return menuBar;
    }

    // Modell- och layoutuppdatering
    void updateNumberTiles() {
        int[][] boardState = model.getBoardState(); // Get current state

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (numberTiles[row][col] == null) {
                    // Om rutan inte finns ännu, skapa den
                    Label tile = new Label();
                    tile.setPrefWidth(50);
                    tile.setPrefHeight(50);
                    tile.setAlignment(Pos.CENTER);
                    tile.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");
                    numberTiles[row][col] = tile; // Lägg till i rutnätet
                }

                // Uppdatera rutans innehåll
                if (boardState[row][col] == 0) {
                    numberTiles[row][col].setText(""); // Töm rutan om värdet är 0
                } else {
                    if (model.copiedBoard[row][col].getInitialValue() == 0){
                        numberTiles[row][col].setFont(Font.font("Monospaced", FontWeight.LIGHT, 20)); // Ändra till önskat teckensnitt
                        numberTiles[row][col].setTextFill(Color.BLACK); // Färg för användarens ifyllda nummer
                    } else {
                        // För fasta värden, använd annan stil
                        numberTiles[row][col].setFont(Font.font("Monospaced", FontWeight.BOLD, 20));
                        numberTiles[row][col].setTextFill(Color.BLACK); // Färg för initiala värden
                    }
                    numberTiles[row][col].setText(String.valueOf(boardState[row][col])); // Uppdatera rutans nummer
                }
            }
        }
    }


    // Händelsehantering
    public void addEventHandlers(SudokuController controller) {
        EventHandler<MouseEvent> tileClickHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Bestäm vilken cell som klickades
                for (int row = 0; row < GRID_SIZE; row++) {
                    for (int col = 0; col < GRID_SIZE; col++) {
                        if (event.getSource() == numberTiles[row][col]) {
                            if (selectedNumber == 10) {
                                controller.clearCell(row, col); // Rensa cell om "C" är valt
                            } else {
                                controller.fillCell(row, col, selectedNumber); // Fyll cellen
                            }
                            return; // Avsluta efter att cellen har hanterats
                        }
                    }
                }
            }
        };

        // Sätt event handler för varje cell
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                numberTiles[row][col].setOnMouseClicked(tileClickHandler);
            }
        }

        // Hantering av övriga kontroller
        clearBoardItem.setOnAction(e -> controller.clearAllFilledCells());
        hintButton.setOnAction(e -> controller.getHint());
        checkButton.setOnAction(e -> controller.checkFilledNumbers());
        getGameRulesItem.setOnAction(e -> controller.getGameRules());
        newGame.setOnAction(e -> controller.generateNewGame());
        easy.setOnAction(e -> controller.chooseDifficulty(SudokuUtilities.SudokuLevel.EASY));
        medium.setOnAction(e -> controller.chooseDifficulty(SudokuUtilities.SudokuLevel.MEDIUM));
        hard.setOnAction(e -> controller.chooseDifficulty(SudokuUtilities.SudokuLevel.HARD));
        loadGameItem.setOnAction(e -> {
            try {
                controller.loadGameFromFile((Stage) mainLayout.getScene().getWindow());
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        saveGameItem.setOnAction(e -> {
            try {
                controller.saveGameToFile((Stage) mainLayout.getScene().getWindow());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        exitItem.setOnAction(e -> Platform.exit());
    }


    // Hjälpmetoder
    void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType == Alert.AlertType.INFORMATION ? "Information" : "Warning");
        alert.setContentText(message);
        alert.showAndWait();
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
    public VBox getMainLayout() {
        return mainLayout;
    }

    String saveGame(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Spara Sudoku Spel");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku Files", "*.sudoku"));

        // Visa dialogen för att välja var filen ska sparas
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            if (!file.getName().endsWith(".sudoku")) {
                file = new File(file.getAbsolutePath() + ".sudoku");
            }
            return file.getAbsolutePath(); // Returnera sökvägen till filen
        }
        return null; // Om ingen fil valdes
    }

    String loadGame(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ladda Sudoku Spel");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku Files", "*.sudoku"));

        // Visa dialogen för att välja fil
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            return file.getAbsolutePath(); // Returnera sökvägen till filen
        }
        return null; // Om ingen fil valdes
    }
}
