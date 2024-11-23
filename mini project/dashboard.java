package sem.project2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard1 extends JFrame implements ActionListener {
    private JPanel sidebar;
    private JPanel contentPanel;

    public Dashboard1() {
        setTitle("Dashboard");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(200, 500));
        sidebar.setBackground(new Color(1, 54, 94)); 
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel logoLabel = new JLabel("Inventory", JLabel.CENTER);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20))); 
        sidebar.add(logoLabel);
        addSidebarButton("Fruits and Vegetables");
        addSidebarButton("Dairy Products");
        addSidebarButton("Cereals and Grains"); 
        addSidebarButton("Spices");
        addSidebarButton("Meat and Poultry");
        addSidebarButton("Images");

        add(sidebar, BorderLayout.WEST);
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
        addImageToContentPanel();

        setVisible(true);
    }

    private void addSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 40));
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(198, 219, 255)); 
        button.setFocusPainted(false);
        button.addActionListener(this);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20))); 
        sidebar.add(button);
    }

    private void addImageToContentPanel() {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/dash.jpg"));
        JLabel imageLabel = new JLabel(imageIcon);
        Image originalImage = imageIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(500, 280, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(imageLabel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if ("Fruits and Vegetables".equals(action)) {
            openFVPage();
        } else if ("Dairy Products".equals(action)) {
            openDPage();
        } 
        else if ("Cereals and Grains".equals(action)) {
            openCGPage();
        } else if ("Spices".equals(action)) {
            openSpicesPage();
        } 
        else if ("Meat and Poultry".equals(action)) {
            openMPPage() ;
        }  else if ("Images".equals(action)) {
            openImagesPage();
        }
        else {
            displayContent(action);
        }
    }
    private void openFVPage() {
        FruitsVeg Page = new FruitsVeg();
        Page.setVisible(true); 
    }
    private void openCGPage() {
        CerealsGrains Page = new CerealsGrains();
        Page.setVisible(true); 
    }
    private void openMPPage() {
        MeatPoultry Page = new MeatPoultry();
        Page.setVisible(true); 
    }
    private void openImagesPage() {
        JFrame TypeFrame = new JFrame("Images");
        TypeFrame.setSize(600, 400);
        TypeFrame.add(new images());
        TypeFrame.setLocationRelativeTo(this);
        TypeFrame.setVisible(true);
    }
    private void openDPage() {
        Dairy Page = new Dairy();
        Page.setVisible(true); 
    }
    private void openSpicesPage() {
        Spices Page = new Spices();
        Page.setVisible(true); 
    }

    private void displayContent(String title) {
        contentPanel.removeAll();
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        contentPanel.add(titleLabel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}