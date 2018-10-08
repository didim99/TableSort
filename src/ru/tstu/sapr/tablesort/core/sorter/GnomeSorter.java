package ru.tstu.sapr.tablesort.core.sorter;

/* by MagLoner */
public class GnomeSorter extends Sorter {

  @Override
  void sortInternal(int[] array) {
    int i = 1;
    while(i < array.length) {
      if(i == 0 || array[i - 1] <= array[i])
        i++;
      else {
        int temp = array[i];
        array[i] = array[i - 1];
        array[i - 1] = temp;
        i--;
      }
    }
  }
}

