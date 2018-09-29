package ru.tstu.sapr.tablesort.core;

import ru.tstu.sapr.tablesort.ui.MainWindow;

public class Application implements AppEventListener {
  private LogWriter globalLogWriter;
  private MainWindow mainWindow;
  private Model model;

  private Application() {
    mainWindow = new MainWindow(this);
    globalLogWriter = mainWindow;
    model = new Model(globalLogWriter);
  }

  @Override
  public void onAppEvent(AppEvent event) {
    switch (event) {
      case GENERATE_DATA:
        mainWindow.updateDataSet(model.generateData());
        break;
      case PROCESS_DATA:
        break;
    }
  }

  public static void main(String[] args) {
    new Application();
  }
}
