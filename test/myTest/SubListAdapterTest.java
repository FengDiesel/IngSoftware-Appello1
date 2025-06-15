package myTest;

import myAdapter.HList;
import myAdapter.ListAdapter;
import myAdapter.SubListAdapter;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Homework - Test Case Documentation
 *
 * Summary:
 *   Verifica il comportamento della classe {@code SubListAdapter}, che fornisce
 *   una viste parziale (sub-list) su un {@code ListAdapter} padre.
 *
 * Test Case Design:
 *   - testSizeAndIsEmpty: controlla size() e isEmpty() su vista non vuota e vuota
 *   - testGetFromSubList: verifica get(i) in mappa correttamente gli indici
 *   - testAddToSubList: verifica add(index, E) modifica sia vista sia lista padre
 *   - testRemoveFromSubList: verifica remove(index) rimuove elemento da vista e padre
 *   - testInvalidIndexInGet: get(i) fuori range solleva IndexOutOfBoundsException
 *   - testConstructorInvalidRange: costruttore con parametri invalidi solleva eccezione
 *
 * Formal Review:
 *   Revisione secondo le specifiche J2SE 1.4.2 per il metodo {@code List.subList},
 *   ensuring backed view semantics.
 *
 * Pre-Condition:
 *   Ogni test inizia con un {@code ListAdapter} popolato (o non) e una nuova {@code SubListAdapter}.
 *
 * Expected Results:
 *   Tutti i metodi devono comportarsi come specificato in {@code HList}:
 *   dimensioni corrette, mapping degli indici, modifica riflessa sul padre,
 *   e corretta gestione delle eccezioni.
 *
 * Post-Condition:
 *   La lista padre e la vista rimangono in uno stato coerente con le operazioni testate.
 *
 * Risk Assessment:
 *   Le operazioni sulla vista richiedono corretta traduzione degli indici e
 *   mantenimento del bound superiore (toIndex) dinamico dopo add/remove.
 *
 * Test Cases:
 * - testSizeAndIsEmpty()
 * - testGetFromSubList()
 * - testAddToSubList()
 * - testRemoveFromSubList()
 * - testInvalidIndexInGet()
 * - testConstructorInvalidRange()
 *
 * Test Case Execution Records:
 *   Esecuzione automatizzata via JUnit4.
 *
 * Attachments:
 *   - SubListAdapter.java
 *   - ListAdapter.java
 *   - junit-<versione>.jar
 */
public class SubListAdapterTest {

    private ListAdapter parent;
    private SubListAdapter sub;

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
     * Summary:
     *   Verifica size() e isEmpty() su sublist non vuota e su vuota.
     *
     * Test Method Scope:
     *   Metodi HList.size() e HList.isEmpty() di SubListAdapter.
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: size()=toIndex-fromIndex; isEmpty() vero se size()==0.
     *
     * Pre-Condition:
     *   parent ha 5 elementi.
     *
     * Test Scripts:
     *   1. sub = new SubListAdapter(parent, 1, 4)  // ["B","C","D"]
     *   2. verificare sub.size() e sub.isEmpty()
     *   3. subEmpty = new SubListAdapter(parent, 2, 2) // vista vuota
     *   4. verificare subEmpty.size() e subEmpty.isEmpty()
     *
     * Expected Results:
     *   - sub.size()==3, sub.isEmpty()==false
     *   - subEmpty.size()==0, subEmpty.isEmpty()==true
     *
     * Post-Condition:
     *   parent invariato.
     */
    @Test
    public void testSizeAndIsEmpty() {
        sub = new SubListAdapter(parent, 1, 4);
        assertEquals(3, sub.size());
        assertFalse(sub.isEmpty());

        SubListAdapter subEmpty = new SubListAdapter(parent, 2, 2);
        assertEquals(0, subEmpty.size());
        assertTrue(subEmpty.isEmpty());
    }

    /**
     * Summary:
     *   Verifica get(i) mappa correttamente l’indice relativo a parent.
     *
     * Test Method Scope:
     *   Metodo HList.get(int) di SubListAdapter.
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: subList.get(i) == parent.get(from + i).
     *
     * Pre-Condition:
     *   parent = ["A","B","C","D","E"].
     *
     * Test Scripts:
     *   1. sub = new SubListAdapter(parent, 1, 4) // ["B","C","D"]
     *   2. assertEquals("B", sub.get(0))
     *   3. assertEquals("D", sub.get(2))
     *
     * Expected Results:
     *   sub.get(0) == "B", sub.get(2) == "D"
     *
     * Post-Condition:
     *   parent invariato.
     */
    @Test
    public void testGetFromSubList() {
        sub = new SubListAdapter(parent, 1, 4);
        assertEquals("B", sub.get(0));
        assertEquals("D", sub.get(2));
    }

    /**
     * Summary:
     *   Verifica add(index, E) inserisce elemento nella vista e nel parent,
     *   aggiornando il bound superiore.
     *
     * Test Method Scope:
     *   Metodo HList.add(int, Object) di SubListAdapter.
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: add in sublist deve riflettersi su parent e incrementare size della vista.
     *
     * Pre-Condition:
     *   sub = ["B","C","D"], parent.size()==5.
     *
     * Test Scripts:
     *   1. sub = new SubListAdapter(parent, 1, 4)
     *   2. sub.add(1, "X")
     *
     * Expected Results:
     *   - sub.get(1) == "X"
     *   - parent.get(2) == "X"
     *   - sub.size() == 4
     *   - parent.size() == 6
     *
     * Post-Condition:
     *   parent e sub coerenti.
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
     * Summary:
     *   Verifica remove(index) elimina elemento da vista e da parent,
     *   aggiornando il bound superiore.
     *
     * Test Method Scope:
     *   Metodo HList.remove(int) di SubListAdapter.
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: remove in sublist deve rimuovere da parent e decrementare size.
     *
     * Pre-Condition:
     *   sub = ["B","C","D"], parent.size()==5.
     *
     * Test Scripts:
     *   1. sub = new SubListAdapter(parent, 1, 4)
     *   2. Object removed = sub.remove(1)
     *
     * Expected Results:
     *   - removed.equals("C")
     *   - sub.size() == 2
     *   - parent.size() == 4
     *   - sub.get(1).equals("D")
     *
     * Post-Condition:
     *   parent e sub coerenti.
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
     * Summary:
     *   Verifica che get(i) lanci IndexOutOfBoundsException per indice fuori range.
     *
     * Test Method Scope:
     *   Metodo HList.get(int) di SubListAdapter con indice invalido.
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: get(i) deve lanciare IndexOutOfBoundsException per i<0 o i>=size().
     *
     * Pre-Condition:
     *   sub = new SubListAdapter(parent, 1, 3).
     *
     * Test Scripts:
     *   1. sub.get(5)
     *
     * Expected Results:
     *   IndexOutOfBoundsException
     *
     * Post-Condition:
     *   parent e sub invariati.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testInvalidIndexInGet() {
        sub = new SubListAdapter(parent, 1, 3);
        sub.get(5);
    }

    /**
     * Summary:
     *   Verifica che il costruttore lanci IndexOutOfBoundsException per parametri invalidi.
     *
     * Test Method Scope:
     *   Costruttore SubListAdapter(int from, int to).
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: subList(from,to) lancia IndexOutOfBoundsException se from<0, to>size o from>to.
     *
     * Pre-Condition:
     *   parent ha 5 elementi.
     *
     * Test Scripts:
     *   1. new SubListAdapter(parent, -1, 3)
     *
     * Expected Results:
     *   IndexOutOfBoundsException
     *
     * Post-Condition:
     *   nessuna modifica a parent.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testConstructorInvalidRange() {
        new SubListAdapter(parent, -1, 3);
    }
}
