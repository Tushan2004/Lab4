package model;

import java.util.Random;

/**
 * Utility class for handling various Sudoku-related operations, including generating Sudoku grids,
 * randomizing start positions, and converting between string and matrix representations.
 */
public class SudokuUtilities {

    /**
     * Enum representing the difficulty levels of a Sudoku puzzle: EASY, MEDIUM, and HARD.
     */
    public enum SudokuLevel {EASY, MEDIUM, HARD}

    public static final int GRID_SIZE = 9;
    public static final int SECTIONS_PER_ROW = 3;
    public static final int SECTION_SIZE = 3;

    /**
     * Generates a 3-dimensional matrix representing a Sudoku grid based on the specified difficulty level.
     * The matrix contains both the initial values and the solution.
     *
     * @param level The difficulty level of the Sudoku grid (EASY, MEDIUM, or HARD).
     * @return A 3-dimensional int matrix where:
     * [row][col][0] represents the initial values, with zero indicating an empty cell.
     * [row][col][1] represents the solution values.
     * @throws IllegalArgumentException if the generated matrix string has an invalid format.
     */
    public static int[][][] generateSudokuMatrix(SudokuLevel level) {
        String representationString;
        Random random = new Random();
        int randomNumber = random.nextInt(4);
        switch (level) {
            case EASY: representationString = easy; break;
            case MEDIUM: representationString = medium; break;
            case HARD: representationString = hard; break;
            default: representationString = medium;
        }
        if (randomNumber == 0 || randomNumber == 1 || randomNumber == 2) {
            String newMatrix = randomizeStartMatrix(representationString);
            return convertStringToIntMatrix(newMatrix);
        }
        return convertStringToIntMatrix(representationString);
    }

    /**
     * Converts a string representation of a Sudoku grid into a 3-dimensional matrix.
     * The string contains both the initial values and the solution.
     *
     * @param stringRepresentation A string of 2*81 characters (162 total), where the first 81 characters represent
     *                             the initial values (with '0' for empty cells) and the next 81 characters represent the solution.
     * @return A 3-dimensional int matrix where:
     * [row][col][0] represents the initial values.
     * [row][col][1] represents the solution.
     * @throws IllegalArgumentException if the string is not exactly 162 characters long or contains invalid characters.
     */
    /*package private*/
    static int[][][] convertStringToIntMatrix(String stringRepresentation) {
        if (stringRepresentation.length() != GRID_SIZE * GRID_SIZE * 2)
            throw new IllegalArgumentException("representation length " + stringRepresentation.length());

        int[][][] values = new int[GRID_SIZE][GRID_SIZE][2];
        char[] charRepresentation = stringRepresentation.toCharArray();

        int charIndex = 0;
        // Populate initial values
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                values[row][col][0] =
                        convertCharToSudokuInt(charRepresentation[charIndex++]);
            }
        }

        // Populate solution values
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                values[row][col][1] =
                        convertCharToSudokuInt(charRepresentation[charIndex++]);
            }
        }

        return values;
    }

    /**
     * Converts a character ('0'-'9') to its corresponding integer value for the Sudoku grid.
     *
     * @param ch The character to convert.
     * @return The corresponding integer value.
     * @throws IllegalArgumentException if the character is not a digit between '0' and '9'.
     */
    private static int convertCharToSudokuInt(char ch) {
        if (ch < '0' || ch > '9') throw new IllegalArgumentException("character " + ch);
        return ch - '0';
    }

    /**
     * Randomizes the initial Sudoku grid by applying one of several transformations (mirror horizontally,
     * mirror vertically, or swap pairs).
     *
     * @param stringRepresentation The string representation of the Sudoku grid.
     * @return A new string with a randomized start configuration.
     */
    private static String randomizeStartMatrix(String stringRepresentation) {
        Random random = new Random();
        int randomNumber = random.nextInt(3);
        switch (randomNumber) {
            case 0: return GenerateNewStartPositions.mirrorHorizontally(stringRepresentation);
            case 1: return GenerateNewStartPositions.mirrorVertically(stringRepresentation);
            case 2: return GenerateNewStartPositions.swapPair(stringRepresentation);
        }
        return null;
    }

    /**
     * Converts a 3-dimensional Sudoku matrix back into its string representation.
     * The first half of the string contains the initial values, and the second half contains the solution.
     *
     * @param matrix The 3-dimensional Sudoku matrix to convert.
     * @return A string representing the matrix, including both the initial values and the solution.
     */
    static String convertMatrixToString(int[][][] matrix) {
        StringBuilder builder = new StringBuilder();

        // Append initial values
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                builder.append(matrix[row][col][0]);
            }
        }

        // Append solution values
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                builder.append(matrix[row][col][1]);
            }
        }

        return builder.toString();
    }

    // Predefined string representations of easy, medium, and hard Sudoku puzzles and their solutions.
    private static final String easy =
            "000914070" +
                    "010000054" +
                    "040002000" +
                    "007569001" +
                    "401000500" +
                    "300100000" +
                    "039000408" +
                    "650800030" +
                    "000403260" + // Solution values after this substring
                    "583914672" +
                    "712386954" +
                    "946752183" +
                    "827569341" +
                    "461238597" +
                    "395147826" +
                    "239675418" +
                    "654821739" +
                    "178493265";

    private static final String medium =
            "300000010" +
                    "000050906" +
                    "050401200" +
                    "030000080" +
                    "002069400" +
                    "000000002" +
                    "900610000" +
                    "200300058" +
                    "100800090" +
                    "324976815" +
                    "718253946" +
                    "659481273" +
                    "536142789" +
                    "872569431" +
                    "491738562" +
                    "985617324" +
                    "267394158" +
                    "143825697";

    private static final String hard =
            "030600000" +
                    "000010070" +
                    "080000000" +
                    "000020000" +
                    "340000800" +
                    "500030094" +
                    "000400000" +
                    "150800200" +
                    "700006050" +
                    "931687542" +
                    "465219378" +
                    "287345916" +
                    "876924135" +
                    "349561827" +
                    "512738694" +
                    "693452781" +
                    "154873269" +
                    "728196453";
}
