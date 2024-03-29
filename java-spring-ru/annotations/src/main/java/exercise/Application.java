package exercise;

import exercise.model.Address;
import exercise.annotation.Inspect;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Application {
    public static void main(String[] args) {
        var address = new Address("London", 12345678);

        // BEGIN
        Arrays.stream(Address.class.getDeclaredMethods()).forEach(it -> {
            if (it.isAnnotationPresent(Inspect.class)) {
                System.out.println("Method " + it.getName() + " returns a value of type " + it.getReturnType().getSimpleName());
            }
        });
        // END
    }
}
