package myTest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * TestRunner: classe principale per l'esecuzione dei test JUnit del progetto.
 *
 * <p>Esegue in sequenza tutte le classi di test del progetto ({@code ListAdapterTest},
 * {@code SubListAdapterTest}, ecc.) utilizzando {@code JUnitCore}, e stampa i risultati
 * sulla console.
 *
 * <p>Per ogni esecuzione, il programma mostra:
 * <ul>
 *   <li>Numero totale di test eseguiti</li>
 *   <li>Numero di test falliti</li>
 *   <li>Tempo totale di esecuzione</li>
 *   <li>Dettagli degli eventuali fallimenti (inclusi stack trace)</li>
 * </ul>
 *
 * <p><b>JUnit version:</b> 4.13.2<br>
 * Le librerie JAR richieste devono essere fornite nella cartella <code>JUnit/</code> come da specifiche dell'appello.
 */
public class TestRunner {
    
    /**
     * Main per l'esecuzione automatica dei test.
     *
     * <p>Utilizza {@code JUnitCore.runClasses()} per eseguire le classi di test specificate.
     * Calcola e stampa il tempo totale di esecuzione e, in caso di fallimenti, stampa dettagli e stack trace.
     *
     * @param args argomenti da linea di comando (non utilizzati)
     */
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(
            ListAdapterTest.class,
            SubListAdapterTest.class
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
