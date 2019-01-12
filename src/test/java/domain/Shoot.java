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
        HomeData homeData = new HomeData(simpleCookiejar.loadForRequest(HttpUrl.get(repository.getUrl("url.home"))),
                homeBody, repository);

        String response = requestService.login(homeData.getCsrf(), homeData.buildCookieHeader(), repository.getUserMathieu());

        System.out.println(response);
    }
}