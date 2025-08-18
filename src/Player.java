import java.awt.Color;
import java.awt.Graphics;

public class Player {
    protected static final int WIDTH = 144, HEIGHT = 28;
    protected int x, y, speed = 3;
    protected boolean left, right;

    protected Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected void update() {
        if (left && x > 0) {
            x-= speed;
        } else if (right && x + WIDTH < Game.WIDTH) {
            x+= speed;
        }
    }

    protected void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }
}
