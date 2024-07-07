package lesson.multiThreading;

public class MultiThreads {


    static class BackgroundProcessor extends Thread {
        public void run() {
            System.out.println("BG process runs..");
        }
    }

    static class BackgroundProcessorRunner implements Runnable {

        @Override
        public void run() {
            System.out.println("BG process runner runs..");
        }
    }

    public static void main(String[] args) {

        // via Thread class
        BackgroundProcessor bg = new BackgroundProcessor();
        bg.start();
        // via Runnable
        BackgroundProcessorRunner bgr = new BackgroundProcessorRunner();
        bgr.run();

        Runnable runnable = () -> System.out.println("Running..");
        runnable.run();
    }
}
