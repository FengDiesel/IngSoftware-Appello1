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

    /**
     * Aggiunge un elemento in fondo alla sottolista.
     * L'inserimento si riflette anche sulla lista padre.
     *
     * @param o elemento da aggiungere
     * @return {@code true} se la sottolista è modificata (sempre true)
     */
    @Override
    public boolean add(Object o) {
        add(size(), o);
        return true;
    }

    /**
     * Rimuove la prima occorrenza dell'elemento specificato dalla sottolista.
     * La rimozione si riflette sulla lista padre.
     *
     * @param o elemento da rimuovere
     * @return {@code true} se un elemento è stato rimosso
     */
    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size(); i++) {
            Object current = get(i);
            if ((current == null && o == null) || (current != null && current.equals(o))) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se la sottolista contiene l'elemento specificato.
     *
     * @param o elemento da cercare
     * @return {@code true} se l'elemento è presente, {@code false} altrimenti
     */
    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    /**
     * Verifica se la sottolista contiene tutti gli elementi presenti nella collezione specificata.
     *
     * @param c collezione da confrontare
     * @return {@code true} se tutti gli elementi sono presenti, {@code false} altrimenti
     * @throws NullPointerException se la collezione è null
     */
    @Override
    public boolean containsAll(HCollection c) {
        if (c == null)
            throw new NullPointerException();
        HIterator it = c.iterator();
        while (it.hasNext()) {
            if (!contains(it.next()))
                return false;
        }
        return true;
    }

    /**
     * Aggiunge tutti gli elementi della collezione specificata in fondo alla sottolista.
     * L'operazione si riflette anche sulla lista padre.
     *
     * @param c collezione di elementi da aggiungere
     * @return {@code true} se la sottolista è stata modificata
     * @throws NullPointerException se la collezione è null
     */
    @Override
    public boolean addAll(HCollection c) {
        return addAll(size(), c);
    }

    /**
     * Aggiunge tutti gli elementi della collezione specificata a partire dall'indice specificato della sottolista.
     * L'operazione si riflette anche sulla lista padre.
     *
     * @param index posizione di inserimento
     * @param c collezione di elementi da aggiungere
     * @return {@code true} se la sottolista è stata modificata
     * @throws NullPointerException se la collezione è null
     * @throws IndexOutOfBoundsException se l'indice è fuori dai limiti della sottolista
     */
    @Override
    public boolean addAll(int index, HCollection c) {
        if (c == null)
            throw new NullPointerException();
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());

        boolean modified = false;
        HIterator it = c.iterator();
        while (it.hasNext()) {
            add(index++, it.next());
            modified = true;
        }
        return modified;
    }
    
    /**
     * Rimuove dalla sottolista tutti gli elementi presenti nella collezione specificata.
     * L'operazione si riflette sulla lista padre.
     *
     * @param c collezione contenente gli elementi da rimuovere
     * @return {@code true} se la sottolista è stata modificata
     * @throws NullPointerException se la collezione è null
     */
    @Override
    public boolean removeAll(HCollection c) {
        if (c == null)
            throw new NullPointerException();
        boolean modified = false;
        for (int i = 0; i < size(); ) {
            if (c.contains(get(i))) {
                remove(i);
                modified = true;
            } else {
                i++;
            }
        }
        return modified;
    }
    
    /**
     * Mantiene nella sottolista solo gli elementi presenti anche nella collezione specificata.
     * L'operazione si riflette sulla lista padre.
     *
     * @param c collezione contenente gli elementi da conservare
     * @return {@code true} se la sottolista è stata modificata
     * @throws NullPointerException se la collezione è null
     */
    @Override
    public boolean retainAll(HCollection c) {
        if (c == null)
            throw new NullPointerException();
        boolean modified = false;
        for (int i = 0; i < size(); ) {
            if (!c.contains(get(i))) {
                remove(i);
                modified = true;
            } else {
                i++;
            }
        }
        return modified;
    }

    /**
     * Rimuove tutti gli elementi dalla sottolista.
     * L'operazione si riflette sulla lista padre.
     */
    @Override
    public void clear() {
        for (int i = size() - 1; i >= 0; i--) {
            remove(i);
        }
    }

    /**
     * Restituisce un array contenente tutti gli elementi della sottolista.
     *
     * @return array degli elementi della sottolista, in ordine
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size()];
        for (int i = 0; i < size(); i++) {
            array[i] = get(i);
        }
        return array;
    }

    /**
     * Copia gli elementi della sottolista nell'array specificato.
     * Se l'array è troppo piccolo, ne viene creato uno nuovo della stessa classe.
     * Se è più grande, la posizione successiva all'ultimo elemento viene impostata a null.
     *
     * @param a array di destinazione
     * @return array contenente gli elementi della sottolista
     * @throws NullPointerException se l'array è null
     */
    @Override
    public Object[] toArray(Object[] a) {
        if (a == null)
            throw new NullPointerException();
        int size = size();
        if (a.length < size) {
            a = new Object[size];
        }
        for (int i = 0; i < size; i++) {
            a[i] = get(i);
        }
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    /**
     * Confronta questa sottolista con un'altra lista per uguaglianza.
     * Due liste sono uguali se contengono gli stessi elementi nello stesso ordine.
     *
     * @param o oggetto da confrontare
     * @return {@code true} se le liste sono uguali
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof HList))
            return false;
        HList other = (HList) o;
        if (other.size() != size())
            return false;
        for (int i = 0; i < size(); i++) {
            Object e1 = get(i);
            Object e2 = other.get(i);
            if (e1 == null ? e2 != null : !e1.equals(e2))
                return false;
        }
        return true;
    }

    /**
     * Restituisce il codice hash della sottolista.
     * Calcolato secondo le specifiche del Java Collections Framework.
     *
     * @return hash code della sottolista
     */
    @Override
    public int hashCode() {
        int hashCode = 1;
        for (int i = 0; i < size(); i++) {
            Object e = get(i);
            hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
        }
        return hashCode;
    }

    /**
     * Restituisce una sottolista della sottolista corrente, compresa tra gli indici specificati.
     *
     * @param fromIndex indice iniziale (inclusivo)
     * @param toIndex indice finale (esclusivo)
     * @return nuova sottolista
     * @throws IndexOutOfBoundsException se gli indici sono invalidi
     */
    @Override
    public HList subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size() || fromIndex > toIndex)
            throw new IndexOutOfBoundsException("Invalid range");
        return new SubListAdapter(parent, offset + fromIndex, offset + toIndex);
    }

    /**
     * Restituisce un iteratore sulla sottolista.
     *
     * @return un {@code HIterator} sugli elementi della sottolista
     */
    @Override
    public HIterator iterator() {
        return listIterator(0); // cast implicito HListIterator -> HIterator
    }

    /**
     * Restituisce un list iterator a partire dalla posizione iniziale della sottolista.
     *
     * @return un {@code HListIterator} sugli elementi della sottolista
     */
    @Override
    public HListIterator listIterator() {
        return listIterator(0);
    }

    /**
     * Restituisce un list iterator a partire dalla posizione specificata della sottolista.
     *
     * @param index posizione iniziale
     * @return {@code HListIterator} sugli elementi
     * @throws IndexOutOfBoundsException se l'indice non è valido
     */
    @Override
    public HListIterator listIterator(int index) {
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        return new SubListIterator(index);
    }

    /**
     * Implementazione interna di {@code HListIterator} per la sottolista.
     */
    private class SubListIterator implements HListIterator {
        
        /**
         * Iteratore interno per {@code SubListAdapter}, basato su {@code HListIterator}.
         */
        private final HListIterator it;

        /**
         * Posizione relativa del cursore all’interno della sottolista.
         */
        private int cursor;

        /**
         * Costruttore dell’iteratore per sottolista a partire da indice iniziale.
         *
         * @param index posizione iniziale del cursore nella sottolista
         */
        public SubListIterator(int index) {
            this.it = parent.listIterator(offset + index);
            this.cursor = index;
        }

        public boolean hasNext() {
            return cursor < size();
        }

        public Object next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            cursor++;
            return it.next();
        }

        public boolean hasPrevious() {
            return cursor > 0;
        }

        public Object previous() {
            if (!hasPrevious()) throw new java.util.NoSuchElementException();
            cursor--;
            return it.previous();
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        public void remove() {
            it.remove();
            toIndex--;
            cursor--;
        }

        public void set(Object o) {
            it.set(o);
        }

        public void add(Object o) {
            it.add(o);
            toIndex++;
            cursor++;
        }
    }


}