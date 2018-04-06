package cr.ac.ucr.ecci.Server.Request;

public class RequestParser {
    private static String[] requestHeaders = {"Host", "Accept", "Date", "Content-Type", "Content-Length", "Referer"};

    public static Request parseRequest(String requestString) {
        String[] requestLines = requestString.split("\n");
        String methodType = requestLines[0].split(" ")[0];
        String[] headers = getHeaders(requestLines);
        return new Request(methodType, headers[0], headers[1], headers[2], headers[3], Integer.parseInt(headers[4]), headers[5]);
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
}
