package myAdapter;

/**
 * Interfaccia equivalente a java.util.ListIterator (J2SE 1.4.2)
 * https://www2.cs.duke.edu/csed/java/jdk1.4.2/docs/api/index.html
 */

public interface HListIterator {

    void add(Object o);
    boolean hasNext();
    boolean hasPrevious();
    Object next();
    int nextIndex();
    Object previous();
    int previousIndex();
    void remove();
    void set(Object o);

}