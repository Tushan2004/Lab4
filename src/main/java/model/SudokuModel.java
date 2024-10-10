package model;

public class SudokuModel {
    private SudokuCell[][] board;  // 9x9 grid of SudokuCells

    public SudokuModel() {
        board = new SudokuCell[9][9];
        initializeBoard();
    }

    // Initialiserar brädet från en genererad matris
    public void initializeBoard() {
        int[][][] matrix = SudokuUtilities.generateSudokuMatrix(SudokuUtilities.SudokuLevel.EASY);

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int initialValue = matrix[row][col][0];
                int solutionValue = matrix[row][col][1];
                boolean isVisible = initialValue != 0;  // Om initialvärdet är noll, är rutan dold.
                board[row][col] = new SudokuCell(initialValue, solutionValue, isVisible);
            }
        }
    }

    // Metod för att uppdatera en cell med användarens inmatning
    public void updateCell(int row, int col, int value) {
        board[row][col].setUserValue(value);
    }

    // Returnerar en 2D-array för att visa i vyn
    public int[][] getBoardState() {
        int[][] state = new int[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                state[row][col] = board[row][col].getDisplayValue();
            }
        }
        return state;
    }
}

