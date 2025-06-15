package test.myTest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Classe di lancio dei test JUnit per il progetto.
 * Esegue tutti i test definiti nella suite.
 */
public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(
            ListAdapterTest.class
            //SubListAdapterTest.class
        );

        System.out.println("=== Risultati Test ===");
        System.out.println("Totale test eseguiti: " + result.getRunCount());
        System.out.println("Numero di fallimenti: " + result.getFailureCount());

        if (!result.wasSuccessful()) {
            System.out.println("--- Dettagli fallimenti ---");
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
                failure.getException().printStackTrace();
            }
        } else {
            System.out.println("Tutti i test sono stati superati con successo.");
        }
    }
}
