package model;

import java.io.Serializable;

/**
 * The SudokuCell class represents an individual cell in a Sudoku puzzle.
 * Each cell has an initial value, a solution value, a visibility flag,
 * and a user-provided value that can be set during gameplay.
 */
public class SudokuCell implements Serializable {

    private int initialValue;  // The initial value of the cell (0 if the cell is empty)
    private int solutionValue;  // The correct solution value of the cell
    private boolean isVisible;  // Whether the initial value should be displayed
    private int userValue;  // The value input by the user

    /**
     * Constructs a SudokuCell with a specified initial value, solution value, and visibility.
     *
     * @param initialValue   the initial value of the cell, or 0 if the cell is empty
     * @param solutionValue  the correct solution value for the cell
     * @param isVisible      whether the initial value should be displayed to the user
     */
    public SudokuCell(int initialValue, int solutionValue, boolean isVisible) {
        this.initialValue = initialValue;
        this.solutionValue = solutionValue;
        this.isVisible = isVisible;
        this.userValue = 0;  // Default user value is 0 when no input has been provided
    }

    /**
     * Retrieves the display value for the cell.
     * If the initial value is visible, it will be displayed; otherwise, the user input value is displayed.
     *
     * @return the value to display in the UI, either the initial or user value
     */
    public int getDisplayValue() {
        return isVisible ? initialValue : userValue;
    }

    /**
     * Sets the value input by the user.
     *
     * @param value the user-provided value for the cell
     */
    public void setUserValue(int value) {
        this.userValue = value;
    }

    /**
     * Sets the initial value of the cell.
     *
     * @param initialValue the initial value to set
     */
    public void setInitialValue(int initialValue) {
        this.initialValue = initialValue;
    }

    /**
     * Retrieves the user input value for the cell.
     *
     * @return the value provided by the user
     */
    public int getUserValue() {
        return userValue;
    }

    /**
     * Checks whether the user's input matches the correct solution value.
     * If no user input is provided (i.e., userValue is 0), it checks if the initial value matches the solution.
     *
     * @return true if the user's input (or initial value) matches the solution; false otherwise
     */
    public boolean isCorrect() {
        if (userValue == 0) {
            return initialValue == solutionValue;
        }
        return userValue == solutionValue;
    }

    /**
     * Retrieves the correct solution value for the cell.
     *
     * @return the solution value of the cell
     */
    public int getSolutionValue() {
        return solutionValue;
    }

    /**
     * Sets the correct solution value for the cell.
     *
     * @param solutionValue the correct solution value to set
     */
    public void setSolutionValue(int solutionValue) {
        this.solutionValue = solutionValue;
    }

    /**
     * Sets whether the initial value should be visible to the user.
     *
     * @param visible true if the initial value should be visible; false otherwise
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    /**
     * Retrieves the initial value of the cell.
     *
     * @return the initial value of the cell
     */
    public int getInitialValue() {
        return initialValue;
    }

    /**
     * Checks if the initial value of the cell is visible to the user.
     *
     * @return true if the initial value is visible; false otherwise
     */
    public boolean isVisible() {
        return isVisible;
    }
}
