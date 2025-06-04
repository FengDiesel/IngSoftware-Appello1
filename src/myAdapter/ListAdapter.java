package myAdapter;

import java.util.Vector;

/**
 * Adapter della classe {@link Vector} che implementa {@link HList} e {@link HCollection}.
 * Compatibile con le specifiche Java 1.4.2 e CLDC 1.1.
 */

public class ListAdapter implements HList, HCollection {

    private Vector delegate;

    /**
     * Costruttore di default, inizializza una lista vuota.
     */
    public ListAdapter() {
        delegate = new Vector();
    }

    /**
     * Costruttore che inizializza una lista con capacità iniziale specificata.
     * @param capacity capacità iniziale
     */
    public ListAdapter(int capacity) {
        delegate = new Vector(capacity);
    }

    /**
     * Iterator compatibile con HListIterator.
     */
    private class ListIteratorAdapter implements HListIterator {
        int cursor;
        int lastRet = -1;

        public ListIteratorAdapter(int index) {
            this.cursor = index;
        }

        public boolean hasNext() {
            return cursor < delegate.size();
        }

        public Object next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            lastRet = cursor;
            return delegate.elementAt(cursor++);
        }

        public boolean hasPrevious() {
            return cursor > 0;
        }

        public Object previous() {
            if (!hasPrevious()) throw new java.util.NoSuchElementException();
            lastRet = --cursor;
            return delegate.elementAt(cursor);
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        public void remove() {
            if (lastRet < 0) throw new IllegalStateException();
            delegate.removeElementAt(lastRet);
            if (lastRet < cursor) cursor--;
            lastRet = -1;
        }

        public void set(Object o) {
            if (lastRet < 0) throw new IllegalStateException();
            delegate.setElementAt(o, lastRet);
        }

        public void add(Object o) {
            delegate.insertElementAt(o, cursor++);
            lastRet = -1;
        }

    }

    //  HCollection  \\

    /**
     * Restituisce il numero di elementi nella lista.
     * @return numero di elementi
     */
    @Override
    public int size() {
        return delegate.size();
    }

    /**
     * Verifica se la lista è vuota.
     * @return {@code true} se la lista è vuota, {@code false} altrimenti
     */
    @Override
    public boolean isEmpty() {
        return delegate.size() == 0;
    }

    /**
     * Verifica se un elemento è presente nella lista.
     * @param o oggetto da cercare
     * @return {@code true} se presente
     */
    @Override
    public boolean contains(Object o) {
        return delegate.contains(o);
    }
    
    /**
     * Restituisce un iteratore per la lista.
     * @return HIterator
     */
    @Override
    public HIterator iterator() {
        return new IteratorAdapter();
    }

    /**
     * Iteratore base (compatibile con Java 1.4).
     */
    private class IteratorAdapter implements HIterator {
        int cursor = 0;
        int lastRet = -1;

        public boolean hasNext() {
            return cursor < delegate.size();
        }

        public Object next() {
            if (cursor >= delegate.size()) {
                throw new java.util.NoSuchElementException();
            }
            lastRet = cursor;
            return delegate.elementAt(cursor++);
        }

        public void remove() {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            delegate.removeElementAt(lastRet);
            cursor = lastRet;
            lastRet = -1;
        }
    }


    /**
     * Restituisce un array contenente tutti gli elementi della lista.
     * @return array di oggetti
     */
    @Override
    public Object[] toArray() {
        Object[] arr = new Object[delegate.size()];
        for (int i = 0; i < delegate.size(); i++) {
            arr[i] = delegate.elementAt(i);
        }
        return arr;
    }

    /**
     * Restituisce un array contenente tutti gli elementi in ordine.
     * Usa l'array passato se abbastanza grande, altrimenti ne crea uno nuovo.
     * @param a array di partenza
     * @return array riempito
     */
    @Override
    public Object[] toArray(Object[] a) {
        int size = delegate.size();
        if (a.length < size) {
            a = (Object[]) java.lang.reflect.Array.newInstance(
                a.getClass().getComponentType(), size
            );
        }
        for (int i = 0; i < size; i++) {
            a[i] = delegate.elementAt(i);
        }
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    /**
     * Aggiunge un elemento in coda alla lista.
     * @param o elemento da aggiungere
     * @return {@code true}
     */
    @Override
    public boolean add(Object o) {
        delegate.addElement(o);  // Vector.addElement()
        return true;
    }

    /**
     * Rimuove l'oggetto specificato, il primo che incontra.
     * @param o oggetto da rimuovere
     * @return {@code true} se rimosso
     */
    @Override
    public boolean remove(Object o) {
        return delegate.removeElement(o);
    }

    /**
     * Verifica se tutti gli elementi sono presenti nella lista.
     * @param c collezione da confrontare
     * @return {@code true} se tutti gli elementi sono contenuti
     */
    @Override
    public boolean containsAll(HCollection c) {
        HIterator it = c.iterator();
        while (it.hasNext()) {
            if (!delegate.contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Aggiunge tutti gli elementi alla fine della lista.
     * @param c collezione da aggiungere
     * @return {@code true} se almeno un elemento è stato aggiunto
     */
    @Override
    public boolean addAll(HCollection c) {
        boolean modified = false;
        HIterator it = c.iterator();
        while (it.hasNext()) {
            delegate.addElement(it.next());
            modified = true;
        }
        return modified;
    }

    /**
     * Rimuove tutti gli elementi presenti nella collezione.
     * @param c collezione da rimuovere
     * @return {@code true} se almeno un elemento è stato rimosso
     */
    @Override
    public boolean removeAll(HCollection c) {
        boolean modified = false;
        HIterator it = c.iterator();
        while (it.hasNext()) {
            Object elem = it.next();
            while (delegate.contains(elem)) {
                delegate.removeElement(elem);
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Mantiene solo gli elementi presenti nella collezione.
     * @param c collezione da mantenere
     * @return {@code true} se la lista è stata modificata
     */
    @Override
    public boolean retainAll(HCollection c) {
        boolean modified = false;
        int i = 0;
        while (i < delegate.size()) {
            Object elem = delegate.elementAt(i);
            if (!c.contains(elem)) {
                delegate.removeElementAt(i);
                modified = true;
            } else i++;
        }
        return modified;
    }

    /**
     * Rimuove tutti gli elementi dalla lista.
     */
    @Override
    public void clear() {
        delegate.removeAllElements();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof HList)) return false;
        HList other = (HList) o;
        if (size() != other.size()) return false;

        for (int i=0; i<size(); i++) {
            Object e1 = get(i);
            Object e2 = other.get(i);
            if (e1 == null ? e2 != null : !e1.equals(e2)) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        for (int i = 0; i < size(); i++) {
            Object obj = get(i);
            hash = 31 * hash + (obj == null ? 0 : obj.hashCode());
        }
        
        return hash;
    }

    //  HList  \\

    /**
     * Restituisce l'elemento alla posizione indicata.
     * @param index indice
     * @return elemento
     * @throws IndexOutOfBoundsException se indice non valido
     */
    @Override
    public Object get(int index) {
        if (index < 0 || index >= delegate.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + delegate.size());
        }

        return delegate.elementAt(index);
    }

    /**
     * Sostituisce l'elemento alla posizione indicata.
     * @param index posizione
     * @param element nuovo valore
     * @return valore precedente
     * @throws IndexOutOfBoundsException se indice non valido
     */
    @Override
    public Object set(int index, Object element) {
        if (index < 0 || index >= delegate.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + delegate.size());
        }
        Object old = delegate.elementAt(index);
        delegate.setElementAt(element, index);
        return old;
    }

    /**
     * Aggiunge un elemento nella posizione specificata.
     * @param index posizione
     * @param element elemento da inserire
     * @throws IndexOutOfBoundsException se l'indice è fuori dal range
     */
    @Override
    public void add(int index, Object element) {
        if (index < 0 || index > delegate.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + delegate.size());
        }
        delegate.insertElementAt(element, index);
    }

    /**
     * Rimuove l'elemento nella posizione specificata.
     * @param index posizione dell'elemento
     * @return elemento rimosso
     * @throws IndexOutOfBoundsException se indice non valido
     */
    @Override
    public Object remove(int index) {
        if (index < 0 || index >= delegate.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + delegate.size());
        }
        Object old = delegate.elementAt(index);
        delegate.removeElementAt(index);
        return old;
    }

    /**
     * Restituisce l'indice dell'oggetto specificato.
     * @param o oggetto da cercare
     * @return indice o -1 se non presente
     */
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < delegate.size(); i++) {
            Object e = delegate.elementAt(i);
            if (e == null ? o == null : e.equals(o)) {
                return i;
            }
        }
        
        return -1;
    }

    /**
     * Restituisce l'ultimo indice dell'oggetto specificato.
     * @param o oggetto da cercare
     * @return indice o -1 se non presente
     */
    @Override
    public int lastIndexOf(Object o) {
        for (int i = delegate.size() - 1; i>= 0; i--) {
            Object e = delegate.elementAt(i);
            if (e == null ? o == null : e.equals(o)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Restituisce un list iterator dalla posizione 0.
     * @return HListIterator
     */
    @Override
    public HListIterator listIterator() {
        return new ListIteratorAdapter(0);
    }

    /**
     * Restituisce un list iterator da un indice specificato.
     * @param index posizione iniziale
     * @return HListIterator
     * @throws IndexOutOfBoundsException se indice non valido
     */
    @Override
    public HListIterator listIterator(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        return new ListIteratorAdapter(index);
    }

    /**
     * Restituisce una sublist vista live da {@code fromIndex} a {@code toIndex - 1}.
     * @param fromIndex inizio (inclusivo)
     * @param toIndex fine (esclusivo)
     * @return sottolista
     * @throws IndexOutOfBoundsException se limiti invalidi
     */
    @Override
    public HList subList(int fromIndex, int toIndex) {
        return new SubListAdapter(this, fromIndex, toIndex);
    }

    /**
     * Aggiunge tutti gli elementi nella posizione specificata.
     * @param index posizione d'inserimento
     * @param c collezione da inserire
     * @return {@code true} se almeno un elemento è stato aggiunto
     * @throws IndexOutOfBoundsException se l'indice non è valido
     */
    @Override
    public boolean addAll(int index, HCollection c) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }

        HIterator it = c.iterator();
        int i = 0;
        while (it.hasNext()) {
            this.add(index + i, it.next()); //
            i++;
        }

        return i > 0;
    }

}