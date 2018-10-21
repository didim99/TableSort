package ru.tstu.sapr.tablesort.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.Collections;
import ru.tstu.sapr.tablesort.core.*;
import ru.tstu.sapr.tablesort.core.sorter.SortResult;

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
  private JProgressBar pbMain;

  private static final int ARRAY_SIDE_LENGTH = 10;
  private static final int ARRAY_SIZE = ARRAY_SIDE_LENGTH * ARRAY_SIDE_LENGTH;
  private static final String[] COLUMN_NAMES = { "Sorting method", "Time (ms)" };
  private static final String WINDOW_TITLE = "Sorting algorithms";
  private static final String TESTING = "Testing...";

  public MainWindow(AppEventListener listener) {
    this.listener = listener;
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setContentPane(rootPanel);
    setTitle(WINDOW_TITLE);
    setResizable(false);
    initComponents();
    setVisible(true);
    pack();
    onViewResized();
  }

  private void onViewResized() {
    //Calculate row height
    tArrayData.setRowHeight(tArrayData.getHeight() / ARRAY_SIDE_LENGTH);
  }

  @Override
  public synchronized void writeMessage(String msg) {
    logArea.append(msg);
    logArea.append("\n");
  }

  private void initComponents() {
    for (String method : Model.SORT_METHOD_NAMES)
      selMethod.addItem(method);
    btnGenerate.addActionListener(e ->
      listener.onAppEvent(Application.Event.GENERATE_DATA));
    btnTest.addActionListener(e ->
      listener.onAppEvent(Application.Event.TEST_METHOD));
    btnTestAll.addActionListener(e ->
      listener.onAppEvent(Application.Event.TEST_ALL));
    pbMain.setString(TESTING);

    //Sets model for array data table
    tArrayData.setModel(new DefaultTableModel(ARRAY_SIDE_LENGTH, ARRAY_SIDE_LENGTH) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    });

    //Sets renderer for array data table
    TableCellRenderer r = new CustomTableCellRenderer(Model.DATA_MIN, Model.DATA_MAX);
    tArrayData.setDefaultRenderer(Object.class, r);
    
    //Sets model for info table
    tInfo.setModel(new DefaultTableModel(COLUMN_NAMES, Model.SORT_METHOD_NAMES.length) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    });
  }

  public void uiLock(boolean state) {
    selMethod.setEnabled(!state);
    btnGenerate.setEnabled(!state);
    btnTestAll.setEnabled(!state);
    btnTest.setEnabled(!state);
    pbMain.setStringPainted(state);
    pbMain.setIndeterminate(state);
  }

  public int getSortMethod() {
    return selMethod.getSelectedIndex();
  }

  public void updateDataSet(int[] data) {
    int cols = tArrayData.getModel().getColumnCount();
    TableModel model = tArrayData.getModel();
    int step = data.length / ARRAY_SIZE;
    for (int i = 0; i < ARRAY_SIZE; i++)
      model.setValueAt(data[i * step], i / cols, i % cols);
  }

  public void updateList(ArrayList<SortResult> results) {
    Collections.sort(results);
    TableModel model = tInfo.getModel();
    for (int i = 0; i < results.size(); ++i) {
      SortResult result = results.get(i);
      model.setValueAt(Model.SORT_METHOD_NAMES[result.getMethodIndex()], i, 0);
      model.setValueAt(result.getTime(), i, 1);
    }
  }
}
