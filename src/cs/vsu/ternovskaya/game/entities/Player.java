package cs.vsu.ternovskaya.game.entities;

import java.awt.Graphics;

public class Player {
    private int x, y;
    private int speed = 5;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        x += speed;
    }

    public void draw(Graphics g) {
        g.fillRect(x, y, 50, 50);
    }
}
