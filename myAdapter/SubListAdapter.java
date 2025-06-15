package myAdapter;

/**
 * Implementazione di {@code HList} che fornisce una vista su una porzione di {@code ListAdapter}.
 * Le modifiche alla sublist si riflettono sulla lista principale.
 */
public class SubListAdapter implements HList {
    /** Lista padre a cui questa sublista si appoggia. */
    private ListAdapter parent;

    /** Indice iniziale (incluso) nella lista padre. */
    private int offset;

    /** Indice finale (escluso) nella lista padre. */
    private int toIndex;

    /**
     * Costruisce una sublist che rappresenta una vista tra due indici di una lista esistente.
     *
     * @param parent la lista da cui deriva la sublist (non deve essere null)
     * @param fromIndex l'indice iniziale (inclusivo)
     * @param toIndex l'indice finale (esclusivo)
     * @throws NullPointerException se la lista padre è null
     * @throws IndexOutOfBoundsException se gli indici non sono validi rispetto alla lista padre
     */
    public SubListAdapter(ListAdapter parent, int fromIndex, int toIndex) {
        if (parent == null)
            throw new NullPointerException("parent list cannot be null");
        if (fromIndex < 0 || toIndex > parent.size() || fromIndex > toIndex)
            throw new IndexOutOfBoundsException("Invalid sublist range");

        this.parent = parent;
        this.offset = fromIndex;
        this.toIndex = toIndex;
    }

    /**
     * Verifica che l'indice sia all'interno dei limiti della sottolista.
     *
     * @param index indice da controllare
     * @throws IndexOutOfBoundsException se fuori dai limiti
     */
    private void rangeCheck(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        }
    }

    /**
     * Restituisce la dimensione della sottolista.
     * @return numero di elementi nella sottolista
     */
    @Override
    public int size() {
        return toIndex - offset;
    }

    /**
     * Verifica se la sottolista è vuota.
     * @return {@code true} se non contiene elementi
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Restituisce l'elemento all'indice specificato della sottolista.
     * @param index indice dell'elemento
     * @return elemento nella posizione indicata
     * @throws IndexOutOfBoundsException se l'indice non è valido
     */
    @Override
    public Object get(int index) {
        rangeCheck(index);
        return parent.get(offset + index);
    }

    /**
     * Sostituisce l'elemento all'indice specificato con il nuovo valore fornito.
     * @param index posizione dell'elemento da modificare
     * @param element nuovo elemento da inserire
     * @return valore precedente nella posizione
     * @throws IndexOutOfBoundsException se l'indice non è valido
     */
    @Override
    public Object set(int index, Object element) {
        rangeCheck(index);
        return parent.set(offset + index, element);
    }

    /**
     * Inserisce un nuovo elemento nella posizione specificata della sottolista.
     * @param index indice in cui inserire l'elemento
     * @param element elemento da aggiungere
     * @throws IndexOutOfBoundsException se l'indice non è valido
     */
    @Override
    public void add(int index, Object element) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        }
        parent.add(offset + index, element);
        toIndex++; // aumentiamo il limite superiore della vista
    }

    /**
     * Rimuove l'elemento nella posizione specificata dalla sottolista.
     * @param index indice dell'elemento da rimuovere
     * @return elemento rimosso
     * @throws IndexOutOfBoundsException se l'indice non è valido
     */
    @Override
    public Object remove(int index) {
        rangeCheck(index);
        Object removed = parent.remove(offset + index);
        toIndex--; // vista si restringe
        return removed;
    }

    /**
     * Restituisce l'indice della prima occorrenza dell'oggetto specificato nella sottolista.
     * @param o oggetto da cercare
     * @return indice dell'oggetto o -1 se non presente
     */
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size(); i++) {
            Object e = get(i);
            if (e == null ? o == null : e.equals(o)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Restituisce l'indice dell'ultima occorrenza dell'oggetto specificato nella sottolista.
     * @param o oggetto da cercare
     * @return ultimo indice dell'oggetto o -1 se non presente
     */
    @Override
    public int lastIndexOf(Object o) {
        for (int i = size() - 1; i >= 0; i--) {
            Object e = get(i);
            if (e == null ? o == null : e.equals(o)) {
                return i;
            }
        }
        return -1;
    }

    // Stub temporanei (opzionali da implementare)
    public boolean add(Object o) { throw new UnsupportedOperationException(); }
    public boolean remove(Object o) { throw new UnsupportedOperationException(); }
    public boolean contains(Object o) { throw new UnsupportedOperationException(); }
    public boolean containsAll(HCollection c) { throw new UnsupportedOperationException(); }
    public boolean addAll(HCollection c) { throw new UnsupportedOperationException(); }
    public boolean addAll(int index, HCollection c) { throw new UnsupportedOperationException(); }
    public boolean removeAll(HCollection c) { throw new UnsupportedOperationException(); }
    public boolean retainAll(HCollection c) { throw new UnsupportedOperationException(); }
    public void clear() { throw new UnsupportedOperationException(); }
    public Object[] toArray() { throw new UnsupportedOperationException(); }
    public Object[] toArray(Object[] a) { throw new UnsupportedOperationException(); }
    public boolean equals(Object o) { throw new UnsupportedOperationException(); }
    public int hashCode() { throw new UnsupportedOperationException(); }
    public HIterator iterator() { throw new UnsupportedOperationException(); }
    public HListIterator listIterator() { throw new UnsupportedOperationException(); }
    public HListIterator listIterator(int index) { throw new UnsupportedOperationException(); }
    public HList subList(int fromIndex, int toIndex) { throw new UnsupportedOperationException(); }
}