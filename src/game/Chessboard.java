package game;
import main.*;
import player.Enum;
import player.Human;
import player.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Chessboard {
    private static int size;
    private Enum.Colour[][] chessBoard;
    private int numOfTurn = 4;

    public Chessboard(int size) {
        Chessboard.setSize(size);
        chessBoard = new Enum.Colour[size][size];
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                chessBoard[j][i] = Enum.Colour.NULLCHESSMAN;
            }
        }
        chessBoard[size / 2 - 1][size / 2 - 1] = Enum.Colour.WHITE;
        chessBoard[size / 2 - 1][size / 2] = Enum.Colour.BLACK;
        chessBoard[size / 2][size / 2] = Enum.Colour.WHITE;
        chessBoard[size / 2][size / 2 - 1] = Enum.Colour.BLACK;
    }


    public void removeChessmen(LinkedHashMap<Enum.Direction, int[]> score, int[] point, Enum.Colour colour) {
        Iterator<Map.Entry<Enum.Direction, int[]>> iterator = score.entrySet().iterator();
        while (iterator.hasNext()) {
            Enum.Direction key = iterator.next().getKey();
            int[] destination = score.get(key);
            int[] origin = {point[0], point[1]};
            chessBoard[point[0]][point[1]] = colour;
            while (origin[0] != destination[0] || origin[1] != destination[1]) {
                //因为已经有边界条件所以changeColor里不涵盖边界检测
                key.changeColor(origin, this, colour);
            }
        }

    }

    //检测是否有一个玩家无符合规则的落子位置
    public boolean isEnd(Player player, Player playerSecond, int[] chessMove) {
        //玩家下错棋||整个棋盘满||一方棋子数等于0
        return  isPlayerChessWrong(playerSecond, chessMove) || isFull(player, playerSecond) || isBeated(player, playerSecond);
    }


    //玩家下错棋
    public boolean isPlayerChessWrong(Player playerSecond, int[] chessMove) {
        boolean isEnd = false;
        if (chessMove == null) {
            System.out.println("Invalid move.");
            Reversi.object[4] = "player.Human Gave Up";
            playerSecond.setWin(true);
            isEnd = true;
        }
        return isEnd;
    }

    public boolean checkIsPlaceToChess(Player player) {
        int scoreAvailable = player.isPlaceToChess(this)[2];
        return scoreAvailable>0;
    }

    //检测该点是否可以翻棋子 返回结束点的坐标以及该点的得分
    public LinkedHashMap<Enum.Direction, int[]> checkPlace(Chessman chessman) {
        ArrayList<Enum.Direction> directions = chessman.isChessmanAround(this);
        //其余位置是否有棋子，没有的话返回null
        if (directions != null) {
            //返回的是方向,int[]里分别为结束的位置坐标x,y以及当时的得分
            LinkedHashMap<Enum.Direction, int[]> score = chessman.score(directions, this);
            //是否有分可得
            if (Human.getTail(score) != null) {
                int totalScore = Human.getTail(score).getValue()[2];
                if (totalScore > 0) {
                    //设置
                    return score;
                }
            }

        }
        return null;
    }


    //棋盘满
    public boolean isFull(Player player, Player playerSecond) {
        boolean isEnd = false;
        if (this.getNumOfTurn() == getSize() * getSize()) {
            isEnd = true;
            int pointSpread = player.getScore() - playerSecond.getScore();
            if (pointSpread > 0) {
                player.setWin(true);
//                System.out.println(player.getClass().toString() + " player wins!");
            } else if (pointSpread == 0) {
                playerSecond.setWin(true);
                player.setWin(true);
//                System.out.println("Drawn!");
            } else {
                playerSecond.setWin(true);
//                System.out.println(playerSecond.getClass().toString() + " player wins!");
            }
        }
        return isEnd;
    }

    //一方棋子已经被吃光。
    public boolean isBeated(Player player, Player playerSecond) {
        boolean isEnd = false;
        if (player.getScore() == 0) {
            isEnd = true;
            playerSecond.setWin(true);
//            System.out.println(playerSecond.getClass().toString() + " player wins!");
        } else if (playerSecond.getScore() == 0) {
            isEnd = true;
            player.setWin(true);
//            System.out.println(player.getClass().toString() + " player wins!");
        }
        return isEnd;
    }

    public void setWinner(Player player,Player player1){
        if (player.getScore()>player1.getScore()) {
            player.setWin(true);
        } else if (player.getScore()<player1.getScore()){
            player1.setWin(true);
        }else {
            player1.setWin(true);
            player.setWin(true);
        }
    }


    public int getNumOfTurn() {
        return numOfTurn;
    }

    public void setNumOfTurn(int numOfTurn) {
        this.numOfTurn = numOfTurn;
    }

    public boolean isCoincided(Chessman chessman) {
        return this.chessBoard[chessman.getPoint()[0]][chessman.getPoint()[1]] != Enum.Colour.NULLCHESSMAN;
    }

    public boolean isCoincided(int[] point) {
        return this.chessBoard[point[0]][point[1]] != Enum.Colour.NULLCHESSMAN;
    }

    public boolean isCoincided(int x, int y) {
        return this.chessBoard[x][y] != Enum.Colour.NULLCHESSMAN;
    }

    public boolean isAnotherColorChessman(int[] point, Enum.Colour colour) {
        return this.chessBoard[point[0]][point[1]] != Enum.Colour.NULLCHESSMAN && this.chessBoard[point[0]][point[1]] != colour;
    }

    public Enum.Colour[][] getChessBoard() {
        return chessBoard;
    }

    public static void setSize(int size) {
        Chessboard.size = size;
    }

    public static int getSize() {
        return size;
    }


}
