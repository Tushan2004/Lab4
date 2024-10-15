package view;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

public class SudokuController {

    final SudokuModel model; // Reference to the model (where data logic exists)
    final SudokuView view;   // Reference to the view (where UI handling occurs)

    public SudokuController(SudokuModel model, SudokuView view) {
        this.model = model;
        this.view = view;
        this.view.addEventHandlers(this); // Add event handlers to the view
    }

    // 1. Generate a new game with the selected difficulty level
    void generateNewGame() {
        model.initializeBoard(model.currentLevel); // Initializes a new board
        view.updateNumberTiles(); // Updates the UI with the new board
    }

    // 2. Choose difficulty level (easy, medium, hard) and generate a new game round
    void chooseDifficulty(SudokuUtilities.SudokuLevel level) {
        model.setDifficulty(level); // Reinitialize the board
        view.updateNumberTiles(); // Update the view with the board
    }

    // 3. Save an unfinished game to a file
    void saveGameToFile(Stage stage) throws IOException {
        // Kalla på vyns metod för att spara spelet, som hanterar filväljaren
        String filePath = view.saveGame(stage); // Denna metod ska returnera sökvägen till den sparade filen
        if (filePath != null) { // Kontrollera att filen valdes
            SudokuIO.saveGameToFile(filePath, model); // Anropa modellens metod för att spara spelet
        }
    }


    // 4. Load a saved game from a file
    void loadGameFromFile(Stage stage) throws IOException, ClassNotFoundException {
        // Kalla på vyns metod för att ladda spelet, som hanterar filväljaren
        String filePath = view.loadGame(stage); // Denna metod ska returnera sökvägen till den laddade filen
        if (filePath != null) { // Kontrollera att filen valdes
            SudokuIO.loadGameFromFile(filePath, model); // Anropa modellens metod för att ladda spelet
            view.updateNumberTiles(); // Uppdatera vyn med den laddade spelstatusen
        }
    }


    // 5. Fill in a number (1-9) in a cell (which was initially empty)
    void fillCell(int row, int col, int number) {
        if (model.isCellEditable(row, col)) { // Check if the cell is editable
            model.updateCell(row, col, number); // Update the model
            view.updateNumberTiles(); // Update the UI to show the new value
            if (model.isBoardFilled()) {
                if (model.isDone()) {
                    view.showAlert("Congratulations!", Alert.AlertType.INFORMATION);
                } else {
                    view.showAlert("Board is not filled with correct values", Alert.AlertType.WARNING);
                }
            }
        }
    }

    // 6. Clear a cell (which was initially empty)
    void clearCell(int row, int col) {
        if (model.isCellEditable(row, col)) { // Check if the cell is editable
            model.updateCell(row, col, 0); // Set the cell to empty (0)
            view.updateNumberTiles(); // Update the UI
        }
    }

    // 7. Clear
    void clearAllFilledCells() {
        model.clearAllEmptyCells(); // Clear all empty cells in the model
        view.updateNumberTiles(); // Update view to reflect model changes
    }

    // 8. Check if currently filled numbers are correct
    void checkFilledNumbers() {
        if (model.checkFilledNumbers()) {
            view.showAlert("All numbers are correct!", Alert.AlertType.INFORMATION);
        } else {
            view.showAlert("There are incorrect numbers. Please check your entries.", Alert.AlertType.WARNING);
        }
    }

    // 9. Get a brief description of the game rules
    void getGameRules() {
        view.showAlert(model.getGameRules(), Alert.AlertType.INFORMATION);
    }

    // 10. Get help by filling a randomly selected cell with the correct solution
    void getHint() {
        model.provideHint(); // Provide a hint
        view.updateNumberTiles(); // Update the UI to reflect the hint
        if (model.isBoardFilled()) {
            if (model.isDone()) {
                view.showAlert("Congratulations!", Alert.AlertType.INFORMATION);
            } else {
                view.showAlert("Board is not filled with correct values", Alert.AlertType.WARNING);
            }
        }
        }
}

