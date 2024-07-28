import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GamePanel extends JPanel implements ActionListener {

    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 600;
    private final int DOT_SIZE = 50; // Größe der Schlange und des Apfels
    private final int ALL_DOTS = 900;
    private final int RAND_POS = (B_WIDTH / DOT_SIZE) - 1;
    private final int DELAY = 200;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;

    public boolean leftDirection = false;
    public boolean rightDirection = true;
    public boolean upDirection = false;
    public boolean downDirection = false;
    private boolean inGame = true;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    public GamePanel() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter(this));
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

    private void loadImages() {
        try {
            BufferedImage ballImage = ImageIO.read(new File("src/resources/dot.png"));
            BufferedImage appleImage = ImageIO.read(new File("src/resources/beer.png"));
            BufferedImage headImage = ImageIO.read(new File("src/resources/head.png"));

            if (ballImage == null || appleImage == null || headImage == null) {
                throw new IOException("Ein oder mehrere Bilddateien konnten nicht geladen werden.");
            }

            ball = ballImage.getScaledInstance(DOT_SIZE, DOT_SIZE, Image.SCALE_SMOOTH);
            apple = appleImage.getScaledInstance(DOT_SIZE, DOT_SIZE, Image.SCALE_SMOOTH);
            head = headImage.getScaledInstance(DOT_SIZE, DOT_SIZE, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1); // Beende das Programm, wenn die Bilder nicht geladen werden können
        }
    }

    private void initGame() {
        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * DOT_SIZE;
            y[z] = 50;
        }

        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        if (inGame) {

            g.drawImage(apple, apple_x, apple_y, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    private void checkApple() {
        int snakeHeadX = x[0];
        int snakeHeadY = y[0];

        // Überprüfen, ob die Mitte des Schlangenkopfes innerhalb der Grenzen des Apfels liegt
        if (snakeHeadX >= apple_x && snakeHeadX < apple_x + DOT_SIZE &&
                snakeHeadY >= apple_y && snakeHeadY < apple_y + DOT_SIZE) {
            dots++;
            locateApple();
        }
    }

    private void move() {
        for (int z = dots; z > 0; z--) {
            x[z] = x[z - 1];
            y[z] = y[z - 1];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    private void checkCollision() {
        for (int z = dots; z > 0; z--) {
            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= B_HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= B_WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    private void locateApple() {
        int r = (int) (Math.random() * RAND_POS);
        apple_x = r * DOT_SIZE;

        r = (int) (Math.random() * RAND_POS);
        apple_y = r * DOT_SIZE;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public boolean isInGame() {
        return inGame;
    }
}
