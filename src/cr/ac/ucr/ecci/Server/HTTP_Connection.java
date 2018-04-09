package cr.ac.ucr.ecci.Server;

import cr.ac.ucr.ecci.Server.InputOutput.FileLoader;
import cr.ac.ucr.ecci.Server.Request.Request;
import cr.ac.ucr.ecci.Server.Request.RequestParser;

import java.io.*;
import java.net.Socket;

public class HTTP_Connection extends Thread {

    private HTTP_Server httpServer;
    public boolean closed;
    private Socket clientSocket;

    private InputStream in;
    private OutputStream out;

    HTTP_Connection (HTTP_Server httpServer, Socket clientSocket) {
        this.httpServer = httpServer;
        this.clientSocket = clientSocket;
    }

    private void listenMessages() throws IOException {

        // Get the readers
        this.in = this.clientSocket.getInputStream();
        this.out = this.clientSocket.getOutputStream();
        InputStreamReader isReader = new InputStreamReader(this.in);
        BufferedReader br = new BufferedReader(isReader);

        //Read the headers of the request
        StringBuilder httpRequestString = new StringBuilder();
        String headerLine;
        while((headerLine = br.readLine()).length() != 0){
            httpRequestString.append(headerLine).append("\n");
        }

        //Read the body of the request, if any
        while(br.ready()){
            httpRequestString.append((char) br.read());
        }

        System.out.println("Request received:");
        System.out.println(httpRequestString);

        byte[] response = this.handleRequest(httpRequestString.toString());
        System.out.println("Response sent:");
        System.out.println(new String(response));

        this.out.write(response);

    }

    private byte[] handleRequest(String request) {
        final String POST = "POST";
        final String GET = "GET";
        final String HEAD = "HEAD";
        byte[] response = new byte[1];
        byte[] file;

        Request httpRequest = RequestParser.parseRequest(request);
        System.out.println("Parsed Request:");
        httpRequest.print();
        System.out.println();

        if (httpRequest.methodType.equals(POST) || httpRequest.methodType.equals(GET)
                || httpRequest.methodType.equals(HEAD)) {

            file = FileLoader.getFile(httpRequest.requestedResource);

            if (file == null) {
                response = ResponseGenerator.generate404();
            } else {
                String fileExtension = FileLoader.getFileExtension(httpRequest.requestedResource);
                if (httpRequest.methodType.equals(POST)) {
                    //response = ResponseGenerator.generate200(file, this.httpServer.getMimeType(fileExtension));
                } else {

                    String mimeType = httpRequest.accept;
                    String mimeTypeExtension = httpServer.getExtension(mimeType);
                    System.out.println(mimeType + " " + mimeTypeExtension);
                    if( mimeType != null && (mimeType.equals("*/*") ||
                            (mimeTypeExtension != null && mimeTypeExtension.equals(fileExtension)) )) {

                        if (httpRequest.methodType.equals(HEAD)) {
                            //response = ResponseGenerator.generateHEAD200(file, this.httpServer.getMimeType(fileExtension));
                        } else {
                            //response = ResponseGenerator.generate200(file, this.httpServer.getMimeType(fileExtension));
                        }

                    } else {
                        //response = ResponseGenerator.generate406();
                    }

                }

            }
        } else {
            //response = ResponseGenerator.generate501();
        }

        return response;
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
