package myTest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Classe che avvia l'esecuzione dei test JUnit per il progetto.
 */
public class TestRunner {

    /** Costrutore di default */
    public TestRunner(){}

    /**
     * Main per l'esecuzione dei test JUnit.
     *
     * @summary.test Avvia l'esecuzione delle classi di test specificate e stampa i risultati in output standard.
     *
     * @design.test Utilizza {@link JUnitCore#runClasses(Class...)} per eseguire {@code ListAdapterTest} e {@code SubListAdapterTest}.
     *          Verifica la presenza di errori e, in caso di fallimenti, stampa dettagli ed eccezioni.
     *
     * @description.test
     * <ul>
     *   <li>Avvia {@code JUnitCore.runClasses(...)} sulle classi di test</li>
     *   <li>Stampa numero totale e fallimenti</li>
     *   <li>Se presenti, stampa ogni fallimento e il relativo stack trace</li>
     * </ul>
     *
     * @precondition.test Le classi di test devono essere compilate correttamente e le dipendenze JUnit devono essere disponibili.
     *
     * @postcondition.test Stampa in console dei risultati del test.
     *
     * @result.test I test vengono eseguiti e i risultati presentati in formato leggibile.
     *
     * @param args argomenti da linea di comando (non utilizzati)
     */
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(
            ListAdapterTest.class,
            SubListAdapterTest.class
        );

        System.out.println("=== Risultati Test ===");
        int total = result.getRunCount();
        int failures = result.getFailureCount();
        int passed = total - failures;
        long time = result.getRunTime();
        

        System.out.println("Totale test eseguiti : " + total);
        System.out.println("Test superati     : " + passed);
        System.out.println("Test falliti      : " + failures);
        System.out.println("Tempo impiegato    : " + time + " ms");
        System.out.println("======================");

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
