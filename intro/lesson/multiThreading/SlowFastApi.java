package lesson.multiThreading;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SlowFastApi {

    private static String getServerResponse(String serverName) {
        return """
                {
                  "server" : %s
                }
                """.formatted(serverName);
    }

    public static void main(String[] args) {
        Callable<String> serverA = () -> {
            try {
                System.out.println("I represent slow API :: " + Thread.currentThread().getName());
                Thread.sleep(300);
                return getServerResponse("serverA");
            } catch (Exception e) {
            }
            return "";
        };

        Callable<String> serverB = () -> {
            try {
                System.out.println("I represent fast API :: " + Thread.currentThread().getName());
                Thread.sleep(100);
                return getServerResponse("serverB");
            } catch (Exception e) {
            }
            return "";
        };

        List<Callable<String>> callableList = List.of(serverA, serverB);

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        try {
            System.out.println(executorService.invokeAny(callableList));
        } catch (Exception e) {
        }

        executorService.shutdown();

    }
}
