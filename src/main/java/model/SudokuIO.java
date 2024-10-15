package model;

import java.io.*;

/**
 * The SudokuIO class provides utility methods for serializing and deserializing SudokuModel objects to and from files.
 * It includes methods for saving the model data to a file and loading it back from a file.
 * This class cannot be instantiated.
 */
public class SudokuIO {

    /**
     * Serializes a SudokuModel object to a file.
     *
     * @param file the file where the SudokuModel object will be saved
     * @param data the SudokuModel object to serialize
     * @throws IOException if an I/O error occurs while writing to the file
     */
    static void serializeToFile(File file, SudokuModel data) throws IOException {
        ObjectOutputStream oos = null; // Create a reference for ObjectOutputStream
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(data);  // Serialize the SudokuModel object
        } finally {
            if (oos != null) {
                oos.close();  // Ensure the stream is closed to avoid resource leaks
            }
        }
    }

    /**
     * Deserializes a SudokuModel object from a file.
     *
     * @param file the file from which the SudokuModel object will be loaded
     * @return the deserialized SudokuModel object
     * @throws IOException if an I/O error occurs while reading from the file
     * @throws ClassNotFoundException if the class of the serialized object cannot be found
     */
    @SuppressWarnings("unchecked")
    static SudokuModel deSerializeFromFile(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = null; // Create a reference for ObjectInputStream
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            return (SudokuModel) ois.readObject();  // Deserialize and return the SudokuModel object
        } finally {
            if (ois != null) {
                ois.close();  // Ensure the stream is closed to avoid resource leaks
            }
        }
    }

    /**
     * Saves the current state of the Sudoku game to a specified file.
     *
     * @param fileName the name of the file to save the game to
     * @param model    the current Sudoku model to serialize
     * @throws IOException if an I/O error occurs during saving
     */
    public static void saveGameToFile(String fileName, SudokuModel model) throws IOException {
        File file = new File(fileName);
        serializeToFile(file, model);  // Serialize the model to the file
        System.out.println("Game saved successfully to " + file.getAbsolutePath());
    }

    /**
     * Loads a previously saved game from a file.
     *
     * @param filepath the path of the file to load the game from
     * @throws IOException            if an I/O error occurs during loading
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    public static void loadGameFromFile(String filepath, SudokuModel model) throws IOException, ClassNotFoundException {
        File file = new File(filepath);
        if (file.exists()) {
            try {
                SudokuModel loadedModel = SudokuIO.deSerializeFromFile(file);  // Deserialize model
                model.updateBoardFromFile(loadedModel);  // Update current model's state from loaded model
            } catch (IOException | ClassNotFoundException ex) {
                // Handle exception
            }
        } else {
            // Handle case where no saved game is found
            throw new IOException("No saved game found at " + filepath);
        }
    }

    /**
     * Private constructor to prevent instantiation of the SudokuIO class.
     * This class contains only static methods.
     */
    private SudokuIO() {
    }
}
