package cr.ac.ucr.ecci.Server;

import cr.ac.ucr.ecci.Server.InputOutput.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class in charge of listening the socket for requests and dispatching connections
 */
public class HTTP_Server extends Thread {

    private ServerSocket serverSocket;
    private boolean keepListening;
    private MimeTypeMapper mimeTypes;
    private Logger logger;

    /**
     * Constructor of the class
     * @param port the port to listen to
     */
    public HTTP_Server(int port) {
        this.mimeTypes = new MimeTypeMapper();
        this.logger = new Logger();

        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Listening cycle, every time a request comes, a connection is dispatched.
     */
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

    /**
     * Gets the file extension of a MimeType
     * @param mimeType the mime type
     * @return the associated extension
     */
    String getExtension(String mimeType) {
        return this.mimeTypes.getExtension(mimeType);
    }

    /**
     * Gets the MimeType of a file extension
     * @param ext the file extension
     * @return the associated mime type
     */
    String getMimeType(String ext) {
        return this.mimeTypes.getMimeType(ext);
    }

    /**
     * Method for connections to write to the log
     * @param method the method of the HTTP request
     * @param referer the name of the referer
     * @param url the name of the url requested
     * @param data the body of the request
     */
    synchronized void writeToLog(String method, String referer, String url, String data) {
        this.logger.registerRequest(method, ResponseGenerator.serverName, referer, url, data);
    }

    /**
     * Indicates the server to stop listening to requests and close everything
     */
    synchronized void closeServer() {

        this.logger.close();

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

    /**
     * Run method for the thread
     */
    @Override
    public void run(){
        this.keepListening = true;
        this.listenConnections();
    }

}
