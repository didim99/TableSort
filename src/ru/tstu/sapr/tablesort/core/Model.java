package ru.tstu.sapr.tablesort.core;

import java.util.ArrayList;
import java.util.Random;
import ru.tstu.sapr.tablesort.core.sorter.*;

public class Model {
  private static final int DATA_SIZE = 100;
  public static final int DATA_MIN = 0;
  public static final int DATA_MAX = DATA_SIZE * 10;

  public static final String[] SORT_METHODS_NAMES = {
    "Quick sort", "Cocktail sort", "Shell sort",
    "Heap sort", "Radix sort", "Gnome sort",
    "Selection sort", "Insertion sort", "Bucket sort"
  };

  private static final class Method {
    static final int QUICK_SORT     = 0; // by keliz
    static final int COCKTAIL_SORT  = 1; // by Medvedev
    static final int SHELL_SORT     = 2; // by Makarov
    static final int HEAP_SORT      = 3; // by makcimbx
    static final int RADIX_SORT     = 4; // by IonShieldQuad
    static final int GNOME_SORT     = 5; // by MagLoner
    static final int SELECTION_SORT = 6; // by Merkulov
    static final int INSERTION_SORT = 7; // by Eremin
    static final int BUCKET_SORT    = 8; // by Erkhova
  }

  private LogWriter logWriter;
  private Timer timer;
  private int[] data;

  Model(LogWriter logWriter) {
    this.timer = new Timer();
    this.logWriter = logWriter;
    data = new int[DATA_SIZE];
  }

  int[] getData() {
    return data;
  }

  int[] generateData() {
    logWriter.writeMessage("Generating new data set...");
    Random random = new Random();

    for (int i = 0; i < data.length; i++)
      data[i] = random.nextInt(DATA_MAX + 1);

    logWriter.writeMessage("Data set generated");
    return data;
  }

  ArrayList<SortResult> testAll() {
    ArrayList<SortResult> results = new ArrayList<>();
    logWriter.writeMessage("Testing all sort methods");

    for (int method = 0; method < SORT_METHODS_NAMES.length; method++) {
      generateData();
      results.add(testMethod(method));
    }

    logWriter.writeMessage("Test completed");
    return results;
  }

  SortResult testMethod(int methodIndex) {
    logWriter.writeMessage("Sorting data by: " + SORT_METHODS_NAMES[methodIndex]);

    Sorter sorter;
    switch (methodIndex) {
      case Method.QUICK_SORT:
        sorter = new QuickSorter();
        break;
      case Method.COCKTAIL_SORT:
        sorter = new CocktailSorter();
        break;
      case Method.SHELL_SORT:
        sorter = new ShellSorter();
        break;
      case Method.HEAP_SORT:
        sorter = new HeapSorter();
        break;
      case Method.RADIX_SORT:
        sorter = new RadixSorter();
        break;
      case Method.GNOME_SORT:
        sorter = new GnomeSorter();
        break;
      case Method.SELECTION_SORT:
        sorter = new SelectionSorter();
        break;
      case Method.INSERTION_SORT:
        sorter = new InsertionSorter();
        break;
      case Method.BUCKET_SORT:
        sorter = new BucketSorter();
        break;
      default:
        throw new IllegalArgumentException("Unknown sort method");
    }

    timer.start();
    data = sorter.sort(data);
    timer.stop();

    logWriter.writeMessage(String.format("Data sorted in %d us", timer.getMicros()));
    return new SortResult(methodIndex, timer.getMicros());
  }
}
