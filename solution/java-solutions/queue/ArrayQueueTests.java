package queue;

public class ArrayQueueTests {
    public static void main(String[] args) {
        ArrayQueue queue1 = new ArrayQueue();
        ArrayQueue queue2 = new ArrayQueue();
        for (int i = 0; i < 5; i++) {
            queue1.enqueue("q_1_" + i);
            queue2.enqueue("q_2_" + i);
        }
        while (!queue1.isEmpty()) {
            System.out.println(queue1.size() + " " + queue1.dequeue());
        }
        while (!queue2.isEmpty()) {
            System.out.println(queue2.size() + " " + queue2.dequeue());
        }
    }
}
