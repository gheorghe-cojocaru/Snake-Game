import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TAdapter extends KeyAdapter {

    private final GamePanel gamePanel;

    public TAdapter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_LEFT) && (!gamePanel.rightDirection)) {
            gamePanel.leftDirection = true;
            gamePanel.upDirection = false;
            gamePanel.downDirection = false;
        }

        if ((key == KeyEvent.VK_RIGHT) && (!gamePanel.leftDirection)) {
            gamePanel.rightDirection = true;
            gamePanel.upDirection = false;
            gamePanel.downDirection = false;
        }

        if ((key == KeyEvent.VK_UP) && (!gamePanel.downDirection)) {
            gamePanel.upDirection = true;
            gamePanel.rightDirection = false;
            gamePanel.leftDirection = false;
        }

        if ((key == KeyEvent.VK_DOWN) && (!gamePanel.upDirection)) {
            gamePanel.downDirection = true;
            gamePanel.rightDirection = false;
            gamePanel.leftDirection = false;
        }
    }
}
