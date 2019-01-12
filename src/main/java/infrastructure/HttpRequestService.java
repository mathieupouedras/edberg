package infrastructure;

import domain.*;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpRequestService implements RequestService {
    private final Repository repository;
    private final OkHttpClient okHttpClient;

    public HttpRequestService(Repository repository, OkHttpClient okHttpClient) {
        this.repository = repository;
        this.okHttpClient = okHttpClient;
    }

    @Override
    public String home() {
        List<Pair> headers = new ArrayList<>();
        headers.add(repository.getUserAgent());
        return get(repository.getUrl("url.home"), headers);
    }

    @Override
    public String login(String csrf, String cookieValue, Pair user) {
        List<Pair> headers = new ArrayList<>();
        headers.add(repository.getUserAgent());
        headers.add(new Pair(repository.getCookieParameterName("header.cookie.name"), cookieValue));
        FormBody formBody = new  FormBody.Builder()
                .add(repository.getFormParameterName("form.parameter.username"), user.getName())
                .add(repository.getFormParameterName("form.parameter.password"), user.getValue())
                .add(repository.getFormParameterName("form.parameter.cookie_enabled.name"),
                        repository.getFormParameterName("form.parameter.cookie_enabled.value"))
                .add(repository.getFormParameterName("form.parameter.club_id.name"),
                        repository.getFormParameterName("form.parameter.club_id.value"))
                .add(repository.getFormParameterName("form.parameter.remember.name"),
                        repository.getFormParameterName("form.parameter.remember.value"))
                .add(repository.getFormParameterName("form.parameter.csrf.name"), csrf)
                .build();

        return post(repository.getUrl("url.login"), headers, formBody);
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