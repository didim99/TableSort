package ru.tstu.sapr.tablesort.core.sorter;

/* by keliz */
class QuickSorter extends Sorter {

  @Override
  void sortInternal(int[] array) {
    sort(array, 0, array.length - 1);
  }

  private static void sort(int[] array, int low, int high) {
    if (array.length == 0) {
      return;
    }
    if (low >= high) {
      return;
    }

    int middle = low + (high - low) / 2;
    int costil = array[middle];
    int i = low, j = high;
    while (i <= j) {
      while (array[i] < costil) {
        i++;
      }
      while (array[j] > costil) {
        j--;
      }
      if (i <= j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        i++;
        j--;
      }
    }

    if (low < j)
      sort(array, low, j);
    if (high > i)
      sort(array, i, high);
  }
}
