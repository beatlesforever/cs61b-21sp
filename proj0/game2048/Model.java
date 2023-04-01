package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.
        board.setViewingPerspective(side);

        //其实向上移动，不需要全部考虑，我们只需要一次次遍历就好了
        //那么我们回看之前的规则
        //1.作为合并结果的图块不会在该倾斜时再次合并。
        //2.当运动方向上的三个相邻图块具有相同的编号时，然后，运动方向上的前两个图块合并，而尾随图块不合并

        //那么有哪些情况呢？
        //第一种是 上面为空，下面不空，则直接移动过去
        //第二种是 上面不空，下面不空，但值不相等，则无法移动
        //第三种是 上面不空，下面不空，且值相等，此时可以移动，而且要计入成绩
        //那么从哪开始考虑呢？因为我们要向上移动，所以应该从最顶行开始考虑，第三行固定不动，然后考虑第二行、第一行、第零行
        //接着让第二行固定不动，再考虑第一行、第零行
        //得出上述的逻辑是因为规则1和规则2，好好揣摩一下
        for(int col = 0;col < board.size();col++){
            for(int row1 = board.size() - 1;row1 >= 0;row1--){
                Tile t1 = board.tile(col,row1);
                if(t1 != null){
                    for(int row2 = row1 - 1;row2 >= 0;row2--){
                        Tile t2 = board.tile(col,row2);
                        if(t2 != null){
                            if(t1.value() == t2.value()){//第二种情况
                                board.move(col,row1,t2);
                                score += 2 * t1.value();
                                changed = true;
                                row1 = row2;//因为之前row1这一行已经合并过了，所以不用再管，直接看下一行
                                break;//为什么break呢？
                        //[2 0 2 2] -> [4 0 0 2] row1 = row2 = 1,如果这时候不break，t1没有更新，t1.value还是2，所以当row为0的时候
                        //这时候仍是相等，所以尽管图案不错，但是成绩就错了，归根结底是因为row1更新后，必须退出row2的循环，因为要更新t1
                            }else{
                                break;//若值不相等，就不用考虑row1了 [2 3 2 2]的结果是 [2 3 4 0]
                                //意思就是说row1和其下一个元素不相等，则row1会这一回合会保留下来，无法合并
                            }
                        }//如果row2为空，则继续看row2的下一行 [2 0 2 2]的结果是[4 2 0 0]

                    }
                }
            }
        }
        //可以把合并操作和去空操作分开，因为row1递增的时候可能会漏掉一些，因为去空时，元素向上走，可能之后就掠过了

        for(int col = 0;col < board.size();col++){
            for(int row1 = board.size() - 1;row1 >= 0;row1--) {
                Tile t1 = board.tile(col, row1);
                if (t1 == null) {
                    for (int row2 = row1 - 1; row2 >= 0; row2--) {
                        Tile t2 = board.tile(col, row2);
                        if (t2 != null) {
                            board.move(col, row1, t2);
                            changed = true;
                            break;//记得break，因为已经找到了，就必须更新t1，不然他会以为t1一直是空的，就会发生这样的事情
                            //[0 2 3 4] -> [2 0 3 4] -> [3 0 0 4] -> [4 0 0 0]
                        }

                    }
                }
            }
        }
        board.setViewingPerspective(Side.NORTH);
        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        for(int i = 0;i < b.size();i++){
            for(int j  = 0;j < b.size();j++){
                if(b.tile(i,j) == null){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        for(int i = 0;i < b.size();i++){
            for(int j = 0;j < b.size();j++){
                Tile tile = b.tile(i,j);
                if(tile == null) {
                    continue;}
                if(tile.value() == MAX_PIECE){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        for(int i = 0;i < b.size();i++) {
            for (int j = 0; j < b.size(); j++) {
                Tile tile = b.tile(i, j);
                if (tile == null) {
                    return true;
                }
                if (i >= 1 && tile.value() == b.tile(i - 1, j).value()) { //left
                    return true;
                }
                if (i < b.size() - 1 && tile.value() == b.tile(i + 1, j).value()) { //right
                    return true;
                }
                if (j < b.size() - 1 && tile.value() == b.tile(i, j + 1).value()) { //down
                    return true;
                }
                if (j >= 1 && tile.value() == b.tile(i, j - 1).value()) {//up
                    return true;
                }
            }
        }
        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
