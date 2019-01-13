package domain;

import okhttp3.Cookie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

public class ResponseData {

    private final List<Cookie> cookies;
    private String body;
    private final Repository repository;

    ResponseData(List<Cookie> cookies, String body, Repository repository) {
        this.cookies = cookies;
        this.body = body;
        this.repository = repository;
    }

    public ResponseData(List<Cookie> cookies, Repository repository) {
        this.cookies = cookies;
        this.repository = repository;
        this.body = null;
    }

    List<Cookie> getCookies() {
        return cookies;
    }

    String getCsrf() {
        Document document = Jsoup.parse(body);
        Element elementById = document.getElementById(repository.getCsrfHidenInputElementId());
        return elementById.attr(repository.getCsrfHidenInputElementAttributeName());
    }

    String getExecuteCsrf() {
        Document document = Jsoup.parse(body);
        Element elementById = document.getElementById(repository.getBookCsrfHidenInputElementId());
        return elementById.attr(repository.getBookCsrfHidenInputElementAttributeName());
    }


    String buildCookieHeader() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < cookies.size(); i++) {
            if (cookies.get(i).name().equals(repository.getCookieParameterName("cookie.parameter.name.uid"))
                    || cookies.get(i).name().equals(repository.getCookieParameterName("cookie.parameter.name.sessionid"))
                    || cookies.get(i).name().equals(repository.getCookieParameterName("cookie.parameter.name.id"))
                    || cookies.get(i).name().equals(repository.getCookieParameterName("cookie.parameter.name.lang"))) {
                stringBuilder.append(cookies.get(i).name());
                stringBuilder.append("=");
                stringBuilder.append(cookies.get(i).value());
                if (i < 2) {
                    stringBuilder.append(";");
                    stringBuilder.append(" ");
                }
            }
        }

        return stringBuilder.toString();
    }

}
