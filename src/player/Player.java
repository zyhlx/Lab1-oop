package player;
import game.*;
import java.util.LinkedHashMap;

public abstract class Player {
    private int score = 2;
    private Enum.Colour colour;
    private boolean isWin = false;

    public abstract int[] chessMove(Chessboard chessboard, Player player);


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Enum.Colour getColour() {
        return colour;
    }

    public void setColour(Enum.Colour colour) {
        this.colour = colour;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    //返回可以下的坐标点和最大得分
    public int[] isPlaceToChess(Chessboard chessboard){
        int[] nextMove = {-1, -1};
        int maxScore = -1;
        //对棋盘中只要没有被占据的点测一遍
        for (int i = 0; i < Chessboard.getSize(); i++) {
            for (int j = 0; j < Chessboard.getSize(); j++) {
                if (!chessboard.isCoincided(i, j)) {
                    Chessman chessman = new Chessman(i,j,this.getColour());
                    LinkedHashMap<Enum.Direction, int[]> score = chessboard.checkPlace(chessman);
                    //如果可以翻棋子比较大小
                    if (Human.getTail(score)!= null) {
                        int totalScore = Human.getTail(score).getValue()[2];
                        if (totalScore>maxScore){
                            nextMove[0] = i;
                            nextMove[1] = j;
                            maxScore = totalScore;
                        }

                    }
                }
            }

        }
        return new int[]{nextMove[0],nextMove[1],maxScore};
    }
}
