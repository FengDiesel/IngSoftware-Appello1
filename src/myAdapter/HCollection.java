package myAdapter;

/**
 * Interfaccia equivalente a java.util.Collection (J2SE 1.4.2)
 * https://www2.cs.duke.edu/csed/java/jdk1.4.2/docs/api/index.html
 */

public interface HCollection {

    boolean add(Object o);
    boolean addAll(HCollection c);
    void clear();
    boolean contains(Object o);
    boolean containsAll(HCollection c);
    boolean equals(Object o);
    int hashCode();
    boolean isEmpty();
    HIterator iterator();
    boolean remove(Object o);
    boolean removeAll(HCollection c);
    boolean retainAll(HCollection c);
    int size();
    Object[] toArray();
    Object[] toArray(Object[] a);

}
