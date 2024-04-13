package search;

public class BinarySearchShift {
    // >>: a[]' == (a[] >> x): ((for i = 0..(n - 1 - x): a'[i] == a[i + x]) && (for  i = (n - x)..n: a'[i] = a[i + x - n]))
    // Pred0: (int(args[]) = a[] >> x : (0 <= x < args.length && for i = 0..(args.length - 1): a[i] < a[i + 1]))
    // Post0: (arr[] = (a[] >> R) : 0 <= R < arr.length && for i = 0..(arr.length - 1): a[i] < a[i + 1])
    public static void main(String[] args) {
        int[] arr = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            arr[i] = Integer.parseInt(args[i]);
        }
        // arr is int[] && int(arr[]) = a[] >> x: 0 <= x < arr.length && for i = 0..(arr.length - 1): a[i] < a[i + 1])
        // System.out.println(iterativeBinarySearch(-1, arr.length, arr));
        // Let arr[-1] = +INF && arr[arr.length] = -INF
        // arr is int[] && arr[] = a[] >> x: 0 <= x < arr.length && for i = 0..(arr.length - 1): a[i] < a[i + 1]) &&
        // && (arr[-1] > arr[arr.length - 1]) && (arr[arr.length] <= arr[arr.length - 1]) && (-1 < arr.length)
        System.out.println(recursiveBinarySearch(-1, arr.length, arr));
    }

    // Let arr[-1] = +INF && arr[arr.length] = -INF
    // Pred0: arr[] is int[] && (arr[] = (a[] >> x) : 0 <= x < arr.length && for i = 0..(arr.length - 1): a[i] < a[i + 1])
    // Post0: (arr[] = (a[] >> R) : 0 <= R < arr.length && for i = 0..(arr.length - 1): a[i] < a[i + 1])
    public static int iterativeBinarySearch(int[] arr) {
        // Pred1: (arr[arr.length] <= arr[arr.length - 1]) && (-1 < arr.length) && Pred0
        int right = arr.length;
        // Post1: (arr[right] <= arr[arr.length - 1]) && (-1 < right) && Pred0
        // Pred2: (arr[-1] > arr[arr.length - 1]) && Post1
        int left = -1;
        // Post2: (arr[left] > arr[arr.length - 1]) && (arr[right] <= arr[arr.length - 1]) && (left < right) && Pred0
        // I: Post2
        while (right - left > 1) {
            // Pred3: I && right' - left' > 1
            // I && (2 * left' < left' + right' - 1 < left' + right' < left' + right' + 1 < 2 * right')
            // Pred4: I && left' < (left' + right') / 2 < right'
            int middle = (right + left) / 2;
            // Post4: I && left' < middle < right'
            // Pred5: I && left' < middle < right'
            if (arr[middle] > arr[arr.length - 1]) {
                // Pred6: Pred5 && cond: I && left' < middle < right' && (arr[middle] > arr[arr.length - 1])
                left = middle;
                // Post6: (arr[left'] > arr[arr.length - 1]) && (arr[right'] <= arr[arr.length - 1]) && (left' < right') && Pred0
            } else {
                // Pred7: Pred5 && !cond: I && left' < middle < right' && (arr[middle] <= arr[arr.length - 1])
                right = middle;
                // Post7: (arr[left'] > arr[arr.length - 1]) && (arr[right'] <= arr[arr.length - 1]) && (left' < right') && Pred0
            }
            // Q5: (arr[left'] > arr[arr.length - 1]) && (arr[right'] <= arr[arr.length - 1]) && (left' < right') && Pred0
            // Q3: I
        }
        // (arr[left'] > arr[arr.length - 1]) && (arr[right'] <= arr[arr.length - 1]) && (left' < right')&& Pred0 && (right' - left' <= 1)
        // arr[left] == max(arr[])
        // (arr[] = (a[] >> left + 1) : for i = 0..(arr.length - 1): a[i] < a[i + 1]) && 0 <= left + 1 < arr.length
        // R = left + 1
        return left + 1;
    }

    // Let arr[-1] = +INF && arr[arr.length] = -INF
    // Pred0: arr[] is int[] && (arr[] = (a[] >> x) : 0 <= x < arr.length && for i = 0..(arr.length - 1): a[i] < a[i + 1]) &&
    // && (arr[left] > arr[arr.length - 1]) && (arr[right] <= arr[arr.length - 1]) && (left < right)...........
    // Post0:  (arr[] = (a[] >> R) : 0 <= R < arr.length && for i = 0..(arr.length - 1): a[i] < a[i + 1])
    public static int recursiveBinarySearch(int left, int right, int[] arr) {
        if (right - left <= 1) {
            // Pred0 && cond: Pred0 && (right - left <= 1)
            // arr[left] == max(arr[])
            // (arr[] = (a[] >> left + 1) : for i = 0..(arr.length - 1): a[i] < a[i + 1]) && 0 <= left + 1 < arr.length
            return left + 1;
        }
        // Pred0 && !cond: Pred0 && (right - left > 1)
        // Pred0 && (2 * left < left + right - 1 < left + right < left + right + 1 < 2 * right)
        // Pred1: Pred0 && left <= (right + left) / 2 < right
        int middle = (right + left) / 2;
        // Post1: left <= middle < right && Pred0
        // Pred2: Post1
        if (arr[middle] > arr[arr.length - 1]) {
            // Pred2 && cond: left <= middle < right && arr[middle] > arr[arr.length - 1] && Pred0
            // (arr[middle] > arr[arr.length - 1]) && (arr[right] <= arr[arr.length - 1]) && Pred0
            return recursiveBinarySearch(middle, right, arr);
        } else {
            // Pred2 && cond: left <= middle < right && arr[middle] <= arr[arr.length - 1] && Pred0
            // (arr[middle] <= arr[arr.length - 1]) && (arr[left] > arr[arr.length - 1]) && Pred0
            return recursiveBinarySearch(left, middle, arr);
        }
    }


}
