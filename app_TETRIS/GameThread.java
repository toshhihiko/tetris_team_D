package app_TETRIS;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameThread extends Thread {

    private GameArea ga;
    public GameThread(GameArea ga) {
        this.ga = ga;
    }

    public void run() {

        while (true) {
            ga.moveDown();
            ga.drawFieldAndMino();
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameThread.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
