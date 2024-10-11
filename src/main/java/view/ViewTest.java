package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.SudokuModel;

public class ViewTest extends Application {

    @Override
    public void start(Stage primaryStage) {

        SudokuModel sudokuModel = new SudokuModel(); // Initiera modellen
        SudokuView sudokuView = new SudokuView(sudokuModel);
        SudokuController sudokuController = new SudokuController(sudokuModel,sudokuView);

        //sudokuView.addEventHandlers(sudokuController);

        Scene scene = new Scene(sudokuView.getMainLayout(),495,400);

        primaryStage.setTitle("Sudoku");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}