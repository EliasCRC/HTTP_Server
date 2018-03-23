package cr.ac.ucr.ecci;

import java.io.*;
import java.net.Socket;

public class HTTP_Connection extends Thread {

    private HTTP_Server httpServer;
    public boolean closed;
    private Socket clientSocket;

    private DataInputStream in;
    private PrintWriter out;

    public HTTP_Connection (HTTP_Server httpServer, Socket clientSocket) {
        this.httpServer = httpServer;
        this.clientSocket = clientSocket;
    }

    private void listenMessages() throws IOException {

        this.in = new DataInputStream(clientSocket.getInputStream());
        this.out = new PrintWriter(this.clientSocket.getOutputStream(), true);

        String httpRequestString = "";
        byte receivedByte;
        while ((receivedByte = in.readByte()) >= 0) {
            httpRequestString += (char)receivedByte;
            System.out.println(httpRequestString);
            System.out.println((char)receivedByte);
            System.out.println("Picha se mama");
        }
        System.out.println(httpRequestString);

        out.println("Picha se mama");

    }

    synchronized void kill() {

        if (this.in != null) {
            try {
                this.in.close();
            } catch (IOException e) {
                //Do nothing
            }
        }

        if (this.out != null) {
            this.out.close();
        }

        if (this.clientSocket != null) {
            try {
                this.clientSocket.close();
            } catch (IOException e) {
                //Do nothing
            }
        }

        this.closed = true;

    }

    @Override
    public void run() {

        try {
            this.listenMessages();
        } catch (IOException e) {
            //Do Nothing
        }

        this.kill();

    }


}
