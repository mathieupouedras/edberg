package domain;

import infrastructure.HttpRequestService;
import infrastructure.PropertiesFileRepository;
import okhttp3.*;
import org.junit.jupiter.api.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Shoot {

    @Test
    void should() {
        SimpleCookiejar simpleCookiejar = new SimpleCookiejar();
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(simpleCookiejar)
                .build();

        Repository repository = new PropertiesFileRepository("repository.data");
        RequestService requestService = new HttpRequestService(repository, client);
        String homeBody = requestService.home();
        ResponseData responseDataHome = new ResponseData(simpleCookiejar.loadForRequest(HttpUrl.get(repository.getUrl("url.home"))),
                homeBody, repository);

        requestService.login(responseDataHome.getCsrf(), responseDataHome.buildCookieHeader(), repository.getUserMathieu());

        ResponseData responseDataLogin = new ResponseData(simpleCookiejar.loadForRequest(HttpUrl.get(repository.getUrl("url.login"))), repository);

        String chooseSchedule = requestService.chooseSchedule(responseDataLogin.buildCookieHeader(), new Pair("date", "3"),
                new Pair("schedule", "34303"),
                new Pair("timestart", "1020"),
                new Pair("duration", "60"));

        System.out.println(chooseSchedule);
    }
}