package project;

import java.awt.*;
import java.util.ArrayList;

class Spaceship extends GameObject {
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private final int SPEED = 10;
    public static final int WIDTH = 60; // or whatever your spaceship width is


    public Spaceship(int x, int y) {
        super(x, y, 60, 30);
    }

    @Override
    public void update() {
        for (Bullet b : bullets) b.move();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(x, y, width, height);
    }
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public void moveLeft() { if (x > 0) x -= SPEED; }
    public void moveRight() { if (x + width < 800) x += SPEED; }
    public void fireBullet() { bullets.add(new Bullet(x + width / 2 - 2, y)); }
    public ArrayList<Bullet> getBullets() { return bullets; }
}
