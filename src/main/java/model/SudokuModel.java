package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The SudokuModel class represents the logic for a Sudoku game, managing the board state,
 * handling game initialization, difficulty levels, and providing hints and validation.
 * This class is responsible for saving, loading, and updating the game state, as well as providing game rules.
 */
public class SudokuModel implements Serializable {

    private SudokuCell[][] board;  // 9x9 grid of SudokuCells
    public SudokuCell[][] copiedBoard;
    private boolean[][] initialEmptyCells;  // Tracks which cells were empty at the start of the game
    public SudokuUtilities.SudokuLevel currentLevel;  // Current difficulty level
    private int[][][] matrix;  // Internal matrix representation of the board

    /**
     * Default constructor initializes the Sudoku board and sets the difficulty level to EASY.
     */
    public SudokuModel() {
        board = new SudokuCell[9][9];
        copiedBoard = new SudokuCell[9][9];
        initialEmptyCells = new boolean[9][9]; // True if the cell was empty at the start
        currentLevel = SudokuUtilities.SudokuLevel.EASY; // Default difficulty level
        matrix = null;
        initializeBoard(currentLevel);
    }

    /**
     * Initializes the Sudoku board based on the provided difficulty level.
     *
     * @param level the difficulty level for the game (EASY, MEDIUM, or HARD)
     */
    public void initializeBoard(SudokuUtilities.SudokuLevel level) {
        switch (level) {
            case EASY:
                matrix = SudokuUtilities.generateSudokuMatrix(SudokuUtilities.SudokuLevel.EASY);
                currentLevel = SudokuUtilities.SudokuLevel.EASY;
                break;
            case MEDIUM:
                matrix = SudokuUtilities.generateSudokuMatrix(SudokuUtilities.SudokuLevel.MEDIUM);
                currentLevel = SudokuUtilities.SudokuLevel.MEDIUM;
                break;
            case HARD:
                matrix = SudokuUtilities.generateSudokuMatrix(SudokuUtilities.SudokuLevel.HARD);
                currentLevel = SudokuUtilities.SudokuLevel.HARD;
                break;
        }

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int initialValue = matrix[row][col][0];
                int solutionValue = matrix[row][col][1];
                boolean isVisible = initialValue != 0;  // If the initial value is zero, the cell is hidden

                board[row][col] = new SudokuCell(initialValue, solutionValue, isVisible);
                copiedBoard[row][col] = new SudokuCell(initialValue, solutionValue, isVisible);

                // Track which cells were empty at the start (where initialValue is 0)
                initialEmptyCells[row][col] = (initialValue == 0);
            }
        }
    }

    private void copyBoard() {

        // Iterera genom varje rad och kolumn i den befintliga board
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                // Skapa en ny SudokuCell baserat på den befintliga
                copiedBoard[row][col] = new SudokuCell(board[row][col].getInitialValue(),board[row][col].getSolutionValue(),board[row][col].isVisible());
            }
        }
    }


    /**
     * Checks if a cell can be edited by the user (if it was empty at the start of the game).
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @return true if the cell was empty at the start, false otherwise
     */
    public boolean isCellEditable(int row, int col) {
        return initialEmptyCells[row][col];
    }

    /**
     * Checks if all filled numbers in the board are correct based on the solution.
     *
     * @return true if all filled numbers are correct, false otherwise
     */
    public boolean checkFilledNumbers() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col].getUserValue() != 0) {
                    if (!board[row][col].isCorrect()) {
                        return false;
                    }
                }
            }
        }
        return true; // All filled numbers are correct
    }

    /**
     * Updates the current SudokuModel with data from another model.
     *
     * @param otherModel the SudokuModel to update from
     */
    public void updateBoardFromFile(SudokuModel otherModel) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                this.board[row][col].setInitialValue(otherModel.board[row][col].getInitialValue());
                this.board[row][col].setSolutionValue(otherModel.board[row][col].getSolutionValue());
                this.board[row][col].setVisible(otherModel.board[row][col].isVisible());
                this.board[row][col].setUserValue(otherModel.board[row][col].getUserValue());  // Copy user value
            }
        }
        this.currentLevel = otherModel.currentLevel;  // Update the difficulty level
    }

    /**
     * Clears all cells that were empty at the start of the game by setting their values to zero.
     */
    public void clearAllEmptyCells() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (initialEmptyCells[row][col]) {
                    board[row][col].setUserValue(0);  // Set the cell's value to zero
                }
            }
        }
    }

    /**
     * Returns the current state of the board for display purposes.
     *
     * @return a 2D array representing the current display values of the board
     */
    public int[][] getBoardState() {
        int[][] state = new int[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                state[row][col] = board[row][col].getDisplayValue();
            }
        }
        return state;
    }

    /**
     * Updates the value of a specific cell on the board based on user input.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @param value the value to set in the cell (should be between 1 and 9)
     */
    public void updateCell(int row, int col, int value) {
        if (value == 0) {
            board[row][col].setUserValue(0);
        }
        if (value < 1 || value > 9) {
            return;  // Do nothing if the value is out of range
        }
        board[row][col].setUserValue(value);  // Set the user's value

        copyBoard();
    }

    /**
     * Sets the difficulty level and initializes the board with a new game.
     *
     * @param level the new difficulty level
     */
    public void setDifficulty(SudokuUtilities.SudokuLevel level) {
        currentLevel = level;  // Save the new difficulty level
        initializeBoard(level);  // Generate a new game based on the difficulty level
    }

    /**
     * Provides the game rules for Sudoku.
     *
     * @return a string explaining the rules of Sudoku
     */
    public String getGameRules() {
        return "The goal is to fill the 9x9 grid so that every row, column, and 3x3 box contains the numbers 1-9 without repetition.";
    }

    /**
     * Provides a hint by filling in a random empty cell with its solution value.
     */
    public void provideHint() {
        Random random = new Random();
        List<int[]> emptyCells = new ArrayList<>();

        // Collect all empty cells
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col].getDisplayValue() == 0) {
                    emptyCells.add(new int[]{row, col});
                }
            }
        }

        if (!emptyCells.isEmpty()) {
            // Select a random empty cell and fill it with the solution value
            int[] cell = emptyCells.get(random.nextInt(emptyCells.size()));
            int row = cell[0];
            int col = cell[1];
            int solutionValue = board[row][col].getSolutionValue();
            updateCell(row, col, solutionValue);  // Fill the cell with the solution value
        }
    }

    /**
     * Checks if the board is completely filled.
     *
     * @return true if the board is completely filled, false otherwise
     */
    public boolean isBoardFilled() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col].getDisplayValue() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the entire board is correctly filled based on the solution values.
     *
     * @return true if the board is fully solved, false otherwise
     */
    public boolean isDone() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (!board[row][col].isCorrect()) {
                    System.out.println("Incorrect");
                    return false;
                }
            }
        }
        System.out.println("Solved");
        return true;
    }
}
