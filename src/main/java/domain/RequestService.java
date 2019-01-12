package domain;

public interface RequestService {

    String home();
    String login(String csrf, String cookieValue, Pair user);

}
