package search;

public class BinarySearch {
    // Pred0: (for i = 1..(args.length - 1): int(args[i]) >= int(args[i + 1])) && int(args[args.length - 1] <= int(args[0])
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int[] arr = new int[args.length - 1];
        for (int i = 0; i < args.length - 1; i++) {
            arr[i] = Integer.parseInt(args[i + 1]);
        }
        // arr is int[] && x is int && (for i = 0..(arr.length - 1): arr[i] >= arr[i + 1]) && arr[args.length - 1] <= x
        //System.out.println(iterativeBinarySearch(x, arr));
        // arr is int[] && x is int && (for i = 0..(arr.length - 1): arr[i] >= arr[i + 1]) && arr[args.length - 1] <= x &&
        // && right > arr.length - 1 && left < 0 && left < right
        System.out.println(recursiveBinarySearch(x, -1, arr.length, arr));
    }

    // Let arr[arr.length] = -INF && arr[-1] = +INF
    // Pred0: arr is int[] && x is int && (i <= j <-> a[i] >= a[j]) && arr[arr.length - 1] <= x
    // Post0: arr[R] <= x && (for i = 0..(R - 1): a[i] > x)
    public static int iterativeBinarySearch(int x, int[] arr) {
        // Pred1: arr[arr.length] <= x && (-1 < arr.length) && Pred0
        int right = arr.length;
        // Post1: arr[right'] <= x && (-1 < right) && Pred0
        // Pred2: arr[right'] <= x && (-1 < right) && Pred0 && (x < arr[-1])
        int left = -1;
        // Post2: arr[right'] <= x && (left = right) && Pred0 && (x < arr[left'])
        // I: Post2
        while (right - left > 1) {
            // Pred3: I && right' - left' > 1
            // I && (2 * left' < left' + right' - 1 < left' + right' < left' + right' + 1< 2 * right')
            // Pred4: I && left' < (left' + right') / 2 < right'
            int middle = (right + left) / 2;
            // Post4: I && left' < middle < right'
            // Pred5: Post4
            if (arr[middle] > x) {
                // Pred5 && cond: I && left' < middle < right' && arr[middle] > x
                // Pred5 && cond: (arr[right'] <= x) && (x < arr[left']) && (left < right) && Pred0 &&
                // left' < middle < right' && (arr[middle] > x)
                // Pred6: (arr[right'] <= x) && (x < arr[middle]) && middle < right' && Pred0
                left = middle;
                // Q6: (arr[right'] <= x ) && (x < arr[left']) && (left < right) && Pred0
            } else {
                // P5 && !cond: (arr[right'] <= x) && (x < arr[left']) &&
                // && Pred0 && left' < middle < right' && arr[middle] <= x && (left < right) && Pred0
                // P7:(arr[middle] <= x) && (x < arr[left']) && left' < middle && Pred0
                right = middle;
                // Q7: (arr[right'] <= x) && (x < arr[left']) && left' < right' && Pred0
            }
            // Q5: (arr[right'] <= x) && (x < arr[left']) && (left < right) && Pred0
            // Q3: I
        }
        // I && right' - left' <= 1
        // (arr[right'] <= x) && (x < arr[left']) && (left < right) && Pred0 && right' - left' <= 1
        // arr[right'] <= x && (for i = 0..(right' - 1): a[i] > x)
        return right;
    }

    // Pred0: arr is int[] && x is int && (i <= j <-> a[i] >= a[j]) && arr[arr.length - 1] <= x &&
    // && (arr[right] <= x ) && (x < arr[left]) && left < right
    // Post0: arr[R] <= x && (for i = 0..(R - 1): a[i] > x)
    public static int recursiveBinarySearch(int x, int left, int right, int[] arr) {
        if (right - left <= 1) {
            //Pred0 && cond: Pred0 && right - left <= 1
            return right;
        }
        // Pred0 && !cond: Pred0 && right - left > 1
        // Pred0 && 2 * left < left + right - 1 < left + right < 2 * right
        // Pred1: Pred0 && left < (right + left) / 2 < right
         int middle = (right + left) / 2;
        // Post1: left < middle < right && Pred0
        // Pred2: left < middle < right && Pred0
        if (arr[middle] > x) {
            // Pred2 && cond: left < middle < right && arr[middle] > x && Pred0
            // Pred2 && cond: left < middle < right && arr[middle] > x && (arr[right] <= x) && (x < arr[left]) && Pred0
            // (arr[right] <= x) && x < arr[middle] && middle < right && Pred0
            return recursiveBinarySearch(x, middle, right, arr);
        } else {
            // Pred2 && cond: left < middle < right && arr[middle] <= x && (arr[right] <= x ) && (x < arr[left]) && Pred0
            // arr[middle] <= x && (x < arr[left]) && left < middle && Pred0
            return recursiveBinarySearch(x, left, middle, arr);
        }
    }
}
