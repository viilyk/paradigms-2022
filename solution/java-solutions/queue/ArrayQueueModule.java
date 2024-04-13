package queue;

// Model: a[1]..a[n]
// Invariant: for i = 1..n: a[i] != null

// Let immutable(n): for i = 1..n: a'[i] == a[i]

public class ArrayQueueModule {
    private static Object[] elements = new Object[2];
    private static int end;
    private static int start;

    // Pred: element != null
    // Post: n' = n + 1 && a[n] == element && immutable(n)
    public static void enqueue(final Object element) {
        assert element != null;
        ensureCapacity(size() + 1);
        elements[end] = element;
        end = (end + 1) % elements.length;
    }

    private static void ensureCapacity(int size) {
        if (elements.length <= size) {
            Object[] newElements = new Object[size * 2];
            System.arraycopy(elements, start, newElements, 0, elements.length - start);
            System.arraycopy(elements, 0, newElements, elements.length - start, end);
            end = size();
            elements = newElements;
            start = 0;
        }
    }

    // Pred: n > 0
    // Post: R == a[1] && n' = n && immutable(n)
    public static Object element(){
        assert !isEmpty();
        return elements[start];
    }

    // Pred: n > 0
    // :NOTE: :FIX THIS: incorrect post-condition
    // Post: R == a[1] && n' = n - 1 && immutable(n')
    public static Object dequeue(){
        assert !isEmpty();
        Object result = elements[start];
        elements[start] = null;
        start = (start + 1) % elements.length;
        return result;
    }

    // Pred: true
    // Post: R == n && n' = n && immutable(n)
    public static int size() {
        if (start > end) {
            return elements.length - start + end;
        } else {
            return end - start;
        }
    }

    // Pred: true
    // Post: R == (n == 0) && n' == n && immutable(n)
    public static boolean isEmpty() {
        return size() == 0;
    }

    // Pred: true
    // Post: n' == 0
    public static void clear(){
        for (int i = 0; i < size(); i++) {
            dequeue();
        }
        start = 0;
        end = 0;
    }

    // Pred: true
    // Post: a[R] == element && for i = 1..(R - 1): a[i] != element
    public static int indexOf(Object element) {
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
    public static int lastIndexOf(Object element) {
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
