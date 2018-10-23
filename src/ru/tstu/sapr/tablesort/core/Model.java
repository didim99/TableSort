package ru.tstu.sapr.tablesort.core;

import java.util.ArrayList;
import java.util.Random;
import ru.tstu.sapr.tablesort.core.sorter.*;

public class Model implements SorterThread.EventListener {
  private static final int DEFAULT_DATA_SIZE = 200000;
  private static final int DATA_MAX_FACTOR = 10;

  public static final String[] SORT_METHOD_NAMES = {
    "Quick sort", "Cocktail sort", "Shell sort",
    "Heap sort", "Radix sort", "Gnome sort",
    "Selection sort", "Insertion sort", "Bucket sort"
  };

  public static final class Method {
    public static final int QUICK_SORT     = 0; // by keliz
    public static final int COCKTAIL_SORT  = 1; // by Medvedev
    public static final int SHELL_SORT     = 2; // by Makarov
    public static final int HEAP_SORT      = 3; // by makcimbx
    public static final int RADIX_SORT     = 4; // by IonShieldQuad
    public static final int GNOME_SORT     = 5; // by MagLoner
    public static final int SELECTION_SORT = 6; // by Merkulov
    public static final int INSERTION_SORT = 7; // by Eremin
    public static final int BUCKET_SORT    = 8; // by Erkhova
  }

  private LogWriter logWriter;
  private AppEventListener listener;
  private ArrayList<SortResult> result;
  private int dataSize, maxValue;
  private int threadCount;

  Model(LogWriter logWriter, AppEventListener listener) {
    this.logWriter = logWriter;
    this.listener = listener;
    this.result = new ArrayList<>();
    updateDataSize(DEFAULT_DATA_SIZE);
    threadCount = 0;
  }

  @Override
  public void onFinished(int[] data, SortResult result) {
    this.result.add(result);
    logWriter.writeMessage(String.format("Completed in: %d ms [%s]",
      result.getTime(), SORT_METHOD_NAMES[result.getMethodIndex()]));
    if (--threadCount == 0)
      listener.onAppEvent(Application.Event.TEST_FINISHED);
  }

  void testAll() {
    logWriter.writeMessage("Testing all sort methods");
    result.clear();

    threadCount = SORT_METHOD_NAMES.length;
    for (int method = 0; method < SORT_METHOD_NAMES.length; method++) {
      logWriter.writeMessage("Starting thread: "
        + SORT_METHOD_NAMES[method]);
      new SorterThread(this, method, generateData()).start();
    }
  }

  private int[] generateData() {
    int[] data = new int[dataSize];
    Random random = new Random();
    for (int i = 0; i < data.length; i++)
      data[i] = random.nextInt(maxValue + 1);
    return data;
  }

  ArrayList<SortResult> getResult() {
    return result;
  }

  void updateDataSize(int newSize) {
    this.dataSize = newSize;
    this.maxValue = dataSize * DATA_MAX_FACTOR;
  }

  int getDataSize() {
    return dataSize;
  }
}
