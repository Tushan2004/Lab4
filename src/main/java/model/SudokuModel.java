package model;

public class SudokuModel {
    private SudokuCell[][] board;  // 9x9 grid of SudokuCells
    private boolean[][] initialEmptyCells;  // Spårar vilka celler som var tomma vid spelets start

    public SudokuModel() {
        board = new SudokuCell[9][9];
        initialEmptyCells = new boolean[9][9]; // True om cellen var tom från början
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

                // Spåra vilka celler som var tomma vid start (där initialValue är 0)
                initialEmptyCells[row][col] = (initialValue == 0);
            }
        }
    }

    // Metod för att uppdatera en cell med användarens inmatning
    public void updateCell(int row, int col, int value) {
        board[row][col].setUserValue(value);
    }

    // Rensar alla celler som var tomma vid spelets start
    public void clearAllEmptyCells() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                // Endast rensa celler som var tomma vid start
                if (initialEmptyCells[row][col]) {
                    board[row][col].setUserValue(0); // Sätter cellens värde till tomt (0)
                }
            }
        }
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
