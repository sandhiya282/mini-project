package sem.project2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class images extends JPanel {
    private JLabel imageLabel;
    private JButton nextButton;
    private int currentImageIndex = 0;
    private String[] imagePaths = {
            "/images/grains.jpg", "/images/fruits1.jpg",
            "/images/dairy.jpg", "/images/meat.jpg", "/images/vege.jpg", "/images/fruits2.jpg", "/images/cereals.jpg"
    };

    public images() {
        setLayout(new BorderLayout());

        // here is image label
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the image

        displayImage(currentImageIndex);
        add(imageLabel, BorderLayout.CENTER);

        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentImageIndex = (currentImageIndex + 1) % imagePaths.length;
                displayImage(currentImageIndex);
            }
        });
        add(nextButton, BorderLayout.SOUTH);
    }

    private void displayImage(int index) {
        URL imageUrl = getClass().getResource(imagePaths[index]);
        if (imageUrl != null) {
            ImageIcon originalIcon = new ImageIcon(imageUrl);
            Image originalImage = originalIcon.getImage();
            Image scaledImage = originalImage.getScaledInstance(600, 400, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        } else {
            JOptionPane.showMessageDialog(this, "Image not found: " + imagePaths[index]);
        }
    }
}