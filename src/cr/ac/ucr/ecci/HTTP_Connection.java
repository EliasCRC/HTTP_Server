package cr.ac.ucr.ecci;

import java.io.*;
import java.net.Socket;

public class HTTP_Connection extends Thread {

    private HTTP_Server httpServer;
    public boolean closed;
    private Socket clientSocket;

    private InputStream in;
    private PrintWriter out;

    public HTTP_Connection (HTTP_Server httpServer, Socket clientSocket) {
        this.httpServer = httpServer;
        this.clientSocket = clientSocket;
    }

    private void listenMessages() throws IOException {

        // Get the readers
        this.in = this.clientSocket.getInputStream();
        this.out = new PrintWriter(this.clientSocket.getOutputStream(), true);
        InputStreamReader isReader = new InputStreamReader(this.in);
        BufferedReader br = new BufferedReader(isReader);

        //code to read and print headers
        StringBuilder httpRequestString = new StringBuilder();
        String headerLine;
        while((headerLine = br.readLine()).length() != 0){
            httpRequestString.append(headerLine).append("\n");
        }

        //code to read the post payload data
        while(br.ready()){
            httpRequestString.append((char) br.read());
        }

        System.out.println(httpRequestString);

        this.out.println("Recibido.");

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
