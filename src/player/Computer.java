package player;

import game.*;

import java.util.LinkedHashMap;

public class Computer extends Player {


    public int[] chessMove(Chessboard chessboard, Player competitor) {
        int[] nextMove = {-1, -1};
        int[] nextMoveAndScore = this.isPlaceToChess(chessboard);

        nextMove[0] = nextMoveAndScore[0];
        nextMove[1] = nextMoveAndScore[1];
        int maxScore = nextMoveAndScore[2];

        if (maxScore > 0) {
            //找出最大的棋子后
            Chessman chessman = new Chessman(nextMove, this.getColour());
            LinkedHashMap<Enum.Direction, int[]> stopMove = chessboard.checkPlace(chessman);

            this.setScore(maxScore + getScore() + 1);
            competitor.setScore(competitor.getScore() - maxScore);

            printScoreComputer(competitor);

            chessboard.removeChessmen(stopMove, nextMove, this.getColour());
            return nextMove;
        } else {
            System.out.println(this.getColour() + " player has no valid move");
            printScoreComputer(competitor);
            return null;
        }
    }

    private void printScoreComputer(Player competitor) {
        System.out.println("电脑的得分现在为" + this.getScore());
        System.out.println("您的得分现在为" + competitor.getScore());
    }
}

