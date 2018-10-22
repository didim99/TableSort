package ru.tstu.sapr.tablesort.core.sorter;

/* by Makarov */
class ShellSorter extends Sorter {

  @Override
  void sortInternal(int[] array) {
    int step = 1;
    while (step <= array.length / 3){
      step = 3 * step + 1;
    }
    while (step > 0){
      for(int i = 0; i < array.length; i++){
        int temp = array[i];
        int j;
        for(j = i; j > step - 1 && array[j - step] >= temp; j -= step){
          array[j] = array[j - step];
        }
        array[j] = temp;
      }
      step = (step - 1) / 3;
    }
  }
}
