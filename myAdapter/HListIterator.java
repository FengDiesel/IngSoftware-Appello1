package myAdapter;

/**
 * Interfaccia per l'iterazione bidirezionale su una lista.
 * <p>
 * Simula {@link java.util.ListIterator} secondo la specifica J2SE 1.4.2.
 */

public interface HListIterator extends HIterator{

    /**
     * Inserisce un nuovo elemento nella posizione corrente.
     * @param o elemento da inserire
     */
    void add(Object o);

    /**
     * Verifica se esiste un elemento succesivo nella lista.
     * @return {@code true} se disponibile un prossimo elemento
     */
    boolean hasNext();

    /**
     * Verifica se c'è un elemento precedente.
     * @return {@code true} se esiste un elemento prima del cursore
     */
    boolean hasPrevious();

    /**
     * Restituisce l'elemento successivo e avanza il cursore.
     * @return elemento successivo
     * @throws java.util.NoSuchElementException se non ci sono altri elementi
     */
    Object next();

    /**
     * Restituisce l'indice dell'elemento che verrebbe restituito da {@code next()}.
     * @return indice del prossimo elemento
     */
    int nextIndex();

    /**
     * Restituisce l'elemento precedente e torna indietro.
     * @return elemento precedente
     * @throws java.util.NoSuchElementException se non esiste
     */
    Object previous();

    /**
     * Restituisce l'indice dell'elemento che verrebbe restituito da {@code previous()}.
     * @return indice dell'elemento precedente
     */
    int previousIndex();

    /**
     * Rimuove l'ultimo elemento restituito da {@code next()} o {@code previous()}.
     * @throws IllegalStateException se {@code next()} o {@code previous()} non è stato chiamato
     */
    void remove();

    /**
     * Sostituisce l'ultimo elemento restituito da {@code next()} o {@code previous()} con il valore specificato.
     *
     * @param o nuovo valore da impostare
     * @throws IllegalStateException se {@code next()} o {@code previous()} non è stato ancora chiamato o se {@code remove()} o {@code add()} sono stati chiamati dopo l’ultima chiamata a {@code next()} o {@code previous()}
     */
    void set(Object o);

    
}