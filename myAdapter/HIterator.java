package myAdapter;

/**
 * Interfaccia per l'iterazione su una collezione compatibile con J2SE 1.4.2.
 * <p>
 * Simula il comportamento di {@link java.util.Iterator}, adattato al progetto {@code myAdapter}.
 */

public interface HIterator {

    /**
     * Verifica se ci sono altri elementi da iterare.
     * @return {@code true} se presenti altri elementi
     */
    boolean hasNext();


    /**
     * Restituisce l'elemento successivo della collezione.
     *
     * @return l'elemento successivo nell'iterazione
     * @throws java.util.NoSuchElementException se non ci sono altri elementi da iterare
     */
    Object next();

    /**
     * Rimuove dalla collezione l'ultimo elemento restituito da {@code next()}.
     *
     * @throws IllegalStateException se {@code next()} non è ancora stato chiamato o se {@code remove()} è già stato chiamato dopo l'ultima chiamata a {@code next()}
     */
    void remove();
}