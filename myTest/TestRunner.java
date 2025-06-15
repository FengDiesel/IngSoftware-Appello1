package myTest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Classe principale per l'esecuzione automatica dei test del progetto {@code ListAdapter} e {@code SubListAdapter}.
 * <p>
 * <strong>Summary:</strong> Questa classe utilizza {@link org.junit.runner.JUnitCore} per eseguire tutti i test presenti nelle classi
 * {@code ListAdapterTest} e {@code SubListAdapterTest}. Al termine dell'esecuzione, stampa un riepilogo con:
 * <ul>
 *   <li>Numero totale di test eseguiti</li>
 *   <li>Numero di test falliti</li>
 *   <li>Dettagli dei fallimenti (inclusi stack trace)</li>
 * </ul>
 *
 * <p>
 * <strong>Design:</strong> L’esecuzione è orchestrata tramite il metodo statico {@code runClasses(Class...)} della classe {@code JUnitCore}.
 * Questo approccio consente di lanciare i test senza l'uso di IDE o plugin esterni.
 *
 * <p>
 * <strong>Dipendenze:</strong>
 * <ul>
 *   <li>{@link ListAdapterTest}</li>
 *   <li>{@link SubListAdapterTest}</li>
 *   <li>{@code junit-4.13.2.jar}</li>
 * </ul>
 *
 * <p>
 * <strong>Note:</strong> La libreria JUnit deve essere inclusa nel classpath, e i test devono essere compatibili con la versione 4.13.2.
 */
public class TestRunner {
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
