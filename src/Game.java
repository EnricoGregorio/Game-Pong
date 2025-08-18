import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {

    private boolean isRunning;
    private Thread thread;

    protected static final int SCALE = 2, WIDTH = 360 * SCALE, HEIGHT = 360 * SCALE;
    protected BufferedImage layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    protected static Player player;
    protected static Enemy enemy;
    protected static Ball ball;

    protected static int playerScore;
    protected static int enemyScore;

    protected Game() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.addKeyListener(this);
        // Instanciar um novo player no centro horizontal, abaixo no eixo vertical.
        player = new Player((WIDTH / 2) - (Player.WIDTH / 2), HEIGHT - Player.HEIGHT);
        enemy = new Enemy((WIDTH / 2) - (Enemy.WIDTH / 2), 0);
        ball = new Ball((WIDTH / 2) - (Ball.SIDE / 2), (HEIGHT / 2) - (Ball.SIDE / 2));
    }

    private void initFrame() {
        JFrame frame = new JFrame();
        frame.setTitle("Pong");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void startGame() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    private void stopGame() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void update() {
        player.update();
        enemy.update();
        ball.update();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics graphics = layer.getGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        player.render(graphics);
        enemy.render(graphics);
        ball.render(graphics);

        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Arial", Font.BOLD, 30));
        graphics.drawString(String.valueOf(playerScore), 20, HEIGHT - 100);
        graphics.drawString(String.valueOf(enemyScore), 20, 100);

        graphics.dispose();
        graphics = bs.getDrawGraphics();
        graphics.drawImage(layer, 0, 0, WIDTH, HEIGHT, null);
        bs.show();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long now;
        double timer = System.currentTimeMillis();
        double maxFPS = 120;
        double ns = 1000000000 / maxFPS;
        double delta = 0;
        int frames = 0;
        while (isRunning) {
            now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {
                update();
                render();
                frames++;
                delta--;
            }

            if (System.currentTimeMillis() - timer >= 1000) {
                System.out.println("FPS: " + frames);
                frames = 0;
                timer += 1000;
            }
        }
        stopGame();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A ^ e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.left = true;
        } else if (e.getKeyCode() == KeyEvent.VK_D ^ e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.right = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A ^ e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.left = false;
        } else if (e.getKeyCode() == KeyEvent.VK_D ^ e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.right = false;
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.initFrame();
        game.startGame();
    }
}
