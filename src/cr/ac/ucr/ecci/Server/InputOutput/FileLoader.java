package cr.ac.ucr.ecci.Server.InputOutput;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileLoader {

    //TODO Hacer que haga esto pero para más tipo de media
    public static byte[] getFile (String filename) {
        if (filename.contains(".png")) {

            try {
                BufferedImage img = ImageIO.read(new File(filename));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(img, "png", baos);
                baos.flush();
                byte[] imageInByte = baos.toByteArray();
                baos.close();
                return imageInByte;
            } catch (IOException e) {
                return null;
            }

        } else {

            File file = new File(filename);

            try {
                return Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                return null;
            }
        }
    }

    public static String getFileExtension(String filename) {
        String[] splitFilename = filename.split("\\.");
        return splitFilename[splitFilename.length - 1];
    }
}
