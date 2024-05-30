package app_TETRIS;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameThread extends Thread {

    private GameArea ga;

    public GameThread(GameArea ga) {
        this.ga = ga;
    }

    public void run() {
        int dropTimer = 0;
        int stackTimer = 0;
        while (true) {
            ga.drawFieldAndMino();
            dropTimer ++;
            if (dropTimer >= 2 && !ga.isCollison(0, 1, 0)) {
                ga.moveDown();
                dropTimer = 0;
            }
            if (ga.isStack()) {
                stackTimer ++;
                if (stackTimer >= 2) {
                    ga.drawBufferFieldAndMino();
                    stackTimer = 0;
                }
            }
            try {
                Thread.sleep(40);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
