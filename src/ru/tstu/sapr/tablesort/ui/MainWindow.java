package ru.tstu.sapr.tablesort.ui;

import ru.tstu.sapr.tablesort.core.AppEvent;
import ru.tstu.sapr.tablesort.core.AppEventListener;
import ru.tstu.sapr.tablesort.core.LogWriter;

import javax.swing.*;

public class MainWindow extends JFrame implements LogWriter {
  //Application level
  private AppEventListener listener;
  //UI components
  private JPanel rootPanel;
  private JTextArea logArea;
  private JButton generateButton;
  private JButton startButton;
  private JTable tArrayData;
  private JTable tInfo;

  public MainWindow(AppEventListener listener) {
    this.listener = listener;
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setContentPane(rootPanel);
    setTitle("Sorting algorithms");
    initComponents();
    setVisible(true);
    pack();
  }

  @Override
  public void writeMessage(String msg) {
    logArea.append(msg);
    logArea.append("\n");
  }

  private void initComponents() {
    generateButton.addActionListener(e ->
      listener.onAppEvent(AppEvent.GENERATE_DATA));
    startButton.addActionListener(e ->
      listener.onAppEvent(AppEvent.PROCESS_DATA));
  }

  public void updateDataSet(int[] data) {

  }
}
