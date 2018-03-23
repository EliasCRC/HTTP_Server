package cr.ac.ucr.ecci;

import java.io.IOException;
import java.util.Scanner;

public class HTTP_ServerController {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the port: ");
        int port = scanner.nextInt();

        HTTP_Server httpServer = new HTTP_Server(port);
        httpServer.start();

        System.out.println("[Press Enter to Exit]");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        httpServer.kill();
    }

}
