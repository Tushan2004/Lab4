package view;

import model.*;

public class SudokuController {

    private SudokuModel model; // Referens till modellen (där datalogiken finns)
    private SudokuView view;   // Referens till vyn (där UI-hantering sker)

    public SudokuController(SudokuModel model, SudokuView view) {
        this.model = model;
        this.view = view;
    }

    public void generateNewGame() {

    }

    // 1. Välja svårighetsnivå (lätt, medel eller svår) och generera en ny spelomgång
    public void chooseDifficulty(SudokuUtilities.SudokuLevel level) {
        // Implementera för att välja svårighetsnivå och generera ett nytt spel
    }

    // 2. Spara ett oavslutat spel till fil, med val för placering och filnamn
    public void saveGameToFile(String filePath) {
        // Implementera för att spara ett spel till fil
    }

    // 3. Välja en sparad fil och öppna spelet
    public void loadGameFromFile(String filePath) {
        // Implementera för att öppna ett sparat spel från fil
    }

    // 4. Fylla i en siffra, 1-9, i en ruta (som från början var tom)
    public void fillCell(int row, int col, int number) {
        // Implementera för att fylla i en ruta med en siffra
    }

    // 5. Tömma en ruta (som från början var tom)
    public void clearCell(int row, int col) {
        // Implementera för att tömma en ruta
    }

    // 6. Rensa alla rutor (som från början var tomma)
    public void clearAllEmptyCells() {
        model.clearAllEmptyCells();  // Anropa modellens metod för att rensa tomma rutor
        view.updateBoard();          // Uppdatera spelbrädet i vyn för att visa förändringarna
    }

    // 7. Kontrollera om hittills ifyllda siffror är korrekta
    public boolean checkFilledNumbers() {
        // Implementera för att kontrollera ifyllda siffror
        return false; // Placeholder, returnera korrekt resultat senare
    }

    // 8. Få kortfattad information om spelregler
    public String getGameRules() {
        // Implementera för att returnera kortfattad spelinformation
        return ""; // Placeholder, returnera regler senare
    }

    // 9. Få hjälp genom att en slumpvis vald ruta fylls i
    public void getHint() {
        // Implementera för att ge en ledtråd genom att fylla i en slumpvis ruta
    }
}
