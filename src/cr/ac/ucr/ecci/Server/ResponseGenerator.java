package cr.ac.ucr.ecci.Server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResponseGenerator {
    //Response headers: Content-Type, Content-Length, Date, Server

    public static String generate404() {
        String response = "HTTP/1.1 404 Not Found\n";
        response += generateDate() + "\n";
        response += "Content-Type: text/html; charset=UTF-8\n\n";
        response += "<html><head><title>Prueba</title></head><body>404</body></html>\n";
        return response;
    }
    public static String generate200(byte[] content) {
        return "200\n";
    }

    public static String generateHEAD200() {
        return "200 Head\n";
    }

    public static String generate501() {
        return "501\n";
    }

    public static String generate406() {
        return "406\n";
    }
  
    private static String generateDate() {
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
