package myAdapter;

/**
 * Interfaccia che rappresenta una collezione generica di oggetti.
 * Simula {@code java.util.Collection} come da specifica J2SE 1.4.2.
 */

public interface HCollection {

    /**
     * Aggiunge un elemento alla collezione.
     * @param o elemento da aggiungere
     * @return {@code true} se la collezione è cambiata
     */
    boolean add(Object o);

    /**
     * Aggiunge tutti gli elementi della collezione specificata.
     * @param c collezione da aggiungere
     * @return {@code true} se la collezione è cambiata
     */
    boolean addAll(HCollection c);

    /**
     * Rimuove tutti gli elementi dalla collezione.
     */
    void clear();

    /**
     * Verifica se la collezione contiene l'oggetto passato.
     * @param o oggetto da verificare
     * @return {@code true} se l'oggetto è presente
     */
    boolean contains(Object o);

    /**
     * Verifica se tutti gli elementi della collezione sono contenuti.
     * @param c collezione da confrontare
     * @return {@code true} se tutti gli elementi sono presenti
     */
    boolean containsAll(HCollection c);

    /**
     * Confronta l'uguaglianza tra due collezioni.
     * @param o oggetto da confrontare
     * @return {@code true} se uguali per contenuto e ordine
     */
    boolean equals(Object o);

    /**
     * Calcola l'hash code della collezione.
     * @return valore hash
     */
    int hashCode();

    /**
     * Verifica se la collezione è vuota.
     * @return {@code true} se la collezione non contiene elementi
     */
    boolean isEmpty();

    /**
     * Restituisce un iteratore sulla collezione.
     * @return un {@code HIterator}
     */
    HIterator iterator();

    /**
     * Rimuove l'elemento specificato.
     * @param o elemento da rimuovere
     * @return {@code true} se l'elemento era presente e rimosso
     */
    boolean remove(Object o);

    /**
     * Rimuove tutti gli elementi della collezione specificata.
     * @param c collezione da rimuovere
     * @return {@code true} se la collezione è cambiata
     */
    boolean removeAll(HCollection c);

    /**
     * Mantiene solo gli elementi contenuti nella collezione specificata.
     * @param c collezione da conservare
     * @return {@code true} se la collezione è cambiata
     */
    boolean retainAll(HCollection c);

    /**
     * Restituisce il numero di elementi nella collezione.
     * @return numero di elementi
     */
    int size();

    /**
     * Restituisce un array contenente tutti gli elementi della collezione.
     * @return array degli elementi
     */
    Object[] toArray();

    /**
     * Restituisce un array contenente tutti gli elementi della collezione, in particolare se l'array specificato è abbastanza grande, viene usato direttamente.
     * @param a array di partenza
     * @return array con gli elementi della collezione
     */
    Object[] toArray(Object[] a);

}
