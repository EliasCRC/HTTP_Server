package cr.ac.ucr.ecci.Server.InputOutput;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

/**
 * Class in charge of registering the requests that the server receives
 */
public class Logger {

    /**
     * The print writer is linked to the log file and writes the lines to it.
     */
    private PrintWriter log;

    /**
     * Constructor of the log, initializes the log and appends the first HTML lines
     */
    public Logger () {
        try {
            this.log = new PrintWriter("log_output/log.html", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.err.println("Error opening the log file.");
        }

        this.log.println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\">\n" +
                "<link rel=\"stylesheet\" href=\"styles.css\">\n" +
                "<title></title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Bitácora del servidor HTTP</h1>\n" +
                "<table>\n" +
                "<thead>\n" +
                "<tr>\n" +
                "<th>Método</th>\n" +
                "<th>Estampilla de tiempo</th>\n" +
                "<th>Servidor</th>\n" +
                "<th>Refiere</th>\n" +
                "<th>URL</th>\n" +
                "<th>Datos</th>\n" +
                "</tr>\n" +
                "</thead>\n" +
                "<tbody>");
    }

    /**
     * Writes a request to the log
     * @param method the method of the HTTP request
     * @param server the name of the server
     * @param referer the name of the referer
     * @param url the name of the url requested
     * @param data the body of the request
     */
    public void registerRequest(String method, String server, String referer, String url, String data) {
        this.log.println("<tr>");

        this.log.println("<td>" + method + "</td>");
        this.log.println("<td>" + new Timestamp(System.currentTimeMillis()).getTime() + "</td>");
        this.log.println("<td>" + server + "</td>");
        this.log.println("<td>" + (referer == null? "" : referer) + "</td>");
        this.log.println("<td>" + url + "</td>");
        this.log.println("<td>" + (data == null? "" : data) + "</td>");

        this.log.println("</tr>");
    }

    /**
     * Writes the last HTML lines and closes the log.
     */
    public void close() {
        this.log.println("</tbody>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>");
        this.log.close();
    }
}
