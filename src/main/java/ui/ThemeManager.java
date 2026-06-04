package ui;

import javax.swing.*;
import java.awt.*;

public class ThemeManager {

    private static boolean darkMode = false;

    public static boolean isDarkMode() {
        return darkMode;
    }

    public static void toggleTheme() {
        darkMode = !darkMode;
    }

    public static Color getBackgroundColor() {
        return darkMode ? new Color(30, 30, 30) : new Color(245, 247, 250);
    }

    public static Color getPanelColor() {
        return darkMode ? new Color(45, 45, 45) : Color.WHITE;
    }

    public static Color getPrimaryColor() {
        return darkMode ? new Color(0, 120, 215) : new Color(30, 144, 255);
    }

    public static Color getTextColor() {
        return darkMode ? Color.WHITE : Color.BLACK;
    }

    public static Color getHeaderTextColor() {
        return Color.WHITE;
    }

    public static Color getTableHeaderBackground() {
        return darkMode ? new Color(60, 60, 60) : new Color(230, 230, 230);
    }

    public static void applyTheme(Component comp) {
        if (comp instanceof JPanel || comp instanceof JFrame || comp instanceof JDialog) {
            comp.setBackground(getBackgroundColor());
        }

        if (comp instanceof JLabel) {
            comp.setForeground(getTextColor());
        }

        if (comp instanceof JButton) {
            JButton b = (JButton) comp;
            b.setBackground(getPrimaryColor());
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
        }

        if (comp instanceof JTable) {
            JTable t = (JTable) comp;
            t.setBackground(getPanelColor());
            t.setForeground(getTextColor());
            t.getTableHeader().setBackground(getTableHeaderBackground());
            t.getTableHeader().setForeground(getTextColor());
        }

        if (comp instanceof JScrollPane || comp instanceof JTabbedPane) {
            comp.setBackground(getBackgroundColor());
        }

        if (comp instanceof Container) {
            for (Component child : ((Container) comp).getComponents()) {
                applyTheme(child);
            }
        }

        comp.repaint();
    }
}
