package cr.ac.ucr.ecci.Server;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Used to generate HTTP responses
 */
class ResponseGenerator {
    /**
     * Response headers: Content-Type, Content-Length, Date, Server
     */
    static final String serverName = "ServidorWeb";

    /**
     * Generates a 404 response
     * @return a byte array with the response
     */
    static byte[] generate404() {
        String content = "<html><head><title>Error 404</title></head><body>404 Not Found</body></html>";
        byte[] contentBytes = content.getBytes(Charset.forName("UTF-8"));

        String responseHeaders = "HTTP/1.1 404 Not Found\r\n";
        responseHeaders += generateDate() + "\r\n";
        responseHeaders += "Content-Length: " + contentBytes.length + "\r\n";
        responseHeaders += "Content-Type: text/html; charset=UTF-8\r\n\r\n";
        byte[] responseHeadersBytes = responseHeaders.getBytes(Charset.forName("UTF-8"));

        return combineHeaderResponse(responseHeadersBytes, contentBytes);
    }

    /**
     * Generates a 200 response
     * @return a byte array with the response
     */
    static byte[] generate200(byte[] contentBytes, String contentType) {
        String responseHeaders = "HTTP/1.1 200 OK\r\n";
        responseHeaders += generateDate() + "\r\n";
        responseHeaders += "Server: " + serverName + "\r\n";
        responseHeaders += "Content-Length: " + contentBytes.length + "\r\n";
        responseHeaders += "Content-Type: " + contentType + "\r\n\r\n";
        byte[] responseHeadersBytes = responseHeaders.getBytes(Charset.forName("UTF-8"));

        return combineHeaderResponse(responseHeadersBytes, contentBytes);
    }

    /**
     * Generates a 200 head response (as it is head it doesn't include the body)
     * @return a byte array with the response
     */
    static byte[] generateHEAD200(byte[] content, String contentType) {
        String responseHeaders = "HTTP/1.1 200 OK\r\n";
        responseHeaders += generateDate() + "\r\n";
        responseHeaders += "Server: " + serverName + "\r\n";
        responseHeaders += "Content-Length: " + content.length + "\r\n";
        responseHeaders += "Content-Type: " + contentType + "\r\n\r\n";

        return responseHeaders.getBytes(Charset.forName("UTF-8"));
    }

    /**
     * Generates a 501 response
     * @return a byte array with the response
     */
    static byte[] generate501() {
        String content = "<html><head><title>Error 501</title></head><body>501 Not Implemented</body></html>";
        byte[] contentBytes = content.getBytes(Charset.forName("UTF-8"));

        String responseHeaders = "HTTP/1.1 501 Not Implemented\r\n";
        responseHeaders += generateDate() + "\r\n";
        responseHeaders += "Server: " + serverName + "\r\n";
        responseHeaders += "Content-Length: " + contentBytes.length + "\r\n";
        responseHeaders += "Content-Type: text/html; charset=UTF-8\r\n\r\n";
        byte[] responseHeadersBytes = responseHeaders.getBytes(Charset.forName("UTF-8"));

        return combineHeaderResponse(responseHeadersBytes, contentBytes);
    }

    /**
     * Generates a 506 response
     * @return a byte array with the response
     */
    static byte[] generate406() {
        String content = "<html><head><title>Error 406</title></head><body>406 Not Acceptable</body></html>";
        byte[] contentBytes = content.getBytes(Charset.forName("UTF-8"));

        String responseHeaders = "HTTP/1.1 406 Not Acceptable\r\n";
        responseHeaders += generateDate() + "\r\n";
        responseHeaders += "Server: " + serverName + "\r\n";
        responseHeaders += "Content-Length: " + contentBytes.length + "\r\n";
        responseHeaders += "Content-Type: text/html; charset=UTF-8\r\n\r\n";
        byte[] responseHeadersBytes = responseHeaders.getBytes(Charset.forName("UTF-8"));

        return combineHeaderResponse(responseHeadersBytes, contentBytes);
    }

    /**
     * Combines the header of the response with the content into a single byte array
     * @return a byte array with the response's header and content
     */
    private static byte[] combineHeaderResponse(byte[] responseHeadersBytes, byte[] contentBytes) {
        byte[] combined = new byte[responseHeadersBytes.length + contentBytes.length];

        System.arraycopy(responseHeadersBytes,0,combined,0,responseHeadersBytes.length);
        System.arraycopy(contentBytes,0,combined,responseHeadersBytes.length,contentBytes.length);

        return combined;
    }

    /**
     * Generates a date with a format based in the one returned by Apache
     * @return the formatted date
     */
    private static String generateDate() {
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
        Date date = new Date();
        return "Date: " + dateFormat.format(date);
    }
}
