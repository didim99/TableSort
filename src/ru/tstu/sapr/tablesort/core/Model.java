package ru.tstu.sapr.tablesort.core;

import java.util.ArrayList;
import java.util.Random;
import ru.tstu.sapr.tablesort.core.sorter.*;

public class Model implements SorterThread.EventListener {
  private static final int DATA_SIZE = 100000;
  public static final int DATA_MIN = 0;
  public static final int DATA_MAX = DATA_SIZE * 10;

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
  private int threadCount;
  private int[] data;

  Model(LogWriter logWriter, AppEventListener listener) {
    this.logWriter = logWriter;
    this.listener = listener;
    this.result = new ArrayList<>();
    threadCount = 0;
  }

  @Override
  public void onFinished(int[] data, SortResult result) {
    this.result.add(result);
    this.data = data;

    logWriter.writeMessage(String.format("Completed in: %d ms [%s]",
      result.getTime(), SORT_METHOD_NAMES[result.getMethodIndex()]));

    if (--threadCount == 0)
      listener.onAppEvent(Application.Event.TEST_ALL_FINISHED);
    else if (threadCount == -1)
      listener.onAppEvent(Application.Event.TEST_FINISHED);
  }

  int[] generateData() {
    int[] data = new int[DATA_SIZE];
    Random random = new Random();
    for (int i = 0; i < data.length; i++)
      data[i] = random.nextInt(DATA_MAX + 1);
    this.data = data;
    return data;
  }

  void testMethod(int sortMethod) {
    result.clear();
    threadCount = 0;
    logWriter.writeMessage(
      "Testing method: " + SORT_METHOD_NAMES[sortMethod]);
    listener.onAppEvent(Application.Event.DATA_GENERATED);
    new SorterThread(this,
      sortMethod, data).start();
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

  ArrayList<SortResult> getResult() {
    return result;
  }

  int[] getData() {
    return data;
  }
}
