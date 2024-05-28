package app_TETRIS;

public class GameArea {
    private int fieldHight = 21;
    private int fieldWidth = 12;
    private int grandHight = 30;
    private int grandWidth = 20;
    private int[][] field;
    private int[][] bufferField;
    private Mino mino;
    private Mino NextMino;

    public GameArea() {
        this.mino = new Mino();
        this.NextMino = new Mino();
        this.field = new int[grandHight][grandWidth];
        this.bufferField = new int[grandHight][grandWidth];
        initBufferField();
        initField();
    }

    //盤面をクリアする
    public void initField() {
        for (int y = 0; y < this.fieldHight; y++) {
            for (int x = 0; x < this.fieldWidth; x++) {
                field[y][x] = bufferField[y][x];
            }
        }
    }

    public void initBufferField() {
        for (int y = 0; y < this.fieldHight; y++) {
            for (int x = 0; x < this.fieldWidth; x++) {
                bufferField[y][x] = 0;
            }
        }
        for (int y = 0; y < this.fieldHight; y++) {
            bufferField[y][0] = bufferField[y][this.fieldWidth - 1] = 1;
        }
        for (int x = 0; x < fieldWidth; x++) {
            bufferField[fieldHight - 1][x] = 1;
        }
    }

    // 描画系
    public void drawField() {
        for (int y = 0; y < fieldHight; y++) {
            for (int x = 0; x < fieldWidth; x++) {
                System.out.printf("%s", (field[y][x] == 1 ? "回" : "・"));
            }
            System.out.println();
        }
    }

   public void drawNextMino(Mino nextMino) {
    int[][][] m = nextMino.getMino();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                System.out.printf("%s", (m[0][y][x] == 1 ? "回" : "・"));
            }
            System.out.println();
        }
    }

    //本丸
    public void drawFieldAndMino(Mino mino, Mino nextMino) {
        if (isCollison(mino)) {
            bufferFieldAddMino(mino);
            initField();
        } else {
            initField();
            fieldAddMino(mino);
        }
        System.out.println("Next Mino");
        drawNextMino(nextMino);
        System.out.println();
        drawField();
    }

    public void fieldAddMino(Mino mino) {
        for (int y = 0; y < mino.getMinoSize(); y++) {
            for (int x = 0; x < mino.getMinoSize(); x++) {
                this.field[mino.getMinoY() + y][mino.getMinoX() + x] |= mino.getMino()[mino.getMinoAngle()][y][x];
            }
        }
    }

    public void bufferFieldAddMino(Mino mino) {
        for (int y = 0; y < mino.getMinoSize(); y++) {
            for (int x = 0; x < mino.getMinoSize(); x++) {
                this.bufferField[mino.getMinoY() + y][mino.getMinoX() + x] |= mino.getMino()[mino.getMinoAngle()][y][x];
            }
        }
    }

    public boolean isCollison(Mino mino) {
        for (int r = 0; r < mino.getMinoSize(); r++) {
            for (int c = 0; c < mino.getMinoSize(); c++) {
                if (this.bufferField[mino.getMinoY() + r + 1][mino.getMinoX() + c] == 1 && mino.getMino()[mino.getMinoAngle()][r][c] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCollison(Mino mino, int _x, int _y, int _angle) {
        for (int r = 0; r < mino.getMinoSize(); r++) {
            for (int c = 0; c < mino.getMinoSize(); c++) {
                if (this.bufferField[_y + r][_x + c] == 1 && mino.getMino()[_angle][r][c] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public void moveDown(Mino mino) {
        mino.addMinoY();
    }

    public void moveRight(Mino mino) {
        mino.addMinoX();
    }

    public void moveLeft(Mino mino) {
        mino.decMinoX();
    }

    public void rotation(Mino mino) {
        mino.setMinoAngle((mino.getMinoAngle() + 1) % mino.getMinoAngleSize());
    }

}
