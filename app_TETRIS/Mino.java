package app_TETRIS;
import java.util.Random;
public class Mino {
    private Random rand;
    private int minoSize = 4;
    private int[][][] minoTypes;
    private int x, y;
    private int minoType;
    private int minoAngle;
    private int minoAngleSize = 4;
    private int[][][] mino_I = { { { 0, 0, 0, 0 }, { 2, 2, 2, 2 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, }, // 0度
            { { 0, 0, 2, 0 }, { 0, 0, 2, 0 }, { 0, 0, 2, 0 }, { 0, 0, 2, 0 }, }, // 90度
            { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 2, 2, 2, 2 }, { 0, 0, 0, 0 }, }, // 180度
            { { 0, 2, 0, 0 }, { 0, 2, 0, 0 }, { 0, 2, 0, 0 }, { 0, 2, 0, 0 }, }, // 270度
    };
    private int[][][] mino_O = { { { 0, 0, 0, 0 }, { 0, 3, 3, 0 }, { 0, 3, 3, 0 }, { 0, 0, 0, 0 }, }, // 0度
            { { 0, 0, 0, 0 }, { 0, 3, 3, 0 }, { 0, 3, 3, 0 }, { 0, 0, 0, 0 }, }, // 90度
            { { 0, 0, 0, 0 }, { 0, 3, 3, 0 }, { 0, 3, 3, 0 }, { 0, 0, 0, 0 }, }, // 180度
            { { 0, 0, 0, 0 }, { 0, 3, 3, 0 }, { 0, 3, 3, 0 }, { 0, 0, 0, 0 }, }, // 270度
    };
    private int[][][] mino_S = { { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 4, 4, 0 }, { 4, 4, 0, 0 }, }, // 0度
            { { 0, 0, 0, 0 }, { 4, 0, 0, 0 }, { 4, 4, 0, 0 }, { 0, 4, 0, 0 }, }, // 90度
            { { 0, 0, 0, 0 }, { 0, 4, 4, 0 }, { 4, 4, 0, 0 }, { 0, 0, 0, 0 }, }, // 180度
            { { 0, 0, 0, 0 }, { 0, 4, 0, 0 }, { 0, 4, 4, 0 }, { 0, 0, 4, 0 }, }, // 270度
    };
    private int[][][] mino_Z = { { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 5, 5, 0, 0 }, { 0, 5, 5, 0 }, }, // 0度
            { { 0, 0, 0, 0 }, { 0, 5, 0, 0 }, { 5, 5, 0, 0 }, { 5, 0, 0, 0 }, }, // 90度
            { { 0, 0, 0, 0 }, { 5, 5, 0, 0 }, { 0, 5, 5, 0 }, { 0, 0, 0, 0 }, }, // 180度
            { { 0, 0, 0, 0 }, { 0, 0, 5, 0 }, { 0, 5, 5, 0 }, { 0, 5, 0, 0 }, }, // 2700度
    };
    private int[][][] mino_J = { { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 6, 6, 6, 0 }, { 0, 0, 6, 0 }, }, // 0度
            { { 0, 0, 0, 0 }, { 0, 6, 0, 0 }, { 0, 6, 0, 0 }, { 6, 6, 0, 0 }, }, // 90度
            { { 0, 0, 0, 0 }, { 6, 0, 0, 0 }, { 6, 6, 6, 0 }, { 0, 0, 0, 0 }, }, // 180度
            { { 0, 0, 0, 0 }, { 0, 6, 6, 0 }, { 0, 6, 0, 0 }, { 0, 6, 0, 0 }, }, // 270度
    };
    private int[][][] mino_L = { { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 7, 7, 7, 0 }, { 7, 0, 0, 0 }, }, // 0度
            { { 0, 0, 0, 0 }, { 7, 7, 0, 0 }, { 0, 7, 0, 0 }, { 0, 7, 0, 0 }, }, // 90度
            { { 0, 0, 0, 0 }, { 0, 0, 7, 0 }, { 7, 7, 7, 0 }, { 0, 0, 0, 0 }, }, // 180度
            { { 0, 0, 0, 0 }, { 0, 7, 0, 0 }, { 0, 7, 0, 0 }, { 0, 7, 7, 0 }, }, // 270度
    };
    private int[][][] mino_T = { { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 8, 8, 8, 0 }, { 0, 8, 0, 0 }, }, // 0度
            { { 0, 0, 0, 0 }, { 0, 8, 0, 0 }, { 8, 8, 0, 0 }, { 0, 8, 0, 0 }, }, // 90度
            { { 0, 0, 0, 0 }, { 0, 8, 0, 0 }, { 8, 8, 8, 0 }, { 0, 0, 0, 0 }, }, // 180度
            { { 0, 0, 0, 0 }, { 0, 8, 0, 0 }, { 0, 8, 8, 0 }, { 0, 8, 0, 0 }, }, // 270度
    };
    public Mino() {
        this.rand = new Random();
        this.y = 0;
        this.x = 6;
        setMinoType();
        setMinoAngle();
        randSet();
    }
    // 出現するミノ
    public void initMino() {
        this.y = 0;
        this.x = 6;
        setMinoType();
        setMinoAngle();
        randSet();
    }
    public int[][][] getMino() {
        return minoTypes;
    }
    public int getMinoSize() {
        return this.minoSize;
    }
    public int getMinoAngleSize() {
        return this.minoAngleSize;
    }
    public int getMinoX() {
        return this.x;
    }
    public int getMinoY() {
        return this.y;
    }
    public void setMinoX(int x) {
        this.x = x;
    }
    public void setMinoY(int y) {
        this.y = y;
    }
    public void addMinoX() {
        this.x++;
    }
    public void addMinoY() {
        this.y++;
    }
    public void decMinoX() {
        this.x--;
    }
    public int getMinoAngle() {
        return this.minoAngle;
    }
    public int getMinoType() {
        return this.minoType;
    }
    public void setMinoType() {
        this.minoType = rand.nextInt(7) + 1;
    }
    public void setMinoAngle() {
        this.minoAngle = rand.nextInt(4);
    }
    public void setMinoAngle(int minoAngle) {
        this.minoAngle = minoAngle;
    }
    private void randSet() {
        switch (getMinoType()) {
            case 1: this.minoTypes = this.mino_I; break;
            case 2: this.minoTypes = this.mino_O; break;
            case 3: this.minoTypes = this.mino_S; break;
            case 4: this.minoTypes = this.mino_Z; break;
            case 5: this.minoTypes = this.mino_J; break;
            case 6: this.minoTypes = this.mino_L; break;
            case 7: this.minoTypes = this.mino_T; break;
        }
    }
}