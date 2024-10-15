package com.lab4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.SudokuModel;
import view.SudokuController;
import view.SudokuView;

public class SudokuMain extends Application {

    @Override
    public void start(Stage primaryStage) {

        SudokuModel sudokuModel = new SudokuModel(); // Initiera modellen
        SudokuView sudokuView = new SudokuView(sudokuModel);
        SudokuController sudokuController = new SudokuController(sudokuModel,sudokuView);

        Scene scene = new Scene(sudokuView.getMainLayout(),495,400);

        primaryStage.setTitle("Sudoku");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.show();

        sudokuView.addEventHandlers(sudokuController);
    }

    public static void main(String[] args) {
        launch(args);
    }
}