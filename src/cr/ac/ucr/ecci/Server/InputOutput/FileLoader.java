package cr.ac.ucr.ecci.Server.InputOutput;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Class used for the interaction with files used by the web server
 */
public class FileLoader {

    /**
     * Converts a file to an array of bytes
     * @param filename the file's name
     * @return a byte array with the file's bytes, null if the file doesn't exist
     */
    public static byte[] getFile (String filename) {

        File file = new File(filename);

        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Gives the file extension of a file
     * @param filename the file's name
     * @return the extension of the file
     */
    public static String getFileExtension(String filename) {
        String[] splitFilename = filename.split("\\.");
        return splitFilename[splitFilename.length - 1];
    }
}
