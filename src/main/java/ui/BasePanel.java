package ui;

import javax.swing.*;
import java.awt.*;

public class BasePanel extends JPanel {

    public BasePanel(String title) {
        setLayout(new BorderLayout());
        setBackground(Theme.BACKGROUND);

        // HEADER
        JPanel header = new JPanel();
        header.setBackground(Theme.PRIMARY);
        header.setPreferredSize(new Dimension(800, 70));
        header.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(Theme.TITLE_FONT);

        header.add(titleLabel);

        add(header, BorderLayout.NORTH);
    }
}