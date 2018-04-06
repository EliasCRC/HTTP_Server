package cr.ac.ucr.ecci.Server.Request;

public class Request {
    String methodType;
    String host;
    String accept;
    String date;
    String contentType;
    int contentLength;
    String referer;

    public Request(String methodType, String host, String accept, String date, String contentType, int contentLength, String referer) {
        this.methodType = methodType;
        this.host = host;
        this.accept = accept;
        this.date = date;
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.referer = referer;
    }

    public void print() {
        System.out.println("Method Type: " + this.methodType);
        System.out.println("Host: " + this.host);
        System.out.println("Accept: " + this.accept);
        System.out.println("Date: " + this.date);
        System.out.println("Content Type: " + this.contentType);
        System.out.println("Content Length: " + this.contentLength);
        System.out.println("Referer: " + this.referer);
    }
}
