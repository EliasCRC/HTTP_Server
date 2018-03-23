package cr.ac.ucr.ecci;

import cr.ac.ucr.ecci.Request;

public class RequestParser {
    static String[] requestHeaders = {"Host", "Accept", "Date", "Content-Type", "Content-Length", "Referer"};

    public static Request parseRequest(String requestString) {
        String[] requestLines = requestString.split("\n");
        String methodType = requestLines[0].split(" ")[0];
        String[] headers = getHeaders(requestLines);
        Request request = new Request(methodType, headers[0], headers[1], headers[2], headers[3], Integer.parseInt(headers[4]), headers[5]);
        return request;
    }

    private static String getHeader(String headerName, String requestLines[]) {
        for (int i = 0; i < requestLines.length; i++) {
            if (requestLines[i].startsWith(headerName)) {
                return requestLines[i].split(" ")[1];
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
