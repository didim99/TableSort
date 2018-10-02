package ru.tstu.sapr.tablesort.core;

import java.util.ArrayList;
import java.util.Random;
import ru.tstu.sapr.tablesort.core.sorter.*;

public class Model {
  public static final String[] SORT_METHODS_NAMES = {
    "Quick sort", "Cocktail sort", "Shell sort", "Heap sort",
    "Radix sort"
  };

  static final class Method {
    static final int QUICK_SORT     = 0; // by keliz
    static final int COCKTAIL_SORT  = 1; // by Medvedev
    static final int SHELL_SORT     = 2; // by Makarov
    static final int HEAP_SORT      = 3; // by makcimbx
    static final int RADIX_SORT     = 4; // by IonShieldQuad
    static final int GNOME_SORT     = 5; // by MagLoner
    static final int INSERTION_SORT = 6; // by link
    static final int BLOCK_SORT     = 7; // by Erkhova
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

    Sorter sorter = null;
    switch (methodIndex) {
      case Method.QUICK_SORT:
        sorter = new QuickSorter();
        break;
      case Method.COCKTAIL_SORT:
        break;
      case Method.SHELL_SORT:
        break;
      case Method.HEAP_SORT:
        sorter = new HeapSorter();
        break;
      case Method.RADIX_SORT:
        sorter = new RadixSorter();
        break;
      default:
        throw new IllegalArgumentException("Unknown sort method");
    }

    timer.start();
    data = sorter.sort(data);
    timer.stop();

    logWriter.writeMessage(String.format("Data sorted: %d us", timer.getMicros()));
    return new SortResult(methodIndex, timer.getMicros());
  }
}
