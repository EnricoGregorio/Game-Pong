import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball {
    protected static final int SIDE = 28;
    protected float x, y, speed = 4.78f;
    private double dx, dy;
    protected boolean left, right;
    private int angle;

    protected Ball(int x, int y) {
        this.x = x;
        this.y = y;

        this.angle = new Random().nextInt(359 - 40) + 40 - 1;
        this.dx = Math.cos(Math.toRadians(angle));
        this.dy = Math.sin(Math.toRadians(angle));
    }

    protected void update() {
        if (x + (dx * speed) + SIDE >= Game.WIDTH ^ x + dx * speed < 0) {
            dx *= -1;
        }

        if (y > Game.HEIGHT) {
            Game.enemyScore++;
            new Game();
            return;
        } else if (y < 0) {
            Game.playerScore++;
            new Game();
            return;
        }

        Rectangle bounds = new Rectangle((int) (x + (dx * speed)), (int) (y + (dy * speed)), SIDE, SIDE);
        Rectangle boundsPlayer = new Rectangle(Game.player.x, Game.player.y, Player.WIDTH, Player.HEIGHT);
        Rectangle boundsEnemy = new Rectangle((int) Game.enemy.x, (int) Game.enemy.y, Enemy.WIDTH,
                Enemy.HEIGHT);

        if (bounds.intersects(boundsPlayer) ^ bounds.intersects(boundsEnemy)) {
            this.angle = new Random().nextInt(120 - 43) + 43 - 1;
            this.dx = Math.cos(Math.toRadians(angle));
            this.dy = Math.sin(Math.toRadians(angle));
            if (bounds.intersects(boundsPlayer) && dy > 0) {
                dy *= -1;
            } else if (bounds.intersects(boundsEnemy) && dy < 0) {
                dy *= -1;
            }
        }

        x += dx * speed;
        y += dy * speed;
    }

    protected void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect((int) x, (int) y, SIDE, SIDE);
    }
}
