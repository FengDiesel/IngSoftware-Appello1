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


    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public HIterator iterator() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Object[] toArray() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Object[] toArray(Object[] a) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean add(Object o) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean containsAll(HCollection c) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean addAll(HCollection c) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean removeAll(HCollection c) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean retainAll(HCollection c) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void clear() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean equals(Object o) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int hashCode() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // HList
    public Object get(int index) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Object set(int index, Object element) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void add(int index, Object element) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Object remove(int index) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int indexOf(Object o) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Not implemented yet");
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

