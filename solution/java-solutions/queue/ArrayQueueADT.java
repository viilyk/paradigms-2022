package queue;

// Model: a[1]..a[n]
// Invariant: for i = 1..n: a[i] != null

// Let immutable(n): for i = 1..n: a'[i] == a[i]

public class ArrayQueueADT {
    private Object[] elements = new Object[2];
    private int end;
    private int start;

    public static ArrayQueueADT create() {
        final ArrayQueueADT queue = new ArrayQueueADT();
        queue.elements = new Object[2];
        return queue;
    }

    // Pred: element != null && queue != null
    // Post: n' = n + 1 && a[n] == element && immutable(n)
    public static void enqueue(ArrayQueueADT queue, final Object element) {
        assert element != null;
        ensureCapacity(queue,size(queue) + 1);
        queue.elements[queue.end] = element;
        queue.end = (queue.end + 1) % queue.elements.length;
    }

    private static void ensureCapacity(ArrayQueueADT queue, int size) {
        if (queue.elements.length <= size) {
            Object[] newElements = new Object[size * 2];
            System.arraycopy(queue.elements, queue.start, newElements, 0, queue.elements.length - queue.start);
            System.arraycopy(queue.elements, 0, newElements, queue.elements.length - queue.start, queue.end);
            queue.end = size(queue);
            queue.elements = newElements;
            queue.start = 0;
        }
    }

    // Pred: n > 0 && queue != null
    // Post: R == a[1] && n' = n && immutable(n)
    public static Object element(ArrayQueueADT queue){
        assert !isEmpty(queue);
        return queue.elements[queue.start];
    }

    // Pred: n > 0 && queue != null
    // Post: R == a[1] && n' = n - 1 && immutable(n')
    public static Object dequeue(ArrayQueueADT queue){
        assert !isEmpty(queue);
        Object result = queue.elements[queue.start];
        queue.elements[queue.start] = null;
        queue.start = (queue.start + 1) % queue.elements.length;
        return result;
    }

    // Pred: queue != null
    // Post: R == n && n' = n && immutable(n)
    public static int size(ArrayQueueADT queue) {
        if (queue.start > queue.end) {
            return queue.elements.length - queue.start + queue.end;
        } else {
            return queue.end - queue.start;
        }
    }

    // Pred: queue != null
    // Post: R == (n == 0) && n' == n && immutable(n)
    public static boolean isEmpty(ArrayQueueADT queue) {
        return size(queue) == 0;
    }

    // Pred: queue != null
    // Post: n' == 0
    public static void clear(ArrayQueueADT queue){
        for (int i = 0; i < size(queue); i++) {
            dequeue(queue);
        }
        queue.start = 0;
        queue.end = 0;
    }

    // Pred: queue != null
    // Post: a[R] == element && for i = 1..(R - 1): a[i] != element
    public static int indexOf(ArrayQueueADT queque, Object element) {
        int index = queque.start - 1;
        for (int i = 0; i < size(queque); i++) {
            index++;
            if (index >= queque.elements.length) {
                index = 0;
            }
            if (queque.elements[index].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    // queue != null
    // Post: a[R] == element && for i = (R + 1)..n: a[i] != element
    public static int lastIndexOf(ArrayQueueADT queque, Object element) {
        assert element != null;
        int index = queque.end;
        for (int i = size(queque) - 1; i >= 0; i--) {
            index--;
            if (index < 0) {
                index = queque.elements.length - 1;
            }
            if (queque.elements[index].equals(element)) {
                return i;
            }
        }
        return -1;
    }
}
