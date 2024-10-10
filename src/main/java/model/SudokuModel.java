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
        // Kontrollera om värdet är giltigt (1-9) innan vi fortsätter
        if (value < 1 || value > 9) {
            return; // Om värdet är utanför intervallet, gör ingenting
        }

        // Kontrollera om värdet redan finns i den 3x3-sektionen
        if (isValueInSection(row, col, value)) {
            return; // Om värdet redan finns i sektionen, gör ingenting
        }

        // Kontrollera om värdet redan finns i samma rad
        if (isValueInRow(row, value)) {
            return; // Om värdet redan finns i raden, gör ingenting
        }

        // Kontrollera om värdet redan finns i samma kolumn
        if (isValueInColumn(col, value)) {
            return; // Om värdet redan finns i kolumnen, gör ingenting
        }

        board[row][col].setUserValue(value); // Sätt användarens värde
    }

    // Ny metod för att kontrollera om ett värde redan finns i 3x3-sektionen
    private boolean isValueInSection(int row, int col, int value) {
        // Hitta vilken sektion vi befinner oss i
        int sectionRowStart = (row / 3) * 3; // Startrow för sektionen
        int sectionColStart = (col / 3) * 3; // Startcol för sektionen

        // Kontrollera varje cell i sektionen
        for (int r = sectionRowStart; r < sectionRowStart + 3; r++) {
            for (int c = sectionColStart; c < sectionColStart + 3; c++) {
                if (board[r][c].getDisplayValue() == value) {
                    return true; // Om värdet finns, returnera true
                }
            }
        }
        return false; // Om värdet inte finns, returnera false
    }

    // Ny metod för att kontrollera om ett värde redan finns i samma rad
    private boolean isValueInRow(int row, int value) {
        for (int col = 0; col < 9; col++) {
            if (board[row][col].getDisplayValue() == value) {
                return true; // Om värdet finns i raden, returnera true
            }
        }
        return false; // Om värdet inte finns, returnera false
    }

    // Ny metod för att kontrollera om ett värde redan finns i samma kolumn
    private boolean isValueInColumn(int col, int value) {
        for (int row = 0; row < 9; row++) {
            if (board[row][col].getDisplayValue() == value) {
                return true; // Om värdet finns i kolumnen, returnera true
            }
        }
        return false; // Om värdet inte finns, returnera false
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
