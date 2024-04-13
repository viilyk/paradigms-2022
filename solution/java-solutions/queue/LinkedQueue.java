package queue;

public class LinkedQueue extends AbstractQueue {
    private Node end = new Node (null, null);
    private Node start = new Node(null, null);


    public static class Node {
        private Object element;
        private Node next;

        public Node(Object element, Node next) {
            this.element = element;
            this.next = next;
        }
    }


    @Override
    protected void enqueueImpl(final Object element, final int size) {
        if (size == 0) {
            start.next = new Node(element, null);
            end = start.next;
        } else {
            end.next = new Node(element, null);
            end = end.next;
        }
    }

    @Override
    protected Object elementImpl() {
        return start.next.element;
    }


    @Override
    protected Object dequeueImpl(int size) {
        Object result = start.next.element;
        start.element = start.next.element;
        start.next = start.next.next;
        if (start.next == null) {
            end = start;
        }

        return result;
    }

    @Override
    protected void reset() {
        start = new Node(null, null);
        end = start;
    }
}

