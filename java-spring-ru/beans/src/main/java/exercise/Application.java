package exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



import exercise.daytime.Daytime;
import exercise.daytime.Day;
import exercise.daytime.Night;


// BEGIN
import java.time.LocalDateTime;
import org.springframework.context.annotation.Bean;
// END

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @Bean()
    public Daytime getDay() { // Имя метода не важно
        var time = LocalDateTime.now();
        if (time.getHour() >= 6 && time.getHour() < 22 ) {
            return new Day();
        }
        return new Night();
    }

    // END
}
