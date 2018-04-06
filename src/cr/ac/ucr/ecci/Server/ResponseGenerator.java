package cr.ac.ucr.ecci.Server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResponseGenerator {
    //Response headers: Content-Type, Content-Length, Date, Server
    static final String serverName = "ServidorWeb"

    public static String generate404() {
        String response = "HTTP/1.1 404 Not Found\r\n";
        response += generateDate() + "\r\n";
        response += "Content-Type: text/html; charset=UTF-8\r\n\r\n";
        response += "<html><head><title>Error 404</title></head><body>404 Not Found</body></html>";
        return response;
    }
    public static String generate200(byte[] content, String contentType) {
        String contentString = new String(content);

        String response = "HTTP/1.1 200 OK\r\n";
        response += generateDate() + "\r\n";
        response +=  "Server: " + serverName + "\r\n";
        response += "Content-Length: " + content.length + "\r\n";
        response += "Content-Type: " + contentType + "\r\n\r\n";
        response += content;

        return response;
    }

    public static String generateHEAD200(byte[] content, String contentType) {
        String response = "HTTP/1.1 200 OK\r\n";
        response += generateDate() + "\n";
        response +=  "Server: " + serverName + "\r\n";
        response += "Content-Length: " + content.length + "\r\n";
        response += "Content-Type: " + contentType + "\r\n\r\n";

        return response;
    }

    public static String generate501() {
        String response = "HTTP/1.1 501 Not Implemented\r\n";
        response += generateDate() + "\n";
        response +=  "Server: " + serverName + "\r\n";
        response += "Content-Type: text/html; charset=UTF-8\r\n\r\n";
        response += "<html><head><title>Error 501</title></head><body>404 Not Implemented</body></html>";

        return response;
    }

    public static String generate406() {
        String response = "HTTP/1.1 406 Not Acceptable\r\n";
        response += generateDate() + "\n";
        response +=  "Server: " + serverName + "\r\n";
        response += "Content-Type: text/html; charset=UTF-8\r\n\r\n";
        response += "<html><head><title>Error 406</title></head><body>406 Not Acceptable</body></html>";

        return response;
    }
  
    private static String generateDate() {
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
        Date date = new Date();
        return "Date: " + dateFormat.format(date);
    }
}
