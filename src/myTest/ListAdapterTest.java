package myTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import myAdapter.HCollection;
import myAdapter.HIterator;
import myAdapter.HList;
import myAdapter.HListIterator;
import myAdapter.ListAdapter;

//  Homework - Test Case Documentation

/**
 * Summary:
 * Questo test case verifica il comportamento della classe {@code ListAdapter},
 * un'implementazione di {@code HList}, con particolare attenzione alla correttezza
 * delle sue operazioni fondamentali (inserimento, rimozione, ricerca, iterazione, ecc.).
 *
 * Test Case Design:
 * Ogni metodo di {@code HList} è testato con uno o più casi, includendo:
 * - condizioni normali (uso previsto)
 * - edge cases (es. liste vuote, indici al limite)
 * - gestione delle eccezioni (input errati o operazioni non permesse)
 * Il test si concentra anche sulla coerenza tra {@code ListAdapter} e le specifiche delle interfacce.
 *
 * Formal Review:
 * Il test è stato revisionato secondo le specifiche di progetto e segue
 * le linee guida richieste per la documentazione in stile Homework.
 *
 * Pre-Condition:
 * La lista viene sempre inizializzata vuota nel metodo {@code setUp()}.
 *
 * Expected Results:
 * Ogni metodo deve comportarsi secondo le specifiche dell'interfaccia {@code HList},
 * sia nei casi normali sia quando si devono lanciare eccezioni.
 *
 * Post-Condition:
 * La lista è modificata coerentemente con le operazioni testate.
 * In caso di errore, il test fallisce tramite {@code Assert}.
 *
 * Risk Assessment:
 * Il test verifica anche metodi complessi come {@code subList}, {@code listIterator},
 * e metodi mutatori in iteratori, per assicurare la stabilità in scenari dinamici.
 *
 * Test Cases:
 * - testEmptyList()
 * - testAddAndGet()
 * - testGetNegativeIndex()
 * - testGetIndexOutOfBounds()
 * - testAddAtIndex()
 * - testAddAtNegativeIndex()
 * - testAddAtTooLargeIndex()
 * - testRemoveByIndex()
 * - testRemoveIndexOutOfBounds()
 * - testRemoveByObject()
 * - testIndexOfAndLastIndexOf()
 * - testContainsAndContainsAll()
 * - testAddAllRemoveAllRetainAll()
 * - testClear()
 * - testEqualsAndHashCode()
 * - testToArray()
 * - testToArrayWithParameter()
 * - testIterator()
 * - testIteratorNextNoSuchElement()
 * - testIteratorRemoveBeforeNext()
 * - testIteratorRemove()
 * - testListIteratorForwardBackward()
 * - testListIteratorAddSetRemove()
 * - testListIteratorSetBeforeNext()
 * - testListIteratorNextTooFar()
 * - testSubListView()
 * - testAddAllAtIndex()
 * - testAddAllAtInvalidIndex()
 *
 * Test Case Execution Records:
 * L’esecuzione è automatizzata tramite {@code JUnit} e può essere integrata in un TestRunner.
 *
 * Attachments:
 * File: {@code ListAdapterTest.java}
 * Dipendenze: {@code ListAdapter}, {@code HList}, {@code HIterator}, {@code HListIterator}, {@code JUnit}
 */
public class ListAdapterTest {
    private HList list;

    @Before
    public void setUp() {
        list = new ListAdapter();
    }
    
    /**
     * Summary:
     *   Verifica che una nuova lista sia vuota e la sua size sia zero.
     *
     * Test Method Scope:
     *   Metodi HList.size() e HList.isEmpty().
     *
     * Formal Review:
     *   Conformità alla specifica J2SE 1.4.2: size() deve ritornare 0 su lista vuota; isEmpty() deve restituire true.
     *
     * Pre-Condition:
     *   list è un nuovo ListAdapter appena creato (vuoto).
     *
     * Test Scripts:
     *   1. Chiamare list.size()
     *   2. Chiamare list.isEmpty()
     *
     * Expected Results:
     *   - list.size() == 0
     *   - list.isEmpty() == true
     *
     * Post-Condition:
     *   list rimane vuota e invariata.
     */
    @Test
    public void testEmptyList() {
        assertEquals("Una nuova lista dovrebbe avere size 0", 0, list.size());
        assertTrue("Una nuova lista dovrebbe essere vuota", list.isEmpty());
    }

    //

    /**
     * Summary:
     *   Verifica che add(E) inserisca in coda e che get(i) restituisca gli elementi in ordine.
     *
     * Test Method Scope:
     *   Metodi HList.add(E) e HList.get(int).
     *
     * Formal Review:
     *   add(E) è operation opzionale ma prevista dal framework 1.4.2; get(i) deve lanciare eccezione se fuori range.
     *
     * Pre-Condition:
     *   list vuota
     *
     * Test Scripts:
     *   1. list.add("A")
     *   2. list.add("B")
     *   3. list.size()
     *   4. list.get(0)
     *   5. list.get(1)
     *
     * Expected Results:
     *   - add("A") e add("B") da true
     *   - list.size() == 2
     *   - list.get(0).equals("A")
     *   - list.get(1).equals("B")
     *
     * Post-Condition:
     *   list contiene ["A", "B"].
     */
    @Test
    public void testAddAndGet() {
        assertTrue("add() deve restituire true", list.add("A"));
        assertTrue(list.add("B"));
        assertEquals("dopo due inserti, size() deve restituire 2", 2, list.size());
        assertEquals("get(0) deve restituire il primo elemento", "A", list.get(0));
        assertEquals("get(1) deve restituire il secondo elemento\n", "B", list.get(1));
    }

    /**
     * Summary:
     *   Verifica che get(-1) lanci IndexOutOfBoundsException quando l’indice è negativo.
     *
     * Test Method Scope:
     *   Comportamento di HList.get(int) per indice fuori range inferiore.
     *
     * Formal Review:
     *   Specifica J2SE 1.4.2: get(i) deve lanciare IndexOutOfBoundsException se i &lt; 0.
     *
     * Pre-Condition:
     *   list è vuoto.
     *
     * Test Scripts:
     *   1. Chiamare list.get(-1)
     *
     * Expected Results:
     *   IndexOutOfBoundsException
     *
     * Post-Condition:
     *   list rimane vuota.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetNegativeIndex() {
        list.get(-1);
    }

    /**
     * Summary:
     *   Verifica che get(i) lanci IndexOutOfBoundsException se i &ge; size.
     *
     * Test Method Scope:
     *   Comportamento di HList.get(int) con indice troppo grande.
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: get(i) deve lanciare IndexOutOfBoundsException per i &ge; size().
     *
     * Pre-Condition:
     *   list contiene un solo elemento.
     *
     * Test Scripts:
     *   1. list.add("X")
     *   2. list.get(1)
     *
     * Expected Results:
     *   lancia IndexOutOfBoundsException
     *
     * Post-Condition:
     *   list rimane invariata con ["X"].
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetIndexOutOfBounds() {
        list.add("X");
        list.get(1);
    }

    /**
     * Summary:
     *   Verifica che add(int, E) inserisca l’elemento nella posizione specificata e sposti gli altri a destra.
     *
     * Test Method Scope:
     *   Metodo HList.add(int, E).
     *
     * Formal Review:
     *   Conformità a J2SE 1.4.2: inserimento a indice valido deve riallineare gli elementi.
     *
     * Pre-Condition:
     *   list contiene ["A", "C"] (inseriti all'inizio).
     *
     * Test Scripts:
     *   1. list.add(1, "B")
     *   2. list.size()
     *   3. list.get(0), list.get(1), list.get(2)
     *
     * Expected Results:
     *   - list.size() == 3
     *   - list.get(0).equals("A")
     *   - list.get(1).equals("B")
     *   - list.get(2).equals("C")
     *
     * Post-Condition:
     *   list = ["A","B","C"].
     */
    @Test
    public void testAddAtIndex() {
        list.add("A");
        list.add("C");
        list.add(1, "B");

        assertEquals(3, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
    }

    /**
     * Summary:
     *   Verifica che add(index, E) lanci IndexOutOfBoundsException per index &lt; 0.
     *
     * Test Method Scope:
     *   Metodo HList.add(int, E) con indice negativo.
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: add(i, e) deve lanciare IndexOutOfBoundsException se i &lt; 0.
     *
     * Pre-Condition:
     *   list è vuoto.
     *
     * Test Scripts:
     *   1. list.add(-1, "X")
     *
     * Expected Results:
     *   lancia IndexOutOfBoundsException
     *
     * Post-Condition:
     *   list rimane vuota.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAtNegativeIndex() {
        list.add(-1, "X");
    }

    /**
     * Summary:
     *   Verifica che add(index, E) lanci IndexOutOfBoundsException per index &gt; size.
     *
     * Test Method Scope:
     *   Metodo HList.add(int, E) con indice fuori range superiore.
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: add(i, e) deve lanciare IndexOutOfBoundsException se i &gt; size().
     *
     * Pre-Condition:
     *   list contiene un elemento.
     *
     * Test Scripts:
     *   1. list.add("A")
     *   2. list.add(2, "B")
     *
     * Expected Results:
     *   lancia IndexOutOfBoundsException
     *
     * Post-Condition:
     *   list rimane ["A"].
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAtTooLargeIndex() {
        list.add("A");
        list.add(2, "B");
    }

    /**
     * Summary:
     *   Verifica che remove(int) rimuova l’elemento all’indice dato, restituisca l’elemento rimosso e riallinei la lista.
     *
     * Test Method Scope:
     *   Metodo HList.remove(int).
     *
     * Formal Review:
     *   Conformità J2SE 1.4.2: l’operazione opzionale remove(int) deve restituire il valore rimosso e ridurre size di 1.
     *
     * Pre-Condition:
     *   list contiene ["A","B","C"].
     *
     * Test Scripts:
     *   1. Object removed = list.remove(1)
     *   2. list.size()
     *   3. list.get(0), list.get(1)
     *
     * Expected Results:
     *   - removed.equals("B")
     *   - list.size() == 2
     *   - list.get(0).equals("A")
     *   - list.get(1).equals("C")
     *
     * Post-Condition:
     *   list = ["A","C"].
     */
    @Test
    public void testRemoveByIndex() {
        list.add("A");
        list.add("B");
        list.add("C");

        Object removed = list.remove(1);

        assertEquals("B", removed);
        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        assertEquals("C", list.get(1));

    }

    /**
     * Summary:
     *   Verifica che remove(index) lanci IndexOutOfBoundsException per index &ge; size.
     *
     * Test Method Scope:
     *   Metodo HList.remove(int) con indice fuori range.
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: remove(i) deve lanciare IndexOutOfBoundsException se i &lt; 0 o &ge; size().
     *
     * Pre-Condition:
     *   list contiene un elemento.
     *
     * Test Scripts:
     *   1. list.add("X")
     *   2. list.remove(1)
     *
     * Expected Results:
     *   lancia IndexOutOfBoundsException
     *
     * Post-Condition:
     *   list rimane ["X"].
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveIndexOutOfBounds() {
        list.add("X");
        list.remove(1);
    }

    /**
     * Summary:
     *   Verifica remove(Object) rimuove la prima occorrenza e restituisce true/false se non trovato.
     *
     * Test Method Scope:
     *   Metodo HList.remove(Object).
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: remove(o) restituisce true se l’oggetto era presente e rimosso, false altrimenti.
     *
     * Pre-Condition:
     *   list contiene ["A","B","C"].
     *
     * Test Scripts:
     *   1. assertTrue(list.remove("B"))
     *   2. assertFalse(list.remove("Z"))
     *
     * Expected Results:
     *   - Dopo rimozione di "B": size()==2, contiene A e C
     *   - remove("Z") restituisce false, size invariata
     *
     * Post-Condition:
     *   list = ["A","C"].
     */
    @Test
    public void testRemoveByObject() {
        list.add("A");
        list.add("B");
        list.add("C");
        assertTrue(list.remove("B"));
        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        assertEquals("C", list.get(1));

        assertFalse("rimuovere un elemento non presente deve restituire false", list.remove("Z"));
        assertEquals(2, list.size());
    }

    /**
     * Summary:
     *   Verifica indexOf(Object) e lastIndexOf(Object) sul contenuto della lista.
     *
     * Test Method Scope:
     *   Metodi HList.indexOf(Object), HList.lastIndexOf(Object).
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: indexOf restituisce primo indice o -1, lastIndexOf ultimo indice o -1.
     *
     * Pre-Condition:
     *   list = ["A","B","A","C"].
     *
     * Test Scripts:
     *   1. list.indexOf("A")
     *   2. list.lastIndexOf("A")
     *   3. list.indexOf("Z")
     *
     * Expected Results:
     *   - indexOf("A")==0
     *   - lastIndexOf("A")==2
     *   - indexOf("Z")==(-1)
     *
     * Post-Condition:
     *   list invariata.
     */
    @Test
    public void testIndexOfAndLastIndexOf() {
        list.add("A");
        list.add("B");
        list.add("A");
        list.add("C");
        assertEquals(0, list.indexOf("A"));
        assertEquals(2, list.lastIndexOf("A"));
        assertEquals(1, list.indexOf("B"));
        assertEquals(1, list.lastIndexOf("B"));
        assertEquals(-1, list.indexOf("Z"));
    }

    /**
     * Summary:
     *   Verifica contains(Object) e containsAll(Collection).
     *
     * Test Method Scope:
     *   Metodi HCollection.contains(Object), HCollection.containsAll(HCollection).
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: 
     *     - contains(o) vero solo se esiste elemento uguale;
     *     - containsAll(c) vero solo se tutti gli elementi di c sono in this.
     *
     * Pre-Condition:
     *   list = ["A","B","C"], other = ["A","C"].
     *
     * Test Scripts:
     *   1. assertTrue(list.contains("B"))
     *   2. assertFalse(list.contains("Z"))
     *   3. assertTrue(list.containsAll(other))
     *   4. other.add("Z")
     *   5. assertFalse(list.containsAll(other))
     *
     * Expected Results:
     *   - contains e containsAll si comportano descritto come sopra.
     *
     * Post-Condition:
     *   list invariata.
     */
    @Test
    public void testContainsAndContainsAll() {
        list.add("A");
        list.add("B");
        list.add("C");

        assertTrue(list.contains("B"));
        assertFalse(list.contains("Z"));

        HCollection other = new ListAdapter();
        other.add("A");
        other.add("C");
        assertTrue(list.containsAll(other));

        other.add("Z");
        assertFalse(list.containsAll(other));
    }

    /**
     * Summary:
     *   Verifica addAll, removeAll e retainAll su collezioni
     *
     * Test Method Scope:
     *   Metodi HCollection.addAll, HCollection.removeAll, HCollection.retainAll.
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: 
     *     - addAll aggiunge tutti e ritorna true se cambia;
     *     - removeAll rimuove tutte le occorrenze di c e ritorna true se cambia;
     *     - retainAll mantiene solo quelli in c e ritorna true se cambia.
     *
     * Pre-Condition:
     *   list vuota, other = ["X","Y","Z"].
     *
     * Test Scripts:
     *   1. list.addAll(other)
     *   2. list.removeAll(["X","B"])
     *   3. list.clear(); list.addAll(["X","Y","Z","A"])
     *   4. list.retainAll(["Z","A"])
     *
     * Expected Results:
     *   - Dopo addAll: list=["X","Y","Z"]
     *   - Dopo removeAll: X e B rimossi, resta Y,Z
     *   - Dopo retainAll: list=["Z","A"]
     *
     * Post-Condition:
     *   list = ["Z","A"].
     */
    @Test
    public void testAddAllRemoveAllRetainAll() {
        HCollection other = new ListAdapter();
        other.add("X");
        other.add("Y");
        other.add("Z");


        assertTrue(list.addAll(other));
        assertEquals(3, list.size());
        assertEquals("X", list.get(0));
        assertEquals("Y", list.get(1));
        assertEquals("Z", list.get(2));


        list.add("A");
        list.add("B");
        HCollection toRemove = new ListAdapter();
        toRemove.add("X");
        toRemove.add("B");

        assertTrue(list.removeAll(toRemove));
        assertFalse(list.contains("X"));
        assertFalse(list.contains("B"));
        assertTrue(list.contains("Y"));
        assertTrue(list.contains("Z"));


        list.clear();
        list.add("X");
        list.add("Y");
        list.add("Z");
        list.add("A");

        HCollection toRetain = new ListAdapter();
        toRetain.add("Z");
        toRetain.add("A");

        assertTrue(list.retainAll(toRetain));
        assertEquals(2, list.size());
        assertTrue(list.contains("Z"));
        assertTrue(list.contains("A"));
        assertFalse(list.contains("X"));
        assertFalse(list.contains("Y"));

    }

    /**
     * Summary:
     *   Verifica clear() svuota la lista.
     *
     * Test Method Scope:
     *   Metodo HCollection.clear().
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: clear() rimuove tutti gli elementi.
     *
     * Pre-Condition:
     *   list contiene ["A","B"].
     *
     * Test Scripts:
     *   1. list.clear()
     *
     * Expected Results:
     *   - size() == 0
     *   - isEmpty() == true
     *
     * Post-Condition:
     *   list vuota.
     */
    @Test
    public void testClear() {
        list.add("A");
        list.add("B");
        assertEquals(2, list.size());
        list.clear();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    /**
     * Summary:
     *   Verifica equals(Object) e hashCode() per liste con stesso/differente contenuto.
     *
     * Test Method Scope:
     *   Metodi HList.equals(Object), HList.hashCode().
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: 
     *     - due liste uguali se stesse dimensioni e stesse occorrenze in ordine;
     *     - hashCode coerente con equals.
     *
     * Pre-Condition:
     *   list1 e list2 entrambi vuoti.
     *
     * Test Scripts:
     *   1. list1.add("A","B"); list2.add("A","B")
     *   2. assertTrue(list1.equals(list2))
     *   3. assertEquals(list1.hashCode(), list2.hashCode())
     *   4. list2.add("C")
     *   5. assertFalse(list1.equals(list2))
     *
     * Expected Results:
     *   equals e hashCode corretti.
     *
     * Post-Condition:
     *   list2 contiene un elemento in più.
     */
    @Test
    public void testEqualsAndHashCode() {
        HList list1 = new ListAdapter();
        HList list2 = new ListAdapter();

        list1.add("A");
        list1.add("B");

        list2.add("A");
        list2.add("B");

        assertTrue("Liste con stessi elemnti devono essere uguali", list1.equals(list2));
        assertEquals("Liste uguali devono avere stesso hash", list1.hashCode(), list2.hashCode());

        list2.add("C");
        assertFalse("Liste con elementi diversi non sono uguali\n", list1.equals(list2));
    }

    /**
     * Summary:
     *   Verifica toArray() restituisce array con tutti gli elementi in ordine.
     *
     * Test Method Scope:
     *   Metodo HCollection.toArray().
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: toArray() deve restituire Object[] di dimensione size().
     *
     * Pre-Condition:
     *   list = ["A","B","C"].
     *
     * Test Scripts:
     *   1. Object[] arr = list.toArray()
     *
     * Expected Results:
     *   - arr.length == 3
     *   - arr[0]=="A", arr[1]=="B", arr[2]=="C"
     *
     * Post-Condition:
     *   list invariata.
     */
    @Test
    public void testToArray() {
        list.add("A");
        list.add("B");
        list.add("C");

        Object[] arr = list.toArray();
        assertEquals(3, arr.length);
        assertEquals("A", arr[0]);
        assertEquals("B", arr[1]);
        assertEquals("C", arr[2]);
    }

    /**
     * Summary:
     *   Verifica toArray(T[]) riempie array esistente o ne crea uno nuovo se troppo piccolo.
     *
     * Test Method Scope:
     *   Metodo HCollection.toArray(T[]).
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: 
     *     - se array.length &ge; size: riempie e mettew null in posizione size;
     *     - se array.length &lt; size: ne crea uno nuovo di tipo T[].
     *
     * Pre-Condition:
     *   list = ["X","Y"].
     *
     * Test Scripts:
     *   1. String[] a1 = new String[3]; Object[] r1 = list.toArray(a1)
     *   2. String[] a2 = new String[1]; Object[] r2 = list.toArray(a2)
     *
     * Expected Results:
     *   - r1 == a1, r1[2]==null
     *   - r2 != a2, r2.length==2
     *
     * Post-Condition:
     *   list invariata.
     */
    @Test
    public void testToArrayWithParameter() {
        list.add("X");
        list.add("Y");

        String[] arr1 = new String[3];
        Object[] result1 = list.toArray(arr1);
        assertSame("Deve restituire l'array passato se sufficientemente grande", arr1, result1);
        assertEquals("X", arr1[0]);
        assertEquals("Y", arr1[1]);
        assertNull("L'elemento dopo gli elementi deve essere null", arr1[2]);

        String[] arr2 = new String[1];
        Object[] result2 = list.toArray(arr2);
        assertNotSame("Deve restuiuire un nuovo array se troppo picolo\n", arr2, result2);
        assertEquals("X", result2[0]);
        assertEquals("Y", result2[1]);
    }

    /**
     * Summary:
     *   Verifica iterator() scorra in avanti tutti gli elementi.
     *
     * Test Method Scope:
     *   Metodo HCollection.iterator().
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: hasNext() e next() devono visitare tutti gli elementi.
     *
     * Pre-Condition:
     *   list = ["A","B","C"].
     *
     * Test Scripts:
     *   1. HIterator it = list.iterator()
     *   2. while(it.hasNext()) it.next()
     *
     * Expected Results:
     *   - next() restituisce “A”, poi “B”, poi “C”
     *   - hasNext() dopo ultimo ritorna false
     *
     * Post-Condition:
     *   iteratore esaurito e lista invariata.
     */
    @Test
    public void testIterator() {
        list.add("A");
        list.add("B");
        list.add("C");

        HIterator it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals("A", it.next());
        assertTrue(it.hasNext());
        assertEquals("B", it.next());
        assertTrue(it.hasNext());
        assertEquals("C", it.next());
        assertFalse(it.hasNext());
    }

    /**
     * Summary:
     *   Verifica che next() lanci NoSuchElementException se nessun elemento rimane.
     *
     * Test Method Scope:
     *   HIterator.next() oltre la fine.
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: next() dopo ultima chiamata deve lanciare NoSuchElementException.
     *
     * Pre-Condition:
     *   list = ["X"], it = list.iterator().
     *
     * Test Scripts:
     *   1. it.next()
     *   2. it.next()
     *
     * Expected Results:
     *   lancia NoSuchElementException
     *
     * Post-Condition:
     *   list invariata.
     */
    @Test(expected = java.util.NoSuchElementException.class)
    public void testIteratorNextNoSuchElement() {
        list.add("X");
        HIterator it = list.iterator();
        it.next();
        it.next();
    }

    /**
     * Summary:
     *   Verifica che remove() prima di next() lanci IllegalStateException.
     *
     * Test Method Scope:
     *   HIterator.remove() senza next().
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: remove() illegale se non è stato invocato next() o previous() in precedenza.
     *
     * Pre-Condition:
     *   list = ["Y"], it = list.iterator().
     *
     * Test Scripts:
     *   1. it.remove()
     *
     * Expected Results:
     *   lancia IllegalStateException
     *
     * Post-Condition:
     *   list invariata.
     */
    @Test(expected = IllegalStateException.class)
    public void testIteratorRemoveBeforeNext() {
        list.add("Y");
        HIterator it = list.iterator();
        it.remove();
    }

    /**
     * Summary:
     *   Verifica che remove() elimini l’ultimo elemento restituito da next().
     *
     * Test Method Scope:
     *   HIterator.remove() dopo next().
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: remove() rimuove l’ultimo elemento visitato.
     *
     * Pre-Condition:
     *   list = ["1","2"], it = iterator().
     *
     * Test Scripts:
     *   1. it.next()
     *   2. it.remove()
     *
     * Expected Results:
     *   - list.size()==1
     *   - list.get(0).equals("2")
     *
     * Post-Condition:
     *   list = ["2"].
     */
    @Test
    public void testIteratorRemove() {
        list.add("1");
        list.add("2");
        HIterator it = list.iterator();
        it.next();
        it.remove();   
        assertEquals(1, list.size());
        assertEquals("2", list.get(0));

    }

    /**
     * Summary:
     *   Verifica HListIterator.next()/hasPrevious()/previous() per avanzare e tornare indietro.
     *
     * Test Method Scope:
     *   HListIterator.next(), hasPrevious(), previous().
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: comportamento bidirezionale dell’iterator di lista.
     *
     * Pre-Condition:
     *   list = ["A","B","C"], it = list.listIterator().
     *
     * Test Scripts:
     *   1. it.next() → “A”
     *   2. it.next() → “B”
     *   3. it.hasPrevious()
     *   4. it.previous() → “B”
     *   5. it.previous() → “A”
     *
     * Expected Results:
     *   come descritto sopra
     *
     * Post-Condition:
     *   it è all’inizio, list invariata.
     */
    @Test
    public void testListIteratorForwardBackward() {
        list.add("A");
        list.add("B");
        list.add("C");

        HListIterator it = list.listIterator();
        assertTrue(it.hasNext());
        assertEquals("A", it.next());
        assertEquals("B", it.next());
        assertTrue(it.hasPrevious());
        assertEquals("B", it.previous());
        assertEquals("A", it.previous());
        assertFalse(it.hasPrevious());
    }

    /**
     * Summary:
     *   Verifica add(E), set(E) e remove() su HListIterator.
     *
     * Test Method Scope:
     *   Metodi HListIterator.add, HListIterator.set, HIterator.remove().
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: operazioni opzionali add e set, rmove dopo next  previous consentito.
     *
     * Pre-Condition:
     *   list vuota.
     *
     * Test Scripts:
     *   1. it.add("X")
     *   2. it.next()
     *   3. it.set("Y")
     *   4. it.remove()
     *
     * Expected Results:
     *   - Dopo add: list=["X"]
     *   - Dopo set: list=["Y"]
     *   - Dopo remove: lista vuota
     *
     * Post-Condition:
     *   list vuota.
     */
    @Test
    public void testListIteratorAddSetRemove() {
        HListIterator it = list.listIterator();
        it.add("X");
        assertEquals(1, list.size());
        assertEquals("X", list.get(0));

        it = list.listIterator();
        assertEquals("X", it.next());
        it.set("Y");
        assertEquals("Y", list.get(0));

        it.remove();
        assertTrue(list.isEmpty());
    }

    /**
     * Summary:
     *   Verifica che set(E) lanci IllegalStateException se invocato prima di next()/previous().
     *
     * Test Method Scope:
     *   HListIterator.set(E) in stato non valido.
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: set illegale se non c’è un elemento corrente.
     *
     * Pre-Condition:
     *   list = ["A"], it = list.listIterator().
     *
     * Test Scripts:
     *   1. it.set("B")
     *
     * Expected Results:
     *   lancio di IllegalStateException
     *
     * Post-Condition:
     *   list invariata.
     */
    @Test(expected = IllegalStateException.class)
    public void testListIteratorSetBeforeNext() {
        list.add("A");
        HListIterator it = list.listIterator();
        it.set("B");
    }

    /**
     * Summary:
     *   Verifica che next() su HListIterator oltre la fine lanci NoSuchElementException.
     *
     * Test Method Scope:
     *   HListIterator.next() oltre ultimo.
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: next() dopo fine deve lanciare NoSuchElementException.
     *
     * Pre-Condition:
     *   list = ["A"], it = list.listIterator().
     *
     * Test Scripts:
     *   1. it.next()
     *   2. it.next()
     *
     * Expected Results:
     *   lancio di NoSuchElementException
     *
     * Post-Condition:
     *   list invariata.
     */
    @Test(expected = java.util.NoSuchElementException.class)
    public void testListIteratorNextTooFar() {
        list.add("A");
        HListIterator it = list.listIterator();
        it.next();
        it.next();
    }

    /**
     * Summary:
     *   Verifica subList(from,to) crea vista su porzione.
     *
     * Test Method Scope:
     *   HList,subList(int, int) e relative operazioni
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: subList deve restituire vista backed by original list.
     *
     * Pre-Condition:
     *   list = ["A","B","C","D"].
     *
     * Test Scripts:
     *   1. HList sub = list.subList(1,3)
     *   2. sub.remove(0)
     *   3. list.set(2,"Z")
     *
     * Expected Results:
     *   - sub iniziale = ["B","C"]
     *   - dopo sub.remove(0): list = ["A","C","D"], sub = ["C"]
     *   - dopo list.set: list[2]=="Z"
     *
     * Post-Condition:
     *   list = ["A","C","Z"]  sub = ["C"].
     */
    @Test
    public void testSubListView() {
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");

        HList sub = list.subList(1, 3);
        assertEquals(2, sub.size());
        assertEquals("B", sub.get(0));
        assertEquals("C", sub.get(1));

        sub.remove(0); 

        assertEquals(3, list.size());
        assertEquals("C", list.get(1));
        assertEquals(1, sub.size());
        assertEquals("C", sub.get(0));

        list.set(2, "Z");
        assertEquals("Z", list.get(2));
    }

    /**
     * Summary:
     *   Verifica addAll(index, c) inserisce tutti gli elementi di c nella posizione specificata.
     *
     * Test Method Scope:
     *   HList.addAll(int, HCollection).
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: addAll(i, c) deve restituire true se cambia.
     *
     * Pre-Condition:
     *   list = ["A","D"], toAdd = ["B","C"].
     *
     * Test Scripts:
     *   1. list.addAll(1, toAdd)
     *
     * Expected Results:
     *   list = ["A","B","C","D"]
     *
     * Post-Condition:
     *   list contiene A,B,C,D in ordine.
     */
    @Test
    public void testAddAllAtIndex() {
        list.add("A");
        list.add("D");

        HCollection toAdd = new ListAdapter();
        toAdd.add("B");
        toAdd.add("C");

        assertTrue(list.addAll(1, toAdd));
        assertEquals(4, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
        assertEquals("D", list.get(3));
    }

    /**
     * Summary:
     *   Verifica addAll(index, c) lanci IndexOutOfBoundsException per index fuori range.
     *
     * Test Method Scope:
     *   HList.addAll(int, HCollection) con indice invalido.
     *
     * Formal Review:
     *   Spec J2SE 1.4.2: addAll(i,c) deve lanciare IndexOutOfBoundsException se i &lt; 0 o &gt; size.
     *
     * Pre-Condition:
     *   list vuota, c = ["X"].
     *
     * Test Scripts:
     *   1. list.addAll(1, c)
     *
     * Expected Results:
     *   lancio di IndexOutOfBoundsException
     *
     * Post-Condition:
     *   list rimane vuota.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAllAtInvalidIndex() {
        HCollection c = new ListAdapter();
        c.add("X");
        list.addAll(1, c);
    }

}
