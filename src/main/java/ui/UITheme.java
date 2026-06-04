package ui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class UITheme {

    public static Color PRIMARY = new Color(30, 144, 255);
    public static Color BACKGROUND = new Color(245, 247, 250);
    public static Color DANGER = new Color(220, 53, 69);
    public static Color SUCCESS = new Color(40, 167, 69);
    public static Color WARNING = new Color(255, 193, 7);

    public static void applyPanel(JPanel panel) {
        panel.setBackground(BACKGROUND);
    }

    public static void styleTable(JTable table) {
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setGridColor(new Color(230, 230, 230));
        table.setShowVerticalLines(false);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(230, 230, 230));
    }

    // 🎯 STATUS COLOR RENDERER (ENTERPRISE FEATURE)
    public static void applyStatusRenderer(JTable table, int statusColumnIndex) {
        table.getColumnModel().getColumn(statusColumnIndex).setCellRenderer((tbl, value, isSelected, hasFocus, row, col) -> {

            JLabel label = new JLabel(value.toString());
            label.setOpaque(true);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));

            String status = value.toString().toUpperCase();

            if (status.contains("ACTIVE")) {
                label.setBackground(new Color(40, 167, 69));
                label.setForeground(Color.WHITE);
            } else if (status.contains("CANCELLED")) {
                label.setBackground(new Color(220, 53, 69));
                label.setForeground(Color.WHITE);
            } else {
                label.setBackground(Color.LIGHT_GRAY);
                label.setForeground(Color.BLACK);
            }

            return label;
        });
    }

    public static JButton primaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(PRIMARY);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return btn;
    }

    public static JButton dangerButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(DANGER);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    public static JTextField searchField() {
        JTextField f = new JTextField(20);
        f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return f;
    }
}