package ui;

import model.User;
import service.UserService;

import javax.swing.*;
import java.awt.*;

public class SignupFrame extends JFrame {

    private JTextField userField;
    private JPasswordField passField;
    private JPasswordField confirmField;
    private JTextField emailField;
    private JTextField phoneField;

    private UserService userService = new UserService();

    public SignupFrame() {
        setTitle("Sign Up");
        setSize(420, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(ThemeManager.getPrimaryColor());
        JLabel title = new JLabel("Create Account", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.add(title, BorderLayout.CENTER);

        JButton themeBtn = new JButton("Toggle Theme");
        themeBtn.addActionListener(e -> {
            ThemeManager.toggleTheme();
            ThemeManager.applyTheme(getContentPane());
        });
        header.add(themeBtn, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(ThemeManager.getBackgroundColor());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        userField = new JTextField(20);
        passField = new JPasswordField(20);
        confirmField = new JPasswordField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 0;
        center.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        center.add(userField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        center.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        center.add(passField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        center.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1;
        center.add(confirmField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        center.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        center.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        center.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        center.add(phoneField, gbc);

        JButton signupBtn = new JButton("Create Account");
        JButton backBtn = new JButton("Back to Login");

        gbc.gridx = 0; gbc.gridy = 5;
        center.add(signupBtn, gbc);
        gbc.gridx = 1;
        center.add(backBtn, gbc);

        add(center, BorderLayout.CENTER);

        signupBtn.addActionListener(e -> doSignup());
        backBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        ThemeManager.applyTheme(getContentPane());
    }

    private void doSignup() {
        String username = userField.getText().trim();
        String password = new String(passField.getPassword());
        String confirm = new String(confirmField.getPassword());
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password are required");
            return;
        }

        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match");
            return;
        }

        userService.addCustomer(username, password, email, phone);

        JOptionPane.showMessageDialog(this, "Account created successfully!");
        dispose();
        new LoginFrame().setVisible(true);
    }
}
