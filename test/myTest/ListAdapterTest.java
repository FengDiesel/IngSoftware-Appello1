package test.myTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

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
}
