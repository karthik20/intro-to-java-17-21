package lesson.hello;

public class HelloWorld {

    // public - access modifier
    // static - keyword denoting no instance needed
    // void - return type for main()
    // args - runtime arguments to run the main method

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        // Bug here if no args, fix on solution
        String firstArg = args[0];
        System.out.println("Hello!" + firstArg);
    }
}