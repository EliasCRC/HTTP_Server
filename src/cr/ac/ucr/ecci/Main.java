package cr.ac.ucr.ecci;

import cr.ac.ucr.ecci.Server.Request.Request;
import cr.ac.ucr.ecci.Server.Request.RequestParser;

public class Main {

    public static void main(String[] args) {
        // write your code here

                Request request = RequestParser.parseRequest("POST / HTTP/1.1\n" +
                                "Host: localhost:90\n" +
                               "User-Agent: curl/7.59.0\n" +
                               "Accept: */*\n" +
                              "Content-Length: 18\n" +
                        "Content-Type: application/x-www-form-urlencoded");
                request.print();
    }

}
