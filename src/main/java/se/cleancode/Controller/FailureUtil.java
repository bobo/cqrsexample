package se.cleancode.Controller;

public class FailureUtil {


    public static void sleep(long delay) {
        try {
            Thread.sleep(delay);
        }catch(Exception ex) {

        }
    }

}
