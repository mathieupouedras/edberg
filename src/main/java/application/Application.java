package application;

import domain.Repository;
import domain.RequestService;
import domain.ResponseData;
import domain.SimpleCookiejar;
import infrastructure.HttpRequestService;
import infrastructure.PropertiesFileRepository;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext context) {
        return  args -> {
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
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

                }
            };

            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

            executorService.scheduleWithFixedDelay(timerTask, 1, TimeUnit.HOURS.toMinutes(2), TimeUnit.MINUTES);
        };
    }
}
