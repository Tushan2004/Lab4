package model;
import java.io.Serializable;

public class SudokuCell implements Serializable{
    private int initialValue;  // Värde från början (kan vara 0 för tom ruta)
    private int solutionValue;  // Det rätta värdet
    private boolean isVisible;  // Ska initialvärdet visas?
    private int userValue;  // Användarens val

    public SudokuCell(int initialValue, int solutionValue, boolean isVisible) {
        this.initialValue = initialValue;
        this.solutionValue = solutionValue;
        this.isVisible = isVisible;
        this.userValue = 0;  // Standardvärdet när inget är valt
    }

    // Hämta värdet som ska visas i vyn (antingen användarens inmatning eller initialvärdet)
    public int getDisplayValue() {
        return isVisible ? initialValue : userValue;
    }

    // Sätt användarens inmatning
    public void setUserValue(int value) {
        this.userValue = value;
    }

    public void setInitialValue(int initialValue) {
        this.initialValue = initialValue;
    }

    // Ny metod för att hämta användarens inmatning
    public int getUserValue() {
        return userValue;
    }

    // Kontrollera om användarens inmatning är korrekt
    public boolean isCorrect() {
        if (userValue == 0) {
            return initialValue == solutionValue;
        }
        return userValue == solutionValue;
    }

    public int getSolutionValue() {
        return solutionValue;
    }

    public void setSolutionValue(int solutionValue) {
        this.solutionValue = solutionValue;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public int getInitialValue() {
        return initialValue;
    }

    public boolean isVisible() {
        return isVisible;
    }
}
