import javax.swing.*;

public class SnakeGame extends JFrame {

    public SnakeGame() {
        add(new GamePanel());
        setTitle("Schlangen Spiel");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SnakeGame();
    }
}
