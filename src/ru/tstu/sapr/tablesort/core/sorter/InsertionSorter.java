package ru.tstu.sapr.tablesort.core.sorter;

/* by Eremin */
public class InsertionSorter extends Sorter {

  @Override
  void sortInternal(int[] array) {
    int n = array.length;
    for (int i = 1; i < n; ++i) {
      int key = array[i];
      int j = i - 1;

      while (j >= 0 && array[j] > key) {
        array[j + 1] = array[j];
        j = j - 1;
      }
      array[j + 1] = key;
    }
  }
}
