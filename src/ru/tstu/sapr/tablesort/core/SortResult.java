package ru.tstu.sapr.tablesort.core;

public class SortResult {
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
}
