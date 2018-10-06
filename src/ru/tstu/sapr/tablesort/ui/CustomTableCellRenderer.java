package ru.tstu.sapr.tablesort.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

public class CustomTableCellRenderer implements TableCellRenderer {
  private static final Color COLOR_START = new Color(0x7fbff3);
  private static final Color COLOR_END = new Color(0xec80aa);
  private static final Color COLOR_TEXT = new Color(0x000000);
  private static final Font FONT = new Font("Arial", Font.PLAIN, 12);

  private static TableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();
  private final int min, max;

  CustomTableCellRenderer(int min, int max) {
    this.min = min;
    this.max = max;
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                 boolean hasFocus, int row, int column) {
    Component c = DEFAULT_RENDERER.getTableCellRendererComponent(
      table, value, isSelected, hasFocus, row, column);

    //Sets layout params
    ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
    c.setFont(FONT);

    float alpha;

    //Finds alpha value
    if (value != null) {
      alpha = ((int) value - min) / (float) (max - min);
    } else {
      alpha = 0.5f;
    }

    //Sets background color
    Color bgColor = lerp(COLOR_START, COLOR_END, alpha);
    c.setBackground(bgColor);

    //Sets text color
    if (hasFocus) {
      c.setForeground(COLOR_TEXT);
    } else {
      c.setForeground(bgColor);
    }

    return c;
  }

  /**
   * Linear interpolation between two colors
   *
   * @param a     First color
   * @param b     Second color
   * @param alpha Blending parameter - how much of second color to include
   * @return The interpolated color
   */
  private Color lerp(Color a, Color b, float alpha) {
    if (alpha < 0.0f || alpha > 1.0f) {
      throw new IllegalArgumentException("Alpha is out of bounds");
    }

    float red = a.getRed() * (1 - alpha) + b.getRed() * alpha;
    float green = a.getGreen() * (1 - alpha) + b.getGreen() * alpha;
    float blue = a.getBlue() * (1 - alpha) + b.getBlue() * alpha;

    return new Color(red / 255, green / 255, blue / 255);
  }
}
