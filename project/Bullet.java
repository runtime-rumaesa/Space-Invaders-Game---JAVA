package project;

import java.awt.*;

class Bullet extends GameObject {
    private final int speed = 8;
    private boolean hit = false;

    public Bullet(int x, int y) {
        super(x, y, 4, 10);
    }

    @Override
    public void update() {
        move();
    }

    public void move() {
        y -= speed;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, width, height);
    }

    public boolean isHit() { return hit; }
    public void setHit(boolean hit) { this.hit = hit; }
}
