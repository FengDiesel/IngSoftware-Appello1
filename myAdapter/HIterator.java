package myAdapter;

/**
 * Interfaccia per l'iterazione su una collezione compatibile con Java 1.4.
 * Simula {@code java.util.Iterator}
 */

public interface HIterator {

    /**
     * Verifica se ci sono altri elementi da iterare.
     * @return {@code true} se presenti altri elementi
     */
    boolean hasNext();


    /**
     * Restituisce l'elemento successivo.
     * @return elemento successivo
     * @throws java.util.NoSuchElementException se non ci sono altri elementi
     */
    Object next();

    /**
     * Rimuove l'ultimo elemento restituito da {@code next()}.
     * @throws IllegalStateException se {@code next()} non è ancora stato chiamato
     */
    void remove();
}