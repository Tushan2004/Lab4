package view;

import javafx.scene.layout.TilePane;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import model.SudokuModel;

public class SudokuView {
    private SudokuModel model;
    private TilePane gameBoard; // TilePane för att hålla spelets celler

    public SudokuView(SudokuModel model) {
        this.model = model;
        this.gameBoard = new TilePane();
        this.gameBoard.setPrefColumns(9);
        this.gameBoard.setMaxWidth(270);  // Använda 9 kolumner för Sudoku-brädet
    }

    // Uppdaterar spelbrädet med aktuell data från modellen
    public void updateBoard() {
        int[][] currentBoard = model.getBoardState(); // Hämta aktuellt bräde från modellen

        gameBoard.getChildren().clear(); // Rensa gamla celler

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                Label cellLabel;

                if (currentBoard[row][col] == 0) {
                    cellLabel = new Label("");  // Tom cell om värdet är 0
                } else {
                    cellLabel = new Label(String.valueOf(currentBoard[row][col])); // Visa siffra
                }

                cellLabel.setPrefWidth(30);
                cellLabel.setPrefHeight(30);
                cellLabel.setAlignment(Pos.CENTER);  // Centrera texten
                cellLabel.setStyle("-fx-border-color: black; -fx-border-width: 1px;");

                gameBoard.getChildren().add(cellLabel); // Lägg till cellen i spelet
            }
        }
    }

    // Returnerar spelets visuella komponent (TilePane)
    public TilePane getGameBoard() {
        return gameBoard;
    }
}
