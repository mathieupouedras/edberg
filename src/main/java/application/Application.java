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

            Booker booker = new DummyBooker();

            boolean loginsAreSuccessfull = booker.loginUsers(repository, requestServiceMathieu, requestServiceJulien);

            if (loginsAreSuccessfull) {
                booker.boo2Hours(repository, courtRepository, requestServiceMathieu, requestServiceJulien);
            }
        };
    }

}
