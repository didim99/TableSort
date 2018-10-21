package ru.tstu.sapr.tablesort.core.sorter;

public class SortResult implements Comparable<SortResult> {
  private int methodIndex;
  private long time;

  SortResult(int methodIndex, long time) {
    this.methodIndex = methodIndex;
    this.time = time;
  }

  public int getMethodIndex() {
    return methodIndex;
  }

  public long getTime() {
    return time;
  }

  @Override
  public int compareTo(SortResult o) {
    return Long.compare(time, o.time);
  }
}
