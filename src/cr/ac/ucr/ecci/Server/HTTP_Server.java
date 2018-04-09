package cr.ac.ucr.ecci.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class HTTP_Server extends Thread {

    private ServerSocket serverSocket;
    private boolean keepListening;
    private MimeTypeMapper mimeTypes;
    private ArrayList<HTTP_Connection> serverConnections;

    public HTTP_Server(int port) {
        this.serverConnections = new ArrayList<>();
        this.mimeTypes = new MimeTypeMapper();

        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void listenConnections() {

        while (this.keepListening) {
            try {
                Socket clientSocket = this.serverSocket.accept();
                HTTP_Connection serverConnection = new HTTP_Connection(this, clientSocket);
                serverConnection.start();
            } catch (Exception e) {
                //Do Nothing
            }
        }

    }

    public String getExtension(String mimeType) {
        return this.mimeTypes.getExtension(mimeType);
    }

    public String getMimeType(String ext) {
        return this.mimeTypes.getMimeType(ext);
    }

    synchronized void kill() {

        for (HTTP_Connection connection : this.serverConnections) {
            if (!connection.closed) {
                connection.kill();
                connection.interrupt();
            }
        }

        this.keepListening = false;
        boolean closed = false;
        while (!closed) {
            try {
                this.serverSocket.close();
                closed = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void run(){
        this.keepListening = true;
        this.listenConnections();
    }

}
