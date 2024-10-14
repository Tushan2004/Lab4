package view;

import javafx.scene.control.Alert;
import model.*;

public class SudokuController {

    private SudokuModel model; // Reference to the model (where data logic exists)
    private SudokuView view;   // Reference to the view (where UI handling occurs)

    public SudokuController(SudokuModel model, SudokuView view) {
        this.model = model;
        this.view = view;
        this.view.addEventHandlers(this); // Add event handlers to the view
    }

    // 1. Generate a new game with the selected difficulty level
    public void generateNewGame() {
        model.initializeBoard(model.currentLevel); // Initializes a new board
        view.updateNumberTiles(); // Updates the UI with the new board
    }

    // 2. Choose difficulty level (easy, medium, hard) and generate a new game round
    public void chooseDifficulty(SudokuUtilities.SudokuLevel level) {
        model.initializeNewBoard(level); // Reinitialize the board
        view.updateNumberTiles(); // Update the view with the board
    }

    // 3. Save an unfinished game to a file
    public void saveGameToFile(String filePath) {
        model.saveGameToFile(filePath); // Save the game to the selected file
    }

    // 4. Load a saved game from a file
    public void loadGameFromFile(String filePath) {
        model.loadGameFromFile(filePath); // Load the game from the selected file
        view.updateNumberTiles(); // Update the view with the loaded game
    }

    // 5. Fill in a number (1-9) in a cell (which was initially empty)
    public void fillCell(int row, int col, int number) {
        if (model.isCellEditable(row, col)) { // Check if the cell is editable
            model.updateCell(row, col, number); // Update the model
            view.updateNumberTiles(); // Update the UI to show the new value
        }
    }

    // 6. Clear a cell (which was initially empty)
    public void clearCell(int row, int col) {
        if (model.isCellEditable(row, col)) { // Check if the cell is editable
            model.updateCell(row, col, 0); // Set the cell to empty (0)
            view.updateNumberTiles(); // Update the UI
        }
    }

    public void clearAllEmptyCells() {
        model.clearAllEmptyCells(); // Clear all empty cells in the model
        view.updateNumberTiles(); // Update view to reflect model changes
    }

    // 8. Check if currently filled numbers are correct
    public void checkFilledNumbers() {
        if (model.checkFilledNumbers()) {
            view.showAlert("All numbers are correct!", Alert.AlertType.INFORMATION);
        } else {
            view.showAlert("There are incorrect numbers. Please check your entries.", Alert.AlertType.WARNING);
        }
    }

    // 9. Get a brief description of the game rules
    public void getGameRules() {
        view.showAlert(model.getGameRules(), Alert.AlertType.INFORMATION);
    }

    // 10. Get help by filling a randomly selected cell with the correct solution
    public void getHint() {
        model.provideHint(); // Provide a hint
        view.updateNumberTiles(); // Update the UI to reflect the hint
        }
}

