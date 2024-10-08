package model;

public class SudokuTest {
    public static void main(String[] args) {
        // Testa varje svårighetsgrad
        testSudokuGeneration(SudokuUtilities.SudokuLevel.EASY);
        testSudokuGeneration(SudokuUtilities.SudokuLevel.MEDIUM);
        testSudokuGeneration(SudokuUtilities.SudokuLevel.HARD);
    }

    private static void testSudokuGeneration(SudokuUtilities.SudokuLevel level) {
        try {
            int[][][] sudokuMatrix = SudokuUtilities.generateSudokuMatrix(level);
            System.out.println("Sudoku matrix generated for level: " + level);
            printSudokuMatrix(sudokuMatrix);
        } catch (Exception e) {
            System.err.println("An error occurred while generating Sudoku for level " + level + ": " + e.getMessage());
        }
    }

    private static void printSudokuMatrix(int[][][] matrix) {
        for (int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                System.out.print(matrix[row][col][0] + " "); // Print initial values
            }
            System.out.println(); // New line after each row
        }
        System.out.println(); // Extra new line for better readability
    }
}
