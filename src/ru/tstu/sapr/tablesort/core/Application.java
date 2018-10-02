package ru.tstu.sapr.tablesort.core;

import ru.tstu.sapr.tablesort.ui.MainWindow;

public class Application implements AppEventListener {
  private MainWindow mainWindow;
  private Model model;

  private Application() {
    mainWindow = new MainWindow(this);
    LogWriter globalLogWriter = mainWindow;
    model = new Model(globalLogWriter);
  }

  @Override
  public void onAppEvent(AppEvent event) {
    switch (event) {
      case GENERATE_DATA:
        mainWindow.updateDataSet(model.generateData());
        break;
      case TEST_METHOD:
        model.testMethod(mainWindow.getSortMethod());
        mainWindow.updateDataSet(model.getData());
        break;
      case TEST_ALL:
        mainWindow.updateList(model.testAll());
        mainWindow.updateDataSet(model.getData());
        break;
    }
  }

  public static void main(String[] args) {
    new Application();
  }
}
