package queue;

import java.util.Objects;
import java.util.function.Predicate;

public abstract class AbstractQueue implements Queue {
    protected int size;

    public void enqueue(final Object element) {
        enqueueImpl(Objects.requireNonNull(element), size);
        size++;
    }

    protected abstract void enqueueImpl(Object element, int size);

    public Object dequeue(){
        assert !isEmpty();
        Object result = dequeueImpl(size);
        size--;
        return result;
    }

    protected abstract Object dequeueImpl(int size);

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        size = 0;
        reset();
    }

    public Object element() {
        assert !isEmpty();
        return elementImpl();
    }

    protected abstract Object elementImpl();

    protected abstract void reset();

    public int indexIf(Predicate<Object> predicate) {
        return index(predicate, true);
    }

    protected int index(Predicate<Object> predicate, boolean f) {
        int index = -1;
        for(int i = 0; i < size; i++) {
            Object a = dequeue();
            if (predicate.test(a) && (!f || index == -1)) {
                index = i;
            }
            enqueue(a);
        }
        return index;
    }

    public int lastIndexIf(Predicate<Object> predicate) {
        return index(predicate, false);
    }
}
