package sem.project2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class login extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;
    private Image backgroundImage;

    public login() {
        setTitle("Login Page");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/login.png"));
        backgroundImage = icon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null);
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(200, 100, 100, 25);
        usernameLabel.setForeground(Color.WHITE); 
        backgroundPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(300, 100, 200, 25);
        backgroundPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(200, 140, 100, 25);
        passwordLabel.setForeground(Color.WHITE); 
        backgroundPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(300, 140, 200, 25);
        backgroundPanel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(250, 200, 90, 25);
        loginButton.addActionListener(this);
        backgroundPanel.add(loginButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(360, 200, 90, 25);
        cancelButton.addActionListener(e -> clearFields());
        backgroundPanel.add(cancelButton);

        add(backgroundPanel);
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        if (authenticateUser(username, password)) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            Dashboard1 dashboard = new Dashboard1();
            dashboard.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }

    private boolean authenticateUser(String username, String password) {
        String url = "jdbc:mysql://localhost:3306/inventory";
        String dbUser = "root";
        String dbPassword = "";
        boolean isAuthenticated = false;

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword)) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isAuthenticated = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return isAuthenticated;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            login loginPage = new login();
            loginPage.setVisible(true);
        });
    }
}