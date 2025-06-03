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

    @Override
    public Object set(int index, Object element) {
        if (index < 0 || index >= delegate.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + delegate.size());
        }
        Object old = delegate.elementAt(index);
        delegate.setElementAt(element, index);
        return old;
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

    @Override
    public HListIterator listIterator() {
        return new ListIteratorAdapter(0);
    }

    @Override
    public HListIterator listIterator(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        return new ListIteratorAdapter(index);
    }

    @Override
    public HList subList(int fromIndex, int toIndex) {
        return new SubListAdapter(this, fromIndex, toIndex);
    }

    public boolean addAll(int index, HCollection c) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}

