package exercise;

import exercise.annotation.Inspect;
import exercise.model.Address;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

// BEGIN
@SpringBootApplication
@RestController
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        Arrays.stream(Address.class.getDeclaredMethods()).forEach(it -> {
            if (it.isAnnotationPresent(Inspect.class))
                System.out.println("Method " + it.getName() + " returns a value of type " + it.getReturnType().getSimpleName());
        });

    }

    @GetMapping("/about")
    String home() {
        return "Welcome to Hexlet!";
    }
}
// END
