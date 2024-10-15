package model;

import model.*;
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
    public static void serializeToFile(File file, SudokuModel data) throws IOException {
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
    public static SudokuModel deSerializeFromFile(File file) throws IOException, ClassNotFoundException {
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
     * Private constructor to prevent instantiation of the SudokuIO class.
     * This class contains only static methods.
     */
    private SudokuIO() {
    }
}
