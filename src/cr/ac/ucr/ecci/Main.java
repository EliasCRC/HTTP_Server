package cr.ac.ucr.ecci;

import cr.ac.ucr.ecci.Server.Request.Request;
import cr.ac.ucr.ecci.Server.Request.RequestParser;

public class Main {

    public static void main(String[] args) {
        HTTP_ServerController http_serverController = new HTTP_ServerController();
        http_serverController.run();
    }

}
