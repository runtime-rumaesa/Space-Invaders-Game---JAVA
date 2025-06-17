package project;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Menu extends JFrame {
    public Menu() {
        setTitle("Space Invaders - Main Menu");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create panel and set full background color
        JPanel backgroundPanel = new JPanel(new BorderLayout());
        backgroundPanel.setBackground(new Color(30, 30, 60)); // Dark navy blue

        // Title
        JLabel title = new JLabel("SPACE INVADERS", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(new Color(30, 30, 60)); // Match panel color
        backgroundPanel.add(title, BorderLayout.NORTH);

        // Start Game button
        JButton startButton = new JButton("START GAME");
        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startButton.setBackground(new Color(0, 0, 0));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);

        // Scoreboard button
        JButton scoreboardButton = new JButton("VIEW SCOREBOARD");
        scoreboardButton.setFont(new Font("Arial", Font.PLAIN, 18));
        scoreboardButton.setBackground(new Color(50, 50, 50));
        scoreboardButton.setForeground(Color.WHITE);
        scoreboardButton.setFocusPainted(false);
        scoreboardButton.setBorderPainted(false);

        // Button panel to stack buttons vertically
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Transparent background
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10)); // 2 rows, vertical gap
        buttonPanel.add(startButton);
        buttonPanel.add(scoreboardButton);

        backgroundPanel.add(buttonPanel, BorderLayout.CENTER);

        // Start game event
        startButton.addActionListener(e -> {
            String playerName = JOptionPane.showInputDialog(this, "Enter your name:");

            // Validate
            if (playerName == null || playerName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty!");
                return;
            }
            if (!playerName.matches("[a-zA-Z ]+")) {
                JOptionPane.showMessageDialog(this, "Name must contain only alphabets.");
                return;
            }

            new GameWindow(playerName.trim()); // Pass to GameWindow
            dispose();
        });



        // View scoreboard event
        scoreboardButton.addActionListener(e -> {
            try {
                String content = new String(Files.readAllBytes(Paths.get("scores.txt")));

                JTextArea textArea = new JTextArea(content);
                textArea.setEditable(false);
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(400, 300));

                JOptionPane.showMessageDialog(this, scrollPane, "Scoreboard", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "No scores found yet.", "Scoreboard", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        setContentPane(backgroundPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Menu::new);
    }
}
