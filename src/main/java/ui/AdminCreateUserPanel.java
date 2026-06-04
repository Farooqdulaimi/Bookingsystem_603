package ui;

import model.User;
import service.UserService;

import javax.swing.*;
import java.awt.*;

public class AdminCreateUserPanel extends JPanel {

    private JTextField userField;
    private JPasswordField passField;
    private JComboBox<String> roleCombo;
    private UserService userService = new UserService();

    public AdminCreateUserPanel() {
        setLayout(new GridBagLayout());
        setBackground(ThemeManager.getBackgroundColor());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Create User (Admin)", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JLabel roleLabel = new JLabel("Role:");

        userField = new JTextField(20);
        passField = new JPasswordField(20);
        roleCombo = new JComboBox<>(new String[]{"admin", "customer"});

        JButton createBtn = new JButton("Create User");

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        add(userLabel, gbc);
        gbc.gridx = 1;
        add(userField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(passLabel, gbc);
        gbc.gridx = 1;
        add(passField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(roleLabel, gbc);
        gbc.gridx = 1;
        add(roleCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(createBtn, gbc);

        createBtn.addActionListener(e -> createUser());

        ThemeManager.applyTheme(this);
    }

    private void createUser() {
        String username = userField.getText().trim();
        String password = new String(passField.getPassword());
        String role = (String) roleCombo.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill in username and password");
            return;
        }

        User u = new User(0, username, password, role, "", "");
        userService.addUser(u);

        JOptionPane.showMessageDialog(this, "User created successfully");
        userField.setText("");
        passField.setText("");
    }
}
