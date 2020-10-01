package DownSeriesFootballSim;

import java.util.Map;

public class LambdaResponse {
    String body;
    int statusCode;
    Map<String, String> headers;
    boolean isBase64Encoded;

    public LambdaResponse(String body, int statusCode, Map<String, String> headers, boolean encoded) {
        this.body = body;
        this.statusCode = statusCode;
        this.headers = headers;
        this.isBase64Encoded = encoded;
    }

    public String getBody() {
        return body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public boolean isBase64Encoded() {
        return isBase64Encoded;
    }
}
