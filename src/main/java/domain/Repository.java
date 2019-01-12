package domain;


public interface Repository {
    Pair getUserAgent();

    String getUrl(String name);

    String getCookieParameterName(String name);

    String getCsrfHidenInputElementId();

    String getCsrfHidenInputElementAttributeName();

    String getFormParameterName(String name);

    Pair getUserMathieu();
}
