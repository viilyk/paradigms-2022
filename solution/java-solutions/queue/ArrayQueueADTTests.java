package queue;

public class ArrayQueueADTTests {
    public static void main(String[] args) {
        ArrayQueueADT queue1 = ArrayQueueADT.create();
        ArrayQueueADT queue2 = ArrayQueueADT.create();
        for (int i = 0; i < 5; i++) {
            ArrayQueueADT.enqueue(queue1, "q_1_" + i);
            ArrayQueueADT.enqueue(queue2, "q_2_" + i);
        }
        while (!ArrayQueueADT.isEmpty(queue1)) {
            System.out.println(ArrayQueueADT.size(queue1) + " " + ArrayQueueADT.dequeue(queue1));
        }
        while (!ArrayQueueADT.isEmpty(queue2)) {
            System.out.println(ArrayQueueADT.size(queue2) + " " + ArrayQueueADT.dequeue(queue2));
        }
    }
}
