package cr.ac.ucr.ecci;

public class Request {
    String methodType;
    String host;
    String accept;
    String date;
    String contentType;
    String contentLength;
    String referer;

    public Request(String methodType, String host, String accept, String date, String contentType, String contentLength, String referer) {
        this.methodType = methodType;
        this.host = host;
        this.accept = accept;
        this.date = date;
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.referer = referer;
    }
}
