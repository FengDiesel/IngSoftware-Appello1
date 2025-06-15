package myTest;

import myAdapter.HList;
import myAdapter.ListAdapter;
import myAdapter.SubListAdapter;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Classe di test per {@code SubListAdapter}.
 * Verifica il comportamento della sottolista vista sulla lista principale.
 * <p>
 * <strong>Summary:</strong> Verifica che {@code SubListAdapter} mantenga correttamente il legame con la lista padre, propagando aggiornamenti e operazioni coerentemente.
 * Include test su metodi fondamentali come {@code get()}, {@code add()}, {@code remove()} e sulla validazione dei range.
 *
 * <p>
 * <strong>Design:</strong> Ogni test costruisce una sublist con range validi o non validi e verifica il comportamento rispetto a modifiche e accessi.
 * Si assume che {@link ListAdapter} sia correttamente funzionante. Le operazioni sono testate sia su sublist non vuote che vuote.
 *
 * <p>
 * <strong>Test Case Design:</strong>
 * <ul>
 *   <li><b>testSizeAndIsEmpty</b>: controlla {@code size()} e {@code isEmpty()} su sublist vuota e non</li>
 *   <li><b>testGetFromSubList</b>: verifica mapping corretto degli indici</li>
 *   <li><b>testAddToSubList</b>: aggiunta riflessa in parent</li>
 *   <li><b>testRemoveFromSubList</b>: rimozione visibile anche in parent</li>
 *   <li><b>testInvalidIndexInGet</b>: eccezione per indice fuori range</li>
 *   <li><b>testConstructorInvalidRange</b>: eccezione per range non valido</li>
 * </ul>
 *
 * <p>
 * <strong>Pre-Condition:</strong> Ogni test inizializza un {@link ListAdapter} con 5 elementi. Le sublist vengono create da esso con vari range.
 *
 * <p>
 * <strong>Expected Results:</strong> Le sublist devono comportarsi come viste: riflettere i cambiamenti sulla lista padre e validare i range.
 *
 * <p>
 * <strong>Post-Condition:</strong> Ogni modifica alla sublist deve essere coerente con la lista padre, e viceversa.
 *
 * <p>
 * <strong>Risk Assessment:</strong> Particolare attenzione è data alla sincronizzazione degli indici e alla gestione dinamica del bound superiore dopo operazioni di mutazione.
 *
 * <p>
 * <strong>Test Execution:</strong> Automatizzati con JUnit 4.13.2 e invocati dal {@link myTest.TestRunner}.
 *
 * <p>
 * <strong>Dipendenze:</strong>
 * <ul>
 *   <li>{@link ListAdapter}</li>
 *   <li>{@link SubListAdapter}</li>
 *   <li>{@code junit-4.13.2.jar}</li>
 * </ul>
 */
public class SubListAdapterTest {

    /**
     * Lista principale da cui viene derivata la sublist.
     */
    private ListAdapter parent;

    /**
     * Sottolista derivata dalla lista principale da testare.
     */
    private SubListAdapter sub;

    /**
     * Inizializza una lista e una sublist di esempio prima di ogni test.
     */
    @Before
    public void setUp() {
        parent = new ListAdapter();
        parent.add("A");
        parent.add("B");
        parent.add("C");
        parent.add("D");
        parent.add("E");
    }

    /**
     * Test dei metodi {@link myAdapter.HList#size()} e {@link myAdapter.HList#isEmpty()} su una sublist.
     *
     * @summary.test Verifica che la sublist calcoli correttamente la dimensione e rilevi se è vuota o meno.
     *
     * @design.test Crea due sublist: una non vuota e una vuota. Verifica i valori restituiti da {@code size()} e {@code isEmpty()}.
     *
     * @description.test
     * <ul>
     *   <li>{@code sub = new SubListAdapter(parent, 1, 4)} → contiene ["B", "C", "D"]</li>
     *   <li>{@code subEmpty = new SubListAdapter(parent, 2, 2)} → vuota</li>
     * </ul>
     *
     * @precondition.test Lista padre contiene ["A", "B", "C", "D", "E"].
     *
     * @postcondition.test Nessuna modifica a parent o sublist.
     *
     * @result.test La dimensione delle due sublist è corretta; {@code isEmpty()} ritorna il valore atteso.
     */
    @Test
    public void testSizeAndIsEmpty() {
        sub = new SubListAdapter(parent, 1, 4);
        assertEquals(3, sub.size());
        assertFalse(sub.isEmpty());

        ListAdapter parent = new ListAdapter();
        parent.add("a");
        parent.add("b");
        SubListAdapter subEmpty = new SubListAdapter(parent, 2, 2); // sub vuota
        assertEquals(0, subEmpty.size());
        assertTrue(subEmpty.isEmpty());
    }


    /**
     * Test del metodo {@link myAdapter.HList#get(int)} sulla sublist.
     *
     * @summary.test Verifica che {@code get(i)} mappi correttamente l’indice relativo al parent.
     *
     * @design.test Crea una sublist tra indici 1 e 4 e accede agli elementi della vista.
     *
     * @description.test
     * <ul>
     *   <li>{@code sub.get(0)} → "B"</li>
     *   <li>{@code sub.get(2)} → "D"</li>
     * </ul>
     *
     * @precondition.test Lista padre = ["A", "B", "C", "D", "E"].
     *
     * @postcondition.test Lista padre invariata.
     *
     * @result.test Gli elementi acceduti dalla sublist corrispondono a quelli della lista padre.
     */
    @Test
    public void testGetFromSubList() {
        sub = new SubListAdapter(parent, 1, 4);
        assertEquals("B", sub.get(0));
        assertEquals("D", sub.get(2));
    }

    /**
     * Test del metodo {@link myAdapter.HList#add(int, Object)} sulla sublist.
     *
     * @summary.test Verifica che {@code add()} nella sublist modifichi anche la lista padre e aggiorni la dimensione.
     *
     * @design.test Crea sublist [1,4] → ["B","C","D"], inserisce "X" in mezzo.
     *
     * @description.test
     * <ul>
     *   <li>{@code sub.add(1, "X")} → sub = ["B","X","C","D"]</li>
     *   <li>{@code parent.get(2)} == "X"</li>
     * </ul>
     *
     * @precondition.test Lista padre contiene 5 elementi.
     *
     * @postcondition.test Lista padre contiene 6 elementi; sublist aggiornata coerentemente.
     *
     * @result.test Elemento correttamente inserito in entrambe le strutture.
     */
    @Test
    public void testAddToSubList() {
        sub = new SubListAdapter(parent, 1, 4);
        sub.add(1, "X");
        assertEquals("X", sub.get(1));
        assertEquals("X", parent.get(2));
        assertEquals(4, sub.size());
        assertEquals(6, parent.size());
    }

    /**
     * Test del metodo {@link myAdapter.HList#remove(int)} sulla sublist.
     *
     * @summary.test Verifica che {@code remove(i)} rimuova correttamente da sublist e riflessivamente da parent.
     *
     * @design.test Rimuove elemento all’indice 1 dalla sublist ["B","C","D"].
     *
     * @description.test
     * <ul>
     *   <li>{@code removed = sub.remove(1)} → "C"</li>
     *   <li>Verifica sub = ["B", "D"]</li>
     *   <li>Verifica parent.size() == 4</li>
     * </ul>
     *
     * @precondition.test Lista padre = ["A","B","C","D","E"].
     *
     * @postcondition.test Lista padre aggiornata coerentemente; sublist ridotta.
     *
     * @result.test Elemento rimosso correttamente da entrambi.
     */
    @Test
    public void testRemoveFromSubList() {
        sub = new SubListAdapter(parent, 1, 4);
        Object removed = sub.remove(1);
        assertEquals("C", removed);
        assertEquals(2, sub.size());
        assertEquals(4, parent.size());
        assertEquals("D", sub.get(1));
    }

    /**
     * Test del metodo {@link myAdapter.HList#get(int)} su una sublist con indice fuori range.
     *
     * @summary.test Verifica che {@code get(i)} lanci {@link IndexOutOfBoundsException} per indice non valido.
     *
     * @design.test Crea sublist tra [1,3] e tenta {@code get(5)}.
     *
     * @description.test
     * <ul>
     *   <li>Chiamata {@code get(5)} su sublist lunga 2 → eccezione</li>
     * </ul>
     *
     * @precondition.test Lista padre contiene ["A", "B", "C", "D", "E"].
     *
     * @postcondition.test Nessuna modifica a parent o sublist.
     *
     * @result.test Deve essere lanciata {@code IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testInvalidIndexInGet() {
        sub = new SubListAdapter(parent, 1, 3);
        sub.get(5);
    }

    /**
     * Test del costruttore {@link myAdapter.SubListAdapter#SubListAdapter(ListAdapter, int, int)}
 con range invalido.
     *
     * @summary.test Verifica che il costruttore lanci {@link IndexOutOfBoundsException} per valori di from/to invalidi.
     *
     * @design.test Crea sublist con {@code from = -1, to = 3}.
     *
     * @description.test
     * <ul>
     *   <li>from &lt; 0 → deve lanciare eccezione</li>
     *   <li>Altri casi di errore (es. from > to o to > size) sono simili</li>
     * </ul>
     *
     * @precondition.test Lista padre contiene 5 elementi.
     *
     * @postcondition.test Lista padre invariata.
     *
     * @result.test Deve essere lanciata {@code IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testConstructorInvalidRange() {
        new SubListAdapter(parent, -1, 3);
    }
}
