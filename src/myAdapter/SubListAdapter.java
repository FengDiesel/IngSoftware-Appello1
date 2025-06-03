package myAdapter;

public class SubListAdapter implements HList {
    private ListAdapter parent;
    private int offset;
    private int toIndex;

    public SubListAdapter(ListAdapter parent, int from, int to) {
        if (from < 0 || to > parent.size() || from > to) {
            throw new IndexOutOfBoundsException();
        }
        this.parent = parent;
        this.offset = from;
        this.toIndex = to;
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        }
    }

    @Override
    public int size() {
        return toIndex - offset;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Object get(int index) {
        rangeCheck(index);
        return parent.get(offset + index);
    }

    @Override
    public Object set(int index, Object element) {
        rangeCheck(index);
        return parent.set(offset + index, element);
    }

    @Override
    public void add(int index, Object element) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        }
        parent.add(offset + index, element);
        toIndex++; // aumentiamo il limite superiore della vista
    }

    @Override
    public Object remove(int index) {
        rangeCheck(index);
        Object removed = parent.remove(offset + index);
        toIndex--; // vista si restringe
        return removed;
    }

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