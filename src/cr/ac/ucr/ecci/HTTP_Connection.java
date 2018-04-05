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

        StringBuilder httpRequestString = new StringBuilder();
        byte receivedByte;
        while ((receivedByte = in.readByte()) >= 0) {
            httpRequestString.append((char) receivedByte);
            if (this.checkCompleteMsg(httpRequestString.toString())) {
                break;
            }

        }

        System.out.println(httpRequestString);
        out.println("Picha se mama");

    }

    private boolean checkCompleteMsg(String msg) {
        if (msg.contains("POST")) {
            // Seek Content Length


            // If it has get to the \r\n\r\n

            // See if all content was sent
            return false;
        } else {
            return msg.contains("\r\n\r\n");
        }
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
