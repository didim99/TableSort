package ru.tstu.sapr.tablesort.core.sorter;

import ru.tstu.sapr.tablesort.core.Model;
import ru.tstu.sapr.tablesort.core.Timer;

public class SorterThread extends Thread {
  private EventListener listener;
  private int methodIndex;
  private int[] data;
  private Timer timer;

  public SorterThread(EventListener listener, int methodIndex, int[] data) {
    this.listener = listener;
    this.methodIndex = methodIndex;
    this.data = data;
    timer = new Timer();
  }

  @Override
  public void run() {
    SortResult result = testMethod(methodIndex);
    listener.onFinished(data, result);
  }

  private SortResult testMethod(int methodIndex) {
    Sorter sorter;
    switch (methodIndex) {
      case Model.Method.QUICK_SORT:
        sorter = new QuickSorter();
        break;
      case Model.Method.COCKTAIL_SORT:
        sorter = new CocktailSorter();
        break;
      case Model.Method.SHELL_SORT:
        sorter = new ShellSorter();
        break;
      case Model.Method.HEAP_SORT:
        sorter = new HeapSorter();
        break;
      case Model.Method.RADIX_SORT:
        sorter = new RadixSorter();
        break;
      case Model.Method.GNOME_SORT:
        sorter = new GnomeSorter();
        break;
      case Model.Method.SELECTION_SORT:
        sorter = new SelectionSorter();
        break;
      case Model.Method.INSERTION_SORT:
        sorter = new InsertionSorter();
        break;
      case Model.Method.BUCKET_SORT:
        sorter = new BucketSorter();
        break;
      default:
        throw new IllegalArgumentException("Unknown sort method");
    }

    timer.start();
    data = sorter.sort(data);
    timer.stop();

    return new SortResult(methodIndex, timer.getMillis());
  }

  public interface EventListener {
    void onFinished(int[] data, SortResult result);
  }
}
