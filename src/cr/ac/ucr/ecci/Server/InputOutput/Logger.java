package cr.ac.ucr.ecci.Server.InputOutput;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

public class Logger {

    private PrintWriter log;

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
                "<link rel=\"stylesheet\" href=\"style.css\">\n" +
                "<title></title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<table>\n" +
                "<thead>\n" +
                "<th>Método</th>\n" +
                "<th>Estampilla de tiempo</th>\n" +
                "<th>Servidor</th>\n" +
                "<th>Refiere</th>\n" +
                "<th>URL</th>\n" +
                "<th>Datos</th>\n" +
                "</thead>\n" +
                "<tbody>");
    }

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

    public void close() {
        this.log.println("</tbody>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>");
        this.log.close();
    }
}
