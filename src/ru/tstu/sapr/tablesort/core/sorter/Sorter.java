package ru.tstu.sapr.tablesort.core.sorter;

import java.util.Arrays;

public abstract class Sorter {

  int[] sort(int[] array) {
    int[] tmp = Arrays.copyOf(array, array.length);
    sortInternal(tmp);
    return tmp;
  }

  abstract void sortInternal(int[] array);
}
