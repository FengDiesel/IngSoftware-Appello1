package myAdapter;

import java.util.Vector;

/**
 * Adapter della collezione Vector compatibile con HList e HCollection.
 * I metodi sono inizialmente non implementati (throw new UnsupportedOperationException).
 */

public class ListAdapter implements HList, HCollection {

    private Vector delegate;

    public ListAdapter() {
        delegate = new Vector();
    }

    public ListAdapter(int capacity) {
        delegate = new Vector(capacity);
    }

    // HCollection

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return delegate.contains(o);
    }
    
    //provvisorio
    @Override
    public HIterator iterator() {
        return new IteratorAdapter();
    }

    private class IteratorAdapter implements HIterator {
        private int cursor = 0;
        private boolean canRemove = false;

        @Override
        public boolean hasNext() {
            return cursor < delegate.size();
        }

        @Override
        public Object next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Object next = delegate.elementAt(cursor);
            cursor++;
            canRemove = true;
            return next;
        }

        @Override
        public void remove() {
            if (!canRemove) {
                throw new IllegalStateException();
            }
            delegate.removeElementAt(--cursor);
            canRemove = false;
        }
    }
    ///////////////////////////


    @Override
    public Object[] toArray() {
        Object[] arr = new Object[delegate.size()];
        for (int i = 0; i < delegate.size(); i++) {
            arr[i] = delegate.elementAt(i);
        }
        return arr;
    }

    @Override
    public Object[] toArray(Object[] a) {
        int size = delegate.size();
        if (a.length < size) {
            // crea un nuovo array dello stesso tipo con la dimensione giusta
            a = (Object[]) java.lang.reflect.Array.newInstance(
                a.getClass().getComponentType(), size
            );
        }
        for (int i = 0; i < size; i++) {
            a[i] = delegate.elementAt(i);
        }
        if (a.length > size) {
            a[size] = null; // terminatore
        }
        return a;
    }

    @Override
    public boolean add(Object o) {
        delegate.addElement(o);  // Vector.addElement()
        return true;
    }


    @Override
    public boolean remove(Object o) {
        return delegate.removeElement(o); // rimuove solo la prima occorrenza
    }

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

    @Override
    public boolean retainAll(HCollection c) {
        boolean modified = false;
        int i = 0;
        while (i < delegate.size()) {
            Object elem = delegate.elementAt(i);
            if (!c.contains(elem)) {
                delegate.removeElementAt(i);
                modified = true;
            } else {
                i++;
            }
        }
        return modified;
    }

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

        for (int i = 0; i < size(); i++) {
            Object e1 = get(i);
            Object e2 = other.get(i);
            if (e1 == null ? e2 != null : !e1.equals(e2)) {
                return false;
            }
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

    // HList
    @Override
    public Object get(int index) {
        if (index < 0 || index >= delegate.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + delegate.size());
        }
    return delegate.elementAt(index);
}


    public Object set(int index, Object element) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void add(int index, Object element) {
        if (index < 0 || index > delegate.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + delegate.size());
        }
        delegate.insertElementAt(element, index);
    }

    @Override
    public Object remove(int index) {
        if (index < 0 || index >= delegate.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + delegate.size());
        }
        Object old = delegate.elementAt(index);
        delegate.removeElementAt(index);
        return old;
    }

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

    @Override
    public int lastIndexOf(Object o) {
        for (int i = delegate.size() - 1; i >= 0; i--) {
            Object e = delegate.elementAt(i);
            if (e == null ? o == null : e.equals(o)) {
                return i;
            }
        }
        return -1;
    }

    public HListIterator listIterator() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public HListIterator listIterator(int index) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public HList subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean addAll(int index, HCollection c) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}

