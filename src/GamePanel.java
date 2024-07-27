import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//Diese Klasse wird die Spiellogik, Zeichnung und Eingabeverarbeitung enthalten
public class GamePanel extends JPanel implements ActionListener {


    private final int B_Width = 800;
    private final int B_Heidt = 600;
    private final int dotSize = 10;
    private final int allDots = 900;
    private final int randPos = 29;
    private final int delay = 140;

    private final int x[] = new int[allDots];
    private final int y[] = new int[allDots];

    private int dots;
    private int apple_x;
    private int apple_y;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;


    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    public GamePanel() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_Width, B_Heidt));
        loadImages();
        initGame();
    }

    private void loadImages() {
        ball = Toolkit.getDefaultToolkit().createImage("src/resources/dot.png");
        apple = Toolkit.getDefaultToolkit().createImage("src/resources/apple.png");
        head = Toolkit.getDefaultToolkit().createImage("src/resources/head.png");
    }

    private void initGame() {
        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
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
        if(inGame) {

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
        FontMetrics metrics = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_Width - metrics.stringWidth(msg)) / 2, B_Heidt / 2);
    }

    private void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {

            dots++;
            locateFood();
        }
    }
    private void move() {

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = x[(z -1)];
        }

        if (leftDirection) {
            x[0] -= dotSize;
        }

        if (rightDirection) {
            x[0] += dotSize;
        }

        if (upDirection) {
            y[0] -= dotSize;
        }

        if (downDirection) {
            y[0] += dotSize;
        }
    }

    private void checkCollision() {

        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if ( y[0] >= B_Heidt) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= B_Width) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }
        if (!inGame) {
            timer.stop();
        }
    }

    
}
