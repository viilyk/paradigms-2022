package queue;
// Model: a[1]..a[n]
// Invariant: for i = 1..n: a[i] != null

// Let immutable(n): for i = 1..n: a'[i] == a[i]

import java.util.function.Predicate;

public interface Queue {

    // Pred: element != null
    // Post: n' = n + 1 && a'[n'] == element && immutable(n)
    public void enqueue(Object element);

    // Pred: n > 0
    // Post: R == a[1] && n' = n && immutable(n);
    public Object element();

    // Pred: n > 0
    // Post: R == a[1] && n' = n - 1 && for i = 1..n' a'[i] = a[i + 1];
    public Object dequeue();

    // Pred: true
    // Post: R == (n == 0) && n' == n && immutable(n)
    public boolean isEmpty();

    // Pred: true
    // Post: R == n && n' = n && immutable(n)
    public int size();

    // Pred: true
    // Post: n' == 0
    public void clear();

    // Pred: true
    // Post: test(R) && for i = 1..(R - 1): !test(a[i])
    public int indexIf(Predicate<Object> predicate);

    // Pred: true
    // Post: test(R) && for i = (R + 1)..n: !test(a[i])
    public int lastIndexIf(Predicate<Object> predicate);
}
