package project;

import javax.swing.*;

public class GameWindow extends JFrame {
    private String playerName;
    private int score = 0;

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score++;
    }

    public GameWindow(String playerName) {
        this.playerName = playerName;

        setTitle("Space Invaders");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        GamePanel gamePanel = new GamePanel(playerName, this);
        add(gamePanel);

        setVisible(true);
        gamePanel.start();
    }

    public static void main(String[] args) {
        String playerName = JOptionPane.showInputDialog("Enter Player Name:");
        SwingUtilities.invokeLater(() -> new GameWindow(playerName));
    }
}
