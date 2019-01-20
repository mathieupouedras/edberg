package domain;


public interface Repository {
    Pair getUserAgent();

    String getUrl(String name);

    String getCookieParameterName(String name);

    String getCsrfHidenInputElementId();

    String getCsrfHidenInputElementAttributeName();

    String getBookCsrfHidenInputElementId();

    String getBookCsrfHidenInputElementAttributeName();


    String getFormParameterName(String name);

    Pair getUserMathieu();

    Pair getUserJulien();

    Pair getDefaultDate();

    Pair getSchedule7();

    Pair getSchedule8();

    Pair getTimeStart10();

    Pair getTimeStart11();

    Pair getDefaultDuration();
}
