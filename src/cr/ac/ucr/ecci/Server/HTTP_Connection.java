package cr.ac.ucr.ecci.Server;

import cr.ac.ucr.ecci.Server.InputOutput.FileLoader;
import cr.ac.ucr.ecci.Server.Request.Request;
import cr.ac.ucr.ecci.Server.Request.RequestParser;

import java.io.*;
import java.net.Socket;

/**
 * Class in charge of handling HTTP requests.
 */
public class HTTP_Connection extends Thread {

    private HTTP_Server httpServer;
    private Socket clientSocket;

    private InputStream in;
    private OutputStream out;

    /**
     * Constructor of the class
     * @param httpServer the server that dispatched the connection
     * @param clientSocket the connection socket with the client
     */
    HTTP_Connection (HTTP_Server httpServer, Socket clientSocket) {
        this.httpServer = httpServer;
        this.clientSocket = clientSocket;
    }

    /**
     * Listens to the socket and receives the HTTP request, once handled, responds to the client
     * @throws IOException if the in and out readers cannot be recovered
     */
    private void manageRequest() throws IOException {

        // Get the readers
        this.in = this.clientSocket.getInputStream();
        this.out = this.clientSocket.getOutputStream();
        InputStreamReader isReader = new InputStreamReader(this.in);
        BufferedReader br = new BufferedReader(isReader);

        // Read the headers of the request
        StringBuilder httpRequestString = new StringBuilder();
        String headerLine;
        while((headerLine = br.readLine()).length() != 0){
            httpRequestString.append(headerLine).append("\n");
        }

        httpRequestString.append("\n"); // To follow the double line break format

        // Read the body of the request, if any
        while(br.ready()){
            httpRequestString.append((char) br.read());
        }

        // Respond to the request
        byte[] response = this.handleRequestFlow(httpRequestString.toString());
        this.out.write(response);

    }

    /**
     * Handles the flow of a request, mostly checking for HTTP errors
     * @param request the HTTP request
     * @return the appropriate HTTP response
     */
    private byte[] handleRequestFlow (String request) {
        final String POST = "POST";
        final String GET = "GET";
        final String HEAD = "HEAD";
        byte[] response;
        byte[] file;

        // Parse and log the request
        Request httpRequest = RequestParser.parseRequest(request);
        this.httpServer.writeToLog(httpRequest.methodType, httpRequest.referer, "/" + httpRequest.requestedResource, httpRequest.body);

        // Check if the method is recognizable
        if (httpRequest.methodType.equals(POST) || httpRequest.methodType.equals(GET)
                || httpRequest.methodType.equals(HEAD)) {

            file = FileLoader.getFile(httpRequest.requestedResource);

            if (file == null) {
                response = ResponseGenerator.generate404();
            } else {

                String fileExtension = FileLoader.getFileExtension(httpRequest.requestedResource);

                // If the method is a POST and the resource exists, just return a 200
                if (httpRequest.methodType.equals(POST)) {
                    response = ResponseGenerator.generate200(file, this.httpServer.getMimeType(fileExtension));
                } else {

                    // Gets the MimeTypes and the associated extension
                    String mimeType = httpRequest.getAccept();
                    String mimeTypeExtension = httpServer.getExtension(mimeType);
                    // Check if the mime type accepts anything, if not check if the file extension and mime type extension match.
                    if( mimeType != null && (mimeType.equals("*/*") ||
                            (mimeTypeExtension != null && mimeTypeExtension.equals(fileExtension)) )) {

                        if (httpRequest.methodType.equals(HEAD)) {
                            response = ResponseGenerator.generateHEAD200(file, this.httpServer.getMimeType(fileExtension));
                        } else {
                            response = ResponseGenerator.generate200(file, this.httpServer.getMimeType(fileExtension));
                        }

                    } else {
                        response = ResponseGenerator.generate406();
                    }

                }

            }
        } else {
            response = ResponseGenerator.generate501();
        }

        return response;
    }

    /**
     * If the connection closes, close everything
     */
    private void close() {

        if (this.in != null) {
            try {
                this.in.close();
            } catch (IOException e) {
                //Do nothing
            }
        }

        if (this.out != null) {
            try {
                this.out.close();
            } catch (IOException e) {
                //Do nothing
            }
        }

        if (this.clientSocket != null) {
            try {
                this.clientSocket.close();
            } catch (IOException e) {
                //Do nothing
            }
        }

    }

    /**
     * Run method for the thread
     */
    @Override
    public void run() {

        try {
            this.manageRequest();
        } catch (IOException e) {
            //Do Nothing
        }

        this.close();
    }


}
