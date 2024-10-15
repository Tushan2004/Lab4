package model;

/**
 * This class provides methods for manipulating Sudoku board configurations, including
 * horizontal and vertical mirroring, and swapping values in the Sudoku grid.
 */
public class GenerateNewStartPositions extends SudokuUtilities {

    /**
     * Mirrors the Sudoku grid horizontally.
     * This swaps rows from top to bottom across the horizontal axis.
     *
     * @param stringRepresentation the string representation of the Sudoku grid
     * @return the new string representation after horizontal mirroring
     */
    static String mirrorHorizontally(String stringRepresentation) {
        int[][][] matrix = convertStringToIntMatrix(stringRepresentation);

        // Loop through half of the rows to mirror horizontally
        for (int row = 0; row < GRID_SIZE / 2; row++) {
            int oppositeRow = GRID_SIZE - 1 - row;

            // Swap row 'row' with row 'oppositeRow'
            for (int col = 0; col < GRID_SIZE; col++) {
                int temp = matrix[row][col][0];
                matrix[row][col][0] = matrix[oppositeRow][col][0];
                matrix[oppositeRow][col][0] = temp;

                temp = matrix[row][col][1];
                matrix[row][col][1] = matrix[oppositeRow][col][1];
                matrix[oppositeRow][col][1] = temp;
            }
        }

        // Update stringRepresentation from the matrix
        return convertMatrixToString(matrix);
    }

    /**
     * Mirrors the Sudoku grid vertically.
     * This swaps columns from left to right across the vertical axis.
     *
     * @param stringRepresentation the string representation of the Sudoku grid
     * @return the new string representation after vertical mirroring
     */
    static String mirrorVertically(String stringRepresentation) {
        int[][][] matrix = convertStringToIntMatrix(stringRepresentation);

        // Loop through half of the columns to mirror vertically
        for (int col = 0; col < GRID_SIZE / 2; col++) {
            int oppositeCol = GRID_SIZE - 1 - col;

            // Swap column 'col' with column 'oppositeCol'
            for (int row = 0; row < GRID_SIZE; row++) {
                int temp = matrix[row][col][0];
                matrix[row][col][0] = matrix[row][oppositeCol][0];
                matrix[row][oppositeCol][0] = temp;

                temp = matrix[row][col][1];
                matrix[row][col][1] = matrix[row][oppositeCol][1];
                matrix[row][oppositeCol][1] = temp;
            }
        }

        // Update stringRepresentation from the matrix
        return convertMatrixToString(matrix);
    }

    /**
     * Swaps all instances of the values 1 and 2 within the Sudoku grid.
     * If the initial or solution value of a cell is 1, it is swapped to 2, and vice versa.
     *
     * @param stringRepresentation the string representation of the Sudoku grid
     * @return the new string representation after swapping 1s and 2s
     */
    static String swapPair(String stringRepresentation) {
        int[][][] matrix = convertStringToIntMatrix(stringRepresentation);

        // Traverse the matrix and swap values of 1 and 2
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (matrix[i][j][1] == 1) {
                    matrix[i][j][1] = 2;
                    if (matrix[i][j][0] != 0) {
                        matrix[i][j][0] = 2;
                    }
                }
                else if (matrix[i][j][1] == 2) {
                    matrix[i][j][1] = 1;
                    if (matrix[i][j][0] != 0) {
                        matrix[i][j][0] = 1;
                    }
                }
            }
        }

        // If necessary, update the string representation
        return convertMatrixToString(matrix);
        // You can use the updatedRepresentation for further processing if needed
    }
}
