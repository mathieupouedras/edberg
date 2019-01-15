package application;

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
                    System.out.println("Hello");
                }
            };

            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(timerTask, 5000l, 500l, TimeUnit.MILLISECONDS);
            Thread.sleep(15000l);
            executorService.shutdown();
        };
    }
}
