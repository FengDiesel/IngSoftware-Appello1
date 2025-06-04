package myAdapter;

/**
 * Interfaccia equivalente a java.util.Iterator (J2SE 1.4.2)
 * https://www2.cs.duke.edu/csed/java/jdk1.4.2/docs/api/index.html
 */

public interface HIterator {

    boolean hasNext();
    Object next();
    void remove();

}