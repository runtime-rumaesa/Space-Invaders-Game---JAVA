package project;

import java.awt.*;
class Asteroid extends GameObject {
    private final int speed = 2;
    private int health = 1;

    public Asteroid(int x, int y) {
        super(x, y, 40, 40);
    }

    @Override
    public void update() {
        move();
    }

    public void move() {
        y += speed;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval(x, y, width, height);
    }

    public void takeDamage() { health--; }
    public boolean isDestroyed() { return health <= 0; }

    public int getY() {
        return y;
    }
}
