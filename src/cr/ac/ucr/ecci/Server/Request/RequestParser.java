package cr.ac.ucr.ecci.Server.Request;

public class RequestParser {
    private static String[] requestHeaders = {"Host", "Accept", "Date", "Content-Type", "Content-Length", "Referer"};

    //TODO Mejorar la forma en que recibe los requests del browser
    public static Request parseRequest(String requestString) {
        String[] requestLines = requestString.split("\n");
        String methodType = requestLines[0].split(" ")[0];
        String requestedResource = requestLines[0].split(" ")[1];
        requestedResource = requestedResource.equals("/") ? "index.html" : requestedResource.substring(1);
        String[] headers = getHeaders(requestLines);
        String body = "";

        if (!isAcceptedHeader(requestLines[requestLines.length-1])) {
            body = requestLines[requestLines.length-1];
        }

        return new Request(methodType, headers[0], headers[1], headers[2], headers[3], headers[4] == null? 0 : Integer.parseInt(headers[4]), headers[5], requestedResource, body);
    }

    private static String getHeader(String headerName, String requestLines[]) {
        for (String requestLine : requestLines) {
            if (requestLine.startsWith(headerName)) {
                return requestLine.split(" ")[1];
            }
        }

        return null; // if it didn't find the header it returns null
    }

    private static String[] getHeaders(String[] requestLines) {
        String headers[] = new String[7];
        for (int i = 0; i < requestHeaders.length; i++) {
            headers[i] = getHeader(requestHeaders[i], requestLines);
        }
        return headers;
    }

    private static boolean isAcceptedHeader(String lastRequestLine) {
        return lastRequestLine.contains("Host") || lastRequestLine.contains("Accept") ||
                lastRequestLine.contains("Date") || lastRequestLine.contains("Content-Type")
                || lastRequestLine.contains("Content-Length") || lastRequestLine.contains("Referer");

    }
}
