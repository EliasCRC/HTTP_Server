package cr.ac.ucr.ecci.Server.InputOutput;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileLoader {

    public static byte[] getFile (String filename) {

        File file = new File(filename);

        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            return null;
        }
    }

    public static String getFileExtension(String filename) {
        String[] splitFilename = filename.split("\\.");
        return splitFilename[splitFilename.length - 1];
    }
}
