package ru.tstu.sapr.tablesort.core.sorter;

/* by makcimbx */
public class HeapSorter extends Sorter {
  private int heapSize;

  @Override
  void sortInternal(int[] array) {
    buildHeap(array);
    while (heapSize > 1) {
      swap(array, 0, heapSize - 1);
      heapSize--;
      heapify(array, 0);
    }
  }

  private void buildHeap(int[] a) {
    heapSize = a.length;
    for (int i = a.length / 2; i >= 0; i--) {
      heapify(a, i);
    }
  }

  private void heapify(int[] a, int i) {
    int l = left(i);
    int r = right(i);
    int largest = i;
    if (l < heapSize && a[i] < a[l]) {
      largest = l;
    }
    if (r < heapSize && a[largest] < a[r]) {
      largest = r;
    }
    if (i != largest) {
      swap(a, i, largest);
      heapify(a, largest);
    }
  }

  private static int right(int i) {
    return 2 * i + 1;
  }

  private static int left(int i) {
    return 2 * i + 2;
  }

  private static void swap(int[] a, int i, int j) {
    int temp = a[i];
    a[i] = a[j];
    a[j] = temp;
  }
}