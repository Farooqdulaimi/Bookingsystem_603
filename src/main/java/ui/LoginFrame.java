package ui;

import model.User;
import service.UserService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField userField;
    private JPasswordField passField;
    private UserService userService = new UserService();

    public LoginFrame() {

        setTitle("Car Rental System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(Theme.BACKGROUND);

        // HEADER
        JLabel header = new JLabel("CAR RENTAL SYSTEM", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setOpaque(true);
        header.setBackground(Theme.PRIMARY);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(500, 80));

        container.add(header, BorderLayout.NORTH);

        // CENTER CARD
        JPanel card = new JPanel();
        card.setLayout(null);
        card.setPreferredSize(new Dimension(400, 250));
        card.setBackground(Color.WHITE);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 40, 100, 25);

        userField = new JTextField();
        userField.setBounds(150, 40, 200, 25);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 90, 100, 25);

        passField = new JPasswordField();
        passField.setBounds(150, 90, 200, 25);

        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setBounds(50, 150, 120, 35);
        loginBtn.setBackground(Theme.SECONDARY);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(Theme.BUTTON_FONT);

        JButton signupBtn = new JButton("SIGN UP");
        signupBtn.setBounds(230, 150, 120, 35);
        signupBtn.setBackground(Color.GRAY);
        signupBtn.setForeground(Color.WHITE);

        loginBtn.addActionListener(e -> doLogin());

        signupBtn.addActionListener(e -> {
            dispose();
            new SignupFrame().setVisible(true);
        });

        card.add(userLabel);
        card.add(userField);
        card.add(passLabel);
        card.add(passField);
        card.add(loginBtn);
        card.add(signupBtn);

        container.add(card, BorderLayout.CENTER);

        add(container);
    }

    private void doLogin() {

        String username = userField.getText();
        String password = new String(passField.getPassword());

        User user = userService.login(username, password);

        if (user == null) {
            JOptionPane.showMessageDialog(this, "Invalid login");
            return;
        }

        dispose();

        if ("admin".equalsIgnoreCase(user.getRole())) {
            new MainFrame(user).setVisible(true);
        } else {
            new CustomerMainFrame(user).setVisible(true);
        }
    }
}