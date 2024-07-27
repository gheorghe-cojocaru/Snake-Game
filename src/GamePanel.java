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

        
    }


}
