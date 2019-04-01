package infrastructure;

import domain.*;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpRequestService implements RequestService {
    private final Repository repository;
    private final OkHttpClient okHttpClient;
    private final SimpleCookiejar simpleCookiejar;
    private final Session session;

    public HttpRequestService(Repository repository) {
        this.repository = repository;
        simpleCookiejar = new SimpleCookiejar();
        okHttpClient = new OkHttpClient.Builder()
                .cookieJar(simpleCookiejar)
                .build();
        session = new Session(simpleCookiejar, repository);

    }

    @Override
    public void home() {
        List<Pair> headers = new ArrayList<>();
        headers.add(repository.getUserAgent());
        String body = get(repository.getUrl("url.home"), headers);
        session.setSecurityToken(getSecurityToken(body, repository.getCsrfHidenInputElementId(),
                repository.getCsrfHidenInputElementAttributeName()));
        System.out.println(body.substring(0, 150));
    }

    //@Todo move this to an other class
    private String getSecurityToken(String body, String elmentId, String attributeName) {
        Document document = Jsoup.parse(body);
        Element elementById = document.getElementById(elmentId);
        String securityToken = elementById.attr(attributeName);

        System.out.println("Security token : " + securityToken);

        return securityToken;
    }


    @Override
    public domain.Response login(Pair user) {
        List<Pair> headers = new ArrayList<>();
        headers.add(repository.getUserAgent());
        headers.add(new Pair(repository.getCookieParameterName("header.cookie.name"), session.buildCookieHeader(repository.getUrl("url.home"))));
        FormBody formBody = new  FormBody.Builder()
                .add(repository.getFormParameterName("form.parameter.username"), user.getName())
                .add(repository.getFormParameterName("form.parameter.password"), user.getValue())
                .add(repository.getFormParameterName("form.parameter.cookie_enabled.name"),
                        repository.getFormParameterName("form.parameter.cookie_enabled.value"))
                .add(repository.getFormParameterName("form.parameter.club_id.name"),
                        repository.getFormParameterName("form.parameter.club_id.value"))
                .add(repository.getFormParameterName("form.parameter.remember.name"),
                        repository.getFormParameterName("form.parameter.remember.value"))
                .add(repository.getFormParameterName("form.parameter.csrf.name"), session.getSecurityToken())
                .build();

        String body = post(repository.getUrl("url.login"), headers, formBody);
        return new domain.Response(body);
    }

    @Override
    public domain.Response chooseSchedule(Court court, Schedule schedule) {
        List<Pair> headers = new ArrayList<>();
        headers.add(repository.getUserAgent());
        headers.add(new Pair(repository.getCookieParameterName("header.cookie.name"), session.buildCookieHeader(repository.getUrl("url.login"))));
        FormBody formBody = new FormBody.Builder()
                .add(repository.getDefaultDate().getName(), String.valueOf(schedule.getDayNumber()))
                .add(repository.getSchedule7().getName(), court.getId())
                .add(repository.getTimeStart10().getName(), schedule.getStartTimeId())
                .add(repository.getDefaultDuration().getName(), String.valueOf(schedule.getDuration()))
                .build();

        String body = post(repository.getUrl("url.book.session"), headers, formBody);
        session.setSecurityToken(getSecurityToken(body, repository.getBookCsrfHidenInputElementId(),
                repository.getBookCsrfHidenInputElementAttributeName()));

        return new domain.Response(body);
    }


    @Override
    public domain.Response createSchedule(Schedule schedule, String date, String partnerId) {
        List<Pair> headers = new ArrayList<>();
        headers.add(repository.getUserAgent());
        headers.add(new Pair(repository.getCookieParameterName("header.cookie.name"), session.buildCookieHeader(repository.getUrl("url.book.session"))));
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add(repository.getFormParameterName("form.parameter.action.name"), repository.getFormParameterName("form.parameter.action.value"));
        formBodyBuilder.add(repository.getFormParameterName("form.parameter.choice.name"), repository.getFormParameterName("form.parameter.choice.value"));
        formBodyBuilder.add(repository.getFormParameterName("form.parameter.default_duration.name"), String.valueOf(schedule.getDuration()));
        formBodyBuilder.add(repository.getFormParameterName("form.parameter.row.name"), String.valueOf(schedule.getDayNumber()));
        formBodyBuilder.add(repository.getFormParameterName("form.parameter.request.name"), repository.getFormParameterName("form.parameter.request.value"));
        formBodyBuilder.add(repository.getFormParameterName("form.parameter.member.name"), partnerId);
        formBodyBuilder.add(repository.getFormParameterName("form.parameter.default_date.name"), date);
        formBodyBuilder.add(repository.getFormParameterName("form.parameter.default_timestart.name"), schedule.getStartTime());
        formBodyBuilder.add(repository.getFormParameterName("form.parameter.timeend.name"), schedule.getEndTime());
        formBodyBuilder.add(repository.getFormParameterName("form.parameter.default_schedule.name"), schedule.getCourt().getId());
        formBodyBuilder.add(repository.getFormParameterName("form.parameter.csrf.reservation.name"), session.getSecurityToken());

        String body = post(repository.getUrl("url.book.create"), headers, formBodyBuilder.build());
        return new domain.Response(body);
    }

    private String post(String url, List<Pair> headers, FormBody formBody) {
        Request.Builder builder = createBuilder(url, headers);
        builder.post(formBody);
        Request request = builder.build();

        return executeRequest(request);
    }

    private String get(String url, List<Pair> headers) {
        Request.Builder builder = createBuilder(url, headers);
        Request request = builder.build();

        return executeRequest(request);

    }

    private String executeRequest(Request request) {
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            return response.body().string();
        }
        catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private Request.Builder createBuilder(String url, List<Pair> headers) {
        Request.Builder builder = new Request.Builder().url(url);
        for (Pair header: headers) {
            builder.header(header.getName(), header.getValue());
        }
        return builder;
    }
}
