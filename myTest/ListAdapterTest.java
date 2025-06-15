package myTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import myAdapter.HCollection;
import myAdapter.HIterator;
import myAdapter.HList;
import myAdapter.HListIterator;
import myAdapter.ListAdapter;

/**
 * Classe di test per {@code ListAdapter}.
 * Contiene test unitari per verificarne il comportamento rispetto all'interfaccia {@code HList}.
 * <p>
 * <strong>Summary:</strong> Questa suite di test verifica il comportamento della classe {@code ListAdapter}, assicurando che rispetti le specifiche dell'interfaccia {@code HList} attraverso test su:
 * <ul>
 *   <li>Operazioni di inserimento, rimozione, ricerca, accesso per indice</li>
 *   <li>Gestione degli iteratori (sia {@code HIterator} che {@code HListIterator})</li>
 *   <li>Operazioni di confronto, conversione in array, subList</li>
 * </ul>
 *
 * <p>
 * <strong>Design:</strong> I test coprono sia condizioni normali che casi limite e comportamenti eccezionali. Ogni test è documentato secondo le specifiche J2SE 1.4.2. I metodi sono testati con
 * input validi e invalidi, verificando pre-condizioni, post-condizioni e risultati attesi. I metodi {@code subList}, {@code listIterator} e altri comportamenti dinamici sono inclusi per copertura estesa.
 *
 * <p>
 * <strong>Pre-Condition:</strong> Prima di ogni test, viene creata una nuova istanza vuota di {@link ListAdapter} tramite {@link #setUp()}.
 *
 * <p>
 * <strong>Expected Results:</strong> Ogni operazione deve comportarsi in accordo con le specifiche di {@code HList} e lanciare eccezioni quando previsto. In caso contrario, il test fallisce.
 *
 * <p>
 * <strong>Post-Condition:</strong> Le modifiche alla lista sono coerenti con le operazioni effettuate, o la lista rimane invariata in caso di errore atteso.
 *
 * <p>
 * <strong>Test Case Execution:</strong> I test sono eseguiti con JUnit 4.13.2 e automatizzati tramite {@link myTest.TestRunner}.
 *
 * <p>
 * <strong>Dipendenze:</strong> {@link ListAdapter}, {@link HList}, {@link HIterator}, {@link HListIterator}, {@link HCollection}, {@code junit-4.13.2.jar}
 */
public class ListAdapterTest {
    /**
     * Lista da testare, viene reinizializzata prima di ogni test.
     */
    private HList list;

    /**
     * Setup eseguito prima di ogni test. Inizializza la lista come vuota.
     */
    @Before
    public void setUp() {
        list = new ListAdapter();
    }
    
    /**
     * Test del metodo {@link myAdapter.HList#isEmpty()} e {@link myAdapter.HList#size()} su una lista vuota.
     * 
     * @summary.test Verifica che una nuova lista sia inizialmente vuota.
     * 
     * @design.test Controlla che {@code isEmpty()} ritorni {@code true} e {@code size()} ritorni 0 per una nuova lista vuota.
     * 
     * @description.test
     * <ul>
     *   <li>Chiamare {@code list.size()}</li>
     *   <li>Chiamare {@code list.isEmpty()}</li>
     * </ul>
     * 
     * @precondition.test La lista è appena inizializzata ed è vuota.
     * 
     * @postcondition.test La lista rimane vuota.
     * 
     * @result.test {@code size()} deve restituire 0, {@code isEmpty()} deve restituire {@code true}.
     */
    @Test
    public void testEmptyList() {
        assertEquals("Una nuova lista dovrebbe avere size 0", 0, list.size());
        assertTrue("Una nuova lista dovrebbe essere vuota", list.isEmpty());
    }

    //

    /**
     * Test dei metodi {@link myAdapter.HList#add(Object)} e {@link myAdapter.HList#get(int)}.
     * 
     * @summary.test Verifica che gli elementi vengano aggiunti in coda e letti in ordine corretto tramite {@code get()}.
     * 
     * @design.test Inserisce due elementi e verifica l'ordine e la dimensione.
     * 
     * @description.test
     * <ul>
     *   <li>Inserire "A" e "B" con {@code add()}</li>
     *   <li>Verificare con {@code get(i)} e {@code size()}</li>
     * </ul>
     * 
     * @precondition.test La lista è vuota.
     * 
     * @postcondition.test La lista contiene ["A", "B"].
     * 
     * @result.test {@code get(0) == "A"}; {@code get(1) == "B"}; {@code size() == 2}.
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
     * Test del metodo {@link myAdapter.HList#get(int)} con indice negativo.
     * 
     * @summary.test Verifica che venga lanciata {@link IndexOutOfBoundsException} per indice negativo.
     * 
     * @design.test Accede alla lista con indice -1.
     * 
     * @description.test Chiama {@code list.get(-1)} e si aspetta un'eccezione.
     * 
     * @precondition.test Lista vuota.
     * 
     * @postcondition.test Lista invariata.
     * 
     * @result.test Lancio di {@code IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetNegativeIndex() {
        list.get(-1);
    }

    /**
     * Test del metodo {@link myAdapter.HList#get(int)} con indice uguale alla dimensione.
     *
     * @summary.test Verifica che {@code get(i)} lanci {@link IndexOutOfBoundsException} se {@code i >= size()}.
     *
     * @design.test Inserisce un elemento, quindi tenta di accedere a {@code get(1)}.
     *
     * @description.test
     * <ul>
     *   <li>Inserisce "X" con {@code add()}</li>
     *   <li>Chiama {@code get(1)} e verifica il lancio di eccezione</li>
     * </ul>
     *
     * @precondition.test Lista contiene un solo elemento.
     *
     * @postcondition.test Lista rimane invariata.
     *
     * @result.test Deve essere lanciata {@code IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetIndexOutOfBounds() {
        list.add("X");
        list.get(1);
    }

    /**
     * Test del metodo {@link myAdapter.HList#add(int, Object)}.
     *
     * @summary.test Verifica che un elemento venga correttamente inserito a un indice specifico e che gli altri vengano spostati.
     *
     * @design.test Inserisce "A" e "C", poi "B" in mezzo. Controlla l’ordine risultante.
     *
     * @description.test
     * <ul>
     *   <li>{@code list.add("A")}</li>
     *   <li>{@code list.add("C")}</li>
     *   <li>{@code list.add(1, "B")}</li>
     * </ul>
     *
     * @precondition.test Lista inizialmente vuota.
     *
     * @postcondition.test Lista contiene ["A", "B", "C"].
     *
     * @result.test Gli elementi sono correttamente ordinati.
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
     * Test del metodo {@link myAdapter.HList#add(int, Object)} con indice negativo.
     *
     * @summary.test Verifica che {@code add()} con indice negativo lanci {@link IndexOutOfBoundsException}.
     *
     * @design.test Si tenta {@code add(-1, "X")} su lista vuota.
     *
     * @description.test
     * <ul>
     *   <li>Chiamata a {@code list.add(-1, "X")}</li>
     * </ul>
     *
     * @precondition.test Lista vuota.
     *
     * @postcondition.test Lista invariata.
     *
     * @result.test Deve essere lanciata {@code IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAtNegativeIndex() {
        list.add(-1, "X");
    }

    /**
     * Test del metodo {@link myAdapter.HList#add(int, Object)} con indice maggiore di {@code size()}.
     *
     * @summary.test Verifica che venga lanciata {@link IndexOutOfBoundsException} per indice non valido.
     *
     * @design.test Inserisce un elemento, poi tenta {@code add(2, "B")}.
     *
     * @description.test
     * <ul>
     *   <li>{@code list.add("A")}</li>
     *   <li>{@code list.add(2, "B")}</li>
     * </ul>
     *
     * @precondition.test Lista ha un solo elemento.
     *
     * @postcondition.test Lista rimane ["A"].
     *
     * @result.test Deve essere lanciata {@code IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAtTooLargeIndex() {
        list.add("A");
        list.add(2, "B");
    }

    /**
     * Test del metodo {@link myAdapter.HList#remove(int)}.
     *
     * @summary.test Verifica la rimozione dell’elemento all’indice specificato e la restituzione corretta.
     *
     * @design.test Inserisce ["A", "B", "C"], rimuove "B" da indice 1 e verifica lista aggiornata.
     *
     * @description.test
     * <ul>
     *   <li>{@code list.remove(1)}</li>
     *   <li>Verifica dimensione e contenuto della lista</li>
     * </ul>
     *
     * @precondition.test Lista contiene ["A", "B", "C"].
     *
     * @postcondition.test Lista = ["A", "C"].
     *
     * @result.test {@code remove(1)} restituisce "B" e riduce la size.
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
     * Test del metodo {@link myAdapter.HList#remove(int)} con indice fuori range superiore.
     *
     * @summary.test Verifica che {@code remove(i)} lanci {@link IndexOutOfBoundsException} se {@code i >= size()}.
     *
     * @design.test Inserisce "X" e tenta {@code remove(1)}.
     *
     * @description.test
     * <ul>
     *   <li>Inserisce "X" con {@code add()}</li>
     *   <li>Chiama {@code remove(1)}</li>
     * </ul>
     *
     * @precondition.test Lista contiene un elemento.
     *
     * @postcondition.test Lista invariata.
     *
     * @result.test Deve essere lanciata {@code IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveIndexOutOfBounds() {
        list.add("X");
        list.remove(1);
    }

    /**
     * Test del metodo {@link myAdapter.HCollection#remove(Object)}.
     *
     * @summary.test Verifica la rimozione di un oggetto esistente e il fallimento su oggetto inesistente.
     *
     * @design.test Inserisce ["A", "B", "C"], rimuove "B", verifica successo. Tenta rimozione di "Z", verifica fallimento.
     *
     * @description.test
     * <ul>
     *   <li>{@code remove("B")} → true</li>
     *   <li>{@code remove("Z")} → false</li>
     * </ul>
     *
     * @precondition.test Lista = ["A", "B", "C"].
     *
     * @postcondition.test Lista = ["A", "C"].
     *
     * @result.test Rimozione valida restituisce {@code true}, invalida {@code false}.
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
     * Test dei metodi {@link myAdapter.HList#indexOf(Object)} e {@link myAdapter.HList#lastIndexOf(Object)}.
     *
     * @summary.test Verifica il comportamento delle ricerche di primo e ultimo indice di un elemento.
     *
     * @design.test Inserisce ["A", "B", "A", "C"], verifica gli indici di "A", "B" e un elemento assente.
     *
     * @description.test
     * <ul>
     *   <li>{@code indexOf("A")} → 0</li>
     *   <li>{@code lastIndexOf("A")} → 2</li>
     *   <li>{@code indexOf("Z")} → -1</li>
     * </ul>
     *
     * @precondition.test Lista = ["A", "B", "A", "C"].
     *
     * @postcondition.test Lista invariata.
     *
     * @result.test Indici corretti restituiti per elementi presenti/assenti.
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
     * Test dei metodi {@link myAdapter.HCollection#contains(Object)} e {@link myAdapter.HCollection#containsAll(HCollection)}.
     *
     * @summary.test Verifica la presenza di singoli elementi e collezioni all’interno della lista.
     *
     * @design.test Crea lista ["A","B","C"] e collezione ["A","C"], verifica i metodi.
     *
     * @description.test
     * <ul>
     *   <li>{@code contains("B")} → true</li>
     *   <li>{@code contains("Z")} → false</li>
     *   <li>{@code containsAll(["A","C"])} → true</li>
     *   <li>{@code containsAll(["A","C","Z"])} → false</li>
     * </ul>
     *
     * @precondition.test Lista popolata; collezioni create.
     *
     * @postcondition.test Lista invariata.
     *
     * @result.test Ritorni corretti per i metodi {@code contains} e {@code containsAll}.
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
     * Test dei metodi {@link myAdapter.HCollection#addAll(HCollection)}, {@link myAdapter.HCollection#removeAll(HCollection)} e {@link myAdapter.HCollection#retainAll(HCollection)}.
     *
     * @summary.test Verifica operazioni di aggiunta, rimozione e intersezione di collezioni.
     *
     * @design.test Lavora su ["X","Y","Z"] e verifica effetto dei metodi addAll, removeAll e retainAll.
     *
     * @description.test
     * <ul>
     *   <li>{@code addAll(["X","Y","Z"])} → aggiunge tutti</li>
     *   <li>{@code removeAll(["X","B"])} → rimuove "X"</li>
     *   <li>{@code retainAll(["Z","A"])} → mantiene "Z", "A"</li>
     * </ul>
     *
     * @precondition.test Lista inizialmente vuota; collezioni create.
     *
     * @postcondition.test Lista aggiornata coerentemente con operazioni.
     *
     * @result.test Le tre operazioni modificano correttamente la lista.
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
     * Test del metodo {@link myAdapter.HCollection#clear()}.
     *
     * @summary.test Verifica che la lista venga svuotata correttamente.
     *
     * @design.test Aggiunge elementi, chiama {@code clear()} e verifica che {@code size()==0} e {@code isEmpty()==true}.
     *
     * @description.test
     * <ul>
     *   <li>{@code list.clear()}</li>
     * </ul>
     *
     * @precondition.test Lista con ["A", "B"].
     *
     * @postcondition.test Lista vuota.
     *
     * @result.test Dopo clear, {@code size()==0}, {@code isEmpty()==true}.
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
     * Test dei metodi {@link myAdapter.HList#equals(Object)} e {@link myAdapter.HList#hashCode()}.
     *
     * @summary.test Verifica uguaglianza e hashCode coerenti tra liste uguali e diversi tra liste diverse.
     *
     * @design.test Confronta liste con stessi elementi, poi aggiunge un elemento a una delle due.
     *
     * @description.test
     * <ul>
     *   <li>{@code list1.equals(list2)} → true</li>
     *   <li>{@code list1.hashCode() == list2.hashCode()}</li>
     *   <li>Modifica list2 e verifica {@code equals()} → false</li>
     * </ul>
     *
     * @precondition.test Entrambe le liste sono inizialmente vuote.
     *
     * @postcondition.test list2 contiene un elemento in più.
     *
     * @result.test Uguaglianza e hash coerenti con contenuto.
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
     * Test del metodo {@link myAdapter.HCollection#toArray()}.
     *
     * @summary.test Verifica che {@code toArray()} restituisca un array con tutti gli elementi in ordine.
     *
     * @design.test Aggiunge tre elementi e controlla array ritornato.
     *
     * @description.test
     * <ul>
     *   <li>{@code toArray()} restituisce un array di size == 3</li>
     *   <li>Contenuto = ["A", "B", "C"]</li>
     * </ul>
     *
     * @precondition.test Lista = ["A", "B", "C"].
     *
     * @postcondition.test Lista invariata.
     *
     * @result.test Array con contenuto identico e ordinato.
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
     * Test del metodo {@link myAdapter.HCollection#toArray(Object[])}.
     *
     * @summary.test Verifica che {@code toArray(T[])} riempia l’array passato o ne crei uno nuovo.
     *
     * @design.test Due test: uno con array abbastanza grande, uno con array troppo piccolo.
     *
     * @description.test
     * <ul>
     *   <li>array1.length == 3 → restituito e riempito</li>
     *   <li>array2.length == 1 → viene creato nuovo array</li>
     * </ul>
     *
     * @precondition.test Lista = ["X", "Y"].
     *
     * @postcondition.test Lista invariata.
     *
     * @result.test Comportamento conforme alla specifica.
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
     * Test del metodo {@link myAdapter.HCollection#iterator()}.
     *
     * @summary.test Verifica che l’iteratore restituisca correttamente tutti gli elementi in ordine.
     *
     * @design.test Inserisce ["A", "B", "C"] e scorre l’iteratore usando {@code hasNext()} e {@code next()}.
     *
     * @description.test
     * <ul>
     *   <li>Scorre l’iteratore con {@code next()}</li>
     *   <li>Controlla i valori restituiti</li>
     * </ul>
     *
     * @precondition.test Lista = ["A", "B", "C"].
     *
     * @postcondition.test Iteratore esaurito, lista invariata.
     *
     * @result.test {@code next()} restituisce tutti gli elementi, {@code hasNext()} è false alla fine.
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
     * Test del metodo {@link myAdapter.HIterator#next()} oltre la fine.
     *
     * @summary.test Verifica che {@code next()} lanci {@link java.util.NoSuchElementException} se chiamato oltre la fine.
     *
     * @design.test Inserisce un solo elemento e chiama {@code next()} due volte.
     *
     * @description.test
     * <ul>
     *   <li>{@code it.next()} → "X"</li>
     *   <li>{@code it.next()} → eccezione</li>
     * </ul>
     *
     * @precondition.test Lista = ["X"].
     *
     * @postcondition.test Lista invariata.
     *
     * @result.test Deve essere lanciata {@code NoSuchElementException}.
     */
    @Test(expected = java.util.NoSuchElementException.class)
    public void testIteratorNextNoSuchElement() {
        list.add("X");
        HIterator it = list.iterator();
        it.next();
        it.next();
    }

    /**
     * Test del metodo {@link myAdapter.HIterator#remove()} chiamato prima di {@code next()}.
     *
     * @summary.test Verifica che {@code remove()} lanci {@link IllegalStateException} se chiamato prima di {@code next()}.
     *
     * @design.test Crea iteratore e chiama direttamente {@code remove()}.
     *
     * @description.test
     * <ul>
     *   <li>Lista = ["Y"]</li>
     *   <li>{@code it.remove()} → eccezione</li>
     * </ul>
     *
     * @precondition.test Lista con un solo elemento.
     *
     * @postcondition.test Lista invariata.
     *
     * @result.test Deve essere lanciata {@code IllegalStateException}.
     */
    @Test(expected = IllegalStateException.class)
    public void testIteratorRemoveBeforeNext() {
        list.add("Y");
        HIterator it = list.iterator();
        it.remove();
    }

    /**
     * Test del metodo {@link myAdapter.HIterator#remove()} dopo {@code next()}.
     *
     * @summary.test Verifica che {@code remove()} elimini l’ultimo elemento visitato.
     *
     * @design.test Inserisce ["1","2"], chiama {@code next()}, poi {@code remove()} e verifica contenuto.
     *
     * @description.test
     * <ul>
     *   <li>{@code next()} → "1"</li>
     *   <li>{@code remove()} → rimuove "1"</li>
     * </ul>
     *
     * @precondition.test Lista = ["1", "2"].
     *
     * @postcondition.test Lista = ["2"].
     *
     * @result.test Elemento correttamente rimosso.
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
     * Test dei metodi {@link myAdapter.HListIterator#next()}, {@link myAdapter.HListIterator#previous()},
     * {@link myAdapter.HListIterator#hasNext()}, {@link myAdapter.HListIterator#hasPrevious()}.
     *
     * @summary.test Verifica che {@code listIterator()} permetta navigazione avanti/indietro corretta.
     *
     * @design.test Inserisce ["A","B","C"], avanza e poi torna indietro con l’iteratore.
     *
     * @description.test
     * <ul>
     *   <li>{@code next()} due volte → "A", "B"</li>
     *   <li>{@code previous()} due volte → "B", "A"</li>
     * </ul>
     *
     * @precondition.test Lista = ["A","B","C"].
     *
     * @postcondition.test Iteratore torna all’inizio; lista invariata.
     *
     * @result.test Navigazione avanti/indietro funziona correttamente.
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
     * Test dei metodi {@link myAdapter.HListIterator#add(Object)}, {@link myAdapter.HListIterator#set(Object)}, {@link myAdapter.HListIterator#remove()}.
     *
     * @summary.test Verifica comportamento dell’iteratore bidirezionale nelle modifiche alla lista.
     *
     * @design.test
     * <ol>
     *   <li>Aggiunge "X" con {@code add()}</li>
     *   <li>Modifica "X" con {@code set("Y")}</li>
     *   <li>Rimuove "Y" con {@code remove()}</li>
     * </ol>
     *
     * @precondition.test Lista vuota.
     *
     * @postcondition.test Lista tornata vuota.
     *
     * @result.test Tutte le operazioni effettuano correttamente la modifica attesa.
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
     * Test del metodo {@link myAdapter.HListIterator#set(Object)} chiamato prima di {@code next()}.
     *
     * @summary.test Verifica che venga lanciata {@link IllegalStateException} se {@code set()} è chiamato senza elemento corrente.
     *
     * @design.test Inserisce "A" e chiama {@code set("B")} senza {@code next()}.
     *
     * @description.test
     * <ul>
     *   <li>{@code it.set("B")} → eccezione</li>
     * </ul>
     *
     * @precondition.test Lista = ["A"].
     *
     * @postcondition.test Lista invariata.
     *
     * @result.test Deve essere lanciata {@code IllegalStateException}.
     */
    @Test(expected = IllegalStateException.class)
    public void testListIteratorSetBeforeNext() {
        list.add("A");
        HListIterator it = list.listIterator();
        it.set("B");
    }

    /**
     * Test del metodo {@link myAdapter.HListIterator#next()} oltre la fine della lista.
     *
     * @summary.test Verifica che {@code next()} lanci {@link java.util.NoSuchElementException} quando non ci sono più elementi.
     *
     * @design.test Inserisce "A", chiama {@code next()} due volte.
     *
     * @precondition.test Lista = ["A"].
     *
     * @postcondition.test Lista invariata.
     *
     * @result.test Deve essere lanciata {@code NoSuchElementException}.
     */
    @Test(expected = java.util.NoSuchElementException.class)
    public void testListIteratorNextTooFar() {
        list.add("A");
        HListIterator it = list.listIterator();
        it.next();
        it.next();
    }

    /**
     * Test del metodo {@link myAdapter.HList#subList(int, int)} e operazioni sulla vista risultante.
     *
     * @summary.test Verifica che la subList sia una vista sulla lista padre e che le modifiche siano riflesse.
     *
     * @design.test
     * <ul>
     *   <li>Crea subList [1,3] → ["B", "C"]</li>
     *   <li>Rimuove "B" dalla sublist</li>
     *   <li>Modifica "D" in "Z" nella lista padre</li>
     * </ul>
     *
     * @precondition.test Lista = ["A", "B", "C", "D"].
     *
     * @postcondition.test Lista = ["A", "C", "Z"]; subList = ["C"].
     *
     * @result.test Sublist riflette correttamente le modifiche bidirezionali.
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
     * Test del metodo {@link myAdapter.HList#addAll(int, HCollection)}.
     *
     * @summary.test Verifica inserimento di una collezione in posizione intermedia.
     *
     * @design.test Lista = ["A", "D"]; collezione = ["B", "C"]; inserimento in indice 1.
     *
     * @precondition.test Lista e collezione create.
     *
     * @postcondition.test Lista = ["A", "B", "C", "D"].
     *
     * @result.test Elementi correttamente inseriti.
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
     * Test del metodo {@link myAdapter.HList#addAll(int, HCollection)} con indice invalido.
     *
     * @summary.test Verifica lancio di {@link IndexOutOfBoundsException} se l’indice è maggiore di {@code size()}.
     *
     * @design.test Lista vuota, tenta inserimento in indice 1.
     *
     * @precondition.test Lista vuota, collezione = ["X"].
     *
     * @postcondition.test Lista invariata.
     *
     * @result.test Deve essere lanciata {@code IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAllAtInvalidIndex() {
        HCollection c = new ListAdapter();
        c.add("X");
        list.addAll(1, c);
    }

}
