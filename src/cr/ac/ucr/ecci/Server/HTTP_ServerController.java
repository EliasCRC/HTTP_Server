package cr.ac.ucr.ecci.Server;

import java.io.IOException;
import java.util.Scanner;

/**
 * Class that controls the flow of the HTTP Server
 */
public class HTTP_ServerController {

    /**
     * Executes the HTTP Server flow
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        int port = 0;
        boolean okPort = false;

        while (!okPort) {
            System.out.print("Enter the port: ");
            if (scanner.hasNextInt()) {
                port = scanner.nextInt();
                okPort = true;
            } else {
                System.out.println("Invalid port!");
                scanner.next();
                System.out.println();
            }
        }

        HTTP_Server httpServer = new HTTP_Server(port);
        httpServer.start();

        System.out.println("[Press Double Enter to Exit]");
        try {
            //Dummy read
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        httpServer.closeServer();
    }

}
