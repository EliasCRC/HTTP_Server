package cr.ac.ucr.ecci.Server.InputOutput;

public class FileLoader {

    public static byte[] getFile (String filename) {
        return null;
    }

    public static String getFileExtension(String filename) {
        String[] splitFilename = filename.split("\\.");
        return splitFilename[splitFilename.length - 1];
    }
}
