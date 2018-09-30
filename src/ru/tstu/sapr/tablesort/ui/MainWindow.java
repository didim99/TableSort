package ru.tstu.sapr.tablesort.ui;

import javax.swing.*;
import java.util.ArrayList;
import ru.tstu.sapr.tablesort.core.*;

public class MainWindow extends JFrame implements LogWriter {
  //Application level
  private AppEventListener listener;
  //UI components
  private JPanel rootPanel;
  private JTextArea logArea;
  private JButton btnGenerate;
  private JButton btnTest;
  private JButton btnTestAll;
  private JComboBox<String> selMethod;
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
    for (String method : Model.SORT_METHODS_NAMES)
      selMethod.addItem(method);
    btnGenerate.addActionListener(e ->
      listener.onAppEvent(AppEvent.GENERATE_DATA));
    btnTest.addActionListener(e ->
      listener.onAppEvent(AppEvent.TEST_METHOD));
    btnTestAll.addActionListener(e ->
      listener.onAppEvent(AppEvent.TEST_ALL));
  }

  public int getSortMethod() {
    return selMethod.getSelectedIndex();
  }

  public void updateDataSet(int[] data) {

  }

  public void updateList(ArrayList<SortResult> results) {

  }
}
