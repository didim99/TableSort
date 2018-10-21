package ru.tstu.sapr.tablesort.core;

import ru.tstu.sapr.tablesort.ui.MainWindow;

public class Application implements AppEventListener {
  public enum Event {
    GENERATE_DATA, TEST_METHOD, TEST_ALL,
    DATA_GENERATED, TEST_FINISHED, TEST_ALL_FINISHED
  }

  private MainWindow mainWindow;
  private Model model;

  private Application() {
    mainWindow = new MainWindow(this);
    LogWriter globalLogWriter = mainWindow;
    model = new Model(globalLogWriter, this);
  }

  @Override
  public void onAppEvent(Event event) {
    switch (event) {
      case GENERATE_DATA:
        mainWindow.updateDataSet(model.generateData());
        break;
      case TEST_METHOD:
        mainWindow.uiLock(true);
        model.testMethod(mainWindow.getSortMethod());
        break;
      case TEST_ALL:
        mainWindow.uiLock(true);
        model.testAll();
        break;
      case DATA_GENERATED:
        mainWindow.updateDataSet(model.getData());
        break;
      case TEST_FINISHED:
        mainWindow.uiLock(false);
        mainWindow.updateDataSet(model.getData());
        break;
      case TEST_ALL_FINISHED:
        mainWindow.uiLock(false);
        mainWindow.updateList(model.getResult());
        mainWindow.updateDataSet(model.getData());
        break;
    }
  }

  public static void main(String[] args) {
    new Application();
  }
}
