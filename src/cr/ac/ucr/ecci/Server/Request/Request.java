package cr.ac.ucr.ecci.Server.Request;

/**
 * Class that contains information of an HTTP Request
 */
public class Request {
    public String methodType;
    public String host;
    private String accept;
    public String date;
    public String contentType;
    public int contentLength;
    public String referer;
    public String requestedResource;
    public String body;

    /**
     * Constructor method for request, fills the different fields of the request
     * @param methodType The method type of the request
     * @param host The content of the Host header of the request
     * @param accept The content of the Accept header of the request
     * @param date The content of the Date header of the request
     * @param contentType The content of the Content-Type header of the request
     * @param contentLength The content of the Content-Length header of the request
     * @param referer The content of the Referer header of the request
     * @param requestedResource The resource that is being requested
     * @param body The body of the request
     */
    Request(String methodType, String host, String accept, String date, String contentType, int contentLength, String referer, String requestedResource, String body) {
        this.methodType = methodType;
        this.host = host;
        this.accept = accept;
        this.date = date;
        this.contentType = contentType;
        this.requestedResource = requestedResource;
        this.contentLength = contentLength;
        this.referer = referer;
        this.body = body;
    }

    /**
     * Prints certain contents of the request, used for debugging
     */
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

    /**
     * Returns the most relevant type in the accept header
     * @return null if there is no accept header, all if it accepts all types, otherwise the first type accepted
     */
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
