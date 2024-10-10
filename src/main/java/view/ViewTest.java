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

    private Label[][] numberTiles; // the tiles/squares to show in the UI grid
    private GridPane numberPane;
    private VBox numberSelector, leftButtons;
    private Button button;
    private MenuBar menuBar;

    @Override
    public void start(Stage primaryStage) {
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
        }
        Button clearButton = new Button("C");
        chooseNmr.getChildren().add(clearButton);// Lägg till knappen i VBox
        clearButton.setPrefWidth(30);
        clearButton.setPrefHeight(30);

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
        MenuItem clearBoardItem= new MenuItem("Clear board");
        MenuItem  getGameRulesItem= new MenuItem("Get game rules");


        // Lägg till menyobjekt i filmenyn
        fileMenu.getItems().addAll(loadGameItem, saveGameItem, exitItem);
        gameMenu.getItems().addAll(restartGameItem, selectDifficultyItem);
        helpMenu.getItems().addAll(clearBoardItem, getGameRulesItem);
        menuBar.getMenus().addAll(fileMenu, gameMenu, helpMenu); // Lägg till filmenyn i menybaren

        return menuBar; // Returnera den skapade menybaren
    }

    private void initNumberTiles() {
        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);
        SudokuModel sudokuModel = new SudokuModel();
        int[][] array = sudokuModel.getBoardState();
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
