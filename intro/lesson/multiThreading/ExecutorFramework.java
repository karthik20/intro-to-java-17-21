package lesson.multiThreading;

import java.util.concurrent.*;

public class ExecutorFramework {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Runnable runEveryOneSecond = () -> {
            try {
                System.out.println("Current Thread running.." + Thread.currentThread().getName());
                Thread.sleep(1000);
            } catch (Exception ie) {
                System.err.println(ie.getMessage());
            }
        };

        for (int i = 0; i < 1000; i++) {
            executorService.execute(runEveryOneSecond);
        }
        executorService.shutdown();

        Callable<String> callApiEveryOneSecond = () -> {
            try {
                System.out.println("Current Callable Thread running.." + Thread.currentThread().getName());
                Thread.sleep(1000);
                return """
                        {
                           "name" : "John",
                           "age": 23
                        }
                        """;
            } catch (Exception ie) {
                System.err.println(ie.getMessage());
            }
            return "";
        };

        executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 100; i++) {
            Future<String> asyncResponse = executorService.submit(callApiEveryOneSecond);
            try {
                var output = asyncResponse.get(1500, TimeUnit.MILLISECONDS);
                System.out.println(output);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
