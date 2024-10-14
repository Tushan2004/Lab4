package model;

import javafx.scene.control.Alert;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SudokuModel implements Serializable{
    public SudokuCell[][] board;  // 9x9 grid of SudokuCells
    private boolean[][] initialEmptyCells;  // Spårar vilka celler som var tomma vid spelets start
    public SudokuUtilities.SudokuLevel currentLevel; // Nuvarande svårighetsnivå
    int[][][] matrix;

    public SudokuModel() {
        board = new SudokuCell[9][9];
        initialEmptyCells = new boolean[9][9]; // True om cellen var tom från början
        currentLevel = SudokuUtilities.SudokuLevel.EASY;// Standard svårighetsnivå
        matrix = null;
        initializeBoard(currentLevel);
    }

    // Initialiserar brädet från en genererad matris
    public void initializeBoard(SudokuUtilities.SudokuLevel level) {

        switch (level) {
            case EASY:
                matrix = SudokuUtilities.generateSudokuMatrix(SudokuUtilities.SudokuLevel.EASY);
                currentLevel = SudokuUtilities.SudokuLevel.EASY;
                break;  // Avbryt efter att ha tilldelat matris för EASY
            case MEDIUM:
                matrix = SudokuUtilities.generateSudokuMatrix(SudokuUtilities.SudokuLevel.MEDIUM);
                currentLevel = SudokuUtilities.SudokuLevel.MEDIUM;
                break;  // Avbryt efter att ha tilldelat matris för MEDIUM
            case HARD:
                matrix = SudokuUtilities.generateSudokuMatrix(SudokuUtilities.SudokuLevel.HARD);
                currentLevel = SudokuUtilities.SudokuLevel.HARD;
                break;  // Avbryt efter att ha tilldelat matris för HARD
        }


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

    // Kontrollera om en cell kan redigeras (om den var tom vid spelets start)
    public boolean isCellEditable(int row, int col) {
        return initialEmptyCells[row][col];
    }

    // Kontrollera om ett värde finns i samma 3x3-sektion, rad eller kolumn
    public boolean checkFilledNumbers() {
        int value;

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col].getUserValue() != 0) {
                    if (!(board[row][col].isCorrect())) {
                        return false;
                    }
                }
            }
        }
        return true; // Allt är korrekt ifyllt
    }

    // Spara ett oavslutat spel till fil
    public void saveGameToFile(String fileName, SudokuModel model) {
        File file = new File(fileName);
        try {
            SudokuIO.serializeToFile(file, this); // Serialisera modellen till fil
            System.out.println("Game saved successfully to " + file.getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Failed to save game: " + ex.getMessage());
        }
    }

    // Ladda ett sparat spel från fil
    public void loadGame(String filepath) {
        File file = new File(filepath);

        if (file.exists()) {
            try {
                SudokuModel loadedModel = SudokuIO.deSerializeFromFile(file); // Deserialize model
                this.updateFrom(loadedModel); // Update current model's state from loaded model
                //updateNumberTiles(); // Update the UI based on the loaded model
                //showAlert("Game loaded successfully from " + SAVE_FILE + "!", Alert.AlertType.INFORMATION);
            } catch (IOException | ClassNotFoundException ex) {
                //showAlert("Failed to load game: " + ex.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            //showAlert("No saved game found in " + SAVE_FILE + ".", Alert.AlertType.WARNING);
        }
    }


    public void updateFrom(SudokuModel otherModel) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                this.board[row][col].setInitialValue(otherModel.board[row][col].getInitialValue());
                this.board[row][col].setSolutionValue(otherModel.board[row][col].getSolutionValue());
                this.board[row][col].setVisible(otherModel.board[row][col].isVisible());
                this.board[row][col].setUserValue(otherModel.board[row][col].getUserValue()); // Kopiera userValue
            }
        }
        this.currentLevel = otherModel.currentLevel;  // Update the difficulty level
    }


    // Rensar alla celler som var tomma vid spelets start
    public void clearAllEmptyCells() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
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

    // Metoder för att kontrollera om ett värde redan finns i samma 3x3-sektion, rad eller kolumn

    // Ny metod för att kontrollera om ett värde redan finns i 3x3-sektionen
    public void updateCell(int row, int col, int value) {

        if (value == 0) {
            board[row][col].setUserValue(0);
        }

        // Kontrollera om värdet är giltigt (1-9) innan vi fortsätter
        if (value < 1 || value > 9) {
            return; // Om värdet är utanför intervallet, gör ingenting
        }
/*
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
        }*/

        board[row][col].setUserValue(value); // Sätt användarens värde
        //isDone();
    }

    /*
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
    }*/

    // Ny metod för att hämta lösningsvärdet för en viss cell
    public int getSolutionValue(int row, int col) {
        return board[row][col].getSolutionValue();
    }

    // Ny metod för att ställa in svårighetsnivå
    public void setDifficulty(SudokuUtilities.SudokuLevel level) {
        currentLevel = level; // Spara den nya svårighetsnivån
        initializeBoard(level); // Generera en ny spelomgång
    }

    // Ny metod för att få spelregler
    public String getGameRules() {
        return "Spelet går ut på att fylla i siffrorna 1–9 i varje rad, kolumn och 3x3-ruta, " +
                "utan upprepning. Fyll i tomma rutor, och använd hjälpmedel vid behov.";
    }

    // Ny metod för att ge hjälp
    public void provideHint() {
        Random random = new Random();
        List<int[]> emptyCells = new ArrayList<>();

        // Samla alla tomma celler
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col].getDisplayValue() == 0) {
                    emptyCells.add(new int[]{row, col});
                }
            }
        }

        if (!emptyCells.isEmpty()) {
            // Välj en slumpmässig cell
            int[] cell = emptyCells.get(random.nextInt(emptyCells.size()));
            int row = cell[0];
            int col = cell[1];
            int solutionValue = getSolutionValue(row, col);
            updateCell(row, col, solutionValue); // Fyll i cellen med lösningsvärdet
        }
    }

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

    public boolean isDone() {

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (!(board[row][col].isCorrect())) {
                    System.out.println("fel");
                    return false;
                }
            }
        }
        System.out.println("klar");
        return true;
    }
}
