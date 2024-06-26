package app_TETRIS;

import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.InputMap;
import javax.swing.ActionMap;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Scanner;

public class App extends JFrame implements AdjustmentListener {
    GameArea ga;

    JScrollBar scrollBar = new JScrollBar(JScrollBar.HORIZONTAL, 50, 5, 0, 105);

    public App(GameArea ga) {
        scrollBar.addAdjustmentListener(this);
        getContentPane().add(BorderLayout.SOUTH, scrollBar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.ga = ga;
        add(ga);
        setSize(630, 885);
        setLocationRelativeTo(null);
        setBackground(Color.gray);

        new GameThread(ga).start();
        initControls();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Tetris");
        System.out.print("名前を入力してください：");

        Scanner sc = new Scanner(System.in);
        String name = sc.next();

        GameArea ga = new GameArea();

        int l = name.length();
        if (0 < l && l <= 16) {
            System.out.println("ようこそ" + name + "さん！");
            ga.setName(name);
        } else {
            System.out.println("ゲスト");
            ga.setName("ゲスト");
        }

        System.out.println("EnterKeyを押してスタート！！");
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
                }
            }
        });

        im.put(KeyStroke.getKeyStroke("LEFT"), "left");
        am.put("left", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!ga.isCollison(-1, 0, 0)) {
                    ga.moveLeft();
                }
            }
        });

        im.put(KeyStroke.getKeyStroke("DOWN"), "down");
        am.put("down", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!ga.isCollison(0, 1, 0)) {
                    ga.moveDown();
                }
            }
        });

        im.put(KeyStroke.getKeyStroke("UP"), "up");
        am.put("up", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!ga.isCollison(0, 0, 1)) {
                    ga.rotation();
                }
            }
        });
        im.put(KeyStroke.getKeyStroke("SPACE"), "space");
        am.put("space", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!ga.isCollison(0, 0, 0)) {
                    ga.pendingMino();
                }
            }
        });
    }
    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        JScrollBar sb = (JScrollBar)e.getSource();
        ga.setSpeed(sb.getValue());
    }
}
