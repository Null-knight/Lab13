package com.mypackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AnimationApp extends JFrame {
    private JPanel animationPanel;
    private JLabel imageLabel;
    private JLabel messageLabel;
    private Timer animationTimer;
    private Timer messageTimer;

    private List<ImageIcon> images;
    private String[] messages = {"Welcome", "Enjoy", "Thanks", "Visit Again"};

    private int imageIndex = 0;
    private int messageIndex = 0;
    private int x = 50, y = 50;
    private int direction = 0; // 0:right, 1:down, 2:left, 3:up
    private int step = 5;

    public AnimationApp() {
        setTitle("Image Animation Demo");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create control panel with buttons
        JPanel controlPanel = new JPanel();
        JButton startBtn = new JButton("Start Animation");
        JButton stopBtn = new JButton("Stop Animation");

        controlPanel.add(startBtn);
        controlPanel.add(stopBtn);

        // Create animation panel
        animationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the square path for reference
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(50, 50, 400, 400);
            }
        };
        animationPanel.setLayout(null);
        animationPanel.setBackground(Color.WHITE);
        animationPanel.setPreferredSize(new Dimension(500, 500));

        // Create image label
        imageLabel = new JLabel();
        imageLabel.setBounds(x, y, 100, 100);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        animationPanel.add(imageLabel);

        // Create message label (fixed position at top)
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        messageLabel.setForeground(Color.BLUE);
        messageLabel.setBounds(200, 10, 200, 40);
        animationPanel.add(messageLabel);

        // Load images
        loadImages();

        // Add components to frame
        add(controlPanel, BorderLayout.NORTH);
        add(animationPanel, BorderLayout.CENTER);

        // Create animation timer (10ms delay)
        animationTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveImage();
                updateImage();
            }
        });

        // Create message timer (2000ms delay for message changes)
        messageTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMessage();
            }
        });

        // Button actions
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startAnimation();
            }
        });

        stopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopAnimation();
            }
        });
    }

    private void loadImages() {
        images = new ArrayList<>();

        // CORRECTED PATH: Go up one level from src to find the images folder
        String imagePath = "../images/";

        // Array of image filenames
        String[] imageFiles = {
                imagePath + "image1.jpg",
                imagePath + "image2.jpg",
                imagePath + "image3.jpg",
                imagePath + "image4.jpg"
        };

        // Show current directory for debugging
        System.out.println("Current directory: " + System.getProperty("user.dir"));
        System.out.println("Looking for images in: " + new File(imagePath).getAbsolutePath());

        boolean imagesFound = false;

        for (String filePath : imageFiles) {
            File imgFile = new File(filePath);
            System.out.println("Checking: " + imgFile.getAbsolutePath() + " - Exists? " + imgFile.exists());

            if (imgFile.exists()) {
                ImageIcon icon = new ImageIcon(filePath);
                if (icon.getIconWidth() > 0) {
                    images.add(icon);
                    System.out.println("✅ Loaded: " + filePath);
                    imagesFound = true;
                }
            }
        }

        // If no images found, create placeholders
        if (!imagesFound || images.isEmpty()) {
            System.out.println("⚠️ No images found. Creating colored placeholders...");
            createPlaceholderImages();
        } else {
            System.out.println("✅ Successfully loaded " + images.size() + " images");
        }

        // Make sure we have at least 4 images (repeat if necessary)
        while (images.size() < 4) {
            images.addAll(images);
        }
    }

    private void createPlaceholderImages() {
        images.clear();
        Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE};
        String[] texts = {"Image 1", "Image 2", "Image 3", "Image 4"};

        for (int i = 0; i < colors.length; i++) {
            // Create a buffered image
            BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = img.createGraphics();

            // Fill background
            g2d.setColor(colors[i]);
            g2d.fillRect(0, 0, 100, 100);

            // Draw border
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRect(2, 2, 95, 95);

            // Draw text
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(texts[i]);
            g2d.drawString(texts[i], (100 - textWidth) / 2, 55);

            g2d.dispose();
            images.add(new ImageIcon(img));
        }
        System.out.println("✅ Created " + images.size() + " placeholder images");
    }

    private void moveImage() {
        // Move in square pattern
        switch (direction) {
            case 0: // moving right
                x += step;
                if (x >= 450) {
                    x = 450;
                    direction = 1;
                }
                break;
            case 1: // moving down
                y += step;
                if (y >= 450) {
                    y = 450;
                    direction = 2;
                }
                break;
            case 2: // moving left
                x -= step;
                if (x <= 50) {
                    x = 50;
                    direction = 3;
                }
                break;
            case 3: // moving up
                y -= step;
                if (y <= 50) {
                    y = 50;
                    direction = 0;
                }
                break;
        }

        imageLabel.setBounds(x, y, 100, 100);
        animationPanel.repaint();
    }

    private void updateImage() {
        if (images.isEmpty()) return;

        // Change image every 20 frames (200ms at 10ms timer)
        if (imageIndex % 20 == 0) {
            int imgIdx = (imageIndex / 20) % images.size();
            ImageIcon icon = images.get(imgIdx);

            // Scale image to fit label
            Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        }
        imageIndex++;
    }

    private void updateMessage() {
        messageLabel.setText(messages[messageIndex]);
        messageIndex = (messageIndex + 1) % messages.length;
    }

    private void startAnimation() {
        animationTimer.start();
        messageTimer.start();
        updateMessage(); // Show first message immediately
        System.out.println("▶️ Animation started");
    }

    private void stopAnimation() {
        animationTimer.stop();
        messageTimer.stop();
        System.out.println("⏹️ Animation stopped");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Set system look and feel
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new AnimationApp().setVisible(true);
            }
        });
    }
}