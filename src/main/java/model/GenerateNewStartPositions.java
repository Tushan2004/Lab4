package model;

public class GenerateNewStartPositions extends SudokuUtilities{

    static String mirrorHorizontally(String stringRepresentation) {
        int[][][] matrix = convertStringToIntMatrix(stringRepresentation);

        // Loop över hälften av raderna för att spegla horisontellt
        for (int row = 0; row < GRID_SIZE / 2; row++) {
            int oppositeRow = GRID_SIZE - 1 - row;

            // Byt rad row med rad oppositeRow
            for (int col = 0; col < GRID_SIZE; col++) {
                int temp = matrix[row][col][0];
                matrix[row][col][0] = matrix[oppositeRow][col][0];
                matrix[oppositeRow][col][0] = temp;

                temp = matrix[row][col][1];
                matrix[row][col][1] = matrix[oppositeRow][col][1];
                matrix[oppositeRow][col][1] = temp;
            }
        }

        // Uppdatera stringRepresentation från matrisen
        return convertMatrixToString(matrix);
    }

    static String mirrorVertically(String stringRepresentation) {
        int[][][] matrix = convertStringToIntMatrix(stringRepresentation);

        // Loop över hälften av kolumnerna för att spegla vertikalt
        for (int col = 0; col < GRID_SIZE / 2; col++) {
            int oppositeCol = GRID_SIZE - 1 - col;

            // Byt kolumn col med kolumn oppositeCol
            for (int row = 0; row < GRID_SIZE; row++) {
                int temp = matrix[row][col][0];
                matrix[row][col][0] = matrix[row][oppositeCol][0];
                matrix[row][oppositeCol][0] = temp;

                temp = matrix[row][col][1];
                matrix[row][col][1] = matrix[row][oppositeCol][1];
                matrix[row][oppositeCol][1] = temp;
            }
        }

        // Uppdatera stringRepresentation från matrisen
        return convertMatrixToString(matrix);
    }


    static String swapPair(String stringRepresentation) {
        int[][][] matrix = convertStringToIntMatrix(stringRepresentation);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
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

        // Om det behövs, uppdatera stringRepresentation
        return convertMatrixToString(matrix);
        // Här kan du använda updatedRepresentation för ytterligare behandling
    }
}
