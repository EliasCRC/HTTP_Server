package cr.ac.ucr.ecci.Server.Request;

public class Request {
    public String methodType;
    public String host;
    private String accept;
    public String date;
    public String contentType;
    public int contentLength;
    public String referer;
    public String requestedResource;

    public Request(String methodType, String host, String accept, String date, String contentType, int contentLength, String referer, String requestedResource) {
        this.methodType = methodType;
        this.host = host;
        this.accept = accept;
        this.date = date;
        this.contentType = contentType;
        this.requestedResource = requestedResource;
        this.contentLength = contentLength;
        this.referer = referer;
    }

    public void print() {
        System.out.println("Method Type: " + this.methodType);
        System.out.println("Requested resource: " + this.requestedResource);
        System.out.println("Host: " + this.host);
        System.out.println("Accept: " + this.accept);
        System.out.println("Date: " + this.date);
        System.out.println("Content Type: " + this.contentType);
        System.out.println("Content Length: " + this.contentLength);
        System.out.println("Referer: " + this.referer);
    }

    public String getAccept() {
        if (this.accept != null) {
            String acceptString = this.accept.replace(",", " ");
            String[] accepts = acceptString.split(" ");
            for (String accept : accepts) {
                if (accept.contains("*/*")) {
                    return("*/*");
                }
            }
            return accepts[0];
        } else {
            return null;
        }
    }
}
