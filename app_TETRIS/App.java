package app_TETRIS;

import javax.swing.JFrame;
import javax.swing.InputMap;
import javax.swing.ActionMap;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.util.Scanner;



public class App extends JFrame {
    GameArea ga;
    public App(GameArea ga) {
        this.ga = ga;
        initControls();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Tetris");
        System.out.print("名前を入力してください:");

        Scanner sc = new Scanner(System.in);
        String name = sc.next();
        
        GameArea ga;

        int l = name.length();
        if(0 < l && l <= 16) {
            System.out.println("ようこそ" + name + "さん！");
            ga = new GameArea();
            ga.setName(name);
        } else {
            System.out.println("ゲスト");
            ga = new GameArea();
            ga.setName("ゲスト");
        }
        
        System.out.println("EnterKeyを押してスタート！！");
        while ((System.in.read()) != '\n') ;
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new App(ga).setVisible(true);
            }
        });
        sc.close();
    }

    private void initControls() {
        InputMap im = this.getRootPane().getInputMap();
        ActionMap am = this.getRootPane().getActionMap();

        im.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        am.put("right", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!ga.isCollison(1, 0, 0)) {
                    ga.moveRight();
                    ga.drawFieldAndMino();
                }
            }
        });

        im.put(KeyStroke.getKeyStroke("LEFT"), "left");
        am.put("left", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!ga.isCollison(-1, 0, 0)) {
                    ga.moveLeft();
                    ga.drawFieldAndMino();
                }
            }
        });

        im.put(KeyStroke.getKeyStroke("DOWN"), "down");
        am.put("down", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!ga.isCollison(0, 1, 0)) {
                    ga.moveDown();
                    ga.drawFieldAndMino();
                }
            }
        });

        im.put(KeyStroke.getKeyStroke("UP"), "up");
        am.put("up", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!ga.isCollison(1, 0, 1)) {
                    ga.rotation();
                    ga.drawFieldAndMino();
                }
            }
        });
    }
}
