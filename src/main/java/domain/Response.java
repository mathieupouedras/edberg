package domain;

public class Response {

    private final String body;

    public Response(String body) {
        this.body = body;
    }

    public boolean isSuccessFull() {
        return body.contains("\"type\":\"success\"");
    }

    public String getBody() {
        return body;
    }
}
