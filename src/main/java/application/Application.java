package application;

import domain.*;
import infrastructure.HttpRequestService;
import infrastructure.PropertiesFileRepository;
import infrastructure.StaticCourtRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

        return args -> {
            Repository repository = new PropertiesFileRepository("repository.data");
            CourtRepository courtRepository = new StaticCourtRepository();
            RequestService requestServiceMathieu = new HttpRequestService(repository);
            RequestService requestServiceJulien = new HttpRequestService(repository);

            LocalDateTime loginTime = LocalDateTime.of(2019, 1, 28, 23, 50, 0, 0);

            LocalDateTime bookingTime = LocalDateTime.of(2019, 1, 28, 0, 0, 0, 0);

            Booker booker = new EffectivBooker();

            TimerTask timerTaskLogin = new TimerTask() {
                @Override
                public void run() {
                    boolean loginsAreSuccessfull = booker.loginUsers(repository, requestServiceMathieu, requestServiceJulien);
                }
            };

            ScheduledExecutorService executorServiceLogin = Executors.newSingleThreadScheduledExecutor();
            executorServiceLogin.schedule(timerTaskLogin, LocalDateTime.now().until(loginTime, ChronoUnit.NANOS), TimeUnit.NANOSECONDS);

            TimerTask timerTaskBooking = new TimerTask() {
                @Override
                public void run() {
                    booker.boo2Hours(repository, courtRepository, requestServiceMathieu, requestServiceJulien);
                }
            };

            ScheduledExecutorService executorServiceBooking = Executors.newSingleThreadScheduledExecutor();
            executorServiceBooking.schedule(timerTaskBooking, LocalDateTime.now().until(bookingTime, ChronoUnit.NANOS), TimeUnit.NANOSECONDS);

        };
    }

}
