package test.myTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import myAdapter.HCollection;
import myAdapter.HList;
import myAdapter.ListAdapter;

/**
 * Test iniziali per ListAdapter: verifica size() e isEmpty().
 */
public class ListAdapterTest {
    private HList list;

    @Before
    public void setUp() {
        list = new ListAdapter();
    }

    @Test
    public void testEmptyList() {
        assertEquals("Una nuova lista dovrebbe avere size 0", 0, list.size());
        assertTrue("Una nuova lista dovrebbe essere vuota", list.isEmpty());
    }

    //

    @Test
    public void testAddAndGet() {
        assertTrue("add() deve restituire true", list.add("A"));
        assertTrue(list.add("B"));
        assertEquals("Dopo due inserti, size() deve restituire 2", 2, list.size());
        assertEquals("get(0) deve restituire il primo elemento", "A", list.get(0));
        assertEquals("get(1) deve restituire il secondo elemento", "B", list.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetNegativeIndex() {
        list.get(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetIndexOutOfBounds() {
        list.add("X");
        list.get(1); // index >= size
    }

    //

    @Test
    public void testAddAtIndex() {
        list.add("A"); // index 0
        list.add("C"); // index 1
        list.add(1, "B"); // inserisce B tra A e C
        assertEquals(3, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAtNegativeIndex() {
        list.add(-1, "X");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAtTooLargeIndex() {
        list.add("A");
        list.add(2, "B"); // posizione 2 non esiste (size = 1)
    }

    //

    @Test
    public void testRemoveByIndex() {
        list.add("A");
        list.add("B");
        list.add("C");
        Object removed = list.remove(1); // rimuove "B"
        assertEquals("B", removed);
        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        assertEquals("C", list.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveIndexOutOfBounds() {
        list.add("X");
        list.remove(1); // posizione 1 non esiste
    }

    @Test
    public void testRemoveByObject() {
        list.add("A");
        list.add("B");
        list.add("C");
        assertTrue(list.remove("B"));
        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        assertEquals("C", list.get(1));

        assertFalse("Rimuovere un elemento non presente deve restituire false", list.remove("Z"));
        assertEquals(2, list.size());
    }

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

    @Test
    public void testAddAllRemoveAllRetainAll() {
        HCollection other = new ListAdapter();
        other.add("X");
        other.add("Y");
        other.add("Z");

        // Test addAll
        assertTrue(list.addAll(other));
        assertEquals(3, list.size());
        assertEquals("X", list.get(0));
        assertEquals("Y", list.get(1));
        assertEquals("Z", list.get(2));

        // Test removeAll
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

        // Test retainAll
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

    @Test
    public void testClear() {
        list.add("A");
        list.add("B");
        assertEquals(2, list.size());
        list.clear();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testEqualsAndHashCode() {
        HList list1 = new ListAdapter();
        HList list2 = new ListAdapter();

        list1.add("A");
        list1.add("B");

        list2.add("A");
        list2.add("B");

        assertTrue("Liste con stessi elementi devono essere uguali", list1.equals(list2));
        assertEquals("Liste uguali devono avere stesso hashCode", list1.hashCode(), list2.hashCode());

        list2.add("C");
        assertFalse("Liste con elementi diversi non sono uguali", list1.equals(list2));
    }

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

    @Test
    public void testToArrayWithParameter() {
        list.add("X");
        list.add("Y");

        // Caso: array passato abbastanza grande
        String[] arr1 = new String[3];
        Object[] result1 = list.toArray(arr1);
        assertSame("Deve restituire l'array passato se sufficientemente grande", arr1, result1);
        assertEquals("X", arr1[0]);
        assertEquals("Y", arr1[1]);
        assertNull("L'elemento dopo gli elementi deve essere null", arr1[2]);

        // Caso: array passato troppo piccolo → restituito uno nuovo
        String[] arr2 = new String[1];
        Object[] result2 = list.toArray(arr2);
        assertNotSame("Deve restituire un nuovo array se troppo piccolo", arr2, result2);
        assertEquals("X", result2[0]);
        assertEquals("Y", result2[1]);
    }


}
