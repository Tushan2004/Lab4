package model;
import model.*;

import java.io.*;
import java.util.List;

public class SudokuIO {

    // Serialisera projekt till fil
    public static void serializeToFile(File file, SudokuModel data) throws IOException {
        ObjectOutputStream oos = null; // Skapa en referens för ObjectOutputStream
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(data);  // Serialisera listan med projekt
        } finally {
            if (oos != null) {
                oos.close();
            }
        }
    }

    // Deserialisera projekt från fil
    @SuppressWarnings("unchecked")
    public static SudokuModel deSerializeFromFile(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = null; // Skapa en referens för ObjectInputStream
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            return (SudokuModel) ois.readObject();  // Deserialisera och returnera listan
        } finally {
            if (ois != null) {
                ois.close();
            }
        }
    }

    // Privat konstruktor för att förhindra instansering
    private SudokuIO() {
    }
}

