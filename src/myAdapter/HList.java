package myAdapter;

/**
 * Interfaccia che estende {@code HCollection} e rappresenta una lista ordinata.
 * Simula {@code java.util.List}
 */

public interface HList {

    /**
     * Inserisce un elemento alla posizione indicata.
     * @param index posizione
     * @param element elemento da inserire
     * @throws IndexOutOfBoundsException se indice non valido
     */
    void add(int index, Object element);

    /**
     * Aggiunge un elemento alla fine dlla lista.
     * @param o elemento da aggiungere
     * @return {@code true} se la lista è stata modificata
     */
    boolean add(Object o);

    /**
     * Aggiunge tutti gli elementi della collezione alla fine della lista.
     * @param c collezione da aggiungere
     * @return {@code true} se la lista è stata modificata
     */
    boolean addAll(HCollection c);

    /**
     * Inserisce tutti gli elementi della collezione in una posizione specificata.
     * @param index posizione di inserimento
     * @param c collezione da inserire
     * @return {@code true} se la lista è cambiata
     * @throws IndexOutOfBoundsException se indice non valido
     */
    boolean addAll(int index, HCollection c);

    /**
     * Rimuove tutti gli elementi dalla lista.
     */
    void clear();

    /**
     * Verifica se la lista contiene l'oggetto specificato.
     * @param o oggetto da cercare
     * @return {@code true} se presente
     */
    boolean contains(Object o);

    /**
     * Verifica se tutti gli elementi della collezione sono contenuti.
     * @param c collezione da confrontare
     * @return {@code true} se tutti gli elementi sono presenti
     */
    boolean containsAll(HCollection c);

    /**
     * Verifica se questa lista è uguale a un altro oggetto.
     * @param o oggetto da confrontare
     * @return {@code true} se uguali per contenuto e ordine
     */
    boolean equals(Object o);

    /**
     * Restituisce l'elemento all'indice specificato.
     * @param index indice
     * @return elemento alla posizione
     * @throws IndexOutOfBoundsException se indice non valido
     */
    Object get(int index);

    /**
     * Restituisce l'hash code calcolato in base al contenuto.
     * @return valore hash della lista
     */
    int hashCode();

    /**
     * Restituisce la prima occorrenza dell'elemento.
     * @param o elemento da cercare
     * @return indice oppure -1 se non presente
     */
    int indexOf(Object o);

    /**
     * Verifica se la lista è vuota.
     * @return {@code true} se non contiene elementi
     */
    boolean isEmpty();

    /**
     * Restituisce un iteratore sugli elementi della lista.
     * @return {@code HIterator}
     */
    HIterator iterator();

    /**
     * Restituisce l'ultima occorrenza dell'elemento.
     * @param o elemento da cercare
     * @return indice oppure -1 se non presente
     */
    int lastIndexOf(Object o);

    /**
     * Restituisce un list iterator a partire da inizio lista.
     * @return un {@code HListIterator}
     */
    HListIterator listIterator();

    /**
     * Restituisce un list iterator a partire da indice specifico
     * @param index posizione iniziale
     * @return un {@code HListIterator}
     * @throws IndexOutOfBoundsException se indice non valido
     */
    HListIterator listIterator(int index);

    /**
     * Rimuove l' elemento alla posizione indicata.
     * @param index posizione
     * @return elemento rimosso
     * @throws IndexOutOfBoundsException se l'indice non valido
     */
    Object 	remove(int index);

    /**
     * Rimuove la prima occorrenza dell'oggetto specificato.
     * @param o oggetto da rimuovere
     * @return {@code true} se rimosso
     */
    boolean remove(Object o);

    /**
     * Rimuove tutti gli elementi contenuti nella collezione.
     * @param c collezione da rimuovere
     * @return {@code true} se almeno un elemento è stato rimosso
     */
    boolean removeAll(HCollection c);

    /**
     * Mantiene solo gli elementi presenti anche nella collezione.
     * @param c collezione da confrontare
     * @return {@code true} se la lista è stata modificata
     */
    boolean retainAll(HCollection c);

    /**
     * Sostituisce l'elemento in posizione {@code index}.
     * @param index posizione
     * @param element nuovo valore
     * @return valore precedente
     * @throws IndexOutOfBoundsException se indice non valido
     */
    Object set(int index, Object element);

    /**
     * Restituisce il numero di elementi nella lista.
     * @return numero di elementi presenti
     */
    int size();

    /**
     * Retituisce una vista live di una porzione della lista.
     * @param fromIndex indice iniziale (incluso)
     * @param toIndex indice finale (escluso)
     * @return sottolista
     * @throws IndexOutOfBoundsException se i limiti non validi
     */
    HList subList(int fromIndex, int toIndex);

    /**
     * Restituisce un array contenente tutti gli elementi in ordine.
     * @return array di oggetti
     */
    Object[] toArray();

    /**
     * Restituisce un array contenente tutti gli elementi della lista.
     * @param a array iniziale
     * @return array riempito con gli elementi della lista
     */
    Object[] toArray(Object[] a);




}