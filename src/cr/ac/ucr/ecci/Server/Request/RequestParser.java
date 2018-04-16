package cr.ac.ucr.ecci.Server.Request;

/**
 * Class used to parse request in the form of Strings to Request a object
 */
public class RequestParser {
    private static String[] requestHeaders = {"Host", "Accept", "Date", "Content-Type", "Content-Length", "Referer"};

    /**
     * Parses a request to create a Request object with that request information
     * @param requestString String with the unparsed request information
     * @return The parsed request
     */
    public static Request parseRequest(String requestString) {
        String[] requestLines = requestString.split("\n");
        String methodType = requestLines[0].split(" ")[0];
        String requestedResource = requestLines[0].split(" ")[1];
        requestedResource = requestedResource.equals("/") ? "index.html" : requestedResource.substring(1);
        String[] headers = getHeaders(requestLines);
        String body = "";

        if (hasBody(requestString)) {
            body = requestLines[requestLines.length-1];
        }

        return new Request(methodType, headers[0], headers[1], headers[2], headers[3], headers[4] == null? 0 : Integer.parseInt(headers[4]), headers[5], requestedResource, body);
    }

    /**
     * Gets the content of a particular header from a request that is being parsed
     * @param headerName the name of the request header (example: Content-Type)
     * @param requestLines the lines of the request
     * @return the respective header if any.
     */
    private static String getHeader(String headerName, String requestLines[]) {
        for (String requestLine : requestLines) {
            if (requestLine.startsWith(headerName)) {
                return requestLine.split(" ")[1];
            }
        }

        return null; // if it didn't find the header it returns null
    }

    /**
     * Uses the getHeader method to get the content of all the supported request headers
     * @param requestLines array where each element is a line of the request
     * @return the content of the supported request headers
     */
    private static String[] getHeaders(String[] requestLines) {
        String headers[] = new String[7];
        for (int i = 0; i < requestHeaders.length; i++) {
            headers[i] = getHeader(requestHeaders[i], requestLines);
        }
        return headers;
    }

    /**
     * Finds out if the request has a body
     * @param requestString the request string to check if it has a body
     * @return true if it has one, false if not
     */
    private static boolean hasBody(String requestString) {
        int indexOfDoubleBreak = requestString.indexOf("\n\n");
        return requestString.length() > (indexOfDoubleBreak+2);
    }
}
