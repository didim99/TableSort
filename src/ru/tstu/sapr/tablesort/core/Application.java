package ru.tstu.sapr.tablesort.core;

import ru.tstu.sapr.tablesort.ui.MainWindow;

public class Application implements AppEventListener {
  public enum Event { TEST, TEST_FINISHED }

  private LogWriter logWriter;
  private MainWindow mainWindow;
  private Model model;

  private Application() {
    mainWindow = new MainWindow(this);
    logWriter = mainWindow;
    model = new Model(logWriter, this);
    mainWindow.updateDataSize(model.getDataSize());
  }

  @Override
  public void onAppEvent(Event event) {
    switch (event) {
      case TEST:
        try {
          model.updateDataSize(Integer.parseInt(mainWindow.getDataSize()));
          mainWindow.updateDataSize(model.getDataSize());
          mainWindow.uiLock(true);
          model.testAll();
        } catch (NumberFormatException e) {
          logWriter.writeMessage("Error: Incorrect size");
          mainWindow.setError(true);
        }
        break;
      case TEST_FINISHED:
        mainWindow.uiLock(false);
        mainWindow.updateList(model.getResult());
        break;
    }
  }

  public static void main(String[] args) {
    new Application();
  }
}
