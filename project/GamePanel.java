package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

class GamePanel extends JPanel implements ActionListener, KeyListener {
    private String playerName;
    private Spaceship spaceship;
    private ArrayList<GameObject> gameObjects;
    private Timer timer;
    private int missedAsteroids = 0;
    private final int MAX_MISSES = 3;
    private int asteroidSpawnTimer = 0;
    private GameWindow gameWindow;

    public GamePanel(String playerName, GameWindow gameWindow) {
        this.playerName = playerName;
        this.gameWindow = gameWindow;

        setFocusable(true);
        requestFocusInWindow();  // Ensure the panel gets keyboard focus
        addKeyListener(this);
        gameObjects = new ArrayList<>();

        spaceship = new Spaceship(0, 0); // temp values, will be repositioned
        gameObjects.add(spaceship);

        timer = new Timer(16, this); // ~60 FPS

        // Resize listener to reposition spaceship
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                repositionSpaceship();
            }
        });
    }

    public void start() {
        repositionSpaceship();
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);

        // Draw all game objects
        for (GameObject obj : gameObjects) {
            obj.draw(g);
        }

        // Draw bullets separately
        g.setColor(Color.WHITE);
        for (Bullet bullet : spaceship.getBullets()) {
            bullet.draw(g);
        }

        // Draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Score: " + gameWindow.getScore(), getWidth() - 150, 30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        spaceship.update();
        for (GameObject obj : gameObjects) {
            obj.update();
        }

        asteroidSpawnTimer++;
        if (asteroidSpawnTimer >= 60) {
            spawnSingleAsteroid();
            asteroidSpawnTimer = 0;
        }

        checkCollisions();
        removeDestroyedOrMissed();
        repaint();

        if (missedAsteroids >= MAX_MISSES) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Game Over! You missed 3 asteroids.\nFinal Score: " + gameWindow.getScore());
            saveScoreToFile();
            System.exit(0);
        }
    }

    private void spawnSingleAsteroid() {
        int x = (int) (Math.random() * (getWidth() - 40));
        gameObjects.add(new Asteroid(x, 0));
    }

    private void checkCollisions() {
        for (Bullet b : spaceship.getBullets()) {
            for (GameObject obj : gameObjects) {
                if (obj instanceof Asteroid a && !a.isDestroyed()) {
                    if (a.getBounds().intersects(b.getBounds())) {
                        a.takeDamage();
                        b.setHit(true);
                        if (a.isDestroyed()) {
                            gameWindow.incrementScore();
                        }
                    }
                }
            }
        }
    }

    private void removeDestroyedOrMissed() {
        int panelHeight = getHeight();

        gameObjects.removeIf(obj -> {
            if (obj instanceof Asteroid a) {
                if (a.getY() > panelHeight) {
                    missedAsteroids++;
                    return true;
                }
                return a.isDestroyed();
            } else if (obj instanceof Bullet b) {
                return b.isHit() || b.y < 0;
            }
            return false;
        });

        spaceship.getBullets().removeIf(b -> b.isHit() || b.y < 0);
    }

    private void repositionSpaceship() {
        int startX = getWidth() / 2 - Spaceship.WIDTH / 2;
        int startY = getHeight() - 100;
        spaceship.setPosition(startX, startY);
    }

    private void saveScoreToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("scores.txt", true))) {
            writer.write("Player: " + playerName + " | Score: " + gameWindow.getScore());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Key Controls
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> spaceship.moveLeft();
            case KeyEvent.VK_RIGHT -> spaceship.moveRight();
            case KeyEvent.VK_SPACE -> spaceship.fireBullet();
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
