package myTest;

import myAdapter.HList;
import myAdapter.ListAdapter;
import myAdapter.SubListAdapter;

import myAdapter.HIterator;
import myAdapter.HListIterator;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Classe di test per {@code SubListAdapter}.
 * Contiene casi di test per ciascun metodo pubblico della classe.
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

    /** Costruttore di default */
    public SubListAdapterTest(){}

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

    /**
     * Test del metodo {@link myAdapter.HList#add(Object)}
     * 
     * @summary.test Verifica che {@code add(Object)} aggiunga un elemento i fondo alla sublist.
     *
     * @design.test Crea una sublist ["B","C","D"], aggiunge "X".
     *
     * @description.test {@code sub.add("X")} → ["B","C","D","X"]
     *
     * @precondition.test Lista padre = ["A","B","C","D","E"]
     *
     * @postcondition.test sublist e parent aggiornate coerentemente.
     *
     * @result.test sub.size() == 4, ultimo elemento == "X", parent aggiornato.
     */
    @Test
    public void testAddObject() {
        sub = new SubListAdapter(parent, 1, 4); // ["B", "C", "D"]
        sub.add("X");
        assertEquals(4, sub.size());
        assertEquals("X", sub.get(3));
        assertEquals("X", parent.get(4)); // perché offset era 1, sub aveva 3 → 1+3=4
    }

    /**
     * Test del metodo {@link myAdapter.HList#remove(Object)}
     * 
     * @summary.test Verifica che {@code remove(Object)} rimuova la prima occorrenza nella sublist.
     *
     * @design.test sub = ["B","C","D"], rimuove "C".
     *
     * @description.test {@code sub.remove("C")} → ["B","D"]
     *
     * @precondition.test Lista padre = ["A","B","C","D","E"]
     *
     * @postcondition.test sub e parent aggiornate; dimensione ridotta di 1.
     *
     * @result.test Ritorna true se elemento presente e rimosso.
     */
    @Test
    public void testRemoveObject() {
        sub = new SubListAdapter(parent, 1, 4); // ["B", "C", "D"]
        boolean result = sub.remove("C");
        assertTrue(result);
        assertEquals(2, sub.size());
        assertEquals("D", sub.get(1));
        assertEquals(4, parent.size()); // da 5 a 4
    } 

    /**
     * Test del metodo {@link myAdapter.HList#contains(Object)}
     * 
     * @summary.test Verifica che {@code contains(Object)} individui correttamente gli elementi nella sublist.
     *
     * @design.test sub = ["B","C","D"], verifica presenza di "C" e assenza di "X".
     *
     * @description.test {@code sub.contains("C")} → true, {@code sub.contains("X")} → false
     *
     * @precondition.test Lista padre = ["A","B","C","D","E"]
     *
     * @postcondition.test Nessuna modifica.
     *
     * @result.test true solo se elemento presente.
     */
    @Test
    public void testContainsObject() {
        sub = new SubListAdapter(parent, 1, 4); // ["B", "C", "D"]
        assertTrue(sub.contains("C"));
        assertFalse(sub.contains("X"));
    }

    /**
     * Test del metodo {@link myAdapter.HList#containsAll(HCollection)}
     * 
     * @summary.test Verifica che {@code containsAll} riconosca correttamente insiemi completamente inclusi o meno.
     *
     * @design.test sub = ["B", "C", "D"], collezione contiene "C" e "D"
     *
     * @description.test {@code sub.containsAll(c)} → true se tutti presenti, false se almeno uno manca
     *
     * @precondition.test Lista padre = ["A","B","C","D","E"]
     *
     * @postcondition.test Nessuna modifica
     *
     * @result.test Ritorna true solo se tutti gli elementi sono presenti
     */
    @Test
    public void testContainsAll() {
        sub = new SubListAdapter(parent, 1, 4); // ["B", "C", "D"]

        ListAdapter other = new ListAdapter();
        other.add("C");
        other.add("D");

        assertTrue(sub.containsAll(other));
        other.add("X");
        assertFalse(sub.containsAll(other));
    }

    /**
     * Test del metodo {@link myAdapter.HList#addAll(HCollection)}
     * 
     * @summary.test Verifica che {@code addAll(c)} aggiunga correttamente gli elementi in fondo alla sottolista.
     *
     * @design.test sub = ["B", "C", "D"], aggiunge ["X", "Y"]
     *
     * @description.test {@code sub.addAll(c)} → ["B", "C", "D", "X", "Y"]
     *
     * @precondition.test Lista padre = ["A","B","C","D","E"]
     *
     * @postcondition.test sub e parent aggiornate coerentemente
     *
     * @result.test La dimensione è aggiornata e gli elementi finali corrispondono
     */
    @Test
    public void testAddAllAtEnd() {
        sub = new SubListAdapter(parent, 1, 4); // ["B", "C", "D"]

        ListAdapter toAdd = new ListAdapter();
        toAdd.add("X");
        toAdd.add("Y");

        assertTrue(sub.addAll(toAdd));
        assertEquals(5, sub.size());
        assertEquals("X", sub.get(3));
        assertEquals("Y", sub.get(4));
    }

    /**
     * Test del metodo {@link myAdapter.HList#addAll(int, HCollection)}
     * 
     * @summary.test Verifica che {@code addAll(int, c)} inserisca gli elementi nel punto corretto della sottolista.
     *
     * @design.test sub = ["B", "C", "D"], aggiunge ["X", "Y"] in posizione 1
     *
     * @description.test {@code sub.addAll(1, c)} → ["B", "X", "Y", "C", "D"]
     *
     * @precondition.test Lista padre = ["A","B","C","D","E"].
     *
     * @postcondition.test sub e parent aggiornate; ordini mantenuti
     *
     * @result.test Gli elementi sono posizionati correttamente nella sublist
     */
    @Test
    public void testAddAllAtIndex() {
        sub = new SubListAdapter(parent, 1, 4); // ["B", "C", "D"]

        ListAdapter toAdd = new ListAdapter();
        toAdd.add("X");
        toAdd.add("Y");

        assertTrue(sub.addAll(1, toAdd));
        assertEquals(5, sub.size());
        assertEquals("B", sub.get(0));
        assertEquals("X", sub.get(1));
        assertEquals("Y", sub.get(2));
        assertEquals("C", sub.get(3));
    }

    /**
     * Test del metodo {@link myAdapter.HList#removeAll(HCollection)}
     * 
     * @summary.test Verifica che {@code removeAll(c)} rimuova tutti gli elementi specificati se presenti.
     *
     * @design.test sub = ["B", "C", "D"], rimuove "C" e "X"
     *
     * @description.test {@code sub.removeAll(["C", "X"])} → ["B", "D"]
     *
     * @precondition.test Lista padre = ["A","B","C","D","E"]
     *
     * @postcondition.test sub e parent aggiornate coerentemente
     *
     * @result.test sub.size() == 2, "C" rimosso
     */
    @Test
    public void testRemoveAll() {
        sub = new SubListAdapter(parent, 1, 4); // ["B", "C", "D"]
        ListAdapter toRemove = new ListAdapter();
        toRemove.add("C");
        toRemove.add("X");

        assertTrue(sub.removeAll(toRemove));
        assertEquals(2, sub.size());
        assertEquals("B", sub.get(0));
        assertEquals("D", sub.get(1));
    }

    /**
     * Test del metodo {@link myAdapter.HList#retainAll(HCollection)}
     * 
     * @summary.test Verifica che {@code retainAll(c)} mantenga solo gli elementi presenti anche in c.
     *
     * @design.test sub = ["B", "C", "D"], conserva solo "C"
     *
     * @description.test {@code sub.retainAll(["C"])} → ["C"]
     *
     * @precondition.test Lista padre = ["A","B","C","D","E"]
     *
     * @postcondition.test sub e parent aggiornate coerentemente
     *
     * @result.test sub.size() == 1, unico elemento "C"
     */
    @Test
    public void testRetainAll() {
        sub = new SubListAdapter(parent, 1, 4); // ["B", "C", "D"]
        ListAdapter toKeep = new ListAdapter();
        toKeep.add("C");

        assertTrue(sub.retainAll(toKeep));
        assertEquals(1, sub.size());
        assertEquals("C", sub.get(0));
    }

    /**
     * Test del metodo {@link myAdapter.HList#clear()}
     * 
     * @summary.test Verifica che {@code clear()} rimuova tutti gli elementi dalla sottolista.
     *
     * @design.test sub = ["B", "C", "D"], chiama {@code clear()}
     *
     * @description.test Dopo l'operazione sub diventa vuota
     *
     * @precondition.test Lista padre = ["A","B","C","D","E"]
     *
     * @postcondition.test sub.size() == 0, parent.size() == 2
     *
     * @result.test Gli elementi della sublist sono rimossi anche dalla lista padre
     */
    @Test
    public void testClear() {
        sub = new SubListAdapter(parent, 1, 4); // ["B", "C", "D"]
        sub.clear();
        assertEquals(0, sub.size());
        assertEquals(2, parent.size()); // erano 5, tolti 3 elementi
        assertEquals("A", parent.get(0));
        assertEquals("E", parent.get(1));
    }

    /**
     * Test del metodo {@link myAdapter.HList#toArray()}
     * 
     * @summary.test Verifica che {@code toArray()} restituisca correttamente tutti gli elementi.
     *
     * @design.test sub = ["B", "C", "D"], converte in array
     *
     * @description.test {@code sub.toArray()} → array = ["B", "C", "D"]
     *
     * @precondition.test Lista padre = ["A", "B", "C", "D", "E"]
     *
     * @postcondition.test Nessuna modifica
     *
     * @result.test L'array contiene gli elementi nella giusta sequenza
     */
    @Test
    public void testToArray() {
        sub = new SubListAdapter(parent, 1, 4); // ["B", "C", "D"]
        Object[] arr = sub.toArray();
        assertArrayEquals(new Object[]{"B", "C", "D"}, arr);
    }

    /**
     * Test del metodo {@link myAdapter.HList#toArray(Object[])}
     * 
     * @summary.test Verifica {@code toArray(Object[] a)} con array preesistente.
     *
     * @design.test sub = ["B", "C", "D"], array = new Object[5]
     *
     * @description.test {@code sub.toArray(arr)} → ["B", "C", "D", null, ...]
     *
     * @precondition.test Lista padre = ["A", "B", "C", "D", "E"]
     *
     * @postcondition.test Nessuna modifica
     *
     * @result.test Gli elementi sono copiati e il successivo è null
     */
    @Test
    public void testToArrayWithGivenArray() {
        sub = new SubListAdapter(parent, 1, 4); // ["B", "C", "D"]
        Object[] arr = new Object[5];
        Object[] result = sub.toArray(arr);
        assertEquals("B", result[0]);
        assertEquals("C", result[1]);
        assertEquals("D", result[2]);
        assertNull(result[3]);
    }

    /**
     * Test dei metodi {@link myAdapter.HList#equals(Object)}, {@link myAdapter.HList#hashCode()}
     * 
     * @summary.test Verifica {@code equals()} e {@code hashCode()} tra due sottoliste uguali.
     *
     * @design.test Due sublist con gli stessi elementi
     *
     * @description.test {@code sub1.equals(sub2)} → true
     *
     * @precondition.test Entrambe le sublist = ["B", "C", "D"]
     *
     * @postcondition.test Nessuna modifica
     *
     * @result.test equals = true, hashCode identici
     */
    @Test
    public void testEqualsAndHashCode() {
        sub = new SubListAdapter(parent, 1, 4); // ["B", "C", "D"]
        ListAdapter otherParent = new ListAdapter();
        otherParent.add("B");
        otherParent.add("C");
        otherParent.add("D");
        SubListAdapter sub2 = new SubListAdapter(otherParent, 0, 3);

        assertTrue(sub.equals(sub2));
        assertEquals(sub.hashCode(), sub2.hashCode());
    }

    /**
     * Test del metodo {@link myAdapter.HList#subList(int, int)}
     * 
     * @summary.test Verifica {@code subList(from, to)} restituisca una sottolista corretta.
     *
     * @design.test sub = ["B", "C", "D"], chiama sub.subList(1, 3)
     *
     * @description.test → nuova sub = ["C", "D"]
     *
     * @precondition.test Lista padre = ["A", "B", "C", "D", "E"]
     *
     * @postcondition.test Nessuna modifica
     *
     * @result.test La nuova sublist è coerente
     */
    @Test
    public void testSubList() {
        sub = new SubListAdapter(parent, 1, 4); // ["B", "C", "D"]
        HList newSub = sub.subList(1, 3);       // ["C", "D"]
        assertEquals(2, newSub.size());
        assertEquals("C", newSub.get(0));
        assertEquals("D", newSub.get(1));
    }

    /**
     * Test del metodo {@link myAdapter.HList#iterator()}
     * 
     * @summary.test Verifica che l'iteratore scorra correttamente tutti gli elementi della sottolista.
     *
     * @design.test sub = ["B", "C", "D"], iteratore restituisce gli elementi in ordine
     *
     * @description.test {@code while (it.hasNext()) it.next()} → "B", "C", "D"
     *
     * @precondition.test Lista padre = ["A", "B", "C", "D", "E"]
     *
     * @postcondition.test Nessuna modifica
     *
     * @result.test Tutti gli elementi vengono restituiti in sequenza
     */
    @Test
    public void testIterator() {
        ListAdapter parent = new ListAdapter();
        parent.add("A"); parent.add("B"); parent.add("C"); parent.add("D"); parent.add("E");

        SubListAdapter sub = new SubListAdapter(parent, 1, 4); // ["B", "C", "D"]
        HIterator it = sub.iterator();

        assertTrue(it.hasNext());
        assertEquals("B", it.next());
        assertEquals("C", it.next());
        assertEquals("D", it.next());
        assertFalse(it.hasNext());
    }

    /**
     * Test del metodo {@link myAdapter.HList#listIterator()}
     * 
     * @summary.test Verifica operazioni di navigazione e modifica con {@code HListIterator}.
     *
     * @design.test sub = ["B", "C", "D"], modifica "C" in "X", aggiunge "Z", rimuove "D"
     *
     * @description.test {@code set, add, remove}
     *
     * @precondition.test Lista padre iniziale = ["A", "B", "C", "D", "E"]
     *
     * @postcondition.test Lista aggiornata: ["A", "B", "X", "Z", "E"]
     *
     * @result.test Operazioni coerenti con il comportamento del list iterator
     */
    @Test
    public void testListIteratorOperations() {
        ListAdapter parent = new ListAdapter();
        parent.add("A"); parent.add("B"); parent.add("C"); parent.add("D"); parent.add("E");

        SubListAdapter sub = new SubListAdapter(parent, 1, 4); // ["B", "C", "D"]
        HListIterator it = sub.listIterator();

        assertEquals("B", it.next());
        assertEquals("C", it.next());

        it.set("X"); // modifica "C" in "X"
        it.add("Z"); // aggiunge "Z" dopo "X"

        assertEquals("D", it.next());
        it.remove(); // rimuove "D"

        Object[] expected = new Object[]{"B", "X", "Z"};
        assertArrayEquals(expected, sub.toArray());

        // Verifica anche la lista padre
        Object[] full = new Object[]{"A", "B", "X", "Z", "E"};
        assertArrayEquals(full, parent.toArray());
    }




}
