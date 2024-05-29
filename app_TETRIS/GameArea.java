package app_TETRIS;

public class GameArea {
    private int fieldHight = 21;
    private int fieldWidth = 12;
    private int grandHight = 30;
    private int grandWidth = 20;
    private int[][] field;
    private int[][] bufferField;
    private Mino mino;
    private Mino nextMino;
    private String name;
    private int score = 0;
    private int linecount = 0;

    public GameArea() {
        this.mino = new Mino();
        this.nextMino = new Mino();
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
        if (isStack()) {
            bufferFieldAddMino();
            initField();
            eraseLine();
            this.mino = this.nextMino;
            this.nextMino = new Mino();
        } else {
            initField();
            fieldAddMino();
        }
        System.out.println("Next Mino");
        drawNextMino();
        System.out.println();
        drawField();
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
            this.bufferField[y][0] = this.bufferField[y][this.fieldWidth - 1] = 1;
        }
        for (int x = 0; x < fieldWidth; x++) {
            this.bufferField[this.fieldHight - 1][x] = 1;
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
        System.out.println("消したライン数：" + linecount); 
        System.out.print("名前:" + name +"   ");
        System.out.println("スコア：" + score); 
    }

    public void drawNextMino() {
        int[][][] m = this.nextMino.getMino();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                System.out.printf("%s", (m[0][y][x] == 1 ? "回" : "・"));
            }
            System.out.println();
        }
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
                this.bufferField[this.mino.getMinoY() + y][this.mino.getMinoX() + x] |= this.mino.getMino()[this.mino.getMinoAngle()][y][x];
            }
        }
    }

    // あたり判定系
    public boolean isStack() {
        for (int r = 0; r < this.mino.getMinoSize(); r++) {
            for (int c = 0; c < this.mino.getMinoSize(); c++) {
                if (this.bufferField[this.mino.getMinoY() + r + 1][this.mino.getMinoX() + c] == 1 && this.mino.getMino()[this.mino.getMinoAngle()][r][c] == 1) {
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
                if (this.bufferField[mino.getMinoY() + y + r][mino.getMinoX() + x + c] == 1 && mino.getMino()[nextAngle][r][c] == 1) {
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
            for (int x = 1; x < fieldWidth - 1; x++) {
                if (bufferField[y][x] == 0) isFill = false;
            }
            if (isFill) {
                for (int _y = y - 1; _y > 0; _y--) {
                    for (int x = 0; x < fieldWidth; x++) {
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
        if (lines == 1) score += 40;
        else if (lines == 2) score += 100;
        else if (lines == 3) score += 300;
        else if (lines == 4) score += 1200;
        else score += 0;
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
}
