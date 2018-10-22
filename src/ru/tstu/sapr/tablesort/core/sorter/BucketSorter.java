package ru.tstu.sapr.tablesort.core.sorter;

/* by Erkhova */
class BucketSorter extends Sorter {

  @Override
  void sortInternal(int[] array) {
    int maxValue = array[0];

    for (int k = 1; k < array.length; k++)
      if (array[k] > maxValue)
        maxValue = array[k];

    int i, j;

    int[] bucket = new int[maxValue + 1];

    for (i = 0; i < array.length; i++)
      bucket[array[i]]++;

    for (i = 0, j = 0; i < bucket.length; i++) {
      for (; bucket[i] > 0; (bucket[i])--) {
        array[j++] = i;
      }
    }
  }
}
