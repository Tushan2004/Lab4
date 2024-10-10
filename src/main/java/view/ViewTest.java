package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.SudokuModel;

public class ViewTest extends Application {
    private static final int GRID_SIZE = 9;
    private static final int SECTION_SIZE = 3;
    private static final int SECTIONS_PER_ROW = 3;

    private Label[][] numberTiles; // Rutorna som visar siffror
    private GridPane numberPane;
    private VBox numberSelector, leftButtons;
    private Button button;
    private MenuBar menuBar;
    private SudokuModel sudokuModel; // Referens till modellen
    private int selectedNumber; // Det valda numret för att fylla i

    @Override
    public void start(Stage primaryStage) {
        sudokuModel = new SudokuModel(); // Initiera modellen
        numberTiles = new Label[GRID_SIZE][GRID_SIZE];
        initNumberTiles();
        numberPane = makeNumberPane();
        numberPane.setPrefWidth(100); // Justera till lämplig storlek
        numberSelector = createButtons();
        leftButtons = createLeftButtons();

        BorderPane root = new BorderPane();
        root.setLeft(leftButtons);
        root.setCenter(numberPane);
        root.setRight(numberSelector);
        root.setPadding(new Insets(10));

        menuBar = createMenuBar(); // Skapa menybaren
        VBox mainLayout = new VBox(menuBar, root); // Lägg till menybaren i layout
        Scene scene = new Scene(mainLayout, 495, 400);

        primaryStage.setTitle("Sudoku");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private VBox createButtons() {
        VBox chooseNmr = new VBox(2); // 5 pixlar mellan varje knapp

        chooseNmr.setPadding(new Insets(10));

        // Skapa knappar från 1 till 9
        for (int i = 1; i <= 9; i++) {
            button = new Button(String.valueOf(i));
            chooseNmr.getChildren().add(button); // Lägg till knappen i VBox
            button.setPrefWidth(30);
            button.setPrefHeight(30);
            // Lägg till händelsehanterare för varje knapp
            int number = i; // För att undvika problem med scoping
            button.setOnAction(e -> {
                selectedNumber = number; // Sätt det valda numret
            });
        }
        Button clearButton = new Button("C");
        chooseNmr.getChildren().add(clearButton); // Lägg till knappen i VBox
        clearButton.setPrefWidth(30);
        clearButton.setPrefHeight(30);

        // Händelsehanterare för Clear-knappen
        clearButton.setOnAction(e -> {
            // Återställ det valda numret
            selectedNumber = 0;
        });

        return chooseNmr;
    }

    private VBox createLeftButtons() {
        VBox leftSideButtons = new VBox(10);

        // Skapa en tom region för att centrera knapparna
        Region spacerTop = new Region();
        Region spacerBottom = new Region();
        VBox.setVgrow(spacerTop, Priority.ALWAYS); // Fyll utrymmet ovanför med spacer
        VBox.setVgrow(spacerBottom, Priority.ALWAYS); // Fyll utrymmet under med spacer

        leftSideButtons.setPadding(new Insets(10));

        Button checkButton = new Button("Check");
        Button hintButton = new Button("Hint");

        leftSideButtons.getChildren().addAll(spacerTop, checkButton, hintButton, spacerBottom);
        return leftSideButtons;
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar(); // Skapa en meny

        Menu fileMenu = new Menu("File"); // Skapa en filmeny
        MenuItem loadGameItem = new MenuItem("Load game"); // Menyobjekt för nytt spel
        MenuItem saveGameItem = new MenuItem("Save game");
        MenuItem exitItem = new MenuItem("Exit"); // Menyobjekt för avsluta

        Menu gameMenu = new Menu("Game");
        MenuItem restartGameItem = new MenuItem("Restart game");
        MenuItem selectDifficultyItem = new MenuItem("Select difficulty");
        Menu helpMenu = new Menu("Help");
        MenuItem clearBoardItem = new MenuItem("Clear board"); // Skapa menyobjekt för att rensa brädet
        MenuItem getGameRulesItem = new MenuItem("Get game rules");

        // Lägg till händelsehanterare för att rensa brädet
        clearBoardItem.setOnAction(e -> {
            sudokuModel.clearAllEmptyCells(); // Anropa modellens metod för att rensa alla tomma celler
            updateNumberTiles(); // Uppdatera brädet i vyn
        });

        // Lägg till menyobjekt i filmenyn
        fileMenu.getItems().addAll(loadGameItem, saveGameItem, exitItem);
        gameMenu.getItems().addAll(restartGameItem, selectDifficultyItem);
        helpMenu.getItems().addAll(clearBoardItem, getGameRulesItem);
        menuBar.getMenus().addAll(fileMenu, gameMenu, helpMenu); // Lägg till filmenyn i menybaren

        return menuBar; // Returnera den skapade menybaren
    }

    private void initNumberTiles() {
        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);
        int[][] array = sudokuModel.getBoardState(); // Hämta brädet från modellen
        Label tile;

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (array[row][col] == 0) {
                    tile = new Label(); // Skapa en tom etikett
                } else {
                    tile = new Label(String.valueOf(array[row][col])); // Skapa etikett för varje siffra
                }
                tile.setPrefWidth(50);
                tile.setPrefHeight(50);
                tile.setFont(font);
                tile.setAlignment(Pos.CENTER);
                tile.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");
                numberTiles[row][col] = tile;

                // Lägg till händelsehanterare för att klicka på rutorna
                int finalRow = row; // För att undvika problem med scoping
                int finalCol = col; // För att undvika problem med scoping
                tile.setOnMouseClicked(e -> {
                    if (selectedNumber != 0 && array[finalRow][finalCol] == 0) {
                        // Om rutan är tom, fyll i det valda numret
                        sudokuModel.updateCell(finalRow, finalCol, selectedNumber);
                        updateNumberTiles(); // Uppdatera rutorna för att återspegla ändringen
                    }
                });
            }
        }
    }

    // Uppdatera UI efter att modellens bräde har ändrats
    private void updateNumberTiles() {
        int[][] boardState = sudokuModel.getBoardState(); // Hämta aktuellt tillstånd

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (boardState[row][col] == 0) {
                    numberTiles[row][col].setText(""); // Sätt cellen som tom
                } else {
                    numberTiles[row][col].setText(String.valueOf(boardState[row][col])); // Uppdatera siffra
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

    public static void main(String[] args) {
        launch(args);
    }
}
