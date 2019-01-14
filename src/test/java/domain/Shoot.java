package domain;

import infrastructure.HttpRequestService;
import infrastructure.PropertiesFileRepository;
import okhttp3.*;
import org.junit.jupiter.api.Test;


import java.util.ResourceBundle;

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

        String login = requestService.login(responseDataHome.getCsrf(), responseDataHome.buildCookieHeader(), repository.getUserMathieu());

        System.out.println(login);

        ResponseData responseDataLogin = new ResponseData(simpleCookiejar.loadForRequest(HttpUrl.get(repository.getUrl("url.login"))), repository);

        String chooseScheduleBody = requestService.chooseSchedule(responseDataLogin.buildCookieHeader(), new Pair("date", "2"),
                new Pair("schedule", "34303"),
                new Pair("timestart", "1020"),
                new Pair("duration", "60"));

        ResponseData responseDataChooseSchedule = new ResponseData(simpleCookiejar.loadForRequest(HttpUrl.get(repository.getUrl("url.book.session"))),
                chooseScheduleBody, repository);

        ExecuteScheduleFormParameters executeScheduleFormParameters = new ExecuteScheduleFormParameters.Builder(repository)
                .member(repository.getFormParameterName("form.parameter.member.emmanuel.value"))
                .date("2019-01-16")
                .timeStart("17:00")
                .timeEnd("18:00")
                .schedule("34303")
                .csrf(responseDataChooseSchedule.getExecuteCsrf())
                .build();

        String create = requestService.createSchedule(responseDataChooseSchedule.buildCookieHeader(), executeScheduleFormParameters);

        System.out.println(create);
    }
}