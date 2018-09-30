package ru.tstu.sapr.tablesort.core;

import java.util.ArrayList;
import java.util.Random;
import ru.tstu.sapr.tablesort.core.sorter.HeapSorter;
import ru.tstu.sapr.tablesort.core.sorter.QuickSorter;

public class Model {
  public static final String[] SORT_METHODS_NAMES = {
    "Quick sort", "Smooth sort", "Shell sort", "Heap sort"
  };

  static final class Method {
    static final int QUICK_SORT  = 0; // by keliz
    static final int SMOOTH_SORT = 1; // by Medvedev
    static final int SHELL_SORT  = 2; // by Makarov
    static final int HEAP_SORT   = 3; // by makcimbx
  }

  private static final int DEFAULT_DATA_SIZE = 100;

  private LogWriter logWriter;
  private Timer timer;
  private int[] data;

  Model(LogWriter logWriter) {
    this.timer = new Timer();
    this.logWriter = logWriter;
    data = new int[DEFAULT_DATA_SIZE];
  }

  int[] getData() {
    return data;
  }

  int[] generateData() {
    logWriter.writeMessage("Generating new data set...");
    int maxValue = data.length * 10 + 1;
    Random random = new Random();

    for (int i = 0; i < data.length; i++)
      data[i] = random.nextInt(maxValue);

    logWriter.writeMessage("Data set generated");
    return data;
  }

  ArrayList<SortResult> testAll() {
    ArrayList<SortResult> results = new ArrayList<>();
    logWriter.writeMessage("Testing all sort methods");

    for (int method = 0; method < SORT_METHODS_NAMES.length; method++) {
      results.add(testMethod(method));
      generateData();
    }

    logWriter.writeMessage("Test completed");
    return results;
  }

  SortResult testMethod(int methodIndex) {
    logWriter.writeMessage("Sorting data by: " + SORT_METHODS_NAMES[methodIndex]);
    timer.start();

    switch (methodIndex) {
      case Method.QUICK_SORT:
        QuickSorter.sort(data);
        break;
      case Method.SMOOTH_SORT:
        break;
      case Method.SHELL_SORT:
        break;
      case Method.HEAP_SORT:
        HeapSorter.sort(data);
        break;
    }

    timer.stop();
    logWriter.writeMessage(String.format("Data sorted: %d us", timer.getMicros()));
    return new SortResult(methodIndex, timer.getMicros());
  }
}
