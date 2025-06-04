package test.myTest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Classe di lancio dei test JUnit.
 */
public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(
    ListAdapterTest.class
        );

        System.out.println("Numero test eseguiti: " + result.getRunCount());
        System.out.println("Fallimenti: " + result.getFailureCount());

        if (!result.wasSuccessful()) {
            System.out.println("Dettagli fallimenti:");
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        } else {
            System.out.println("Tutti i test sono passati.");
        }

    }
}