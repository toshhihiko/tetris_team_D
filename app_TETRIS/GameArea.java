package app_TETRIS;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GameArea extends JPanel {
    private int fieldHight = 25;
    private int fieldWidth = 14;
    private int grandHight = 28;
    private int grandWidth = 16;
    private int[][] field;
    private int[][] bufferField;
    private Mino mino;
    private Mino nextMino;
    private Mino pendingMino;
    private Mino storeMino;
    private String name;
    private int score = 0;
    private int linecount = 0;
    private int edge_left = 2;
    private int edge_top = 4;

    String RESET = "\u001B[0m";
    String BLACK = "\u001B[30m";
    String RED = "\u001B[31m";
    String GREEN = "\u001B[32m";
    String YELLOW = "\u001B[33m";
    String BLUE = "\u001B[34m";
    String PURPLE = "\u001B[35m";
    String CYAN = "\u001B[36m";
    String PINK = "\u001B[95m";

    public GameArea() {
        this.mino = new Mino();
        this.nextMino = new Mino();
        this.pendingMino = new Mino();
        this.storeMino = new Mino();
        this.field = new int[grandHight][grandWidth];
        this.bufferField = new int[grandHight][grandWidth];
        initBufferField();
        initField();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 本丸
    public void drawFieldAndMino() {
        initField();
        fieldAddMino();

        System.out.println("Next Mino");
        drawNextMino();
        System.out.println("Pending Mino");
        drawPendingMino();
        System.out.println();
        drawField();
        repaint();
    }

    public void drawBufferFieldAndMino() {
        bufferFieldAddMino();
        initField();
        eraseLine();
        this.mino = this.nextMino;
        this.nextMino = new Mino();

        System.out.println("Next Mino");
        drawNextMino();
        System.out.println("Pending Mino");
        drawPendingMino();
        System.out.println();
        drawField();
        repaint();

        for (int i = edge_left + 1; i < fieldWidth - 1; i++) {
            if (this.field[3][i] >= 2) {
                System.out.println("GameOver");
                System.out.println(this.name + "  あなたのスコア:" + this.score);
                System.exit(0);
            }
        }
    }

    // 盤面をクリアする
    public void initField() {
        for (int y = 0; y < this.fieldHight; y++) {
            for (int x = 0; x < this.fieldWidth; x++) {
                this.field[y][x] = this.bufferField[y][x];
            }
        }
    }

    public void initBufferField() {
        for (int y = 0; y < this.fieldHight; y++) {
            for (int x = 0; x < this.fieldWidth; x++) {
                this.bufferField[y][x] = 0;
            }
        }
        for (int y = 0; y < this.fieldHight; y++) {
            this.bufferField[y][edge_left] = this.bufferField[y][this.fieldWidth - 1] = 1;
        }
        for (int x = edge_left; x < fieldWidth; x++) {
            this.bufferField[this.fieldHight - 1][x] = 1;
        }
    }

    // 描画系
    public void drawField() {
        for (int y = edge_top; y < fieldHight; y++) {
            for (int x = edge_left; x < fieldWidth; x++) {
                switch (field[y][x]) {
                    case 0 : System.out.print("・"); break;
                    case 1 : System.out.print("回"); break;
                    case 2 : System.out.print(RED + "回" + RESET); break;
                    case 3 : System.out.print(GREEN + "回" + RESET); break;
                    case 4 : System.out.print(YELLOW + "回" + RESET); break;
                    case 5 : System.out.print(BLUE + "回" + RESET); break;
                    case 6 : System.out.print(PURPLE + "回" + RESET); break;
                    case 7 : System.out.print(CYAN + "回" + RESET); break;
                    case 8 : System.out.print(PINK + "回" + RESET); break;
                    default: throw new AssertionError();
                }
            }
            System.out.println();
        }
        System.out.println("消したライン数：" + linecount);
        System.out.print("名前:" + name + "   ");
        System.out.println("スコア：" + score);
    }

    public void drawNextMino() {
        int[][][] m = this.nextMino.getMino();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                switch (m[0][y][x]) {
                    case 0 : System.out.print("・"); break;
                    case 1 : System.out.print("回"); break;
                    case 2 : System.out.print(RED + "回" + RESET); break;
                    case 3 : System.out.print(GREEN + "回" + RESET); break;
                    case 4 : System.out.print(YELLOW + "回" + RESET); break;
                    case 5 : System.out.print(BLUE + "回" + RESET); break;
                    case 6 : System.out.print(PURPLE + "回" + RESET); break;
                    case 7 : System.out.print(CYAN + "回" + RESET); break;
                    case 8 : System.out.print(PINK + "回" + RESET); break;
                    default: throw new AssertionError();
                }
            }
            System.out.println();
        }
    }

    public void drawPendingMino() {
        int[][][] m = this.pendingMino.getMino();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                switch (m[storeMino.getMinoAngle()][y][x]) {
                    case 0 : System.out.print("・"); break;
                    case 1 : System.out.print("回"); break;
                    case 2 : System.out.print(RED + "回" + RESET); break;
                    case 3 : System.out.print(GREEN + "回" + RESET); break;
                    case 4 : System.out.print(YELLOW + "回" + RESET); break;
                    case 5 : System.out.print(BLUE + "回" + RESET); break;
                    case 6 : System.out.print(PURPLE + "回" + RESET); break;
                    case 7 : System.out.print(CYAN + "回" + RESET); break;
                    case 8 : System.out.print(PINK + "回" + RESET); break;
                    default: throw new AssertionError();
                }
            }
            System.out.println();
        }
    }

    // Windowに表示
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawFieldWindow(g2);
        drawNextMinoWindow(g2);
        drawPendingMinoWindow(g2);
        drawStatus(g2);
    }

    public void drawFieldWindow(Graphics2D g2) {
        for (int y = edge_top; y < fieldHight; y++) {
            for (int x = edge_left; x < fieldWidth; x++) {
                switch (field[y][x]) {
                    case 0 : drawSquare(g2, Color.lightGray, Color.white, x, y, 75 , 0, 30, 30); break;
                    case 1 : drawSquare(g2, Color.black, Color.black, x, y, 75, 0, 30, 30); break;
                    case 2 : drawSquare(g2, Color.red, Color.black, x, y, 75, 0, 30, 30); break;
                    case 3 : drawSquare(g2, Color.green, Color.black, x, y, 75, 0, 30, 30); break;
                    case 4 : drawSquare(g2, Color.yellow, Color.black, x, y, 75, 0, 30, 30); break;
                    case 5 : drawSquare(g2, Color.blue, Color.black, x, y, 75, 0, 30, 30); break;
                    case 6 : drawSquare(g2, Color.ORANGE, Color.black, x, y, 75, 0, 30, 30); break;
                    case 7 : drawSquare(g2, Color.cyan, Color.black, x, y, 75, 0, 30, 30); break;
                    case 8 : drawSquare(g2, Color.pink, Color.black, x, y, 75, 0, 30, 30); break;
                    default: throw new AssertionError();
                }
            }
        }
    }

    public void drawNextMinoWindow(Graphics2D g2) {
        g2.setFont(new Font("MSPゴシック", Font.PLAIN, 24));
        g2.setColor(Color.black);
        g2.drawString("NEXT", 525, 105);

        int[][][] m = this.nextMino.getMino();
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.black);
        g2.drawRect(510, 120, 90, 90);
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                switch (m[0][y][x]) {
                    case 0 : drawSquare(g2, Color.LIGHT_GRAY, Color.white, x, y, 525, 135, 15, 15); break;
                    case 1 : drawSquare(g2, Color.black, Color.black, x, y, 525, 135, 15, 15); break;
                    case 2 : drawSquare(g2, Color.red, Color.black, x, y, 525, 135, 15, 15); break;
                    case 3 : drawSquare(g2, Color.green, Color.black, x, y, 525, 135, 15, 15); break;
                    case 4 : drawSquare(g2, Color.yellow, Color.black, x, y, 525, 135, 15, 15); break;
                    case 5 : drawSquare(g2, Color.blue, Color.black, x, y, 525, 135, 15, 15); break;
                    case 6 : drawSquare(g2, Color.ORANGE, Color.black, x, y, 525, 135, 15, 15); break;
                    case 7 : drawSquare(g2, Color.cyan, Color.black, x, y, 525, 135, 15, 15); break;
                    case 8 : drawSquare(g2, Color.pink, Color.black, x, y, 525, 135, 15, 15); break;
                    default: throw new AssertionError();
                }
            }
            System.out.println();
        }
    }
    public void drawPendingMinoWindow(Graphics2D g2) {
        g2.setFont(new Font("MSPゴシック", Font.PLAIN, 24));
        g2.setColor(Color.black);
        g2.drawString("HOLD", 45, 105);

        int[][][] m = this.pendingMino.getMino();
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.black);
        g2.drawRect(30, 120, 90, 90);
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                switch (m[storeMino.getMinoAngle()][y][x]) {
                    case 0 : drawSquare(g2, Color.LIGHT_GRAY, Color.white, x, y, 45, 135, 15, 15); break;
                    case 1 : drawSquare(g2, Color.black, Color.black, x, y, 45, 135, 15, 15); break;
                    case 2 : drawSquare(g2, Color.red, Color.black, x, y, 45, 135, 15, 15); break;
                    case 3 : drawSquare(g2, Color.green, Color.black, x, y, 45, 135, 15, 15); break;
                    case 4 : drawSquare(g2, Color.yellow, Color.black, x, y, 45, 135, 15, 15); break;
                    case 5 : drawSquare(g2, Color.blue, Color.black, x, y, 45, 135, 15, 15); break;
                    case 6 : drawSquare(g2, Color.ORANGE, Color.black, x, y, 45, 135, 15, 15); break;
                    case 7 : drawSquare(g2, Color.cyan, Color.black, x, y, 45, 135, 15, 15); break;
                    case 8 : drawSquare(g2, Color.pink, Color.black, x, y, 45, 135, 15, 15); break;
                    default: throw new AssertionError();
                }
            }
            System.out.println();
        }
    }

    public void drawSquare(Graphics2D g2, Color fillColor, Color lineColor, int x, int y, int mergin_x, int mergin_y, int width, int hight) {
        g2.setColor(fillColor);
        g2.fillRect(x * width + mergin_x, y * hight + mergin_y, width, hight);
        g2.setStroke(new BasicStroke(2));
        g2.setColor(lineColor);
        g2.drawRect(x * width + mergin_x, y * hight + mergin_y, width, hight);
    }
    public void drawStatus(Graphics2D g2) {
        g2.setFont(new Font("MSPゴシック", Font.PLAIN, 24));
        g2.setColor(Color.black);
        g2.drawString("消したライン数：" + this.linecount, 135, 785);
        g2.drawString("名前：" + this.name, 135, 825);
        g2.drawString("スコア：" + this.score, 315, 825);
    }


    // ミノをフィールドに書き込む系
    public void fieldAddMino() {
        for (int y = 0; y < this.mino.getMinoSize(); y++) {
            for (int x = 0; x < this.mino.getMinoSize(); x++) {
                this.field[this.mino.getMinoY() + y][this.mino.getMinoX()
                        + x] |= this.mino.getMino()[this.mino.getMinoAngle()][y][x];
            }
        }
    }

    public void bufferFieldAddMino() {
        for (int y = 0; y < this.mino.getMinoSize(); y++) {
            for (int x = 0; x < this.mino.getMinoSize(); x++) {
                this.bufferField[this.mino.getMinoY() + y][this.mino.getMinoX()
                        + x] |= this.mino.getMino()[this.mino.getMinoAngle()][y][x];
            }
        }
    }

    // あたり判定系
    public boolean isStack() {
        for (int r = 0; r < this.mino.getMinoSize(); r++) {
            for (int c = 0; c < this.mino.getMinoSize(); c++) {
                if (this.bufferField[this.mino.getMinoY() + r + 1][this.mino.getMinoX() + c] >= 1
                        && this.mino.getMino()[this.mino.getMinoAngle()][r][c] >= 2) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCollison(int x, int y, int angle) {
        for (int r = 0; r < this.mino.getMinoSize(); r++) {
            for (int c = 0; c < this.mino.getMinoSize(); c++) {
                int nextAngle = (mino.getMinoAngle() + angle) % mino.getMinoAngleSize();
                if (this.bufferField[mino.getMinoY() + y + r][mino.getMinoX() + x + c] >= 1
                        && mino.getMino()[nextAngle][r][c] >= 2) {
                    return true;
                }
            }
        }
        return false;
    }

    // ライン削除処理
    public void eraseLine() {
        int lines = 0;
        for (int y = fieldHight - 2; y > 0; y--) {
            boolean isFill = true;
            for (int x = edge_left + 1; x < fieldWidth - 1; x++) {
                if (bufferField[y][x] == 0)
                    isFill = false;
            }
            if (isFill) {
                for (int _y = y - 1; _y > 0; _y--) {
                    for (int x = edge_left; x < fieldWidth; x++) {
                        bufferField[_y + 1][x] = bufferField[_y][x];
                    }
                }
                lines++;
                y++;
            }
        }
        this.linecount += lines;
        addScore(lines);
    }

    public void addScore(int lines) {
        switch (lines) {
            case 1:
                score += 40;
                break;
            case 2:
                score += 100;
                break;
            case 3:
                score += 300;
                break;
            case 4:
                score += 1200;
                break;
            default:
                score += 0;
        }
    }

    // 操作系
    public void moveDown() {
        this.mino.addMinoY();
    }

    public void moveRight() {
        this.mino.addMinoX();
    }

    public void moveLeft() {
        this.mino.decMinoX();
    }

    public void rotation() {
        this.mino.setMinoAngle((this.mino.getMinoAngle() + 1) % this.mino.getMinoAngleSize());
    }

    public void pendingMino() {
        this.storeMino = this.mino;
        pendingMino.setMinoX(this.storeMino.getMinoX());
        pendingMino.setMinoY(this.storeMino.getMinoY());
        this.mino = this.pendingMino;
        this.pendingMino = this.storeMino;
    }
}