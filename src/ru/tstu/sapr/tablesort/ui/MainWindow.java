package ru.tstu.sapr.tablesort.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
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
  private JTextField etSize;
  private JButton btnTest;
  private JTable tInfo;
  private JProgressBar pbMain;

  private static final Color BG_ERROR = new Color(0xffdada);
  private static final Color BG_NORMAL = new Color(0xffffff);
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
  }

  @Override
  public synchronized void writeMessage(String msg) {
    logArea.append(msg);
    logArea.append("\n");
  }

  private void initComponents() {
    pbMain.setString(TESTING);
    btnTest.addActionListener(e ->
      listener.onAppEvent(Application.Event.TEST));
    
    //Sets model for info table
    tInfo.setModel(new DefaultTableModel(COLUMN_NAMES, Model.SORT_METHOD_NAMES.length) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    });
  }

  public void uiLock(boolean state) {
    etSize.setEnabled(!state);
    btnTest.setEnabled(!state);
    pbMain.setStringPainted(state);
    pbMain.setIndeterminate(state);
  }

  public String getDataSize() {
    setError(false);
    return etSize.getText();
  }

  public void updateDataSize(int size) {
    etSize.setText(String.valueOf(size));
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

  public void setError(boolean isError) {
    etSize.setBackground(isError ? BG_ERROR : BG_NORMAL);
  }
}
