package queue;

import java.util.function.Predicate;

public class ArrayQueue extends AbstractQueue {
    private Object[] elements;
    private int end;
    private int start;

    public ArrayQueue() {
        this.elements = new Object[2];
    }

    @Override
    protected void enqueueImpl(Object element, int size) {
        ensureCapacity(size + 1);
        elements[end] = element;
        end = (end + 1) % elements.length;
    }

    private void ensureCapacity(int size) {
        if (elements.length <= size) {
            Object[] newElements = new Object[size * 2];
            System.arraycopy(elements, start, newElements, 0, elements.length - start);
            System.arraycopy(elements, 0, newElements, elements.length - start, end);
            end = size - 1;
            elements = newElements;
            start = 0;
        }
    }

    public Object elementImpl(){
        return elements[start];
    }

    @Override
    protected Object dequeueImpl(int size) {
        Object result = elements[start];
        elements[start] = null;
        start = (start + 1) % elements.length;
        return result;
    }

    @Override
    protected void reset() {
        start = 0;
        end = 0;
        elements = new Object[2];
    }

    // Pred: true
    // Post: a[R] == element && for i = 1..(R - 1): a[i] != element
    public int indexOf(Object element) {
        int index = start - 1;
        for (int i = 0; i < size(); i++) {
            index++;
            if (index >= elements.length) {
                index = 0;
            }
            if (elements[index].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    // Pred: true
    // Post: a[R] == element && for i = (R + 1)..n: a[i] != element
    public int lastIndexOf(Object element) {
        assert element != null;
        int index = end;
        for (int i = size() - 1; i >= 0; i--) {
            index--;
            if (index < 0) {
                index = elements.length - 1;
            }
            if (elements[index].equals(element)) {
                return i;
            }
        }
        return -1;
    }
}
