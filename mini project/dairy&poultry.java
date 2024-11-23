package sem.project2;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class Dairy extends JFrame {
    private JPanel dairyPanel;
    private JTable table;
    private JTextField idField, nameField, availabilityField;

    // DB connection details
    private static final String URL = "jdbc:mysql://localhost:3306/inventory";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public Dairy() {
        setTitle("Dairy Products");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        dairyPanel = new JPanel(new BorderLayout());
        dairyPanel.setBackground(new Color(173, 216, 230)); // Background color

        // Upper left form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.setBackground(new Color(195, 215, 240));

        // Text fields
        idField = new JTextField();
        nameField = new JTextField();
        availabilityField = new JTextField();

        Dimension textFieldSize = new Dimension(150, 25);
        idField.setPreferredSize(textFieldSize);
        nameField.setPreferredSize(textFieldSize);
        availabilityField.setPreferredSize(textFieldSize);

        // Form labels
        formPanel.add(new JLabel("ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Availability:"));
        formPanel.add(availabilityField);

        dairyPanel.add(formPanel, BorderLayout.WEST);

        // Center table
        table = new JTable();
        loadDataFromDatabase(); // Load data from DB
        JScrollPane tableScrollPane = new JScrollPane(table);
        dairyPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Bottom panel - buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(202, 202, 202)); // Background color

        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton removeButton = new JButton("Remove");
        JButton clearButton = new JButton("Clear");
        addButton.addActionListener(e -> addRecord());
        editButton.addActionListener(e -> editRecord());
        removeButton.addActionListener(e -> removeRecord());
        clearButton.addActionListener(e -> clearFormFields());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(clearButton);
        dairyPanel.add(buttonPanel, BorderLayout.SOUTH);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    fillFormFields(selectedRow);
                }
            }
        });

        add(dairyPanel);
    }

    private void loadDataFromDatabase() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Dairy")) {

            // Get metadata to create column names
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Column names for JTable
            Vector<String> columnNames = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            // Data rows for JTable
            Vector<Vector<Object>> data = new Vector<>();
            while (resultSet.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getObject(i));
                }
                data.add(row);
            }

            // Set model for JTable
            table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading data from database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fillFormFields(int selectedRow) {
        idField.setText(table.getValueAt(selectedRow, 0).toString());
        nameField.setText(table.getValueAt(selectedRow, 1).toString());
        availabilityField.setText(table.getValueAt(selectedRow, 2).toString());
    }

    private void addRecord() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "INSERT INTO Dairy (id, name, availability) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, idField.getText());
            preparedStatement.setString(2, nameField.getText());
            preparedStatement.setString(3, availabilityField.getText());
            preparedStatement.executeUpdate();
            loadDataFromDatabase();
            clearFormFields();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding record to database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editRecord() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "UPDATE Dairy SET name = ?, availability = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nameField.getText());
            preparedStatement.setString(2, availabilityField.getText());
            preparedStatement.setString(3, idField.getText());
            preparedStatement.executeUpdate();
            loadDataFromDatabase();
            clearFormFields();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error editing record in database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeRecord() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "DELETE FROM Dairy WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, idField.getText());
            preparedStatement.executeUpdate();
            loadDataFromDatabase();
            clearFormFields();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error removing record from database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFormFields() {
        idField.setText("");
        nameField.setText("");
        availabilityField.setText("");
    }
}