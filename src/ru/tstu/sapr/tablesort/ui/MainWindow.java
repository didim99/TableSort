package ru.tstu.sapr.tablesort.ui;

import ru.tstu.sapr.tablesort.core.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.util.ArrayList;

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
  
  private final int ARRAY_SIDE_LENGTH = 10;
  private final int DATA_MIN = 0;
  private final int DATA_MAX = 1000;

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
  
    //Sets model for array data table
    tArrayData.setModel(new DefaultTableModel(ARRAY_SIDE_LENGTH, ARRAY_SIDE_LENGTH) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    });
    
    //Sets renderer for array data table
    TableCellRenderer r = new CustomTableCellRenderer(DATA_MIN, DATA_MAX);
    tArrayData.setDefaultRenderer(Object.class, r);
    
    //Sets model for info table
    tInfo.setModel(new DefaultTableModel(new String[]{"Sorting method", "Time"}, Model.SORT_METHODS_NAMES.length){
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    });
  }

  public int getSortMethod() {
    return selMethod.getSelectedIndex();
  }

  public void updateDataSet(int[] data) {
    int cols = tArrayData.getModel().getColumnCount();
    
    for (int i = 0; i < data.length; ++i) {
      int value = data[i];
      tArrayData.getModel().setValueAt(value, i / cols, i % cols);
    }
  }

  public void updateList(ArrayList<SortResult> results) {
    for (int i = 0; i < results.size(); ++i) {
      SortResult result = results.get(i);
      tInfo.getModel().setValueAt(Model.SORT_METHODS_NAMES[result.getMethodIndex()], i, 0);
      tInfo.getModel().setValueAt(result.getTime(), i, 1);
    }
  }
}
