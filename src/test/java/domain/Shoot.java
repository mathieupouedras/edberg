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

        SimpleCookiejar simpleCookiejar2 = new SimpleCookiejar();
        OkHttpClient client2 = new OkHttpClient.Builder()
                .cookieJar(simpleCookiejar2)
                .build();


        Repository repository = new PropertiesFileRepository("repository.data");
        RequestService requestService = new HttpRequestService(repository, client);

        book_11h(simpleCookiejar, repository, requestService);

        RequestService requestService2 = new HttpRequestService(repository, client2);
        book_10h(simpleCookiejar2, repository, requestService2);


    }

    private void book_11h(SimpleCookiejar simpleCookiejar, Repository repository, RequestService requestService) {
        String homeBody = requestService.home();
        ResponseData responseDataHome = new ResponseData(simpleCookiejar.loadForRequest(HttpUrl.get(repository.getUrl("url.home"))),
                homeBody, repository);

        String login = requestService.login(responseDataHome.getCsrf(), responseDataHome.buildCookieHeader(), repository.getUserJulien());

        System.out.println(login);

        ResponseData responseDataLogin = new ResponseData(simpleCookiejar.loadForRequest(HttpUrl.get(repository.getUrl("url.login"))), repository);

        String chooseScheduleBody = requestService.chooseSchedule(responseDataLogin.buildCookieHeader(),
                repository.getDefaultDate(),
                repository.getSchedule7(),
                repository.getTimeStart11(),
                repository.getDefaultDuration());

        ResponseData responseDataChooseSchedule = new ResponseData(simpleCookiejar.loadForRequest(HttpUrl.get(repository.getUrl("url.book.session"))),
                chooseScheduleBody, repository);

        ExecuteScheduleFormParameters executeScheduleFormParameters = new ExecuteScheduleFormParameters.Builder(repository)
                .member(repository.getFormParameterName("form.parameter.member.damien.value"))
                .date("2019-01-27")
                .timeStart(repository.getFormParameterName("form.parameter.timeend.11.value"))
                .timeEnd(repository.getFormParameterName("form.parameter.timeend.12.value"))
                .schedule(repository.getFormParameterName("form.parameter.schedule.value.7"))
                .csrf(responseDataChooseSchedule.getExecuteCsrf())
                .build();

        String create = requestService.createSchedule(responseDataChooseSchedule.buildCookieHeader(), executeScheduleFormParameters);

        System.out.println(create);

    }

    private void book_10h(SimpleCookiejar simpleCookiejar, Repository repository, RequestService requestService) {
        String homeBody = requestService.home();
        ResponseData responseDataHome = new ResponseData(simpleCookiejar.loadForRequest(HttpUrl.get(repository.getUrl("url.home"))),
                homeBody, repository);

        String login = requestService.login(responseDataHome.getCsrf(), responseDataHome.buildCookieHeader(), repository.getUserMathieu());

        System.out.println(login);

        ResponseData responseDataLogin = new ResponseData(simpleCookiejar.loadForRequest(HttpUrl.get(repository.getUrl("url.login"))), repository);

        String chooseScheduleBody = requestService.chooseSchedule(responseDataLogin.buildCookieHeader(),
                repository.getDefaultDate(),
                repository.getSchedule7(),
                repository.getTimeStart10(),
                repository.getDefaultDuration());

        ResponseData responseDataChooseSchedule = new ResponseData(simpleCookiejar.loadForRequest(HttpUrl.get(repository.getUrl("url.book.session"))),
                chooseScheduleBody, repository);

        ExecuteScheduleFormParameters executeScheduleFormParameters = new ExecuteScheduleFormParameters.Builder(repository)
                .member(repository.getFormParameterName("form.parameter.member.emmanuel.value"))
                .date("2019-01-27")
                .timeStart(repository.getFormParameterName("form.parameter.timestart.10.value"))
                .timeEnd(repository.getFormParameterName("form.parameter.timeend.11.value"))
                .schedule(repository.getFormParameterName("form.parameter.schedule.value.7"))
                .csrf(responseDataChooseSchedule.getExecuteCsrf())
                .build();

        String create = requestService.createSchedule(responseDataChooseSchedule.buildCookieHeader(), executeScheduleFormParameters);

        System.out.println(create);
    }
}