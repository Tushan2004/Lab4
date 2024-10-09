package com.lab4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import model.SudokuModel;

public class TilePaneNumbers extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Skapa ett Sudoku-spel
        SudokuModel game = new SudokuModel();
        game.initializeBoard();
        int[][] array = game.getBoardState();

        // Skapa en TilePane
        TilePane tilePane = new TilePane();
        tilePane.setPrefColumns(9);
        tilePane.setMaxWidth(270);// Ställ in antal kolumner till 9

        // Lägg till siffror som etiketter
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                Label numberLabel;

                if (array[i][j] == 0) {
                    numberLabel = new Label(); // Skapa en tom etikett
                } else {
                    numberLabel = new Label(String.valueOf(array[i][j])); // Skapa etikett för varje siffra
                }

                numberLabel.setPrefWidth(30); // Sätt preferensbredd
                numberLabel.setPrefHeight(30); // Sätt preferenshöjd
                numberLabel.setStyle("-fx-border-color: black; -fx-background-color: lightgray; -fx-alignment: center;"); // Stil
                numberLabel.setAlignment(javafx.geometry.Pos.CENTER); // Centrera text
                tilePane.getChildren().add(numberLabel); // Lägg till etikett i TilePane
            }
        }

        // Skapa en MenuBar
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem newGameItem = new MenuItem("New Game");
        MenuItem exitItem = new MenuItem("Exit");

        // Lägg till menyobjekt
        fileMenu.getItems().addAll(newGameItem, exitItem);
        menuBar.getMenus().addAll(fileMenu);

        // Skapa en BorderPane som root
        BorderPane root = new BorderPane();
        root.setTop(menuBar); // Sätt MenuBar högst upp
        root.setCenter(tilePane); // Sätt TilePane i mitten

        // Skapa scenen och visa den
        Scene scene = new Scene(root, 300, 300); // Justera storleken på scenen om nödvändigt
        primaryStage.setTitle("TilePane med 9x9 Sudoku");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
