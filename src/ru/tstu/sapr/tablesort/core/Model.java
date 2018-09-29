package ru.tstu.sapr.tablesort.core;

import java.util.Random;

class Model {
  private static final int DEFAULT_DATA_SIZE = 100;

  private LogWriter logWriter;
  private int[] data;

  Model(LogWriter logWriter) {
    this.logWriter = logWriter;
    data = new int[DEFAULT_DATA_SIZE];
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
}
