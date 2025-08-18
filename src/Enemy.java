import java.awt.Color;
import java.awt.Graphics;

public class Enemy {
    protected static final int WIDTH = 144, HEIGHT = 28;
    protected float x, y;
    protected boolean left, right;

    protected Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected void update() {
        x += ( (Game.ball.x + Ball.SIDE / 2) - (x + WIDTH / 2) ) * 0.03;
        if (x + WIDTH > Game.WIDTH) {
            x = Game.WIDTH - WIDTH;
        } else if (x < 0) {
            x = 0;
        }
    }

    protected void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect((int)x, (int)y, WIDTH, HEIGHT);
    }
}
